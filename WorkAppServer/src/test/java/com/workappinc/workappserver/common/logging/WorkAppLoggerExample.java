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
		WorkAppLogger.getInstance().LogFatal(ctx, WorkAppLoggerExample.class);
		WorkAppLogger.getInstance().LogError(ctx, WorkAppLoggerExample.class);
		WorkAppLogger.getInstance().LogWarn(ctx, WorkAppLoggerExample.class);
		WorkAppLogger.getInstance().LogInfo(ctx, WorkAppLoggerExample.class);
		WorkAppLogger.getInstance().LogDebug(ctx, WorkAppLoggerExample.class);
		WorkAppLogger.getInstance().LogTrace(ctx, WorkAppLoggerExample.class);
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
	private static void multiThreadingLoggingTest()
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
		multiThreadingLoggingTest();

	}
}
