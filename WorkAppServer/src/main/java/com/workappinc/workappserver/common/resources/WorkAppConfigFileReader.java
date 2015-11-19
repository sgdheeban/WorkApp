package com.workappinc.workappserver.common.resources;

import com.workappinc.workappserver.common.exception.SingletonInitException;
import com.workappinc.workappserver.common.logging.IApplicationLogger;

/**
 * WorkAppConfigReader is a singleton implementation of IReader interface to read from
 * disk and convert to POJO representing configuration
 * 
 * @author dhgovindaraj
 *
 */
public class WorkAppConfigFileReader implements IReader
{
	private static IApplicationLogger mLogger = null;
	private static WorkAppConfigFileReader mInstance = null;
	
	private WorkAppConfigFileReader(IApplicationLogger logger)
	{
		mLogger = logger;
	}

	/**
	 * getInstance method is used to get a singleton object
	 * 
	 * @return
	 */
	public static WorkAppConfigFileReader getInstance(IApplicationLogger logger)
	{
		try
		{
			if (mInstance == null)
			{
				synchronized (WorkAppConfigFileReader.class)
				{
					if (mInstance == null)
					{
						mInstance = new WorkAppConfigFileReader(logger);
					}
				}
			}
			return mInstance;
		}
		catch (Exception ex)
		{
			throw new SingletonInitException("Error during Singleton Object Creation for WorkAppConfigFileReader Class", ex);
		}
	}

}
