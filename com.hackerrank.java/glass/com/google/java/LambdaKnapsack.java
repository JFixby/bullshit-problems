
package com.google.java;

import java.text.NumberFormat;
import java.util.HashMap;

public class LambdaKnapsack {

	public interface λw {
		public double evaluate (int maxWeight);
	}

	public static void main (final String[] args) {

		Item knapsack = Item.EMPTY;
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
		final double value = knapsack.valueFunction.evaluate(maxWeight);

		print(knapsack);

		unroll(knapsack, value, maxWeight);

	}

	private static void unroll (final Item item, final double value, final int maxWeight) {

		int w = maxWeight;
		int solutionWeight = 0;
		final NumberFormat nf = NumberFormat.getInstance();

		System.out.println("You can carry te following materials " + "in the knapsack:");
		for (Item i = item; i != Item.EMPTY && w >= 0; i = i.previousItem) {
			if (i.getInKnapsack(w)) {
				w = w - i.weight;
				solutionWeight = solutionWeight + i.weight;
				System.out.println(i);
			}

		} // for()
		System.out.println();
		System.out.println("Total value              = " + value);
		System.out.println("Total weight             = " + nf.format(solutionWeight / 1) + " kg");
		System.out.println("Maximal weight           = " + nf.format(maxWeight / 1) + " kg");

		System.out.println();
	}

	private static void print (final Item item) {
		System.out.println("Items list:");
		for (Item i = item; i != Item.EMPTY; i = i.previousItem) {
			System.out.println(i);
		}
		System.out.println();
	}

	public static Item addItem (final String ID, final int weight, final int value, final int limit, final Item previousItem) {
		Item result = previousItem;
		for (int k = 0; k < limit; k++) {
			result = new Item(ID + "-" + k, weight, value, result);// append item[k]
		}
		return result;
	}

	private static λw memoization (final λw function) {
		final HashMap<Integer, Double> cache = new HashMap<Integer, Double>();
		return w -> {
			Double value = cache.get(w);// check cache
			if (value == null) {// if not present
				value = function.evaluate(w);// compute
				cache.put(w, value);// store computed value
			}
			return value;
		};
	}

	static class Item {
		final public String ID;
		final public int weight;
		final public double value;
		final public Item previousItem;
		final public λw valueFunction;
		final private int index;

		public static final Item EMPTY = new Item();

		private Item () {
			this.weight = Integer.MAX_VALUE;
			this.value = 0;
			this.ID = "EMPTY";
			this.previousItem = null;
			this.valueFunction = w -> 0;
			this.index = -1;
		}

		public Item (final String ID, final int weight, final double value, final Item previousItem) {
			if (previousItem == null) {
				throw new Error();
			}
			this.ID = ID;
			this.weight = weight;
			this.value = value;
			this.previousItem = previousItem;
			this.valueFunction = memoization(//
				w -> //
				weight > w ? //
					previousItem.valueFunction.evaluate(w)//
					: // else
					Math.max(//
						previousItem.valueFunction.evaluate(w), // value 1
						previousItem.valueFunction.evaluate(w - weight) + value // value 2
					)//
			);
			this.index = previousItem.index + 1;
		}

		public boolean getInKnapsack (final int j) {
			return this.valueFunction.evaluate(j) != this.previousItem.valueFunction.evaluate(j);
		}

		@Override
		public String toString () {
			return String.format("%1$-2s %2$-30s %3$-3s %4$-5s %5$-15s ", "", this.ID, "weight", this.weight + "",
				"value = " + this.value + "");

		}

	}

}
