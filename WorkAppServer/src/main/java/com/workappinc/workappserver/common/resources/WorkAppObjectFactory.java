package com.workappinc.workappserver.common.resources;

import com.workappinc.workappserver.common.exception.SingletonInitException;
import com.workappinc.workappserver.common.logging.IApplicationLogger;

/**
 * WorkAppObjectFactory is class with static methods to create objects systematically, every new object type should be registered manually in this factory class
 * Registration means basically, added a API support to call these call constructors of the specified object 
 * It's the programmers responsibility to use this systematically, could potentially lead to bloat in this file, as the available objects proliferate
 * Singleton objects are not considered by this factory, when you add a singleton, register it with singletonRegistry
 * Add reference in the factory method as classes are created
 * @author dhgovindaraj
 *
 */
public class WorkAppObjectFactory
{
	private static IApplicationLogger mLogger = null;
	private static WorkAppObjectFactory mInstance = null;

	private WorkAppObjectFactory(IApplicationLogger logger)
	{
		mLogger = logger;
	}

	/**
	 * Get Instance method is used to get a singleton object
	 * 
	 * @return
	 */
	public static WorkAppObjectFactory getInstance(IApplicationLogger logger)
	{
		try
		{
			if (mInstance == null)
			{
				synchronized (WorkAppUtility.class)
				{
					if (mInstance == null)
					{
						mInstance = new WorkAppObjectFactory(logger);
					}
				}
			}
			return mInstance;
		}
		catch (Exception ex)
		{
			SingletonInitException singletonEx = new SingletonInitException(
					"Error during Singleton Object Creation for WorkAppObjectFactory Class", ex);
			mLogger.LogException(singletonEx, WorkAppUtility.class);
			throw singletonEx;
		}
	}

	
	//TODO Add API Suppor to create objects systematically
	//Typically, it would be a public static ReturnType Foo(param...)
}
