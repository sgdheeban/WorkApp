package com.workappinc.workappserver.common.logging;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;

import com.workappinc.workappserver.common.resources.IContext;
import com.workappinc.workappserver.common.resources.WorkAppContext;

/**
 * JUnit TestCases
 * 
 * @author dhgovindaraj
 *
 */
public class WorkAppLoggerTestCase
{
	private static IContext ctx = new WorkAppContext("2634b48f-c7ee-418c-8d0a-7c1fb3a1fc99", "Test GUID");

	@Test
	public void doBasicTest()
	{
		WorkAppLogger.getInstance(null).LogFatal(ctx, WorkAppLoggerTestCase.class);
		WorkAppLogger.getInstance(null).LogError(ctx, WorkAppLoggerTestCase.class);
		WorkAppLogger.getInstance(null).LogWarn(ctx, WorkAppLoggerTestCase.class);
		WorkAppLogger.getInstance(null).LogInfo(ctx, WorkAppLoggerTestCase.class);
		WorkAppLogger.getInstance(null).LogDebug(ctx, WorkAppLoggerTestCase.class);
		WorkAppLogger.getInstance(null).LogTrace(ctx, WorkAppLoggerTestCase.class);
		WorkAppLogger.getInstance(null).LogException(new Exception("Test Exception"), WorkAppLoggerTestCase.class);
		WorkAppLogger.getInstance(null).LogPerf(ctx, WorkAppLoggerTestCase.class);
	}

	@Test
	@Ignore
	public void doCommandLineBasedConfigParamTest()
	{
		fail("Not yet implemented");
	}

	@Test
	@Ignore
	public void doMultiThreadedLoggingTest()
	{
		fail("Not yet implemented");
	}

}
