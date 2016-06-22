package com.workappinc.workappserver.common.resources.examples;

import java.io.IOException;

import com.workappinc.workappserver.common.exception.AlgorithmException;
import com.workappinc.workappserver.common.resources.implementation.aglorithms.UnionFindAlgorithm;

public class UnionFindAlgorithmExample {
	public static void main(String args[]) throws IOException {
		try
		{
			UnionFindAlgorithm algo = new UnionFindAlgorithm(10);
			algo.union(1, 2);
			algo.union(2, 4);
			algo.union(4, 5);
			algo.union(7, 8);

			System.out.println(" Connection between 5,8 before connect is : " + algo.find(5, 8));
			System.out.println(" Connection between 1,5 is : " + algo.find(1, 5));
			System.out.println(" Connection between 2,5 is : " + algo.find(2, 5));
			System.out.println(" Connection between 5,4 is : " + algo.find(5, 4));
			System.out.println(" Connection between 5,1 is : " + algo.find(5, 1));
			System.out.println(" Connection between 8,7 is : " + algo.find(8, 7));

			algo.union(2, 7);

			System.out.println(" Connection between 5,8 after connect is : " + algo.find(5, 8));
		}
		catch(AlgorithmException ex)
		{
			ex.printStackTrace();
		}
	}
}
