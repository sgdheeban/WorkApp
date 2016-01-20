package com.workappinc.workappserver.dataaccess.resources.implementation;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.workappinc.workappserver.common.exception.SingletonInitException;
import com.workappinc.workappserver.common.logging.IApplicationLogger;
import com.workappinc.workappserver.common.resources.implementation.WorkAppUtil;
import com.workappinc.workappserver.dataaccess.resources.interfaces.IReader;

/**
 * WorkAppPropertyFileReader is a singleton implementation of IReader interface
 * to read from disk and convert to POJO representing configuration
 * 
 * @author dhgovindaraj
 *
 */
public class WorkAppPropertyFileReader implements IReader
{
	private IApplicationLogger mLogger = null;
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
			throw new SingletonInitException("Error during Singleton Object Creation for WorkAppPropertyFileReader Class", ex);
		}
	}

	/**
	 * Load Property File from File System
	 * 
	 * @param filePath
	 * @return
	 */
	public Properties loadPropertyFromFileSystem(String filePath)
	{
		Properties prop = new Properties();
		InputStream input = null;
		try
		{
			input = new FileInputStream(filePath);
			prop.load(input);
		}
		catch (IOException ex)
		{
			if (mLogger != null)
				mLogger.LogException(ex, WorkAppPropertyFileReader.class);
		}
		finally
		{
			if (input != null)
			{
				try
				{
					input.close();
				}
				catch (IOException ex)
				{
					if (mLogger != null)
						mLogger.LogException(ex, WorkAppPropertyFileReader.class);
				}
			}
		}
		return prop;
	}

	/**
	 * Load Property File from Classpath
	 * 
	 * @param filePath
	 * @return
	 */
	public Properties loadPropertyFromClassPath(String filePath)
	{
		Properties prop = new Properties();
		InputStream input = null;
		try
		{
			String filename = filePath;
			input = getClass().getClassLoader().getResourceAsStream(filename);
			if (input == null)
			{
				if (mLogger != null)
					mLogger.LogError("Sorry, unable to find " + filename, WorkAppPropertyFileReader.class);
				return null;
			}
			prop.load(input);
		}
		catch (IOException ex)
		{
			if (mLogger != null)
				mLogger.LogException(ex, WorkAppPropertyFileReader.class);
		}
		finally
		{
			if (input != null)
			{
				try
				{
					input.close();
				}
				catch (IOException ex)
				{
					if (mLogger != null)
						mLogger.LogException(ex, WorkAppPropertyFileReader.class);
				}
			}
		}
		return prop;
	}

}
