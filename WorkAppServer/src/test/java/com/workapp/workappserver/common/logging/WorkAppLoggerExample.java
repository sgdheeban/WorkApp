package com.workapp.workappserver.common.logging;

import java.io.IOException;

import com.workapp.workappserver.common.exception.SystemException;
import com.workapp.workappserver.common.logging.WorkAppLogger;
import com.workapp.workappserver.common.resources.implementation.WorkAppContext;
import com.workapp.workappserver.common.resources.interfaces.IContext;

/**
 * Example testing WorkAppLogger functionality
 * 
 * @author dhgovindaraj
 *
 */
public class WorkAppLoggerExample
{
	private static IContext ctx = new WorkAppContext("2634b48f-c7ee-418c-8d0a-7c1fb3a1fc99", "Test GUID");

	/**
	 * Testing Default Logging Behavior, with inbuilt log4j.properties added to
	 * the buildpath
	 */
	private static void defaultLoggerFunctionalityTest()
	{
		WorkAppLogger logger;
		try
		{
			logger = new WorkAppLogger(null);
			logger.LogFatal(ctx, WorkAppLoggerExample.class);
			logger.LogError(ctx, WorkAppLoggerExample.class);
			logger.LogWarn(ctx, WorkAppLoggerExample.class);
			logger.LogInfo(ctx, WorkAppLoggerExample.class);
			logger.LogDebug(ctx, WorkAppLoggerExample.class);
			logger.LogTrace(ctx, WorkAppLoggerExample.class);
			logger.LogException(new Exception("Test Exception"), WorkAppLoggerExample.class);
			logger.LogPerf(ctx, WorkAppLoggerExample.class);
		}
		catch (SystemException ex)
		{
			ex.printStackTrace();
		}
	}

	/**
	 * Static main method as entry to the test class
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String args[]) throws IOException
	{
		defaultLoggerFunctionalityTest();
	}
}
