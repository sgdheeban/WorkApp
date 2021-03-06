package com.workapp.workappserver.common.resources.examples;

import com.workapp.workappserver.common.logging.IApplicationLogger;
import com.workapp.workappserver.common.logging.AppLogger;
import com.workapp.workappserver.common.resources.implementation.AllocationTrackerUtil;

/**
 * Test Class to illustrate WorkApp Allocation Tracker
 * 
 * @author dhgovindaraj
 *
 */
public class WorkAppAllocationTrackerUtilExample
{
	public static void main(String[] args) throws Exception
	{
		IApplicationLogger logger = new AppLogger(null);

		/**
		 * Enabling Allocation Tracker to track all Heap Object Allocations
		 * (Usage) Add JVM param to call this:
		 * -javaagent:local-maven-repo-path-to-jar/java-allocation-instrumenter-
		 * 3.0.jar Example:
		 * -javaagent:/home/dhgovindaraj/.m2/repository/com/google/code/java-
		 * allocation-instrumenter/java-allocation-instrumenter/3.0/java-
		 * allocation-instrumenter-3.0.jar
		 */
		AllocationTrackerUtil.trackHeapAllocation(logger);

		/**
		 * Enabling Allocation Tracker to track all Constructor Allocations
		 * (Usage) Add JVM param to call this:
		 * -javaagent:local-maven-repo-path-to-jar/java-allocation-instrumenter-
		 * 3.0.jar Example:
		 * -javaagent:/home/dhgovindaraj/.m2/repository/com/google/code/java-
		 * allocation-instrumenter/java-allocation-instrumenter/3.0/java-
		 * allocation-instrumenter-3.0.jar
		 */
		AllocationTrackerUtil.trackConstructorAllocationTest(logger);

		for (int i = 0; i < 10; i++)
		{
			new String("foo");
			new Integer(i);
		}

		for (int i = 0; i < 20; i++)
		{
			new AllocationTrackerUtil.TestTracker();
		}
		System.out.println("Constructed " + AllocationTrackerUtil.TestTracker.count + " instances of TestTracker");
	}
}
