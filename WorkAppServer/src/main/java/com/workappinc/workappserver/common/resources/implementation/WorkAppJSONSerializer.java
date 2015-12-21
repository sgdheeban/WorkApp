package com.workappinc.workappserver.common.resources.implementation;

import com.workappinc.workappserver.common.logging.IApplicationLogger;
import com.workappinc.workappserver.common.resources.interfaces.ISerializer;

/**
 * WorkAppJSONSerializer is an implementation of ISerializer interface for
 * serializing and de-serializing JSON and POJO
 * 
 * @author dhgovindaraj
 *
 */
public class WorkAppJSONSerializer implements ISerializer
{
	/**
	 * Serializes POJO to JSON String
	 */
	public synchronized static String serialize(Object pojo, IApplicationLogger logger)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Serializes POJO to JSON File
	 */
	public synchronized static boolean serializeToFile(Object pojo, String jsonFilePath, IApplicationLogger logger)
	{
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Deserializes JSON String to POJO
	 */
	public synchronized static Object deSerialize(Object pojo, String json, IApplicationLogger logger)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Deserializes JSON File to POJO
	 */
	public synchronized static Object deSerializeFromFile(Object pojo, String jsonFilePath, IApplicationLogger logger)
	{
		// TODO Auto-generated method stub
		return null;
	}

}
