package com.workapp.workappserver.common.logging;

import com.workapp.workappserver.common.exception.WorkAppUncaughtException;

/**
 * WorkAppUncaughtException handler is a background thread, that handles any
 * fatal, uncaught exceptions
 * 
 * @author dhgovindaraj
 *
 */
public class UncaughtExceptionLogger implements ILogger
{
	public static void setDefaultUncaughtExceptionHandler(AppLogger workAppLoggerInstance)
	{
		Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler()
		{
			@Override
			public void uncaughtException(final Thread th, final Throwable t)
			{
				workAppLoggerInstance.LogError(new WorkAppUncaughtException("Uncaught exception in thread " + th, t), this.getClass());
			}
		});
	}
}
