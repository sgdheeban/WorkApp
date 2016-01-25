package com.workappinc.workappserver.common.resources.examples;

import com.workappinc.workappserver.common.logging.IApplicationLogger;
import com.workappinc.workappserver.common.logging.WorkAppLogger;
import com.workappinc.workappserver.common.resources.implementation.WorkAppAllocationTrackerUtil;

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
		IApplicationLogger logger = new WorkAppLogger(null);

		/**
		 * Enabling Allocation Tracker to track all Heap Object Allocations
		 * (Usage) Add JVM param to call this:
		 * -javaagent:local-maven-repo-path-to-jar/java-allocation-instrumenter-
		 * 3.0.jar Example:
		 * -javaagent:/home/dhgovindaraj/.m2/repository/com/google/code/java-
		 * allocation-instrumenter/java-allocation-instrumenter/3.0/java-
		 * allocation-instrumenter-3.0.jar
		 */
		WorkAppAllocationTrackerUtil.trackHeapAllocation(logger);

		/**
		 * Enabling Allocation Tracker to track all Constructor Allocations
		 * (Usage) Add JVM param to call this:
		 * -javaagent:local-maven-repo-path-to-jar/java-allocation-instrumenter-
		 * 3.0.jar Example:
		 * -javaagent:/home/dhgovindaraj/.m2/repository/com/google/code/java-
		 * allocation-instrumenter/java-allocation-instrumenter/3.0/java-
		 * allocation-instrumenter-3.0.jar
		 */
		WorkAppAllocationTrackerUtil.trackConstructorAllocationTest(logger);

		for (int i = 0; i < 10; i++)
		{
			new String("foo");
			new Integer(i);
		}

		for (int i = 0; i < 20; i++)
		{
			new WorkAppAllocationTrackerUtil.TestTracker();
		}
		System.out.println("Constructed " + WorkAppAllocationTrackerUtil.TestTracker.count + " instances of TestTracker");
	}
}
