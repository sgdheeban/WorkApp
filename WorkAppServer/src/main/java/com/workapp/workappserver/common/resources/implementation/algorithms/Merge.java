package com.workapp.workappserver.common.resources.implementation.algorithms;

import com.workapp.workappserver.common.resources.interfaces.IAlgorithm;

/**
 * Standard implementation of Merge Sort - runs in O(N log N) worst-case
 * 
 * @author sgdheeban
 *
 */
public class Merge implements IAlgorithm {

	// This class should not be instantiated.
	private Merge() {
	}

	// stably merge a[lo .. mid] with a[mid+1 ..hi] using aux[lo .. hi]
	private static void merge(Comparable[] a, Comparable[] aux, int lo, int mid, int hi) {
		// precondition: a[lo .. mid] and a[mid+1 .. hi] are sorted subarrays
		assert isSorted(a, lo, mid);
		assert isSorted(a, mid + 1, hi);

		// copy to aux[]
		for (int k = lo; k <= hi; k++) {
			aux[k] = a[k];
		}

		// merge back to a[]
		int i = lo, j = mid + 1;
		for (int k = lo; k <= hi; k++) {
			if (i > mid)
				a[k] = aux[j++];
			else if (j > hi)
				a[k] = aux[i++];
			else if (less(aux[j], aux[i]))
				a[k] = aux[j++];
			else
				a[k] = aux[i++];
		}

		// postcondition: a[lo .. hi] is sorted
		assert isSorted(a, lo, hi);
	}

	// mergesort a[lo..hi] using auxiliary array aux[lo..hi]
	private static void sort(Comparable[] a, Comparable[] aux, int lo, int hi) {
		if (hi <= lo)
			return;
		int mid = lo + (hi - lo) / 2;
		sort(a, aux, lo, mid);
		sort(a, aux, mid + 1, hi);
		merge(a, aux, lo, mid, hi);
	}

	/**
	 * Rearranges the array in ascending order, using the natural order.
	 * 
	 * @param a
	 *            the array to be sorted
	 */
	public static void sort(Comparable[] a) {
		Comparable[] aux = new Comparable[a.length];
		sort(a, aux, 0, a.length - 1);
		assert isSorted(a);
	}

	/***************************************************************************
	 * Helper sorting function.
	 ***************************************************************************/

	// is v < w ?
	private static boolean less(Comparable v, Comparable w) {
		return v.compareTo(w) < 0;
	}

	/***************************************************************************
	 * Check if array is sorted - useful for debugging.
	 ***************************************************************************/
	private static boolean isSorted(Comparable[] a) {
		return isSorted(a, 0, a.length - 1);
	}

	private static boolean isSorted(Comparable[] a, int lo, int hi) {
		for (int i = lo + 1; i <= hi; i++)
			if (less(a[i], a[i - 1]))
				return false;
		return true;
	}

	/***************************************************************************
	 * Index mergesort.
	 ***************************************************************************/
	// stably merge a[lo .. mid] with a[mid+1 .. hi] using aux[lo .. hi]
	private static void merge(Comparable[] a, int[] index, int[] aux, int lo, int mid, int hi) {

		// copy to aux[]
		for (int k = lo; k <= hi; k++) {
			aux[k] = index[k];
		}

		// merge back to a[]
		int i = lo, j = mid + 1;
		for (int k = lo; k <= hi; k++) {
			if (i > mid)
				index[k] = aux[j++];
			else if (j > hi)
				index[k] = aux[i++];
			else if (less(a[aux[j]], a[aux[i]]))
				index[k] = aux[j++];
			else
				index[k] = aux[i++];
		}
	}

	/**
	 * Returns a permutation that gives the elements in the array in ascending
	 * order.
	 * 
	 * @param a
	 *            the array
	 * @return a permutation <tt>p[]</tt> such that <tt>a[p[0]]</tt>,
	 *         <tt>a[p[1]]</tt>, ..., <tt>a[p[N-1]]</tt> are in ascending order
	 */
	public static int[] indexSort(Comparable[] a) {
		int n = a.length;
		int[] index = new int[n];
		for (int i = 0; i < n; i++)
			index[i] = i;

		int[] aux = new int[n];
		sort(a, index, aux, 0, n - 1);
		return index;
	}

	// mergesort a[lo..hi] using auxiliary array aux[lo..hi]
	private static void sort(Comparable[] a, int[] index, int[] aux, int lo, int hi) {
		if (hi <= lo)
			return;
		int mid = lo + (hi - lo) / 2;
		sort(a, index, aux, lo, mid);
		sort(a, index, aux, mid + 1, hi);
		merge(a, index, aux, lo, mid, hi);
	}

	// print array to standard output
	public static void show(Comparable[] a) {
		for (int i = 0; i < a.length; i++) {
			System.out.println(a[i]);
		}
	}
}