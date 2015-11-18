package com.workappinc.workappserver.common.logging;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

import com.workappinc.workappserver.common.resources.IContext;
import com.workappinc.workappserver.common.resources.WorkAppContext;

/**
 * Example testing WorkAppLogger functionality
 * @author dhgovindaraj
 *
 */
public class WorkAppLoggerExample
{

	public static void main(String args[]) throws IOException
	{
		
		IContext ctx = new WorkAppContext("2634b48f-c7ee-418c-8d0a-7c1fb3a1fc99","Test GUID");
		WorkAppLogger.getInstance().LogInfo(ctx, WorkAppLoggerExample.class);
		WorkAppLogger.getInstance().LogWarn(ctx, WorkAppLoggerExample.class);
		WorkAppLogger.getInstance().LogDebug(ctx, WorkAppLoggerExample.class);
		WorkAppLogger.getInstance().LogError(ctx, WorkAppLoggerExample.class);
		WorkAppLogger.getInstance().LogTrace(ctx, WorkAppLoggerExample.class);
		WorkAppLogger.getInstance().LogFatal(ctx, WorkAppLoggerExample.class);
		
	}
}
