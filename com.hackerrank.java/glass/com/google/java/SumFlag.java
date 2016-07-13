
package com.google.java;

import java.util.ArrayList;

public class SumFlag {

	public static void main (final String[] args) {
		final int[] input = new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
		final int targetSumm = 8;

		final ArrayList<Integer> list = new ArrayList<Integer>();
		for (final BinaryFlag index = new BinaryFlag(input.length); index.isWithinLimit(); index.pp()) {
			if (sum(index, input, list) == targetSumm) {
				System.out.println(list);
			}
			list.clear();
		}
	}

	private static long sum (final BinaryFlag i, final int[] input, final ArrayList<Integer> list) {
		long sum = 0;
		for (int k = 0; k < input.length; k++) {
			if (i.isOn(k)) {
				final int num = input[k];
				sum = sum + num;
				list.add(num);
			}
		}
		return sum;
	}

	static class BinaryFlag {
		boolean[] flags;
		private boolean isWithinLimit;

		public BinaryFlag (final int limit) {
			this.flags = new boolean[limit];
			this.isWithinLimit = true;
		}

		public boolean isOn (final int k) {
			return this.flags[k];
		}

		public void pp () {
			this.pp(0);
		}

		private void pp (final int i) {
			if (i >= this.flags.length) {
				this.isWithinLimit = false;
				return;
			}
			if (!this.flags[i]) {
				this.flags[i] = true;
				return;
			}
			this.flags[i] = false;
			this.pp(i + 1);
		}

		public boolean isWithinLimit () {
			return this.isWithinLimit;
		}

	}

}
