package com.workapp.workappserver.common.resources.testcases;

import static org.junit.Assert.*;

import org.junit.Test;

import com.workapp.workappserver.common.exception.AlgorithmException;
import com.workapp.workappserver.common.resources.implementation.aglorithms.UnionFindAlgorithm;
import com.workapp.workappserver.common.resources.implementation.datastructures.RwayTrieST;
import com.workapp.workappserver.common.resources.implementation.datastructures.TernarySearchTrieST;

/**
 * Common Test case class to test all available Algorithms
 * 
 * @author sgdheeban
 *
 */
public class AlgoDSTestCase {

	/**
	 * Testing Union Find Algorithm
	 */
	@Test
	public void testUnionFindAlgorithm() {
		try {
			UnionFindAlgorithm algo = new UnionFindAlgorithm(10);
			algo.union(1, 2);
			algo.union(2, 4);
			algo.union(4, 5);
			algo.union(7, 8);
			assertEquals(true, algo.find(5, 4));
			assertEquals(false, algo.find(5, 8));
		} catch (AlgorithmException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Testing Rway & TS Tries data structures
	 */
	@Test
	public void testTrieST() {

		RwayTrieST<Integer> rwayTrie = new RwayTrieST<Integer>();
		rwayTrie.put("cat", 1);
		rwayTrie.put("bat", 2);
		assertEquals(new Integer(1), rwayTrie.get("cat"));
		assertNotEquals(new Integer(1), rwayTrie.get("dat"));
		assertEquals(new Integer(2), rwayTrie.get("bat"));

		RwayTrieST<String> rwayTrie2 = new RwayTrieST<String>();
		rwayTrie2.put("cat", "cat");
		assertEquals("cat", rwayTrie2.get("cat"));
		assertNotEquals("dat", rwayTrie2.get("dat"));

		TernarySearchTrieST<Integer> tst = new TernarySearchTrieST<Integer>();
		tst.put("cat", 1);
		tst.put("aa", 5);
		assertEquals(new Integer(1), tst.get("cat"));
		assertNotEquals(new Integer(2), tst.get("dat"));
		assertEquals(new Integer(5), tst.get("aa"));

	}

}
