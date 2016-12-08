
package com.google.java;

import java.util.Arrays;

import com.jfixby.cmns.api.debug.Debug;
import com.jfixby.cmns.api.debug.DebugTimer;
import com.jfixby.cmns.api.desktop.DesktopSetup;
import com.jfixby.cmns.api.log.L;
import com.jfixby.cmns.api.random.Random;

public class CountSort {

	public static void main (final String[] args) {
		DesktopSetup.deploy();
		final int N = 100_000;
		final int[] values = new int[N];
		Random.setSeed(0);
		final int TOP = N;
		for (int i = 0; i < N; i++) {
			values[i] = Random.newInt(0, TOP);
		}
		final DebugTimer timer = Debug.newTimer();
		final int[] copy = Arrays.copyOf(values, N);

		printArray("values", values);

		timer.reset();
		Arrays.sort(copy);
		timer.printTime("Arrays.sort");
// printArray("copy ", copy);

		timer.reset();
		CountSort.sort(values);
		timer.printTime("CountSort.sort");
// printArray("sorted", values);
		L.d("EQUAL", Arrays.equals(values, copy));

	}

	private static void sort (final int[] values) {
		final int[] occurences = new int[findTopIndex(values) + 1 + 1 + 0];// size--offset-check(last==0)
// L.d("occurences", occurences.length);
		for (int i = 0; i < values.length; i++) {
			occurences[values[i] + 1]++;
		}
// printArray("occurences", occurences);

		for (int i = 1; i < occurences.length; i++) {
			occurences[i] = occurences[i - 1] + occurences[i];
		}
// printArray("offsets", occurences);

		final int[] tmp = new int[values.length];
		for (int i = 0; i < values.length; i++) {
			final int value = values[i];
			final int tmpIndex = occurences[value];
			tmp[tmpIndex] = value;
			occurences[value]++;

		}
// printArray("tmp ", tmp);
		System.arraycopy(tmp, 0, values, 0, values.length);
	}

	private static int findTopIndex (final int[] values) {
		int top = 0;
		for (int i = 0; i < values.length; i++) {
			if (values[i] > top) {
				top = values[i];
			}
		}
		return top;
	}

	private static void printArray (final String tag, final int[] values) {
		System.out.println(tag + " > " + Arrays.toString(values));
	}

}
