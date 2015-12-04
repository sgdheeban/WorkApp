package com.workappinc.workappserver.dataaccess.resources;

import java.util.Properties;

import com.workappinc.workappserver.common.exception.SingletonInitException;
import com.workappinc.workappserver.common.logging.IApplicationLogger;

/**
 * WorkAppPropertyFileWriter abstract class for writing in-memory properties to
 * files
 * 
 * @author dhgovindaraj
 *
 */
public class WorkAppPropertyFileWriter extends WorkAppFileWriter
{
	private static IApplicationLogger mLogger = null;
	private static IWriter mInstance = null;

	private WorkAppPropertyFileWriter(IApplicationLogger logger)
	{
		mLogger = logger;
	}

	/**
	 * getInstance method is used to get a singleton object
	 * 
	 * @return
	 */
	public static IWriter getInstance(IApplicationLogger logger)
	{
		try
		{
			if (mInstance == null)
			{
				synchronized (WorkAppPropertyFileWriter.class)
				{
					if (mInstance == null)
					{
						mInstance = new WorkAppPropertyFileWriter(logger);
					}
				}
			}
			return mInstance;
		}
		catch (Exception ex)
		{
			throw new SingletonInitException(
					"Error during Singleton Object Creation for WorkAppPropertyFileWriter Class", ex);
		}
	}

	/**
	 * Writes Properties to File System
	 */
	@Override
	public void writeToFile(Object inputProperties)
	{
		// TODO Auto-generated method stub

	}

	/**
	 * Formats Properties if required
	 */
	@Override
	public Object format(Object input)
	{
		// TODO Auto-generated method stub
		return null;
	}

}
