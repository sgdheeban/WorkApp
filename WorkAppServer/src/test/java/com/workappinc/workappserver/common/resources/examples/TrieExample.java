package com.workappinc.workappserver.common.resources.examples;

import java.io.IOException;

import com.workappinc.workappserver.common.exception.AlgorithmException;
import com.workappinc.workappserver.common.resources.implementation.datastructures.RwayTrie;
import com.workappinc.workappserver.common.resources.implementation.datastructures.TernarySearchTrie;

public class TrieExample {
	public static void main(String args[]) throws IOException {
		try
		{
			RwayTrie<Integer> rwayTrie = new RwayTrie<Integer>();

			rwayTrie.put("cat", 1);
			rwayTrie.put("bat", 2);
			rwayTrie.put("catch", 3);

			System.out.println("Get cat: " + rwayTrie.get("cat"));
			System.out.println("Get ca: " + rwayTrie.get("ca"));
			System.out.println("Get bat:" + rwayTrie.get("bat"));
			System.out.println("Get catch:" + rwayTrie.get("catch"));
			System.out.println("Get ctach:" + rwayTrie.get("ctach"));
			System.out.println("Get aaa:" + rwayTrie.get("aaa"));

			rwayTrie.put("aa", 5);
			System.out.println("Get aaa:" + rwayTrie.get("aaa"));
			System.out.println("Get aa:" + rwayTrie.get("aa"));
			
			RwayTrie<String> rwayTrie2 = new RwayTrie<String>();
			rwayTrie2.put("cat", "cat");
			System.out.println("Get String cat:" + rwayTrie2.get("cat"));
			System.out.println("Get String dat:" + rwayTrie2.get("dat"));
			
			TernarySearchTrie<Integer> tst = new TernarySearchTrie<Integer>();

			tst.put("cat", 1);
			tst.put("bat", 2);
			tst.put("catch", 3);

			System.out.println("Get cat - TST: " + tst.get("cat"));
			System.out.println("Get ca  - TST: " + tst.get("ca"));
			System.out.println("Get bat  - TST:" + tst.get("bat"));
			System.out.println("Get catch  - TST:" + tst.get("catch"));
			System.out.println("Get ctach  - TST:" + tst.get("ctach"));
			System.out.println("Get aaa  - TST:" + tst.get("aaa"));

			tst.put("aa", 5);
			System.out.println("Get aaa  - TST:" + tst.get("aaa"));
			System.out.println("Get aa  - TST:" + tst.get("aa"));
		
		}
		catch(AlgorithmException ex)
		{
			ex.printStackTrace();			
		}
	}
}
