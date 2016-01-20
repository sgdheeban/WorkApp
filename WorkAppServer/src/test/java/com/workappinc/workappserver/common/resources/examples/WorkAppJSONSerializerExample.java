package com.workappinc.workappserver.common.resources.examples;

import java.io.IOException;

import com.workappinc.workappserver.common.exception.JSONSerializationException;
import com.workappinc.workappserver.common.logging.IApplicationLogger;
import com.workappinc.workappserver.common.logging.WorkAppLogger;
import com.workappinc.workappserver.common.resources.implementation.WorkAppJSONSerializer;
import com.workappinc.workappserver.dataaccess.resources.examples.testtable.User;

/**
 * Sample JSON Serializer Example
 * 
 * @author dhgovindaraj
 *
 */
public class WorkAppJSONSerializerExample
{

	/**
	 * Serialize Tests
	 * 
	 * @throws JSONSerializationException
	 */
	public static void serializeTests() throws JSONSerializationException
	{
		IApplicationLogger logger = WorkAppLogger.getInstance(null);
		User user = new User();
		user.setAge(11);
		user.setName("sgd");
		System.out.println(WorkAppJSONSerializer.serialize(user, logger));
		System.out.println(WorkAppJSONSerializer.serializeInPrettyPrint(user, logger));
		WorkAppJSONSerializer.serializeToFile(user, "user-output.json", logger);
	}

	/**
	 * DeSerialize Tests
	 * 
	 * @throws JSONSerializationException
	 */
	public static void deSerializeTests() throws JSONSerializationException
	{
		IApplicationLogger logger = WorkAppLogger.getInstance(null);
		User user = new User();
		User user1 = (User) WorkAppJSONSerializer.deSerialize(user, "{\"name\":\"sgd\",\"age\":11}", User.class, logger);
		System.out.println(user1.getAge() + " :: " + user1.getName());

		User user2 = (User) WorkAppJSONSerializer.deSerialize(user, "{\"name\":\"sgd-21\",\"age\":121}", User.class, logger);
		System.out.println(user2.getAge() + " :: " + user2.getName());

	}

	public static void main(String args[]) throws IOException, JSONSerializationException
	{
		serializeTests();
		deSerializeTests();
	}
}
