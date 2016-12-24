
package com.google.java;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

import org.junit.Test;

import com.jfixby.scarabei.api.debug.Debug;
import com.jfixby.scarabei.api.debug.DebugTimer;
import com.jfixby.scarabei.api.desktop.DesktopSetup;
import com.jfixby.scarabei.api.log.L;

public class CountingSort {

	@Test
	public void test () {
		DesktopSetup.deploy();
		final DebugTimer timer = Debug.newTimer();
		final Random r = new Random(System.currentTimeMillis());
		final int N = 100_000_000;
		final int B = 1_000_000;
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

	static class Counter {
		final HashMap<Integer, Integer> map;
		Integer max = null;
		Integer min = null;

		public Counter (final int length) {
			this.map = new HashMap<Integer, Integer>(128);
		}

		final public void countAll (final int[] input) {
			for (int i = 0; i < input.length; i++) {
				this.count(input[i]);
			}
		}

		final public void count (final int key) {
			if (this.max == null) {
				this.max = key;
			} else {
				this.max = Math.max(key, this.max);
			}

			if (this.min == null) {
				this.min = key;
			} else {
				this.min = Math.min(key, this.min);
			}

			final Integer occurences = this.map.get(key);
			if (occurences == null) {
				this.map.put(key, 1);
				return;
			}
			this.map.put(key, occurences + 1);
		}

		final public void print () {
			L.d("---[" + this.min + " , " + this.max + "]--------------");
			for (int i = this.min; i <= this.max; i++) {
				final int numOfI = this.numberOf(i);
				if (numOfI != 0) {
					L.d("    <" + i + "> = " + numOfI);
				}
			}
		}

		final public int numberOf (final int i) {
			final Integer num = this.map.get(i);
			if (num != null) {
				return num;
			}
			return 0;
		}

		final public int getMin () {
			Debug.checkNull(this.min);
			return this.min;
		}

		final public int getMax () {
			Debug.checkNull(this.max);
			return this.max;
		}
	}

	public static final void sort (final int[] input) {
		if (input.length <= 1) {
			return;
		}
		final Counter counter = new Counter(input.length);
		counter.countAll(input);
// counter.print();
		int index = 0;
		final int min = counter.getMin();
		final int max = counter.getMax();
		for (int i = min; i <= max; i++) {
			final int numOfI = counter.numberOf(i);
			for (int k = 0; k < numOfI; k++) {
				input[index] = i;
				index++;
			}
		}

	}

}
