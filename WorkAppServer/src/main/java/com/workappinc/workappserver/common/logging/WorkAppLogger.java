package com.workappinc.workappserver.common.logging;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.apache.log4j.PropertyConfigurator;

import com.workappinc.workappserver.common.exception.SingletonInitException;
import com.workappinc.workappserver.common.exception.SystemException;
import com.workappinc.workappserver.common.resources.IContext;
import com.workappinc.workappserver.common.resources.WorkAppUtility;

/**
 * WorkAppLogger is an implementation of ILogger Interface
 * 
 * @author dhgovindaraj
 *
 */
public class WorkAppLogger implements IApplicationLogger
{
	private ConcurrentHashMap<Class<?>, Logger> mLoggerInstances = new ConcurrentHashMap<Class<?>, Logger>();
	private static IApplicationLogger mInstance = null;
	private StringBuilder mStringBuilder = null;

	private WorkAppLogger(Properties config) throws SystemException
	{
		// If External Config File Exists, override default log4j.properties
		if (config != null)
			configureDefaults(config);

		// Obtain System Info for Logging
		MDC.put("hostname", WorkAppUtility.getMyHostInfo(null, false));
		MDC.put("port", WorkAppUtility.getMyPid(null));

		// Logger to catch uncaught exceptions in a separate thread
		WorkAppUncaughtExceptionLogger.setDefaultUncaughtExceptionHandler(this);
	}

	/**
	 * getInstance method is used to get a singleton object
	 * 
	 * @return
	 */
	public static IApplicationLogger getInstance(Properties config)
	{
		try
		{
			if (mInstance == null)
			{
				synchronized (WorkAppLogger.class)
				{
					if (mInstance == null)
					{
						mInstance = new WorkAppLogger(config);
					}
				}
			}
			return mInstance;
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw new SingletonInitException("Error during Singleton Object Creation for WorkAppLogger Class", ex);

		}
	}

	private static void configureDefaults(Properties config)
	{
		PropertyConfigurator.configure(config);
	}

	private synchronized Logger getLoggerInstance(Class<?> className)
	{
		Logger returnLogger = null;
		returnLogger = mLoggerInstances.putIfAbsent(className, Logger.getLogger(className));
		returnLogger = mLoggerInstances.get(className);
		return returnLogger;
	}

	@Override
	public synchronized void LogPerf(Object ctx, Class<?> className)
	{
		if (getLoggerInstance(className).isInfoEnabled())
			getLoggerInstance(className).info(format(ctx));
	}

	@Override
	public synchronized void LogException(Object ctx, Class<?> className)
	{
		if (getLoggerInstance(className).isInfoEnabled())
			getLoggerInstance(className).info(format(ctx));
	}

	@Override
	public synchronized void LogFatal(Object ctx, Class<?> className)
	{
		getLoggerInstance(className).fatal(format(ctx));
	}

	@Override
	public synchronized void LogError(Object ctx, Class<?> className)
	{
		getLoggerInstance(className).error(format(ctx));
	}

	@Override
	public synchronized void LogWarn(Object ctx, Class<?> className)
	{
		getLoggerInstance(className).warn(format(ctx));
	}

	@Override
	public synchronized void LogInfo(Object ctx, Class<?> className)
	{
		if (getLoggerInstance(className).isInfoEnabled())
			getLoggerInstance(className).info(format(ctx));
	}

	@Override
	public synchronized void LogDebug(Object ctx, Class<?> className)
	{
		if (getLoggerInstance(className).isDebugEnabled())
			getLoggerInstance(className).debug(format(ctx));
	}

	@Override
	public synchronized void LogTrace(Object ctx, Class<?> className)
	{
		getLoggerInstance(className).trace(format(ctx));
	}

	private String format(Object ctx)
	{
		String returnString = null;
		if (ctx instanceof Exception)
		{
			mStringBuilder = new StringBuilder();
			mStringBuilder.append(" [ ");
			mStringBuilder.append(((Exception) ctx).getMessage());
			mStringBuilder.append(" ] ");
			mStringBuilder.append(" - Stacktrace : ");
			StringWriter errors = new StringWriter();
			((Throwable) ctx).printStackTrace(new PrintWriter(errors));
			mStringBuilder.append(errors.toString());
			returnString = mStringBuilder.toString();
		}
		else if (ctx instanceof IContext)
		{
			mStringBuilder = new StringBuilder();
			mStringBuilder.append(" [");
			mStringBuilder.append(((IContext) ctx).getGUID());
			mStringBuilder.append(",");
			mStringBuilder.append(((IContext) ctx).getMessage());
			mStringBuilder.append("] ");
			returnString = mStringBuilder.toString();
		}
		else if (ctx instanceof String)
		{
			returnString = ctx.toString();
		}
		return returnString;
	}

}
