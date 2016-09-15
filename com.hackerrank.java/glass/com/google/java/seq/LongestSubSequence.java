
package com.google.java.seq;

import com.jfixby.cmns.api.arrays.Arrays;
import com.jfixby.cmns.api.collections.Collections;
import com.jfixby.cmns.api.collections.List;
import com.jfixby.red.desktop.DesktopSetup;

public class LongestSubSequence {

	public static void main (final String[] args) {
		DesktopSetup.deploy();
		final int[] input = new int[] {33, 34, 10, 11, 12, 13, 14, 15, 16, 0, 1, 0, 1, 2, 0, 1, 2, 3, 17, 18};

		final LongestSubsequenContainer container = new LongestSubsequenContainer();

		for (int i = 0; i < input.length; i++) {
			final int next = input[i];

			final int[] longest = container.getLongestThatEndsBefore(next);

			final int[] grow = append(longest, next);

			container.store(grow);
		}

		final List<Integer> longest = Arrays.newIntsList(container.getLongestThatEndsBefore(Integer.MAX_VALUE));
		longest.print("longest");

	}

	private static int[] append (int[] a, final int e) {
		a = java.util.Arrays.copyOf(a, a.length + 1);
		a[a.length - 1] = e;
		return a;
	}

	private static List<Integer> longestSubSequence (final int[] input, final int from, final int to) {
		if (from == to) {
			return Collections.newList(input[from]);
		}

		final List<Integer> longestSub = longestSubSequence(input, from, to - 1);
		final Integer last = longestSub.getLast();
		final int next = input[to];
		if (last > next) {
			return longestSub;
		} else {
			longestSub.add(next);
			return longestSub;
		}
	}

}
