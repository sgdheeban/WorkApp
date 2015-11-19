package com.workappinc.workappserver.common.logging;

import com.workappinc.workappserver.common.exception.WorkAppUncaughtException;

/**
 * WorkAppUncaughtException handler is a background thread, that handles any
 * fatal, uncaught exceptions
 * 
 * @author dhgovindaraj
 *
 */
public class WorkAppUncaughtExceptionLogger implements ILogger
{
	public static void setDefaultUncaughtExceptionHandler(WorkAppLogger workAppLoggerInstance)
	{
		Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler()
		{
			@Override
			public void uncaughtException(final Thread th, final Throwable t)
			{
				workAppLoggerInstance.LogError(new WorkAppUncaughtException("Uncaught exception in thread " + th, t),
						this.getClass());
			}
		});
	}
}
