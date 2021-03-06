package au.com.cybersearch2.example;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

/**
 * A user object with a name.
 */
@Entity(name="tableUser")
public class User 
{
	/** We use this field-name so we can query for users with a certain id */
	public final static String ID_FIELD_NAME = "id";

	/** This id is generated by the database and set on the object when it is passed to the create method */
    @Id @GeneratedValue
	int id;

	@Column
	String name;

	/** Posts belonging to this user. The association is bi-directional, but the user "owns" it */
    @ManyToMany(fetch=FetchType.EAGER)
    Collection<Post> posts;
    
    /**
     * User default constructor for ormlite
     */
	User() 
	{
	}

	/**
	 * Construct User object
	 * @param name String
	 */
	public User(String name) 
	{
		this.name = name;
	}
}
