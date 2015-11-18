package com.workappinc.workappserver.common.resources;

import java.lang.management.ManagementFactory;

import com.workappinc.workappserver.common.exception.SingletonInitException;

public class WorkAppUtility
{
	private static WorkAppUtility mInstance = null;

	private WorkAppUtility()
	{
	}

	/**
	 * getInstance method is used to get a singleton object
	 * 
	 * @return
	 */
	public static WorkAppUtility getInstance()
	{
		try
		{
			if (mInstance == null)
			{
				synchronized (WorkAppUtility.class)
				{
					if (mInstance == null)
					{
						mInstance = new WorkAppUtility();
					}
				}
			}
			return mInstance;
		}
		catch (Exception ex)
		{
			throw new SingletonInitException("Error during Singleton Object Creation for WorkAppUtility Class", ex);
		}
	}

	/**
	 * Get current Process Id of the running Application
	 * 
	 * @return
	 */
	public static String getMyPid()
	{
		String pid = "-1";
		try
		{
			final String nameStr = ManagementFactory.getRuntimeMXBean().getName();

			// TODO: Basic parsing assuming that nameStr will be of the form
			// "pid@hostname", which is probably not guaranteed.
			pid = nameStr.split("@")[0];
		}
		catch (RuntimeException e)
		{
			// Fall through.
		}
		return pid;
	}

}
