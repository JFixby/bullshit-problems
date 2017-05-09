
package com.google.java;

import java.util.Arrays;
import java.util.HashMap;

public class Sum2 {

	public static void main (final String[] args) {

		// 1. take input
		final int[] input = new int[] {1, 2, 3, 4, 5, 0, 0, -2, -7, -2, -3, -5};
// final int[] input = new int[] {-1, 0, 0, 0, 0, 1, 0};

// 2. sort?
// Arrays.sort(input);// optional?
// array form [--------000000000+++++++++++++], also: [000000000+++++++++++++], [--------000000000], [--------+++++++++++++]
// and etc...

		// Pointers set: [--------FNN{0}0000000LNP{0}+++++++++++++]
		final int N = input.length;
		final int[] brothers = new int[N];
		final int targetSum = -1;
		add(input, -targetSum, brothers);

		final int lastNonPositive = lastNonPositive(brothers);
		final int firstNonNegative = firstNonNegative(brothers);

		final int left = firstNonNegative;
		final int right = lastNonPositive;
		while (left < right) {

		}

		log("firstNonNegative", firstNonNegative);
		log("lastNonPositive", lastNonPositive);

// final HashMap<Integer, Integer> valueToNumberOfValues = collect(input);

		log("" + Arrays.toString(input));
		log("" + Arrays.toString(brothers));

	}

	private static void add (final int[] input, final int add, final int[] output) {
		for (int i = 0; i < input.length; i++) {
			output[i] = input[i] + add;
		}
	}

	private static int lastNonPositive (final int[] input) {
		int lastNonPositive = -1;
		for (int i = 0; i < input.length; i++) {
			if (input[i] <= 0) {
				lastNonPositive = i;
			}
		}
		return lastNonPositive;
	}

	private static int firstNonNegative (final int[] input) {
		int firstNonNegative = input.length;
		for (int i = 0; i < input.length; i++) {
			if (input[input.length - 1 - i] >= 0) {
				firstNonNegative = input.length - 1 - i;
			}
		}
		return firstNonNegative;
	}

	private static void log (final Object... msg) {
		for (int i = 0; i < msg.length; i++) {
			System.out.print(msg[i] + " ");
		}
		System.out.println();
	}

	private static HashMap<Integer, Integer> collect (final int[] input) {
		final HashMap<Integer, Integer> valueToNumberOfValues = new HashMap<Integer, Integer>();

		for (int i = 0; i < input.length; i++) {
			final int value = input[i];
			final Integer num = valueToNumberOfValues.get(value);
			if (num == null) {
				valueToNumberOfValues.put(value, 1);
			} else {
				valueToNumberOfValues.put(value, num + 1);
			}
		}

		return valueToNumberOfValues;
	}

}
