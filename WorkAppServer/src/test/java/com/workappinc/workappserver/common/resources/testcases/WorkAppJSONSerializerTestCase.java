package com.workappinc.workappserver.common.resources.testcases;

import static org.junit.Assert.*;

import org.junit.Test;

import com.workappinc.workappserver.common.exception.JSONSerializationException;
import com.workappinc.workappserver.common.logging.IApplicationLogger;
import com.workappinc.workappserver.common.logging.WorkAppLogger;
import com.workappinc.workappserver.common.resources.implementation.WorkAppJSONSerializer;
import com.workappinc.workappserver.dataaccess.resources.examples.testtable.User;

/**
 * WorkAppJSONSerializer JUnit Test Cases
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
		IApplicationLogger logger = WorkAppLogger.getInstance(null);
		User user = new User();
		user.setAge(11);
		user.setName("sgd");
		assertEquals(WorkAppJSONSerializer.serialize(user, logger), "{\"name\":\"sgd\",\"age\":11}");
	}

	/**
	 * DeSerialize Tests
	 * 
	 * @throws JSONSerializationException
	 */
	@Test
	public void deSerializeTests() throws JSONSerializationException
	{
		IApplicationLogger logger = WorkAppLogger.getInstance(null);
		User user = new User();
		User user1 = (User) WorkAppJSONSerializer.deSerialize(user, "{\"name\":\"sgd\",\"age\":11}", User.class, logger);
		User user2 = (User) WorkAppJSONSerializer.deSerialize(user, "{\"name\":\"sgd-21\",\"age\":121}", User.class, logger);

		assertEquals(user1.getAge(), 11);
		assertEquals(user1.getName(), "sgd");
		assertEquals(user2.getAge(), 121);
		assertEquals(user2.getName(), "sgd-21");

	}

}
