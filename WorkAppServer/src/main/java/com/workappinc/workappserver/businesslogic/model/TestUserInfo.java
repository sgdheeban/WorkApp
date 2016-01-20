package com.workappinc.workappserver.businesslogic.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Test POJO for serialize/de-serialize
 * 
 * @author dhgovindaraj
 *
 */
public class TestUserInfo
{
	private final String firstName;
	private final String lastName;

	public TestUserInfo(String first, String last)
	{
		this.firstName = first;
		this.lastName = last;
	}

	@JsonProperty
	public String getFirstName()
	{
		return firstName;
	}

	@JsonProperty
	public String getLastName()
	{
		return lastName;
	}
}
