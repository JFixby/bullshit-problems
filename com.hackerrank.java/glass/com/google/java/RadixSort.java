
package com.google.java;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import org.junit.Test;

import com.jfixby.cmns.api.debug.Debug;
import com.jfixby.cmns.api.debug.DebugTimer;
import com.jfixby.cmns.api.desktop.DesktopSetup;
import com.jfixby.cmns.api.log.L;

public class RadixSort {

	@Test
	public void test () {
		DesktopSetup.deploy();
		final DebugTimer timer = Debug.newTimer();
		final Random r = new Random(System.currentTimeMillis());
		final int N = 100_000_000;
		final int B = 1_000;
		final int[] input = new int[N];
		for (int i = 0; i < input.length; i++) {
			input[i] = N - 1 - i;
// input[i] = r.nextInt(B);
			input[i] = r.nextInt(B);
		}
		// r.nextBytes(input);

		final int[] testInput = Arrays.copyOf(input, input.length);

// System.out.println("input: " + Arrays.toString(input));
		L.d("data prepared", N);
		timer.reset();
		Arrays.sort(input);
		timer.printTime("Arrays.sort()");
// System.out.println("sorted: " + Arrays.toString(input));
		timer.reset();
		sort(testInput);
		timer.printTime("RadixSort.sort()");
// System.out.println("tested: " + Arrays.toString(testInput) + " : " + call);
		final boolean equals = Arrays.equals(input, testInput);
		System.out.println("equals: " + equals);
		assertTrue(equals);

	}

	public static final void sort (final int[] array) {
		final int digitVariations = 10;
		final int numOfBuckets = digitVariations;
		final ArrayList<Integer>[] buckets = new ArrayList[numOfBuckets];
		for (int i = 0; i < numOfBuckets; i++) {
			buckets[i] = new ArrayList<Integer>();
		}

	}

}
