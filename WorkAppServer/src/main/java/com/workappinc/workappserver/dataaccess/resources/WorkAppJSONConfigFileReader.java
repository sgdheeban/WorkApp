package com.workappinc.workappserver.dataaccess.resources;

import com.workappinc.workappserver.common.exception.SingletonInitException;
import com.workappinc.workappserver.common.logging.IApplicationLogger;

/**
 * WorkAppJSONConfigFileReader is a singleton implementation of IReader
 * interface to read from disk and convert to POJO representing configuration
 * 
 * @author dhgovindaraj
 *
 */
public class WorkAppJSONConfigFileReader implements IReader
{
	private static IApplicationLogger mLogger = null;
	private static IReader mInstance = null;

	private WorkAppJSONConfigFileReader(IApplicationLogger logger)
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
				synchronized (WorkAppJSONConfigFileReader.class)
				{
					if (mInstance == null)
					{
						mInstance = new WorkAppJSONConfigFileReader(logger);
					}
				}
			}
			return mInstance;
		}
		catch (Exception ex)
		{
			throw new SingletonInitException(
					"Error during Singleton Object Creation for WorkAppJSONConfigFileReader Class", ex);
		}
	}

}
