package com.workapp.workappserver.dataaccess.resources.examples;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.workapp.workappserver.common.exception.SystemException;
import com.workapp.workappserver.common.logging.IApplicationLogger;
import com.workapp.workappserver.common.logging.AppLogger;

/**
 * Test Driver to test WorkAppTestUserDataManager
 * 
 * @author dhgovindaraj
 *
 */
public class WorkAppTestUserDataManagerDriver
{
	public static void main(String args[]) throws IOException, InterruptedException, SystemException
	{
		IApplicationLogger logger = new AppLogger(null);
		WorkAppTestUserDataManager userDataManager = (WorkAppTestUserDataManager) WorkAppTestUserDataManager.getInstance(logger);
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:sss");
		logger.LogDebug("WorkAppTestUserDataManagerDriver started at " + sdf.format(cal.getTime()), WorkAppTestUserDataManagerDriver.class);
		for (int i = 0; i < 10; i++)
		{
			if (i == 5)
			{
				logger.LogDebug("WorkAppTestUserDataManagerDriver slept at " + sdf.format(cal.getTime()), WorkAppTestUserDataManagerDriver.class);
				Thread.sleep(3000);
			}

			userDataManager.addEntry(new WorkAppUserTestEntry("sgd88-" + i, i));
		}
		logger.LogDebug("WorkAppTestUserDataManagerDriver ended at " + sdf.format(cal.getTime()), WorkAppTestUserDataManagerDriver.class);
	}
}
