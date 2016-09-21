
package com.google.java.seq;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Random;

import org.junit.Test;

import com.jfixby.cmns.api.debug.Debug;
import com.jfixby.cmns.api.debug.DebugTimer;
import com.jfixby.cmns.api.log.L;
import com.jfixby.red.desktop.DesktopSetup;

public class QuickSort {
	@Test
	public void main () {

		DesktopSetup.deploy();
		final DebugTimer timer = Debug.newTimer();
		final Random r = new Random(0);
		final int N = 2000;
		final int B = 10;
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
		sort(array, 0, array.length - 1);
	}

	private static void sort (final int[] array, final int indexFrom, final int indexTo) {
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

		final int mid = indexFrom + elementsToSort / 2;
		final int pivotValue = array[mid];
		final int tmpPivotPosition = indexTo;
		swap(array, tmpPivotPosition, mid);

		int pointerLeft = indexFrom;
		int pointerRight = indexTo - 1;

		while (pointerLeft <= pointerRight) {
			final int leftValue = array[pointerLeft];
// final int rightValue = array[pointerRight];
			if (leftValue < pivotValue) {
				pointerLeft++;
			} else {
				swap(array, pointerLeft, pointerRight);
				pointerRight--;
			}
		}

		final int pivotPosition = pointerLeft;
		swap(array, tmpPivotPosition, pivotPosition);

		sort(array, indexFrom, pivotPosition - 1);
		sort(array, pivotPosition + 1, indexTo);

	}

	private static void swap (final int[] array, final int a, final int b) {
		final int tmp = array[a];
		array[a] = array[b];
		array[b] = tmp;
	}

}
