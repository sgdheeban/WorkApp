package com.workappinc.workappserver.common.logging;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

import com.workappinc.workappserver.common.resources.IContext;
import com.workappinc.workappserver.common.resources.WorkAppContext;

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
		WorkAppLogger.getInstance(null).LogFatal(ctx, WorkAppLoggerExample.class);
		WorkAppLogger.getInstance(null).LogError(ctx, WorkAppLoggerExample.class);
		WorkAppLogger.getInstance(null).LogWarn(ctx, WorkAppLoggerExample.class);
		WorkAppLogger.getInstance(null).LogInfo(ctx, WorkAppLoggerExample.class);
		WorkAppLogger.getInstance(null).LogDebug(ctx, WorkAppLoggerExample.class);
		WorkAppLogger.getInstance(null).LogTrace(ctx, WorkAppLoggerExample.class);
		WorkAppLogger.getInstance(null).LogException(new Exception("Test Exception"), WorkAppLoggerExample.class);
		WorkAppLogger.getInstance(null).LogPerf(ctx, WorkAppLoggerExample.class);
	}

	/**
	 * Testing command line param overide of log4j.properties to avoid
	 * redeployment of the build
	 */
	private static void commandLineLoggerConfigParamTest()
	{

	}

	/**
	 * Testing logger from multiple threads to detect any race conditions
	 */
	private static void multiThreadedLoggingTest()
	{

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
		commandLineLoggerConfigParamTest();
		multiThreadedLoggingTest();

	}
}
