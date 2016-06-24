package com.workappinc.workappserver.common.resources.implementation.datastructures;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import com.workappinc.workappserver.common.resources.interfaces.IAlgorithm;

/**
 * TST is an improved memory efficient Trie variant used to build a Symbol
 * Table.
 * 
 * As fast or faster than a hash table in general
 * 
 * Search-Hit: O(L + Ln N) Search-Miss: O(Ln N) Insert: O(L + Ln N) Space:O(4N)
 * 
 * Better than Hash Table for most cases. Avoids Out-of-Memory error for large
 * datasets over Rway Tries
 * 
 * @author dhgovindaraj
 * 
 */
public class TernarySearchTrieST<Value> implements IAlgorithm {
	TernarySearchTrieNode root;

	public void put(String key, Value value) {
		root = put(root, key, value, 0);
	}

	private TernarySearchTrieNode put(TernarySearchTrieNode node, String key, Value value, int d) {
		if (node == null) {
			node = new TernarySearchTrieNode();
			node.setC(key.charAt(d));
		}

		if (d == (key.length() - 1)) {
			node.setValue(value);
			return node;
		}

		if (key.charAt(d) < node.getC())
			node.setLeft(put(node.getLeft(), key, value, d));
		else if (key.charAt(d) > node.getC())
			node.setRight(put(node.getRight(), key, value, d));
		else
			node.setMiddle(put(node.getMiddle(), key, value, d + 1));

		return node;
	}

	@SuppressWarnings("unchecked")
	public Value get(String key) {
		Value returnObj = null;
		TernarySearchTrieNode returnNode = get(root, key, 0);
		if (returnNode == null)
			return null;
		else
			returnObj = (Value) returnNode.getValue();
		return returnObj;
	}

	private TernarySearchTrieNode get(TernarySearchTrieNode node, String key, int d) {
		if (node == null)
			return null;

		if (d == (key.length() - 1))
			return node;

		if (key.charAt(d) < node.getC())
			return get(node.getLeft(), key, d);
		else if (key.charAt(d) > node.getC())
			return get(node.getRight(), key, d);
		else
			return get(node.getMiddle(), key, d + 1);
	}

	private void collect(TernarySearchTrieNode x, StringBuilder prefix, Queue<Object> queue) {
		if (x == null)
			return;
		collect(x.getLeft(), prefix, queue);
		if (x.getValue() != null)
			queue.add(prefix.toString() + x.getC());
		collect(x.getMiddle(), prefix.append(x.getC()), queue);
		prefix.deleteCharAt(prefix.length() - 1);
		collect(x.getRight(), prefix, queue);
	}

	public Iterator<Object> keys() {
		Queue<Object> queue = new LinkedList<Object>();
		collect(root, new StringBuilder(), queue);
		return queue.iterator();
	}

	public Iterator<Object> getkeysWithPrefix(String prefix) {
		Queue<Object> queue = new LinkedList<Object>();
		TernarySearchTrieNode x = get(root, prefix, 0);
		if (x == null)
			return queue.iterator();
		if (x.getValue() != null)
			queue.add(prefix);
		collect(x.getMiddle(), new StringBuilder(prefix), queue);
		return queue.iterator();
	}

	public Object getLongestMatchingKey(String query) {
		if (query == null || query.length() == 0)
			return null;
		int length = 0;
		TernarySearchTrieNode x = root;
		int i = 0;
		while (x != null && i < query.length()) {
			char c = query.charAt(i);
			if (c < x.getC())
				x = x.getLeft();
			else if (c > x.getC())
				x = x.getRight();
			else {
				i++;
				if (x.getValue() != null)
					length = i;
				x = x.getMiddle();
			}
		}
		return query.substring(0, length);
	}

	public void delete(String key) {
		root = delete(root, key, 0);
	}

	private TernarySearchTrieNode delete(TernarySearchTrieNode x, String key, int d) {
		if (x == null)
			return null;
		if (d == key.length() - 1) {
			if (x.getValue() != null)
				;
			x.setValue(null);
		}

		if (d < key.length() - 1) {
			char c = key.charAt(d);
			if (c < x.getC())
				x.setLeft(delete(x.getLeft(), key, d));
			else if (c > x.getC())
				x.setRight(delete(x.getRight(), key, d));
			else if (c == x.getC())
				x.setMiddle(delete(x.getMiddle(), key, d + 1));
		}

		// remove subtrie rooted at x if it is completely empty
		if (x.getValue() != null)
			return x;
		if (x.getLeft() != null || x.getMiddle() != null || x.getRight() != null)
			return x;
		return null;
	}

	public Iterator<Object> getKeysThatMatch(String pattern) {
		Queue<Object> queue = new LinkedList<Object>();
		collect(root, pattern, new StringBuilder(), 0, queue);
		return queue.iterator();
	}

	private void collect(TernarySearchTrieNode n, String prefix, StringBuilder str, int i, Queue<Object> queue) {
		if (n == null)
			return;

		char c = prefix.charAt(i);

		if (c == '.' || c < n.getC())
			collect(n.getLeft(), prefix, str, i, queue);

		if (c == '.' || c == n.getC()) {

			if (i == prefix.length() - 1 && n.getValue() != null)
				queue.add(str.toString() + n.getC());

			if (i < prefix.length() - 1) {
				collect(n.getMiddle(), prefix, str.append(n.getC()), i + 1, queue);
				str.deleteCharAt(str.length() - 1);

			}
		}

		if (c == '.' || c > n.getC())
			collect(n.getRight(), prefix, str, i, queue);
	}

}

/**
 * TernarySearchTrieNode private class denoting the Node Structure
 * 
 * @author dhgovindaraj
 * 
 */
class TernarySearchTrieNode {
	private char c;
	private Object value; // Generic Object to be typecasted later
	private TernarySearchTrieNode right, middle, left;

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public TernarySearchTrieNode getRight() {
		return right;
	}

	public void setRight(TernarySearchTrieNode right) {
		this.right = right;
	}

	public TernarySearchTrieNode getMiddle() {
		return middle;
	}

	public void setMiddle(TernarySearchTrieNode middle) {
		this.middle = middle;
	}

	public TernarySearchTrieNode getLeft() {
		return left;
	}

	public void setLeft(TernarySearchTrieNode left) {
		this.left = left;
	}

	public char getC() {
		return c;
	}

	public void setC(char c) {
		this.c = c;
	}

}