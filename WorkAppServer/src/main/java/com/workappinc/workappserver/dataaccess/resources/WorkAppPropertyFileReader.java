package com.workappinc.workappserver.dataaccess.resources;

import com.workappinc.workappserver.common.exception.SingletonInitException;
import com.workappinc.workappserver.common.logging.IApplicationLogger;

/**
 * WorkAppPropertyFileReader is a singleton implementation of IReader interface
 * to read from disk and convert to POJO representing configuration
 * 
 * @author dhgovindaraj
 *
 */
public class WorkAppPropertyFileReader implements IReader
{
	private static IApplicationLogger mLogger = null;
	private static IReader mInstance = null;

	private WorkAppPropertyFileReader(IApplicationLogger logger)
	{
		mLogger = logger;
	}

	/**
	 * getInstance method is used to get a singleton object
	 * 
	 * @return
	 */
	public static IReader getInstance(IApplicationLogger logger)
	{
		try
		{
			if (mInstance == null)
			{
				synchronized (WorkAppPropertyFileReader.class)
				{
					if (mInstance == null)
					{
						mInstance = new WorkAppPropertyFileReader(logger);
					}
				}
			}
			return mInstance;
		}
		catch (Exception ex)
		{
			throw new SingletonInitException(
					"Error during Singleton Object Creation for WorkAppPropertyFileReader Class", ex);
		}
	}

}
