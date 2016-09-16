
package com.google.java.seq;

import com.jfixby.red.desktop.DesktopSetup;

public class LongestSubSequence {

	public static void main (final String[] args) {
		DesktopSetup.deploy();
		final int[] input = new int[] {33, 34, 10, 11, 12, 13, 14, 15, 16, 0, 1, 0, 1, 2, 0, 1, 2, 3, 17, 18, 35};

		final LongestSubsequenContainer container = new LongestSubsequenContainer();

		for (int i = 0; i < input.length; i++) {
			final int next = input[i];
			container.appendLongest(next);
		}

		container.printLongest();

	}

// private static int[] append (int[] a, final int e) {
// a = java.util.Arrays.copyOf(a, a.length + 1);
// a[a.length - 1] = e;
// return a;
// }

}
