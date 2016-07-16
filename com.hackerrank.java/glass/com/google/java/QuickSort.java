
package com.google.java;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Random;

import org.junit.Test;

public class QuickSort {

	public static void main (final String[] args) {

	}

	@Test
	public void test () {
		final Random r = new Random(System.currentTimeMillis());
		final byte[] input = new byte[40];
		for (int i = 0; i < input.length; i++) {
			input[i] = (byte)(-i);
		}
		// r.nextBytes(input);

		final byte[] testInput = Arrays.copyOf(input, input.length);

		System.out.println("input: " + Arrays.toString(input));
		Arrays.sort(input);
// System.out.println("sorted: " + Arrays.toString(input));
		quickSort(testInput);
		System.out.println("tested: " + Arrays.toString(testInput) + " : " + call);
		assertTrue(Arrays.equals(input, testInput));

	}

	static int call = 0;

	public static void quickSort (final byte[] input) {
		quickSort(input, 0, input.length);
	}

	public static void quickSort (final byte[] input, final int from, final int to) {

		final int howMany = to - from;
		if (howMany <= 1) {

			return;
		}

		final int mid = rotate(input, from, to);

		quickSort(input, mid + 1, to);
		quickSort(input, from, mid);

	}

	private static int rotate (final byte[] A, final int from, final int to) {

		final int pivotIndex = to - 1;
		final byte pivot = A[pivotIndex];// save pivot

		int left = from;
		int right = pivotIndex - 1;
		while (left < right) {
			if (A[left] <= pivot) {
				left++;
			} else {
				swap(A, left, right);
				right--;
			}

		}
		swap(A, left, pivotIndex);
		return left;
	}

	private static void swap (final byte[] A, final int x, final int y) {
		final byte tmp = A[x];
		A[x] = A[y];
		A[y] = tmp;
		call++;
	}

}
