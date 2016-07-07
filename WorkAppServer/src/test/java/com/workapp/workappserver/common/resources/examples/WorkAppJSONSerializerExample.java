package com.workapp.workappserver.common.resources.examples;

import java.io.IOException;

import com.workapp.workappserver.common.exception.JSONSerializationException;
import com.workapp.workappserver.common.exception.SystemException;
import com.workapp.workappserver.common.logging.IApplicationLogger;
import com.workapp.workappserver.common.logging.AppLogger;
import com.workapp.workappserver.common.resources.implementation.JSONSerializer;
import com.workapp.workappserver.dataaccess.resources.examples.testtable.User;


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
	 * @throws SystemException
	 */
	public static void serializeTests(IApplicationLogger logger) throws JSONSerializationException
	{
		User user = new User();
		user.setAge(11);
		user.setName("sgd");
		System.out.println(JSONSerializer.serialize(user, logger));
		System.out.println(JSONSerializer.serializeInPrettyPrint(user, logger));
		JSONSerializer.serializeToFile(user, "user-output.json", logger);
	}

	/**
	 * DeSerialize Tests
	 * 
	 * @throws JSONSerializationException
	 */
	public static void deSerializeTests(IApplicationLogger logger) throws JSONSerializationException
	{
		User user = new User();
		User user1 = (User) JSONSerializer.deSerialize(user, "{\"name\":\"sgd\",\"age\":11}", User.class, logger);
		System.out.println(user1.getAge() + " :: " + user1.getName());

		User user2 = (User) JSONSerializer.deSerialize(user, "{\"name\":\"sgd-21\",\"age\":121}", User.class, logger);
		System.out.println(user2.getAge() + " :: " + user2.getName());

	}

	public static void main(String args[]) throws IOException, JSONSerializationException, SystemException
	{
		IApplicationLogger logger = new AppLogger(null);
		serializeTests(logger);
		deSerializeTests(logger);
	}
}
