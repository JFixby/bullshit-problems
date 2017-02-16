
package com.google.java.seq;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Random;

import org.junit.Test;

import com.jfixby.scarabei.api.debug.Debug;
import com.jfixby.scarabei.api.debug.DebugTimer;
import com.jfixby.scarabei.api.desktop.ScarabeiDesktop;
import com.jfixby.scarabei.api.log.L;

public class MergeSort {
	@Test
	public void main () {

		ScarabeiDesktop.deploy();
		final DebugTimer timer = Debug.newTimer();
		final Random r = new Random(0);
		final int N = 10000;
		final int B = 100;
		final int[] input = new int[N];
		for (int i = 0; i < input.length; i++) {
			input[i] = N - 1 - i;
			input[i] = r.nextInt();
		}
		// r.nextBytes(input);

		final int[] testInput = Arrays.copyOf(input, input.length);

		System.out.println("input: " + Arrays.toString(input));
		L.d("data prepared", N);
		timer.reset();
		Arrays.sort(input);
		timer.printTime("Arrays.sort()");
		System.out.println("sorted: " + Arrays.toString(input));
		timer.reset();
		sort(testInput);
		timer.printTime("RadixSort.sort()");
		System.out.println("tested: " + Arrays.toString(testInput) + " : ");
		final boolean equals = Arrays.equals(input, testInput);
		System.out.println("equals: " + equals);
		assertTrue(equals);

	}

	public static void sort (final int[] array) {
		final int[] tmp = new int[array.length];
		sort(array, 0, array.length - 1, tmp);
	}

	private static void sort (final int[] array, final int indexFrom, final int indexTo, final int[] tmp) {
		if (indexTo <= indexFrom) {
			return;
		}

		final int elementsToSort = indexTo - indexFrom + 1;

		if (elementsToSort == 2) {
			final int leftValue = array[indexFrom];
			final int rightValue = array[indexTo];
			if (leftValue > rightValue) {
				swap(array, indexFrom, indexTo);
			}
			return;
		}

		final int midIndex = indexFrom + elementsToSort / 2;
		sort(array, indexFrom, midIndex, tmp);
		sort(array, midIndex + 1, indexTo, tmp);
		merge(array, indexFrom, midIndex, midIndex + 1, indexTo, tmp);

	}

	private static void merge (final int[] array, final int indexA, final int indexATo, final int indexB, final int indexBTo,
		final int[] tmp) {
		final int numOfA = indexATo - indexA + 1;

		System.arraycopy(array, indexA, tmp, 0, numOfA);

		int currentIndex = indexA;

		int pointerA = 0;
		int pointerB = indexB;

		for (; pointerA < numOfA && pointerB <= indexBTo;) {
			final int valueA = tmp[pointerA];
			final int valueB = array[pointerB];
			if (valueA <= valueB) {
				array[currentIndex] = valueA;
				pointerA++;
			} else {
				array[currentIndex] = valueB;
				pointerB++;
			}
			currentIndex++;
		}

		while (pointerA < numOfA) {
			final int valueA = tmp[pointerA];
			array[currentIndex] = valueA;
			pointerA++;
			currentIndex++;
		}

	}

	private static void swap (final int[] array, final int a, final int b) {
		final int tmp = array[a];
		array[a] = array[b];
		array[b] = tmp;
	}

}
