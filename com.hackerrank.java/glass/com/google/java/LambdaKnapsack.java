
package com.google.java;

import java.text.NumberFormat;
import java.util.HashMap;

public class LambdaKnapsack {
	public static final boolean USE_MEMOIZATION = true;

	public interface λ {
		public double evaluate (int maxWeight);
	}

	private static long MEMORY_USAGE = 0;
	private static long RUNTIME_OPERATIONS = 0;

	public static void main (final String[] args) {
		// Constructing solution:
		Item knapsack = Item.EMPTY;

// knapsack = addItem("1", 1, 15, 1, knapsack);
// knapsack = addItem("2", 5, 10, 1, knapsack);
// knapsack = addItem("3", 3, 9, 1, knapsack);
// knapsack = addItem("4", 4, 5, 1, knapsack);

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
// Solved: solution is λw:=knapsack.valueFunction

		printItems(knapsack);

		final int maxWeight = 400;
		unrollSolution(knapsack, maxWeight);

		System.out.println("Memory usage              = " + MEMORY_USAGE);
		System.out.println("Runtime operations        = " + RUNTIME_OPERATIONS);

	}

	public static void unrollSolution (final Item item, final int maxWeight) {
		final double value = item.valueFunction.evaluate(maxWeight);// evaluate solution;

		// print result:
		int w = maxWeight;
		int solutionWeight = 0;

		System.out.println("You can carry te following materials " + "in the knapsack:");
		for (Item i = item; i != Item.EMPTY && w >= 0; i = i.previousItem) {
			if (i.isItemTaken(w)) {
				w = w - i.weight;
				solutionWeight = solutionWeight + i.weight;
				System.out.println(i);
			}
		} // for()

		System.out.println();
		final NumberFormat nf = NumberFormat.getInstance();
		System.out.println("Total value              = " + value);
		System.out.println("Total weight             = " + nf.format(solutionWeight / 1) + " kg");
		System.out.println("Maximal weight           = " + nf.format(maxWeight / 1) + " kg");

		System.out.println();
	}

	public static void printItems (final Item item) {
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

	public static λ memoization (final λ function) {
		if (!USE_MEMOIZATION) {
			return function;
		}
		final HashMap<Integer, Double> cache = new HashMap<Integer, Double>();
		return w -> {

			Double value = cache.get(w);// check cache
			if (value == null) {// if not present
				value = function.evaluate(w);// compute
				cache.put(w, value);// store computed value
				MEMORY_USAGE++;
			}
			return value;
		};

	}

	public static class Item {
		final public String ID;
		final public int weight;
		final public double value;
		final public Item previousItem;
		final public λ valueFunction;
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

		public Item (final String ID, final int itemWeight, final double itemValue, final Item previousItem) {
			if (previousItem == null) {
				throw new Error("Null not allowed.");
			}
			this.ID = ID;
			this.weight = itemWeight;
			this.value = itemValue;
			this.previousItem = previousItem;
			this.valueFunction = memoization(// use memoization
				w -> // input weight argument
				itemWeight > w & call() ? // item weight is too big?
					previousItem.valueFunction.evaluate(w)// yes, ignore item
					: // else
					Math.max(// deciding about the item
						previousItem.valueFunction.evaluate(w), // discard item
						previousItem.valueFunction.evaluate(w - itemWeight) + itemValue // take item
					)//
			);
			this.index = previousItem.index + 1;
		}

		static final private boolean call () {
			RUNTIME_OPERATIONS++;
			return true;
		}

		public boolean isItemTaken (final int weightLimit) {
			// taking item changes it's value function
			return this.valueFunction.evaluate(weightLimit) != this.previousItem.valueFunction.evaluate(weightLimit);
		}

		@Override
		public String toString () {
			return String.format("%1$-2s %2$-30s %3$-3s %4$-5s %5$-15s ", "", this.ID, "weight", this.weight + "",
				"value = " + this.value + "");

		}

	}

}
