
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

		final int N = 100;
		final int[] array = new int[N];
		Random.setSeed(0);
		for (int i = 0; i < N; i++) {
			array[i] = Random.newInt(0, 100);
		}

// array = new int[] {25, 12, 14, 22, 19, 15, 10, 23};
		L.d("array(" + N + ")", Arrays.toString(array));
		final int targetSum = SUB_ARRAY_SUMM.valueOf(array, 0, array.length - 1) + 1;
// targetSum = 55;
		cahedSum.clear();
		L.d("targetSum", targetSum);

		final DebugTimer timer = Debug.newTimer();
		timer.reset();
		if (findSubsequence(array, targetSum)) {
			L.d("array [" + F + ", " + T + "]", Arrays.toString(Arrays.copyOfRange(array, F, T)));

		}
		timer.printTime("findSubsequence(" + N + ")");
		L.d("ops", ops + "");

	}

	static long ops = 0;
	private static int F;
	private static int T;

	private static boolean findSubsequence (final int[] array, final int targetSum) {
		if (array.length == 0) {
			return false;
		}
		for (int segmentSize = 0; segmentSize < array.length; segmentSize++) {
			for (int fromIndex = 0;; fromIndex++) {
				F = fromIndex;
				T = fromIndex + segmentSize;
				if (T >= array.length) {
					break;
				}
				if (SUB_ARRAY_SUMM.valueOf(array, fromIndex, T) == targetSum) {
					return true;
				}
			}
		}
		return false;
	}

	interface λ {
		public Integer valueOf (final int[] array, int fromIndex, int toIndex);
	}

	final static HashMap<Long, Integer> cahedSum = new HashMap<Long, Integer>();

	final static λ SUB_ARRAY_SUMM = memo(lambda());

	private static λ lambda () {
		return (A, fromIndex, toIndex) -> {
			ops++;
			if (toIndex == fromIndex) {
				return A[fromIndex];
			}
			final int mid = (fromIndex + toIndex) / 2;
			return SUB_ARRAY_SUMM.valueOf(A, fromIndex, mid) + SUB_ARRAY_SUMM.valueOf(A, mid + 1, toIndex);
		};
	}

	private static λ memo (final λ lambda) {
		if (!USE_CACHE) {
			return lambda;
		}
		return (A, fromIndex, toIndex) -> {
			final long key = ((long)fromIndex << 32) | toIndex;
			Integer sum = cahedSum.get(key);
			if (sum != null) {
				return sum;
			}
			sum = lambda.valueOf(A, fromIndex, toIndex);
			cahedSum.put(key, sum);
			return sum;
		};
	}

// private static int sum (final int[] array, final int fromIndex, final int toIndex) {
// if (USE_CACHE) {
//
//
// L.d("fromIndex", String.format("%08X", fromIndex));
// L.d(" toIndex", String.format("%08X", toIndex));
// L.d(" key", String.format("%016X", key));
// L.d();
//

// if (fromIndex != toIndex) {
// sum = sum(array, fromIndex + 1, toIndex) + array[fromIndex];
// cahedSum.put(key, sum);
// } else {
// sum = array[fromIndex];
// }
// ops++;
//
// return sum;
// }
//
// int sum = 0;
// for (int i = fromIndex; i <= toIndex; i++) {
// sum = sum + array[i];
// ops++;
// }
//
// return sum;
// }

}
