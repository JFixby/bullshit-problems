
package com.google.java;

import java.util.HashMap;

public class LambdaKnapsack {

	public interface λw {
		public double evaluate (int maxWeight);
	}

	public static void main (final String[] args) {

		λw knapsack = w -> 0;// empty knapsack
		knapsack = addItem("map", 9, 150, 1, knapsack);
		knapsack = addItem("compass", 13, 35, 1, knapsack);
		knapsack = addItem("water", 153, 200, 3, knapsack);
		knapsack = addItem("sandwich", 50, 60, 2, knapsack);
		knapsack = addItem("glucose", 15, 60, 2, knapsack);
		knapsack = addItem("tin", 68, 45, 3, knapsack);
		knapsack = addItem("banana", 27, 60, 3, knapsack);
		knapsack = addItem("apple", 39, 40, 3, knapsack);
		knapsack = addItem("cheese", 23, 30, 1, knapsack);
		knapsack = addItem("beer", 52, 10, 3, knapsack);
		knapsack = addItem("suntan cream", 11, 70, 1, knapsack);
		knapsack = addItem("camera", 32, 30, 1, knapsack);
		knapsack = addItem("t-shirt", 24, 15, 2, knapsack);
		knapsack = addItem("trousers", 48, 10, 2, knapsack);
		knapsack = addItem("umbrella", 73, 40, 1, knapsack);
		knapsack = addItem("waterproof trousers", 42, 70, 1, knapsack);
		knapsack = addItem("waterproof overclothes", 43, 75, 1, knapsack);
		knapsack = addItem("note-case", 22, 80, 1, knapsack);
		knapsack = addItem("sunglasses", 7, 20, 1, knapsack);
		knapsack = addItem("towel", 18, 12, 2, knapsack);
		knapsack = addItem("socks", 4, 50, 1, knapsack);
		knapsack = addItem("book", 30, 10, 2, knapsack);
		final int maxWeight = 400;
		System.out.println("solution > " + knapsack.evaluate(maxWeight));

	}

	private static λw memo (final λw knapsack) {
		final HashMap<Integer, Double> memo = new HashMap<Integer, Double>();
		return w -> {
			Double value = memo.get(w);
			if (value == null) {
				value = knapsack.evaluate(w);
				memo.put(w, value);
			}
			return value;
		};
	}

	public static λw addItem (final String ID, final int weight, final double value, final λw knapsack) {
		return memo(w -> {
			if (weight > w) {
				return knapsack.evaluate(w);
			}
			return Math.max(knapsack.evaluate(w), knapsack.evaluate(w - weight) + value);
		});
	}

	public static λw addItem (final String ID, final int weight, final double value, final int limit, final λw knapsack) {
		λw result = knapsack;
		for (int k = 0; k < limit; k++) {
			result = addItem(ID + "-" + k, weight, value, result);
		}
		return result;
	}

}
