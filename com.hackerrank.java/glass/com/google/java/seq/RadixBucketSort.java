
package com.google.java.seq;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import org.junit.Test;

import com.jfixby.cmns.api.debug.Debug;
import com.jfixby.cmns.api.debug.DebugTimer;
import com.jfixby.cmns.api.log.L;
import com.jfixby.red.desktop.DesktopSetup;

public class RadixBucketSort {
	static int call = 0;

	@Test
	public void test () {
		DesktopSetup.deploy();
		final DebugTimer timer = Debug.newTimer();
		final Random r = new Random(System.currentTimeMillis());
		final int N = 10000000;
		final int B = Integer.MAX_VALUE / 100;
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
		sort(testInput);
		timer.printTime("RadixSort.sort()");
// System.out.println("tested: " + Arrays.toString(testInput) + " : " + call);
		final boolean equals = Arrays.equals(input, testInput);
		System.out.println("equals: " + equals);
		assertTrue(equals);

	}

	public final static void sort (final int[] input) {

		final int DIGIT_SIZE = 100;
		final int N = DIGIT_SIZE;
		final Bucket[] buckets = new Bucket[N];
		for (int i = 0; i < N; i++) {
			buckets[i] = new Bucket(i);
		}
		int max = Integer.MIN_VALUE;
		for (int i = 0; i < input.length; i++) {
			final int value = input[i];
			max = Math.max(max, value);
		}

// Arrays.sort(input);
		int keyLevel = 0;
		final int keyLevelMax = (max + "").length() + 1;
		while (keyLevel < keyLevelMax) {
			final int elephant = pow(DIGIT_SIZE, keyLevel);
			for (int i = 0; i < input.length; i++) {
				final int value = input[i];

// final int key = 0xff & (value >> (8 * keyLevel));
				final int key = (value / elephant) % DIGIT_SIZE;
				buckets[key].values.add(value);
			}
			keyLevel++;
// L.d("", buckets);
			int index = 0;
			for (int bucketId = 0; bucketId < N; bucketId++) {
				final Bucket bucket = buckets[bucketId];
				for (int k = 0; k < bucket.values.size(); k++) {
					final int kth = bucket.values.get(k);
					input[index] = kth;
					index++;
				}
				bucket.values.clear();
// L.d("index", index);
			}
		}
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

	static class Bucket {
		public Bucket (final int bucketId) {
			this.bucketId = bucketId;
		}

		int bucketId = 0;
		final ArrayList<Integer> values = new ArrayList<Integer>(10000);

		@Override
		public String toString () {
			return "Bucket[" + this.bucketId + "] : " + this.values;
		}

	}

}
