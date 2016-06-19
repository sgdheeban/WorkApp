package com.workappinc.workappserver.common.resources.implementation.aglorithms;

import java.io.IOException;
import com.workappinc.workappserver.common.resources.interfaces.IAlgorithm;

/**
 * Weighted Union Find with Path Compression enables to find connected components
 * Big-O: O(N+ m* log*N)
 * 
 * where N = no of objects
 * m = total no of union-find operations
 * log*N is a constant of 5 for real world
 * 
 * 
 * @author dhgovindaraj
 *
 */
public class UnionFindAlgorithm implements IAlgorithm
{
	private int[] id;
	private int[] size;
	
	// O(N)
	public UnionFindAlgorithm(int N) {
		id = new int[N];
		size = new int[N];
		for(int i=0; i< N; i++) {
			id[i] = i;
			size[i] = 1;
		}
	}

	// Implements path compression
	public int findRoot(int p) {
		int index=-1;
		while(id[p] != p) {
			size[id[p]] -= size[p]; // updating sub-tree sizes
			size[id[id[p]]] += size[p];
			id[p] = id[id[p]]; // path compression
			p = id[p];
		}
		index = p;
		return index;
	}
	
	//Implements weighted union
	public void union(int p, int q) {
		int pid = id[p];
		int qid = id[q];
		
		int proot = findRoot(p);
		int qroot = findRoot(q);
		
		int prootsize = size[proot];
		int qrootsize = size[qroot];
		
		if(prootsize <= qrootsize) {
			size[qroot] += size[proot];
			id[proot] = qroot;
		} else {
			size[proot] += size[qroot];
			id[qroot] = proot;
		}
	}
	
	public boolean find(int p, int q) {
		int proot = findRoot(p);
		int qroot = findRoot(q);
		
		if(proot == qroot) return true;
		else return false;
	}
}
