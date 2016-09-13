package com.workapp.workappserver.common.resources.examples;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import com.workapp.workappserver.common.resources.implementation.algorithms.Merge;

public class MergeSortExample {

	public static Integer[] readNumsFromCommandLine() {
		Scanner scanner = new Scanner(System.in);
		ArrayList<Integer> list = new ArrayList<Integer>();
		System.out.println("Enter block size");
		int blockSize = scanner.nextInt();
		System.out.println("Enter data rows:");
		int count = 0;
		while (count < blockSize) {
			list.add(scanner.nextInt());
			count++;
		}
		System.out.println("\nThe data you entered is:");
		System.out.println(list);
		Integer[] stockArr = new Integer[list.size()];
		stockArr = list.toArray(stockArr);
		scanner.close();
		return stockArr;
	}

	public static void main(String args[]) throws IOException {
		/**
		 * Reads in a sequence of strings from standard input; mergesorts them;
		 * and prints them to standard output in ascending order.
		 */
		Integer[] a = readNumsFromCommandLine();
		Merge.sort(a);
		Merge.show(a);
	}
}
