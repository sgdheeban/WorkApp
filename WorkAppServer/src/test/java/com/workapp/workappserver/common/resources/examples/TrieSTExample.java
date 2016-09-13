package com.workapp.workappserver.common.resources.examples;

import java.io.IOException;
import java.util.Iterator;

import com.workapp.workappserver.common.resources.implementation.datastructures.RwayTrieST;
import com.workapp.workappserver.common.resources.implementation.datastructures.TernarySearchTrieST;

public class TrieSTExample
{
	public static void main(String args[]) throws IOException
	{

		System.out.println("####### RWAY TRIE ST #########");
		RwayTrieST<Integer> rwayTrie = new RwayTrieST<Integer>();

		rwayTrie.put("cat", 1);
		rwayTrie.put("bat", 2);
		rwayTrie.put("catch", 3);

		System.out.println("Get cat: " + rwayTrie.get("cat"));
		System.out.println("Get ca: " + rwayTrie.get("ca"));
		System.out.println("Get bat:" + rwayTrie.get("bat"));
		System.out.println("Get catch:" + rwayTrie.get("catch"));
		System.out.println("Get ctach:" + rwayTrie.get("ctach"));
		System.out.println("Get aaa:" + rwayTrie.get("aaa"));

		rwayTrie.put("bbb", 2);
		rwayTrie.put("aa", 5);
		System.out.println("Get aaa:" + rwayTrie.get("aaa"));
		System.out.println("Get aa:" + rwayTrie.get("aa"));
		System.out.println("Get bbb:" + rwayTrie.get("bbb"));

		System.out.println("Keys that match (.atch): ");

		Iterator<Object> it = rwayTrie.gKeysThatMatch(".atch");
		while (it.hasNext())
		{
			System.out.println(it.next());
		}

		rwayTrie.delete("aa");
		System.out.println("Get aa  after deletion - TST:" + rwayTrie.get("aa"));

		rwayTrie.put("aa", 9);
		System.out.println("Get aa  after reinsert - TST:" + rwayTrie.get("aa"));

		System.out.println("Iterator - Keys value");
		Iterator<Object> it3 = rwayTrie.keys();
		while (it3.hasNext())
		{
			System.out.println(it3.next());
		}

		System.out.println("Iterator - Keys with Prefix (ba)");
		Iterator<Object> it2 = rwayTrie.getKeysWithPrefix("ba");
		while (it2.hasNext())
		{
			System.out.println(it2.next());
		}

		System.out.println("Longest Matching Key (catc): " + rwayTrie.getLongestMatchingKey("catc"));

		System.out.println("########## TST ST ###########");

		TernarySearchTrieST<Integer> tst = new TernarySearchTrieST<Integer>();

		tst.put("cat", 1);
		tst.put("bat", 2);
		tst.put("catch", 3);

		System.out.println("Get cat - TST: " + tst.get("cat"));
		System.out.println("Get ca  - TST: " + tst.get("ca"));
		System.out.println("Get bat  - TST:" + tst.get("bat"));
		System.out.println("Get catch  - TST:" + tst.get("catch"));
		System.out.println("Get ctach  - TST:" + tst.get("ctach"));
		System.out.println("Get aaa  - TST:" + tst.get("aaa"));

		tst.put("bbb", 2);
		tst.put("aa", 5);
		System.out.println("Get aaa  - TST:" + tst.get("aaa"));
		System.out.println("Get bbb  - TST:" + tst.get("bbb"));
		System.out.println("Get aa  - TST:" + tst.get("aa"));

		System.out.println("Keys that match (.atch): ");
		Iterator<Object> it1 = tst.getKeysThatMatch(".atch");
		while (it1.hasNext())
		{
			System.out.println(it1.next());
		}

		tst.delete("aa");
		System.out.println("Get aa  after deletion - TST:" + tst.get("aa"));

		tst.put("aa", 9);
		System.out.println("Get aa  after reinsert - TST:" + tst.get("aa"));

		System.out.println("Iterator - Keys value");
		Iterator<Object> it31 = tst.keys();
		while (it31.hasNext())
		{
			System.out.println(it31.next());
		}

		System.out.println("Iterator - Keys with Prefix (ba)");
		Iterator<Object> it21 = tst.getkeysWithPrefix("ba");
		while (it21.hasNext())
		{
			System.out.println(it21.next());
		}

		System.out.println("Longest Matching Key (catc): " + tst.getLongestMatchingKey("catc"));
	}
}
