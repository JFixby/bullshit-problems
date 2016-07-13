
package com.google.java;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SumBinary {

	public static void main (final String[] args) {
		final Integer[] input = new Integer[] {1, -2, -1, 2};

		final ArrayList<Integer> numbers = new ArrayList<Integer>(Arrays.asList(input));
// Collections.shuffle(numbers);
		final ArrayList<Integer> list = new ArrayList<Integer>();

		sum(numbers, list, 0, 0);

//

	}

	private static void sum (final ArrayList<Integer> numbers, final List<Integer> list, final int targetSumm,
		final int startIndex) {
		final int currentSumm = current(list);

		if (currentSumm > targetSumm) {
			return;
		}
		if (currentSumm == targetSumm) {
			System.out.println(list);
			return;
		}
		for (int i = startIndex; i < numbers.size(); i++) {
			final Integer element = numbers.remove(i);
			list.add(element);
			sum(numbers, list, targetSumm, i);
			list.remove(list.size() - 1);
			numbers.add(i, element);
		}

	}

	private static int current (final List<Integer> list) {

		long sum = 0;
		for (int i = 0; i < list.size(); i++) {
			sum = sum + list.get(i);
		}
		return (int)sum;
	}

	private static void sum (final int[] array, final int a, final int len, final int sum) {
		if (len == 0) {
			return;
		}
		if (len == 1) {
			for (int i = a; a < len; i++) {
				if (array[i] == sum) {
					log(array[i]);
				}
			}
		}
	}

	private static void log (final int i) {
	}

}
