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
package au.com.cybersearch2.classydb;

import au.com.cybersearch2.classytask.WorkStatus;

import com.j256.ormlite.support.ConnectionSource;

/**
 * DatabaseAdmin
 * @author Andrew Bowley
 * 05/07/2014
 */
public interface DatabaseAdmin
{
    /** Wait one minute maximum for tasks to complete */
    public final static int MAX_TASK_WAIT_SECS = 60;
    /** Property key for create database */
    public final static String SCHEMA_FILENAME = "schema-filename";
    /** Property key for drop database */
    public final static String DROP_SCHEMA_FILENAME = "drop-schema-filename";
    /** Property key for populate database */
    public final static String DATA_FILENAME = "data-filename";
    /** Property key for database version */
    public final static String DATABASE_VERSION = "database_version";
    /** Property key for database name */
    public final static String DATABASE_NAME = "database-name";

    /**
     * To support android.database.sqlite.SQLiteOpenHelper
     * Called when the database is created for the first time. This is where the
     * creation of tables and the initial population of the tables should happen.
     *
     * @return ConnectionSource used for implementation to allow post-creation operations.
     */
    ConnectionSource onCreate();
    
    /**
     * See android.database.sqlite.SQLiteOpenHelper
     * Called when the database needs to be upgraded. The implementation
     * should use this method to drop tables, add tables, or do anything else it
     * needs to upgrade to the new schema version.
     *
     * <p>
     * The SQLite ALTER TABLE documentation can be found
     * <a href="http://sqlite.org/lang_altertable.html">here</a>. If you add new columns
     * you can use ALTER TABLE to insert them into a live table. If you rename or remove columns
     * you can use ALTER TABLE to rename the old table, then create the new table and then
     * populate the new table with the contents of the old table.
     * </p><p>
     * This method executes within a transaction.  If an exception is thrown, all changes
     * will automatically be rolled back.
     * </p>
     *
     * @param oldVersion The old database version.
     * @param newVersion The new database version.
     * @return ConnectionSource used for implementation to allow post-creation operations.
     */
    ConnectionSource onUpgrade(int oldVersion, int newVersion);
    
    /**
     * Wait for currently executing persistence unit task to complete
     * @return WorkStatus Status value will be FINISHED or FAILED
     */
    WorkStatus waitForTask();
    
    /**
     * Wait up to specified number of seconds for currently executing persistence unit task to complete
     * @param timeoutSecs int
     * @return WorkStatus Status value will be FINISHED or FAILED
     */
    WorkStatus waitForTask(int timeoutSecs);
}