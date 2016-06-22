package com.workappinc.workappserver.common.resources.implementation.datastructures;

import com.workappinc.workappserver.common.resources.interfaces.IAlgorithm;

/**
 * TST is an improved memory efficient Trie variant
 * 
 * As fast or faster than a hash table in general
 * 
 * Search-Hit: O(L + Ln N) Search-Miss: O(Ln N) Insert: O(L + Ln N) Space:O(4N)
 * 
 * Better than Hash Table for most cases. Avoids Out-of-Memory error for large datasets over Rway Tries
 * 
 * @author dhgovindaraj
 * 
 */
public class TernarySearchTrie<Value> implements IAlgorithm {
	TernarySearchTrieNode root;

	public void put(String key, Value value) {
		root =  put(root, key, value, 0);
	}

	private TernarySearchTrieNode put(TernarySearchTrieNode node, String key, Value value, int d) {
		
		if(node == null)
		{
			node = new TernarySearchTrieNode();
			node.setC(key.charAt(d));
		}
		
		if(d==(key.length()-1))
		{
			node.setValue(value);
			return node;
		}
		
		if(node.getC() < key.charAt(d))
			node.setLeft(put(node.getLeft(), key, value, d));
		else if(node.getC() > key.charAt(d))
			node.setRight(put(node.getRight(), key, value, d));
		else
			node.setMiddle(put(node.getMiddle(), key, value, d+1));
		
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
		
		if(node == null)
			return null;
		
		if(d==(key.length()-1))
			return node;
		
		if(node.getC() < key.charAt(d))
			return get(node.getLeft(), key,d);
		else if (node.getC() > key.charAt(d))
			return get(node.getRight(), key, d);
		else
			return get(node.getMiddle(), key, d+1);
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