package com.workappinc.workappserver.common.logging;

import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

import com.workappinc.workappserver.common.IContext;

/**
 * WorkAppLogger is an implementation of ILogger Interface
 * @author dhgovindaraj
 *
 */
public class WorkAppLogger implements ILogger {

	private ConcurrentHashMap<Class<?>, Logger> mLoggerInstances = new ConcurrentHashMap<Class<?>, Logger> ();

	@SuppressWarnings("unused")
	private Logger getLoggerInstance(Class<?> className) {
		Logger returnLogger = null;
		returnLogger = mLoggerInstances.putIfAbsent(className, Logger.getLogger(className));
		return returnLogger;
	}
	
	@Override
	public void LogPerf(IContext ctx, Class<?> className) {
		getLoggerInstance(className).info(format(ctx));
	}

	@Override
	public void LogInfo(IContext ctx, Class<?> className) {
		getLoggerInstance(className).info(format(ctx));
	}

	@Override
	public void LogWarn(IContext ctx, Class<?> className) {
		getLoggerInstance(className).warn(format(ctx));
	}

	@Override
	public void LogDebug(IContext ctx, Class<?> className) {
		getLoggerInstance(className).debug(format(ctx));
	}

	@Override
	public void LogError(IContext ctx, Class<?> className) {
		getLoggerInstance(className).error(format(ctx));
	}

	@Override
	public void LogTrace(IContext ctx, Class<?> className) {
		getLoggerInstance(className).trace(format(ctx));
	}

	@Override
	public void LogFatal(IContext ctx, Class<?> className) {
		getLoggerInstance(className).fatal(format(ctx));
	}
	
	private String format(IContext ctx) {
		String returnString = null;
		return returnString;
	}

}
