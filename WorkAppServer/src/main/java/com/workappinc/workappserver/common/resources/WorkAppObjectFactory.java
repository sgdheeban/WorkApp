package com.workappinc.workappserver.common.resources;

import java.util.HashMap;

import com.workappinc.workappserver.common.exception.SingletonInitException;
import com.workappinc.workappserver.common.logging.IApplicationLogger;

/**
 * WorkAppObjectFactory is an implementation of IFactory, with static methods to
 * create objects systematically, every new object type should be registered
 * manually in this factory class Registration means basically, added a API
 * support to call these call constructors of the specified object It's the
 * programmers responsibility to use this systematically, could potentially lead
 * to bloat in this file, as the available objects proliferate Singleton objects
 * are also considered by this factory, when you add a singleton, register it
 * with singletonRegistry Add reference in the factory method as classes are
 * created, and will tie up with Object Allocation Tracker to track object track
 * orphaned objects
 * 
 * @author dhgovindaraj
 *
 */
public class WorkAppObjectFactory implements IFactory
{
	/**
	 * Format is <ClassName,Method-Definition> Example :
	 * <WorkAppConfigFileReader,public static WorkAppConfigFileReader
	 * getInstance(IApplicationLogger logger)>
	 */
	private static HashMap<String, String> mSingletonDefinitions = new HashMap<String, String>();

	/**
	 * Singleton Definitions listed here, as singleton POJOs are created
	 */
	static
	{

	}

	private static IApplicationLogger mLogger = null;
	private static IFactory mInstance = null;

	private WorkAppObjectFactory(IApplicationLogger logger)
	{
		mLogger = logger;
	}

	/**
	 * Get Instance method is used to get a singleton object
	 * 
	 * @return
	 */
	public static IFactory getInstance(IApplicationLogger logger)
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

	/**
	 * Returns a list of all singleton objects available in the system
	 * 
	 * @return
	 */
	public static HashMap<String, String> getSingletonList()
	{
		return mSingletonDefinitions;
	}

	/**
	 * Method definitions are listed here as normal POJOs are created
	 */
	// Typically, it would be a public static ReturnType Foo(param...)
}
