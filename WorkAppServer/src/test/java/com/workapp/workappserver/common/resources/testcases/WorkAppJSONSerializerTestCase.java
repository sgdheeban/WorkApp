package com.workapp.workappserver.common.resources.testcases;

import static org.junit.Assert.*;

import org.junit.Test;

import com.workapp.workappserver.common.exception.JSONSerializationException;
import com.workapp.workappserver.common.exception.SystemException;
import com.workapp.workappserver.common.logging.IApplicationLogger;
import com.workapp.workappserver.common.logging.AppLogger;
import com.workapp.workappserver.common.resources.implementation.JSONSerializer;
import com.workapp.workappserver.dataaccess.resources.examples.testtable.User;

/**
 * JSONSerializer JUnit Test Cases
 * 
 * @author dhgovindaraj
 *
 */
public class WorkAppJSONSerializerTestCase
{
	/**
	 * Serialize Tests
	 * 
	 * @throws JSONSerializationException
	 */
	@Test
	public void serializeTests() throws JSONSerializationException
	{
		IApplicationLogger logger;
		try
		{
			logger = new AppLogger(null);
			User user = new User();
			user.setAge(11);
			user.setName("sgd");
			assertEquals(JSONSerializer.serialize(user, logger), "{\"name\":\"sgd\",\"age\":11}");
		}
		catch (SystemException ex)
		{
			ex.printStackTrace();
			assertTrue(false);
		}

	}

	/**
	 * DeSerialize Tests
	 * 
	 * @throws JSONSerializationException
	 */
	@Test
	public void deSerializeTests() throws JSONSerializationException
	{
		IApplicationLogger logger;
		try
		{
			logger = new AppLogger(null);
			User user = new User();
			User user1 = (User) JSONSerializer.deSerialize(user, "{\"name\":\"sgd\",\"age\":11}", User.class, logger);
			User user2 = (User) JSONSerializer.deSerialize(user, "{\"name\":\"sgd-21\",\"age\":121}", User.class, logger);

			assertEquals(user1.getAge(), 11);
			assertEquals(user1.getName(), "sgd");
			assertEquals(user2.getAge(), 121);
			assertEquals(user2.getName(), "sgd-21");
		}
		catch (SystemException ex)
		{
			ex.printStackTrace();
			assertTrue(false);
		}

	}

}
