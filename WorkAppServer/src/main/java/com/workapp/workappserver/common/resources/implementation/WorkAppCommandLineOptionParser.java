package com.workapp.workappserver.common.resources.implementation;

import com.workapp.workappserver.common.exception.SingletonInitException;
import com.workapp.workappserver.common.logging.IApplicationLogger;
import com.workapp.workappserver.common.resources.interfaces.IParser;

/**
 * WorkAppCommandLineOptionParser is a singleton implementation of IParser
 * interface, to read command Line Options passed to the program
 * 
 * @author dhgovindaraj
 *
 */
public class WorkAppCommandLineOptionParser implements IParser
{
	private static IApplicationLogger mLogger = null;
	private static WorkAppCommandLineOptionParser mInstance = null;

	private WorkAppCommandLineOptionParser(IApplicationLogger logger)
	{
		mLogger = logger;
	}

	/**
	 * getInstance method is used to get a singleton object
	 * 
	 * @return
	 */
	public static WorkAppCommandLineOptionParser getInstance(IApplicationLogger logger)
	{
		try
		{
			if (mInstance == null)
			{
				synchronized (WorkAppCommandLineOptionParser.class)
				{
					if (mInstance == null)
					{
						mInstance = new WorkAppCommandLineOptionParser(logger);
					}
				}
			}
			return mInstance;
		}
		catch (Exception ex)
		{
			throw new SingletonInitException("Error during Singleton Object Creation for WorkAppCommandLineOptionParser Class", ex);
		}
	}

}
