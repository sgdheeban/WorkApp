
package com.workappinc.workappserver.common.resources.implementation.datastructures;

import com.workappinc.workappserver.common.exception.AlgorithmException;
import com.workappinc.workappserver.common.resources.interfaces.IAlgorithm;

/**
 * Tries are memory efficient for fast indexing and retrieval of string kv pairs
 * 
 * As fast or faster than a hash table in general
 * 
 * Search-Hit: O(L) Search-Miss: O(Log-R N) Insert: O(L) Space:O(R+1(N))
 * 
 * @author dhgovindaraj
 * 
 */
public class RwayTrie<Value> implements IAlgorithm {
	RwayTrieNode root;

	public RwayTrie() {
		root = new RwayTrieNode();
	}

	public void put(String key, Value value) throws AlgorithmException {
		try
		{
			put(root, key, value, 0);
		}
		catch(RuntimeException ex) 
		{
			throw new AlgorithmException("Put method throws Exception in RwayTrie Datastructure", ex.getCause());
		}
		catch(Exception ex) 
		{
			throw new AlgorithmException("Put method throws Exception in RwayTrie Datastructure", ex.getCause());
		}
	}

	private RwayTrieNode put(RwayTrieNode node, String key, Value value, int d) {
		if (node == null) {
			node = new RwayTrieNode();
		}

		if (d == key.length() - 1) {
			node.setValue(value);
			return node;
		}

		node.setNext((int) (key.charAt(d)), put(node.getNext((int) (key.charAt(d))), key, value, d + 1));
		return node;
	}

	@SuppressWarnings("unchecked")
	public Value get(String key) throws AlgorithmException {
		try
		{
			Value returnObj = null;
			RwayTrieNode returnNode = get(root, key, 0);

			if (returnNode == null)
				return null;
			else
				returnObj = (Value) returnNode.getValue();

			return returnObj;
		}catch(RuntimeException ex) 
		{
			throw new AlgorithmException("Get method throws Exception in RwayTrie Datastructure", ex.getCause());
		}
		catch(Exception ex) 
		{
			throw new AlgorithmException("Get method throws Exception in RwayTrie Datastructure", ex.getCause());
		}
	}

	private RwayTrieNode get(RwayTrieNode node, String key, int d) {
		if (node == null) {
			return null;
		}

		if (d == key.length() - 1) {
			return node;
		}

		return get(node.getNext((int) (key.charAt(d))), key, d + 1);

	}

}

/**
 * RWayTrieNode private class denoting the Node Structure
 * 
 * @author dhgovindaraj
 * 
 */
class RwayTrieNode {
	private static final int R = 128; // ASCII character set
	private Object value; // Generic Object to be typecasted later
	private RwayTrieNode[] next = new RwayTrieNode[R];

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public RwayTrieNode getNext(int index) {
		return next[index];
	}

	public void setNext(int index, RwayTrieNode node) {
		this.next[index] = node;
	}
}