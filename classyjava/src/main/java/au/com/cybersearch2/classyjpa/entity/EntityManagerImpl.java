/**
    Copyright (C) 2014  www.cybersearch2.com.au

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/> */
package au.com.cybersearch2.classyjpa.entity;

import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.persistence.EntityTransaction;
import javax.persistence.FlushModeType;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import au.com.cybersearch2.classyjpa.EntityManagerLite;
import au.com.cybersearch2.classyjpa.persist.PersistenceConfig;
import au.com.cybersearch2.classyjpa.query.NamedDaoQuery;
import au.com.cybersearch2.classyjpa.query.NamedSqlQuery;
import au.com.cybersearch2.classyjpa.transaction.EntityTransactionImpl;
import au.com.cybersearch2.classyjpa.transaction.SetRollbackTransaction;
import au.com.cybersearch2.classyjpa.transaction.TransactionCallable;
import au.com.cybersearch2.classyjpa.transaction.UserTransactionSupport;

import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.support.DatabaseConnection;

/**
 * EntityManagerImpl
 * Implementation of EntityManager interface
 * Transaction scoped. Entity objects are managed only while a transaction is active.
 * Excect for when the EntityManger has been closed, a new transaction will begin whenever an object needs to be managed and the transaction is not active. 
 * @author Andrew Bowley
 * 01/05/2014
 */
public class EntityManagerImpl implements EntityManagerLite, UserTransactionSupport
{
    class OnTransactionPreCommitCallback implements TransactionCallable
    {
        @Override
        public Boolean call(DatabaseConnection databaseConnection) throws Exception 
        {
            updateAllManagedObjects();
            return true; // Commit 
        }        
    }

    /** Flag set when close() is called */
    protected volatile boolean isOpen;
    /** Enclosing transaction object */ 
    protected EntityTransaction entityTransaction;
    /** Connection Source to use for all database connections */
    protected final ConnectionSource connectionSource;
    /** PersistenceUnitAdmin Unit configuration */
    protected final PersistenceConfig persistenceConfig;
    /** Callback to complete transaction management prior to commit */
    protected final OnTransactionPreCommitCallback onTransactionPreCommitCallback;
    /** Flag for user transaction mode. If true, getTransaction() returns rollbackonly transaction otherwise it returns the actual entityTransaction */
    protected boolean isUserTransaction; 
    /** Delegate management of entity objects */ 
    protected ObjectMonitor objectMonitor;
 
    /**
     * Create ClassyEntityManager object
     * @param connectionSource Source of all database connections
     * @param persistenceConfig PersistenceUnitAdmin Unit configuration
     */
    public EntityManagerImpl(
            ConnectionSource connectionSource, 
            PersistenceConfig persistenceConfig)
    {
        this.connectionSource = connectionSource;
        this.persistenceConfig = persistenceConfig;
        onTransactionPreCommitCallback = new OnTransactionPreCommitCallback();
        entityTransaction = new EntityTransactionImpl(connectionSource, onTransactionPreCommitCallback);
        objectMonitor = new ObjectMonitor();
        isOpen = true;
    }

    /**
     * setUserTransaction
     * Determines if getTransaction() returns null (false) or entityTransaction (true)
     * @param value boolean
     */
    @Override
    public void setUserTransaction(boolean value)
    {
        isUserTransaction = value;
    }
    
   /**
     * Make an entity instance managed and persistent.
     * @param entity The entity instance
     * @throws IllegalArgumentException if not an entity
     * @throws IllegalStateException if this EntityManager has been closed.
     */
    @Override
    public void persist(Object entity) 
    {
        if (entity == null)
            throw new IllegalArgumentException("Parameter \"entity\" is null");
        checkEntityManagerClosed("persist()");
        OrmDaoHelper<?,?> ormDaoHelper = getOrmDaoHelperForClass(entity.getClass());
        Object primaryKey = ormDaoHelper.extractId(entity);
        Object alreadyManaged = objectMonitor.startManagingEntity(entity, primaryKey, PersistOp.persist);
        if ((alreadyManaged != null) || 
            ((primaryKey != null) && ormDaoHelper.entityExists(entity)))
            throw new EntityExistsException("Entity of class " + entity.getClass() + ", primary key " + primaryKey.toString() + " already exists");
        if (!entityTransaction.isActive())
            entityTransaction.begin(); // Transaction commit/rollback triggers refresh
        if (ormDaoHelper.create(entity) == 0)
            throw new PersistenceException("persist operation returned result count 0");
        // DAO may update primary key value on entity during create operation
        if (!objectMonitor.monitorNewEntity(entity, primaryKey, ormDaoHelper.extractId(entity)))
        {
            // No Primary key or matches one belonging to existing managed entity
            entityTransaction.rollback();
            throw new PersistenceException("Error persisting entity class " + entity.getClass().getName() + ": No Primary key or matches one belonging to managed entity");
        }
    }
    
    /**
     * Merge the state of the given entity into the
     * current persistence context.
     * @param entity The entity instance
     * @return The instance that the state was merged to
     * @throws IllegalArgumentException if instance is not an entity or is a removed entity
     * @throws IllegalStateException if this EntityManager has been closed.
     */
    @Override
    public <T> T merge(T entity) 
    {
        checkEntityManagerClosed("merge()");
        OrmDaoHelper<?,?> ormDaoHelper = getOrmDaoHelperForClass(entity.getClass());
        Object primaryKey = ormDaoHelper.extractId(entity);
        T managed = objectMonitor.startManagingEntity(entity, primaryKey, PersistOp.merge);
        if (!entityTransaction.isActive())
            entityTransaction.begin(); // Transaction commit triggers update and refresh
        return (T) managed;
    }

    /** 
     * Refresh the state of the instance from the database,
     * overwriting changes made to the entity, if any.
     * @param entity The entity instance
     * @throws IllegalArgumentException if not an entity or entity is not managed
     * @throws IllegalStateException if this EntityManager has been closed.
     */
    @Override
    public void refresh(Object entity) 
    {
        checkEntityManagerClosed("refresh()");
        OrmDaoHelper<?,?> ormDaoHelper = getOrmDaoHelperForClass(entity.getClass());
        Object primaryKey = ormDaoHelper.extractId(entity);
        // For refresh, the returned object is the entity, not the former managed object
        Object managed = objectMonitor.startManagingEntity(entity, primaryKey, PersistOp.refresh);
        if (managed == null)
            throw new IllegalArgumentException("Entity of class " + entity.getClass() + ", primary key " + primaryKey.toString() + " is not managed");
        if (!entityTransaction.isActive())
            entityTransaction.begin(); // Transaction commit/rollback triggers refresh
        if (ormDaoHelper.refresh(managed) == 0)
            throw new PersistenceException("refresh operation returned result count 0");
    }


    /**
     * Remove the entity instance.
     * @param entity The entity instance
     * @throws IllegalArgumentException if not an entity or if a detached entity
     * @throws IllegalStateException if this EntityManager has been closed.
     */
    @Override
    public void remove(Object entity) 
    {
        checkEntityManagerClosed("remove()");
        OrmDaoHelper<?,?> ormDaoHelper = getOrmDaoHelperForClass(entity.getClass());
        Object primaryKey = ormDaoHelper.extractId(entity);
        objectMonitor.markForRemoval(entity.getClass(), primaryKey);
        if (!entityTransaction.isActive())
            entityTransaction.begin();
        if (ormDaoHelper.delete(entity) == 0)
            throw new PersistenceException("remove operation returned result count 0");
    }

    /**
     * Find by primary key.
     * Does not require transaction.
     * @param entityClass The class of the entity
     * @param primaryKey The primary key as Object
     * @return the found entity instance or null if the entity does not exist
     * @throws IllegalStateException if this EntityManager has been closed.
     * @throws IllegalArgumentException if the first argument does
     *    not denote an entity type or the second
     *    argument is not a valid type for that
     *    entity's primary key
     */
    @Override
    public <T> T find(Class<T> entityClass, Object primaryKey) 
    {
        checkEntityManagerClosed("find()");
        @SuppressWarnings("unchecked")
        OrmDaoHelper<T,?> ormDaoHelper = (OrmDaoHelper<T, ?>) getOrmDaoHelperForClass(entityClass);
        return ormDaoHelper.queryForId(primaryKey);
    }

    /**
     * Javax PersistenceUnitAdmin: "Get an instance, whose state may be lazily fetched".
     * This implementation is just an alias for find()
     * If the requested instance does not exist in the database,
     * throws {@link EntityNotFoundException} when the instance state is
     * first accessed.
     *
     * The application should not expect that the instance state will
     * be available upon detachment, unless it was accessed by the
     * application while the entity manager was open.
     * @param entityClass The class of the entity
     * @param primaryKey The primary key as Object
     * @return the found entity instance
     * @throws IllegalArgumentException if the first argument does
     *    not denote an entity type or the second
     *    argument is not a valid type for that
     *    entity's primary key
     * @throws EntityNotFoundException if the entity state
     *    cannot be accessed
     * @throws IllegalStateException if this EntityManager has been closed.
     */
    @Override
    public <T> T getReference(Class<T> entityClass, Object primaryKey) 
    {
        checkEntityManagerClosed("getReference()");
        T entity = find(entityClass, primaryKey);
        if (entity == null)
            throw new EntityNotFoundException("Not found: class " + entityClass.getName() + ", primary key " + primaryKey.toString());
        return entity;
    }

    /**
     * Synchronize the persistence context to the underlying database.
     * @throws PersistenceException if the flush fails
     * @throws IllegalStateException if this EntityManager has been closed.
     */
    @Override
    public void flush() 
    {
        checkEntityManagerClosed("flush()");
        if (entityTransaction.isActive())
            entityTransaction.commit();
        entityTransaction.begin();
    }

    /**
    * Set the flush mode that applies to all objects contained
    * in the persistence context.
    * @param flushMode  The flush mode
    * @throws IllegalStateException if this EntityManager has been closed.
    */
    @Override
    public void setFlushMode(FlushModeType flushMode) 
    {
        checkEntityManagerClosed("setFlushMode()");
        if (FlushModeType.AUTO.equals(flushMode))
            throw new UnsupportedOperationException("FlushModeType.AUTO not supported");
    }

    /**
    * Get the flush mode that applies to all objects contained
    * in the persistence context.
    * @return flush mode - always FlushModeType.COMMIT
     * @throws IllegalStateException if this EntityManager has been closed.
    */
    @Override
    public FlushModeType getFlushMode() 
    {
        checkEntityManagerClosed("getFlushMode()");
        return FlushModeType.COMMIT;
    }

    /**
    * NOT SUPPORTED. 
    * Set the lock mode for an entity object contained in the persistence context.
    * @param entity
    * @param lockMode
    * @throws UnsupportedOperationException
    */
    @Override
    public void lock(Object entity, LockModeType lockMode) 
    {
       throw new UnsupportedOperationException("lock() not available");
    }

    /**
    * Clear the persistence context, causing all managed
    * entities to become detached. Changes made to entities that
    * have not been flushed to the database will not be persisted.
     * @throws IllegalStateException if this EntityManager has been closed.
    */
    @Override
    public void clear() 
    {
        checkEntityManagerClosed("clear()");
        if (entityTransaction.isActive())
            entityTransaction.rollback();
        objectMonitor.release();
        entityTransaction.begin();
    }

    /**
     * Check if the instance belongs to the current persistence
     * context.
     * @param entity The entity instance
     * @return <code>true</code> if the instance belongs to 
     * the current persistence context.
     * @throws IllegalArgumentException if not an entity
     * @throws IllegalStateException if this EntityManager has been closed.
     */
    @Override
    public boolean contains(Object entity) 
    {
        checkEntityManagerClosed("contains()");
        OrmDaoHelper<?,?> ormDaoHelper = getOrmDaoHelperForClass(entity.getClass());
        Object primaryKey = ormDaoHelper.extractId(entity);
        Object alreadyManaged = objectMonitor.startManagingEntity(entity, primaryKey, PersistOp.contains);
        return (alreadyManaged != null) || 
                ((primaryKey != null) && ormDaoHelper.entityExists(entity));
    }


    /**
     * Create an instance of Query for executing a
     * named query (executed using OrmLite QueryBuilder or in native SQL).
     * @param name The name of a query 
     * @return the new query instance
     * @throws IllegalArgumentException if a query has not been
     * defined with the given name
     * @throws IllegalStateException if this EntityManager has been closed.
     */
    @Override
    public Query createNamedQuery(String name) 
    {
        checkEntityManagerClosed("createNamedQuery()");
        NamedDaoQuery namedDaoQuery = persistenceConfig.getNamedQueryMap().get(name);
        if (namedDaoQuery == null)
        {
            NamedSqlQuery namedSqlQuery = persistenceConfig.getNativeQueryMap().get(name);
            if (namedSqlQuery == null)
                throw new IllegalArgumentException("Named query '" + name + "' not found");
            return namedSqlQuery.createQuery();
        }
        PersistenceDao<?, ?> dao = getOrmDaoHelperFactoryForClass(namedDaoQuery.getEntityClass()).getDao(connectionSource);
        return namedDaoQuery.createQuery(dao);
    }

    /**
     * NOT SUPPORTED
     * Indicate to the EntityManager that a JTA transaction is
     * active. This method should be called on a JTA application
     * managed EntityManager that was created outside the scope
     * of the active transaction to associate it with the current
     * JTA transaction.
     * @throws UnsupportedOperationException
     */
    @Override
    public void joinTransaction() 
    {
        throw new UnsupportedOperationException("joinTransaction() not available");
    }

    /**
    * Returns EntityManagerDelegate object which provides access to OrmLite DAOs.
    * Return type of Object complies with PersistenceUnitAdmin API. Use cast to access the returned object as EntityManagerDelegate type.
    * @return Object 
    * @throws IllegalStateException if this EntityManager has been closed.
    */
    @Override
    public Object getDelegate() 
    {
        checkEntityManagerClosed("getDelegate()");
        return new EntityManagerDelegate(connectionSource, entityTransaction, persistenceConfig.getHelperFactoryMap());
    }

    /**
	 * DO NOT CALL close() as the container will do this automatically.
     * Close an application-managed EntityManager.
     * After the close method has been invoked, all methods
     * on the EntityManager instance and any Query objects obtained
     * from it will throw the IllegalStateException except
     * for getTransaction and isOpen (which will return false).
     * If this method is called when the EntityManager is
     * associated with an active transaction, the persistence
     * context remains managed until the transaction completes.
     * @throws IllegalStateException if the EntityManager
     * is container-managed or has been already closed.
     */
    @Override
    public void close() 
    {   
        checkEntityManagerClosed("close()");
        isOpen = false;
        if (entityTransaction.isActive())
             entityTransaction.commit();
        objectMonitor.release();
        // Do not close connection. This is managed by the EntityManagerFactory
    }

	/**
	 * Determine whether the entity manager is open.
	 * 
	 * @return true until the entity manager has been closed
	 */
    @Override
    public boolean isOpen() 
    {
        return isOpen;
    }

    /**
     * Returns the resource-level transaction object, if User Transactions selected,  otherwise proxy object returned for which only setRollbackOnly() is active.
     * In User Transaction mode, the EntityTransaction instance may be used serially to begin and commit multiple transactions.
     * @return EntityTransaction instance if in User Transaction mode, otherwise null
     */
    @Override
    public EntityTransaction getTransaction() 
    {
        if (!isUserTransaction)
            return new SetRollbackTransaction(entityTransaction);
        return entityTransaction;
    }

    /**
     * Returns ORMLite DAO helper for specified class 
     * @param clazz Entity class
     * @return OrmDaoHelper
     * @throws IllegalStateException if class is unknown to the current PersistenceUnitAdmin Unit.
     */
    private OrmDaoHelper<?,?> getOrmDaoHelperForClass(Class<?> clazz)
    {
        return getOrmDaoHelperFactoryForClass(clazz).getOrmDaoHelper(connectionSource);
    }

    /**
     * Returns ORMLite DAO helper for specified class 
     * @param clazz Entity class
     * @return OrmDaoHelper
     * @throws IllegalStateException if class is unknown to the current PersistenceUnitAdmin Unit.
     */
    private OrmDaoHelperFactory<?,?> getOrmDaoHelperFactoryForClass(Class<?> clazz)
    {
        OrmDaoHelperFactory<?,?> ormDaoHelperFactory = persistenceConfig.getHelperFactoryMap().get(clazz.getName());
        if (ormDaoHelperFactory == null)
            throw new IllegalArgumentException("Class " + clazz.getName() + " not an entity in this persistence context");
        return ormDaoHelperFactory;
    }

    /**
     * Perform outstanding updates on all managed objects
     */
    private void updateAllManagedObjects()
    {
        List<Object> updateList = objectMonitor.getObjectsToUpdate();
        for (Object entity: updateList)
        {
            OrmDaoHelper<?,?> ormDaoHelper = getOrmDaoHelperForClass(entity.getClass());
            if (ormDaoHelper.update(entity) == 0)
                throw new PersistenceException("update operation returned result count 0");
        }
    }

    /**
     * Confirm this Entity Manager is open
     * @param method Name of method being invoked
     * @throws IllegalStateException if this Entity Manager is closed.
     */
    private void checkEntityManagerClosed(String method)
    {
        if (!isOpen)
            throw new IllegalStateException(method + " called after EntityManager has been closed");
    }
    
}
