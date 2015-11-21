package com.workappinc.workappserver.common.resources;

import java.util.concurrent.atomic.AtomicBoolean;

import com.workappinc.workappserver.common.exception.SingletonInitException;
import com.workappinc.workappserver.common.logging.IApplicationLogger;

/**
 * A Singleton class for starting a perf counter to collect metrics and store it
 * in a database
 * 
 * @author dhgovindaraj
 *
 */
public class WorkAppPerfCounter implements ICounter
{
	private static IApplicationLogger mLogger = null;
	private static AtomicBoolean mStarted = new AtomicBoolean(false);
	private static ICounter mInstance = null;

	private WorkAppPerfCounter(IApplicationLogger logger)
	{
		mLogger = logger;
	}

	/**
	 * getInstance method is used to get a singleton object
	 * 
	 * @return
	 */
	public static ICounter getInstance(IApplicationLogger logger)
	{
		try
		{
			if (mInstance == null)
			{
				synchronized (WorkAppPerfCounter.class)
				{
					if (mInstance == null)
					{
						mInstance = new WorkAppPerfCounter(logger);
					}
				}
			}
			return mInstance;
		}
		catch (Exception ex)
		{
			throw new SingletonInitException("Error during Singleton Object Creation for WorkAppPerfCounter Class", ex);
		}
	}

	/**
	 * 
	 * StartCounter method is thread-safe, only one thread will ever set the
	 * StartCounter, to start the counter in the background
	 */
	public synchronized void startCounter()
	{
		try
		{
			if (mStarted.compareAndSet(false, true))
			{
				new WorkAppPerfCounterThread().run();
			}
		}
		catch (Exception ex)
		{
			// Log Exception - decide if you should swallow or propagate the
			// exception
		}
	}

	/**
	 * Inner-Class to encapsulate Counter Thread within the singleton counter
	 * object
	 * 
	 * @author dhgovindaraj
	 *
	 */
	private class WorkAppPerfCounterThread implements Runnable
	{

		@Override
		public void run()
		{
			// TODO Auto-generated method stub

		}

	}

}
