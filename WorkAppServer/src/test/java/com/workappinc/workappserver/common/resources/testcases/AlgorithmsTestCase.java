package com.workappinc.workappserver.common.resources.testcases;

import static org.junit.Assert.*;

import org.junit.Test;

import com.workappinc.workappserver.common.resources.implementation.aglorithms.UnionFindAlgorithm;

/**
 * Common Test case class to test all available Algorithms 
 * @author sgdheeban
 *
 */
public class AlgorithmsTestCase {

	/**
	 * Testing Union Find Algorithm
	 */
	@Test
	public void testUnionFindAlgorithm() {
		UnionFindAlgorithm algo = new UnionFindAlgorithm(10);
		algo.union(1,2);
		algo.union(2,4);
		algo.union(4,5);
		algo.union(7,8);
		assertEquals(true,algo.find(5, 4));
		assertEquals(false, algo.find(5,8));
	}
	
}
