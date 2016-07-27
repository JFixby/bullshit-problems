
package com.google.java;

import java.util.Arrays;
import java.util.HashMap;

import com.jfixby.cmns.api.debug.Debug;
import com.jfixby.cmns.api.debug.DebugTimer;
import com.jfixby.cmns.api.log.L;
import com.jfixby.cmns.api.random.Random;
import com.jfixby.red.desktop.DesktopSetup;

public class RunningSum {
	static final boolean USE_CACHE = true;

	public static final void main (final String[] args) {

		DesktopSetup.deploy();

		final int N = 4_000;
		final int[] array = new int[N];
		Random.setSeed(0);
		for (int i = 0; i < N; i++) {
			array[i] = Random.newInt(0, 100);
		}

		L.d("array(" + N + ")", Arrays.toString(array));
		final int targetSum = sum(array, 0, N - 1) + 1;
		L.d("targetSum", targetSum);

		final DebugTimer timer = Debug.newTimer();
		timer.reset();
		if (findSubsequenceBrut(array, targetSum)) {
			L.d("array [" + F + ", " + T + "]", Arrays.toString(Arrays.copyOfRange(array, F, T)));

		}
		timer.printTime("findSubsequenceBrut(" + N + ")");
		L.d("ops", ops + "");

	}

	static long ops = 0;
	private static int F;
	private static int T;

	private static boolean findSubsequenceBrut (final int[] array, final int targetSum) {
		if (array.length == 0) {
			return false;
		}
		for (int i = 0; i < array.length; i++) {
			for (int j = array.length - 1; j >= i; j--) {
				final int sum = sum(array, i, j);
				if (sum == targetSum) {
					F = i;
					T = j;
					return true;
				}
			}
		}
		return false;
	}

	final static HashMap<Long, Integer> cahedSum = new HashMap<Long, Integer>();

	private static int sum (final int[] array, final int fromIndex, final int toIndex) {
		if (USE_CACHE) {
			final long key = ((long)fromIndex << 32) | toIndex;

			L.d("fromIndex", String.format("%08X", fromIndex));
			L.d(" toIndex", String.format("%08X", toIndex));
			L.d(" key", String.format("%016X", key));
			L.d();

			Integer sum = cahedSum.get(key);
			if (sum != null) {
				return sum;
			}
			if (fromIndex != toIndex) {
				sum = sum(array, fromIndex + 1, toIndex) + array[fromIndex];
				cahedSum.put(key, sum);
			} else {
				sum = array[fromIndex];
			}
			ops++;

			return sum;
		}

		int sum = 0;
		for (int i = fromIndex; i <= toIndex; i++) {
			sum = sum + array[i];
			ops++;
		}

		return sum;
	}

}
