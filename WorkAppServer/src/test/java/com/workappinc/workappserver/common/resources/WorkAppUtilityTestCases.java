package com.workappinc.workappserver.common.resources;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;

/**
 * WorkAppUtilitiesTestCases cover JUnit test cases to test all functionalities
 * of WorkAppUtilitiesExample functionalities
 * 
 * @author dhgovindaraj
 *
 */
public class WorkAppUtilityTestCases
{
	/**
	 * Testing get Process ID
	 */
	@Test
	public void getPIDTest()
	{
		String pid1 = WorkAppUtility.getMyPid(null);
		String pid2 = WorkAppUtility.getMyPid(null);
		assertEquals(pid1, pid2);
	}
	
	/**
	 * Test Get Host Info
	 */
	@Test
	public void getHostInfoTest()
	{
		String hostname1 = WorkAppUtility.getMyHostInfo(null, false);
		String hostname2 = WorkAppUtility.getMyHostInfo(null, false);
		String hostIP1 = WorkAppUtility.getMyHostInfo(null, true);
		String hostIP2 = WorkAppUtility.getMyHostInfo(null, true);
		assertEquals(hostname1, hostname2);
		assertEquals(hostIP1, hostIP2);
	}

}
