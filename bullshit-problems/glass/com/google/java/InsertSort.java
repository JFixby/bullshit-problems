
package com.google.java;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Random;

import org.junit.Test;

public class InsertSort {

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
		insertSort(testInput);
		System.out.println("tested: " + Arrays.toString(testInput) + " : " + call);
		assertTrue(Arrays.equals(input, testInput));

	}

	static int call = 0;

	public static void insertSort (final byte[] A) {
		for (int top = 1; top < A.length; top++) { // [1,0,2,3];
			final byte current = A[top];
			findAndInsert(current, top, A);
		}

	}

	private static void findAndInsert (final byte current, final int top, final byte[] A) {
		for (int i = 0; i < top; i++) {
			final byte scan = A[i];
			if (scan > current) {// found
				shift(A, i, top);
				A[i] = current;
				return;
			}
		}
	}

	private static void shift (final byte[] A, final int from, final int to) {
		for (int k = to; k > from; k--) {
			A[k] = A[k - 1];
		}
	}

	private static void swap (final byte[] A, final int x, final int y) {
		final byte tmp = A[x];
		A[x] = A[y];
		A[y] = tmp;
		call++;
	}

}
