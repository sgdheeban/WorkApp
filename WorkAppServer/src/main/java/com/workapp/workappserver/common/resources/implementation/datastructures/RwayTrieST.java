package com.workapp.workappserver.common.resources.implementation.datastructures;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

/**
 * RwayTrie is a data structure as fast or faster than a hash table in general,
 * used to build a Symbol Table.
 * 
 * Search-Hit: O(L ) Search-Miss: O(L) Insert: O(L) Space:O((R+1)N)
 * 
 * Better than Hash Table for most cases.
 * 
 * But, throws Out-of-Memory error for large R i.e., Extended Ascii etc.
 * 
 * @author dhgovindaraj
 * 
 */
public class RwayTrieST<Value>
{
	private static final int R = 128;
	private Node root;
	private int N;

	private static class Node
	{
		private Object val;
		private Node[] next = new Node[R];
	}

	public RwayTrieST()
	{
	}

	public Value get(String key)
	{
		Node x = get(root, key, 0);
		if (x == null)
			return null;
		return (Value) x.val;
	}

	public boolean contains(String key)
	{
		return get(key) != null;
	}

	private Node get(Node x, String key, int d)
	{
		if (x == null)
			return null;
		if (d == key.length())
			return x;
		char c = key.charAt(d);
		return get(x.next[c], key, d + 1);
	}

	public void put(String key, Value val)
	{
		if (val == null)
			delete(key);
		else root = put(root, key, val, 0);
	}

	private Node put(Node x, String key, Value val, int d)
	{
		if (x == null)
			x = new Node();
		if (d == key.length())
		{
			if (x.val == null)
				N++;
			x.val = val;
			return x;
		}
		char c = key.charAt(d);
		x.next[c] = put(x.next[c], key, val, d + 1);
		return x;
	}

	public int size()
	{
		return N;
	}

	public boolean isEmpty()
	{
		return size() == 0;
	}

	public Iterator<Object> keys()
	{
		return getKeysWithPrefix("");
	}

	public Iterator<Object> getKeysWithPrefix(String prefix)
	{
		Queue<Object> results = new LinkedList<Object>();
		Node x = get(root, prefix, 0);
		collect(x, new StringBuilder(prefix), results);
		return results.iterator();
	}

	private void collect(Node x, StringBuilder prefix, Queue<Object> results)
	{
		if (x == null)
			return;
		if (x.val != null)
			results.add(prefix.toString());
		for (char c = 0; c < R; c++)
		{
			prefix.append(c);
			collect(x.next[c], prefix, results);
			prefix.deleteCharAt(prefix.length() - 1);
		}
	}

	public Iterator<Object> gKeysThatMatch(String pattern)
	{
		Queue<Object> results = new LinkedList<Object>();
		collect(root, new StringBuilder(), pattern, results);
		return results.iterator();
	}

	private void collect(Node x, StringBuilder prefix, String pattern, Queue<Object> results)
	{
		if (x == null)
			return;
		int d = prefix.length();
		if (d == pattern.length() && x.val != null)
			results.add(prefix.toString());
		if (d == pattern.length())
			return;
		char c = pattern.charAt(d);
		if (c == '.')
		{
			for (char ch = 0; ch < R; ch++)
			{
				prefix.append(ch);
				collect(x.next[ch], prefix, pattern, results);
				prefix.deleteCharAt(prefix.length() - 1);
			}
		}
		else
		{
			prefix.append(c);
			collect(x.next[c], prefix, pattern, results);
			prefix.deleteCharAt(prefix.length() - 1);
		}
	}

	public String getLongestMatchingKey(String query)
	{
		int length = longestPrefixOf(root, query, 0, -1);
		if (length == -1)
			return null;
		else return query.substring(0, length);
	}

	private int longestPrefixOf(Node x, String query, int d, int length)
	{
		if (x == null)
			return length;
		if (x.val != null)
			length = d;
		if (d == query.length())
			return length;
		char c = query.charAt(d);
		return longestPrefixOf(x.next[c], query, d + 1, length);
	}

	public void delete(String key)
	{
		root = delete(root, key, 0);
	}

	private Node delete(Node x, String key, int d)
	{
		if (x == null)
			return null;
		if (d == key.length())
		{
			if (x.val != null)
				N--;
			x.val = null;
		}
		else
		{
			char c = key.charAt(d);
			x.next[c] = delete(x.next[c], key, d + 1);
		}

		// remove subtrie rooted at x if it is completely empty
		if (x.val != null)
			return x;
		for (int c = 0; c < R; c++)
			if (x.next[c] != null)
				return x;
		return null;
	}
}
