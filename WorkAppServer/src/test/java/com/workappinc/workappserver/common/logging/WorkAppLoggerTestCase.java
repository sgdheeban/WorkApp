package com.workappinc.workappserver.common.logging;

import static org.junit.Assert.*;

import java.util.Properties;

import org.junit.Ignore;
import org.junit.Test;

import com.workappinc.workappserver.common.exception.SystemException;
import com.workappinc.workappserver.common.resources.implementation.WorkAppContext;
import com.workappinc.workappserver.common.resources.interfaces.IContext;
import com.workappinc.workappserver.dataaccess.resources.implementation.WorkAppPropertyFileReader;

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
		WorkAppLogger logger;
		try
		{
			logger = new WorkAppLogger(null);
			logger.LogFatal(ctx, WorkAppLoggerTestCase.class);
			logger.LogError(ctx, WorkAppLoggerTestCase.class);
			logger.LogWarn(ctx, WorkAppLoggerTestCase.class);
			logger.LogInfo(ctx, WorkAppLoggerTestCase.class);
			logger.LogDebug(ctx, WorkAppLoggerTestCase.class);
			logger.LogTrace(ctx, WorkAppLoggerTestCase.class);
			logger.LogException(new Exception("Test Exception"), WorkAppLoggerTestCase.class);
			logger.LogPerf(ctx, WorkAppLoggerTestCase.class);
			assertTrue(true);
		}
		catch (SystemException ex)
		{
			ex.printStackTrace();
			assertTrue(false);
		}

	}

	/**
	 * Testing properties based overide of log4j.properties to avoid
	 * redeployment of the build
	 */
	@Test
	public void Log4jPropertiesConfigOverrideTest()
	{
		try
		{
			WorkAppLogger logger;
			String fileSystemLog4jPath = "/home/dhgovindaraj/Documents/git_clone/WorkApp/WorkAppServer/config/log4j.properties";
			WorkAppPropertyFileReader propertiesFileReader = (WorkAppPropertyFileReader) WorkAppPropertyFileReader.getInstance(null);
			Properties prop = propertiesFileReader.loadPropertyFromFileSystem(fileSystemLog4jPath);
			logger = new WorkAppLogger(prop);
			logger.LogFatal(ctx, WorkAppLoggerTestCase.class);
			logger.LogError(ctx, WorkAppLoggerTestCase.class);
			logger.LogWarn(ctx, WorkAppLoggerTestCase.class);
			logger.LogInfo(ctx, WorkAppLoggerTestCase.class);
			logger.LogDebug(ctx, WorkAppLoggerTestCase.class);
			logger.LogTrace(ctx, WorkAppLoggerTestCase.class);
			logger.LogException(new Exception("Test Exception"), WorkAppLoggerTestCase.class);
			logger.LogPerf(ctx, WorkAppLoggerTestCase.class);
			assertTrue(true);
		}
		catch (Exception ex)
		{
			assertTrue(false);
		}
	}

}
