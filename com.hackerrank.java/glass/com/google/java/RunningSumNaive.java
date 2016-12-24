
package com.google.java;

import java.util.Arrays;

import com.jfixby.scarabei.api.debug.Debug;
import com.jfixby.scarabei.api.debug.DebugTimer;
import com.jfixby.scarabei.api.desktop.DesktopSetup;
import com.jfixby.scarabei.api.log.L;
import com.jfixby.scarabei.api.random.Random;

public class RunningSumNaive {

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
		final int targetSum = sum(array, 0, array.length - 1) + 1;
		L.d("targetSum", targetSum);
// targetSum = 55;

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
			for (int j = i; j < array.length; j++) {
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

	private static int sum (final int[] array, final int fromIndex, final int toIndex) {
		int sum = 0;
		for (int i = fromIndex; i <= toIndex; i++) {
			sum = sum + array[i];
			ops++;
		}
		return sum;
	}

}
