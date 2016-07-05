package com.workapp.workappserver.common.logging;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.apache.log4j.PropertyConfigurator;

import com.workapp.workappserver.common.exception.SingletonInitException;
import com.workapp.workappserver.common.exception.SystemException;
import com.workapp.workappserver.common.resources.implementation.WorkAppUtil;
import com.workapp.workappserver.common.resources.interfaces.IContext;

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

	public WorkAppLogger(Properties config) throws SystemException
	{
		// If External Config File Exists, override default log4j.properties
		if (config != null)
			configureDefaults(config);

		// Obtain System Info for Logging
		MDC.put("hostname", WorkAppUtil.getMyHostInfo(this, null, false));
		MDC.put("port", WorkAppUtil.getMyPid(this, null));

		// Logger to catch uncaught exceptions in a separate thread
		WorkAppUncaughtExceptionLogger.setDefaultUncaughtExceptionHandler(this);
	}

	private static void configureDefaults(Properties config)
	{
		PropertyConfigurator.configure(config);
	}

	private Logger getLoggerInstance(Class<?> className)
	{
		Logger returnLogger = null;
		returnLogger = mLoggerInstances.putIfAbsent(className, Logger.getLogger(className));
		returnLogger = mLoggerInstances.get(className);
		return returnLogger;
	}

	@Override
	public void LogPerf(Object ctx, Class<?> className)
	{
		if (getLoggerInstance(className).isInfoEnabled())
			getLoggerInstance(className).info(format(ctx));
	}

	@Override
	public void LogException(Object ctx, Class<?> className)
	{
		if (getLoggerInstance(className).isInfoEnabled())
			getLoggerInstance(className).info(format(ctx));
	}

	@Override
	public void LogFatal(Object ctx, Class<?> className)
	{
		getLoggerInstance(className).fatal(format(ctx));
	}

	@Override
	public void LogError(Object ctx, Class<?> className)
	{
		getLoggerInstance(className).error(format(ctx));
	}

	@Override
	public void LogWarn(Object ctx, Class<?> className)
	{
		getLoggerInstance(className).warn(format(ctx));
	}

	@Override
	public void LogInfo(Object ctx, Class<?> className)
	{
		if (getLoggerInstance(className).isInfoEnabled())
			getLoggerInstance(className).info(format(ctx));
	}

	@Override
	public void LogDebug(Object ctx, Class<?> className)
	{
		if (getLoggerInstance(className).isDebugEnabled())
			getLoggerInstance(className).debug(format(ctx));
	}

	@Override
	public void LogTrace(Object ctx, Class<?> className)
	{
		getLoggerInstance(className).trace(format(ctx));
	}

	private String format(Object ctx)
	{
		StringBuilder mStringBuilder = null;
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
			mStringBuilder.append(((IContext) ctx).getContextComment());
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
