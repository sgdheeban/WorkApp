package com.workappinc.workappserver.common.resources.implementation;

import java.lang.instrument.UnmodifiableClassException;

import com.google.monitoring.runtime.instrumentation.AllocationRecorder;
import com.google.monitoring.runtime.instrumentation.ConstructorCallback;
import com.google.monitoring.runtime.instrumentation.ConstructorInstrumenter;
import com.google.monitoring.runtime.instrumentation.Sampler;
import com.workappinc.workappserver.common.logging.IApplicationLogger;

/**
 * WorkAppAllocationTrackerUtil is a util class which tracks all objects created
 * by WorkApp
 * 
 * @author dhgovindaraj
 *
 */
public class WorkAppAllocationTrackerUtil
{
	public static class TestTracker
	{
		public static int count = 0;
		int x;

		public TestTracker()
		{
			x = count;
		}
	}

	/**
	 * Tracks all heap objects monitored under profiler Causes program to run
	 * slower, so use it optionally Tracks all heap object allocations. (Usage)
	 * Add JVM param to call this:
	 * -javaagent:local-maven-repo-path-to-jar/java-allocation-instrumenter-3.0.
	 * jar Example:
	 * -javaagent:/home/dhgovindaraj/.m2/repository/com/google/code/java-
	 * allocation-instrumenter/java-allocation-instrumenter/3.0/java-allocation-
	 * instrumenter-3.0.jar
	 * 
	 */
	public static void trackHeapAllocation(IApplicationLogger logger)
	{
		AllocationRecorder.addSampler(new Sampler()
		{
			public void sampleAllocation(int count, String desc, Object newObj, long size)
			{
				if (logger != null)
					logger.LogDebug("Allocated the object " + newObj + " of type " + desc + " whose size is " + size,
							WorkAppAllocationTrackerUtil.class);
				if (count != -1)
				{
					if (logger != null)
						logger.LogDebug("It's an array of size " + count, WorkAppAllocationTrackerUtil.class);
				}
			}
		});
	}

	/**
	 * Sample method to track constructor allocation of classes. More classes
	 * can be added similarly. (Usage) Add JVM param to call this:
	 * -javaagent:local-maven-repo-path-to-jar/java-allocation-instrumenter-3.0.
	 * jar Example:
	 * -javaagent:/home/dhgovindaraj/.m2/repository/com/google/code/java-
	 * allocation-instrumenter/java-allocation-instrumenter/3.0/java-allocation-
	 * instrumenter-3.0.jar
	 */
	public static void trackConstructorAllocationTest(IApplicationLogger logger)
	{
		try
		{
			// Track TestTracker Constructor Allocation
			ConstructorInstrumenter.instrumentClass(TestTracker.class, new ConstructorCallback<TestTracker>()
			{
				@Override
				public void sample(TestTracker t)
				{
					if (logger != null)
						logger.LogDebug("Constructing an element of type TestTracker with x = " + t.x,
								WorkAppAllocationTrackerUtil.class);
					TestTracker.count++;
				}
			});
		}
		catch (UnmodifiableClassException ex)
		{
			if (logger != null)
				logger.LogException(ex, WorkAppAllocationTrackerUtil.class);
		}
	}
}
