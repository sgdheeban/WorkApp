package com.workappinc.workappserver.common.logging;

import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

import com.workappinc.workappserver.common.exception.SingletonInitException;
import com.workappinc.workappserver.common.resources.IContext;

/**
 * WorkAppLogger is an implementation of ILogger Interface
 * 
 * @author dhgovindaraj
 *
 */
public class WorkAppLogger implements IApplicationLogger
{
	private ConcurrentHashMap<Class<?>, Logger> mLoggerInstances = new ConcurrentHashMap<Class<?>, Logger>();
	private static WorkAppLogger mInstance = null;

	private WorkAppLogger()
	{
	}

	/**
	 * getInstance method is used to get a singleton object 
	 * @return
	 */
	public static WorkAppLogger getInstance()
	{
		try 
		{
			if (mInstance == null)
			{
				synchronized (WorkAppLogger.class)
				{
					if (mInstance == null)
					{
						mInstance = new WorkAppLogger();
					}
				}
			}
			return mInstance;
		}
		catch(Exception ex)
		{
			throw new SingletonInitException("Error during Singleton Object Creation for WorkAppLogger Class",ex);
		}
	}

	private Logger getLoggerInstance(Class<?> className)
	{
		Logger returnLogger = null;
		returnLogger = mLoggerInstances.putIfAbsent(className, Logger.getLogger(className));
		return returnLogger;
	}

	@Override
	public void LogPerf(Object ctx, Class<?> className)
	{
		getLoggerInstance(className).info(format(ctx));
	}

	@Override
	public void LogInfo(Object ctx, Class<?> className)
	{
		getLoggerInstance(className).info(format(ctx));
	}

	@Override
	public void LogWarn(Object ctx, Class<?> className)
	{
		getLoggerInstance(className).warn(format(ctx));
	}

	@Override
	public void LogDebug(Object ctx, Class<?> className)
	{
		getLoggerInstance(className).debug(format(ctx));
	}

	@Override
	public void LogError(Object ctx, Class<?> className)
	{
		getLoggerInstance(className).error(format(ctx));
	}

	@Override
	public void LogTrace(Object ctx, Class<?> className)
	{
		getLoggerInstance(className).trace(format(ctx));
	}

	@Override
	public void LogFatal(Object ctx, Class<?> className)
	{
		getLoggerInstance(className).fatal(format(ctx));
	}

	private String format(Object ctx)
	{
		String returnString = null;
		return returnString;
	}

}
