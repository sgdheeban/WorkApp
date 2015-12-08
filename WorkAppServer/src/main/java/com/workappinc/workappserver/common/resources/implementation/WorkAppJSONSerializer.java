package com.workappinc.workappserver.common.resources.implementation;

import com.workappinc.workappserver.common.exception.SingletonInitException;
import com.workappinc.workappserver.common.logging.IApplicationLogger;
import com.workappinc.workappserver.common.resources.interfaces.ISerializer;

/**
 * WorkAppJSONSerializer is a singleton implementation of ISerializer interface
 * for serializing and de-serializing JSON and POJO
 * 
 * @author dhgovindaraj
 *
 */
public class WorkAppJSONSerializer implements ISerializer
{
	private static IApplicationLogger mLogger = null;
	private static ISerializer mInstance = null;

	private WorkAppJSONSerializer(IApplicationLogger logger)
	{
		mLogger = logger;
	}

	/**
	 * getInstance method is used to get a singleton object
	 * 
	 * @return
	 */
	public static ISerializer getInstance(IApplicationLogger logger)
	{
		try
		{
			if (mInstance == null)
			{
				synchronized (WorkAppJSONSerializer.class)
				{
					if (mInstance == null)
					{
						mInstance = new WorkAppJSONSerializer(logger);
					}
				}
			}
			return mInstance;
		}
		catch (Exception ex)
		{
			throw new SingletonInitException("Error during Singleton Object Creation for WorkAppJSONSerializer Class",
					ex);
		}
	}

	@Override
	public Object serialize(Object deserializedObject)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object deSerialize(Object serializedObject)
	{
		// TODO Auto-generated method stub
		return null;
	}

}
