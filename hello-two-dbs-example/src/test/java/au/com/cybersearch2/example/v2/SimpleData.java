package au.com.cybersearch2.example.v2;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * A simple demonstration object we are creating and persisting to the database.
 */
@Entity(name="Simple")
public class SimpleData 
{
	/** This id is generated by the database and set on the object when it is passed to the create method */
    @Id @GeneratedValue
	int id;

	@Column
    String name;
    
	@Column
	long millis;
	
	@Column
	boolean even;

	@Column 
	String quote;
	
	SimpleData() 
	{
		// needed by ormlite
	}

	public SimpleData(String name, long millis, String quote) 
	{
		this.name = name;
		this.millis = millis;
		this.even = ((this.millis % 2) == 0);
		this.quote = quote;
	}
	
	public long getMillis() 
	{
		return millis;
	}

	public String getName()
	{
		return name;
	}
	
	public void setQuote(String value)
	{
		quote = value;
	}
	
	@Override
	public String toString() 
	{
		StringBuilder sb = new StringBuilder(name);
		sb.append(": id=").append(id);
		sb.append(", ").append("millis=").append(getMillis());
		sb.append(", ").append("even=").append(even);
		sb.append(": \"").append(quote).append("\"");
		return sb.toString();
	}
}