package com.workappinc.workappserver.dataaccess.resources;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.workappinc.workappserver.common.exception.FileWritingException;
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
	@SuppressWarnings("unchecked")
	@Override
	public void writeToFile(String outputFilePath, Object inputProperties)
	{
		OutputStream output = null;
		Properties prop = null;
		try
		{
			File outputFile = new File(outputFilePath);
			if (!outputFile.exists())
			{
				outputFile.createNewFile();
			}
			output = new FileOutputStream(outputFile);

			if (inputProperties instanceof Properties)
			{
				prop = (Properties) inputProperties;
			}
			else if (inputProperties instanceof Map)
			{
				prop = new Properties();
				prop.putAll((HashMap<String, String>) inputProperties);
			}
			else
			{
				mLogger.LogError("Unsupported Type passed to writeToFile method", WorkAppPropertyFileWriter.class);
				throw new FileWritingException("Unsupported Type passed to writeToFile method");
			}
			prop.store(output, null);
		}
		catch (Exception ex)
		{
			mLogger.LogException(ex, WorkAppPropertyFileWriter.class);
		}
		finally
		{
			if (output != null)
			{
				try
				{
					output.close();
				}
				catch (IOException ex)
				{
					mLogger.LogException(ex, WorkAppPropertyFileWriter.class);
				}
			}
		}
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
