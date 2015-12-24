package com.workappinc.workappserver.dataaccess.entry;

/**
 * PerfTableEntry is a sample database entry - Maps fields of User table <String
 * name, Int Age> are the fields in this test table Also provides Getter and
 * Setter Fields
 * 
 * @author dhgovindaraj
 *
 */
public class PerfTableEntry implements IEntry
{
	private String name;
	private int age;

	public PerfTableEntry()
	{

	}

	public PerfTableEntry(String name, int age)
	{
		super();
		this.name = name;
		this.age = age;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public int getAge()
	{
		return age;
	}

	public void setAge(int age)
	{
		this.age = age;
	}

}
