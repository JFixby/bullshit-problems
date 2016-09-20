
package com.google.java.seq;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Random;

import org.junit.Test;

import com.jfixby.cmns.api.debug.Debug;
import com.jfixby.cmns.api.debug.DebugTimer;
import com.jfixby.cmns.api.log.L;
import com.jfixby.red.desktop.DesktopSetup;

public class RadixSort {
	@Test
	public void test () {
		DesktopSetup.deploy();
		final DebugTimer timer = Debug.newTimer();
		final Random r = new Random(System.currentTimeMillis());
		final int N = 10;
		final int B = 10;
		final int[] input = new int[N];
		for (int i = 0; i < input.length; i++) {
			input[i] = N - 1 - i;
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
		this.sort(testInput);
		timer.printTime("RadixSort.sort()");
// System.out.println("tested: " + Arrays.toString(testInput) + " : " + call);
		final boolean equals = Arrays.equals(input, testInput);
		System.out.println("equals: " + equals);
		assertTrue(equals);

	}

	private void sort (final int[] input) {
		if (input.length < 2) {
			return;
		}

		int max = input[0];
		for (int i = 0; i < input.length; i++) {
			max = Math.max(max, input[i]);
		}
		final int MAX_DIGIT = (max + "").length();

		for (int digit = 0; digit < MAX_DIGIT; digit++) {
			final int N = input.length - 1;
			for (int i = 0; i < N; i++) {
				final int digitI = digitOf(input[i], digit);
				final int digitIpp = digitOf(input[i + 1], digit);
				if (digitI > digitIpp) {
					swap(input, i, i + 1);
				}
			}
			L.d("digit = " + digit, input);
		}

	}

	static final private int digitOf (final int value, final int digit) {
		return (value / pow(10, digit)) % 10;
	}

	final private static int pow (final int x, final int power) {
		if (power == 0) {
			return 1;
		}
		if (power == 1) {
			return x;
		}
		if (power == 2) {
			return x * x;
		}
		if (power == 3) {
			return x * x * x;
		}
		if (power == 4) {
			return x * x * x * x;
		}
		if (power == 5) {
			return x * x * x * x * x;
		}
		if (power % 2 == 0) {
			final int n = power >> 1;
			final int xp = pow(x, n);
			return xp * xp;
		}

		final int n = (power - 1) >> 1;
		final int xp = pow(x, n);
		return xp * xp * x;

	}

	static int tmp;

	public static final void swap (final int[] array, final int indexA, final int indexB) {
		tmp = array[indexA];
		array[indexA] = array[indexB];
		array[indexB] = tmp;
	}

}
