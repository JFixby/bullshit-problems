
package com.google.java;

import java.util.HashMap;

import com.google.java.Knapsack.SetMask;
import com.jfixby.cmns.api.collections.Collections;
import com.jfixby.cmns.api.collections.List;
import com.jfixby.red.desktop.DesktopSetup;

public class Knapsack2 {

	private static SetMask bestMask;

	public static void main (final String[] args) {
		DesktopSetup.deploy();
		final int maxWeight = 200;

// solver.add(new Item("1", 1, 15, 1));
// solver.add(new Item("2", 5, 10, 1));
// solver.add(new Item("3", 3, 9, 1));
// solver.add(new Item("4", 4, 5, 1));

		final λKnapsakSolver solver = new λKnapsakSolver();

		solver.add(new Item("map", 9, 150, 1));
		solver.add(new Item("compass", 13, 35, 1));
		solver.add(new Item("water", 153, 200, 3));
		solver.add(new Item("sandwich", 50, 60, 2));
		solver.add(new Item("glucose", 15, 60, 2));
		solver.add(new Item("tin", 68, 45, 3));
		solver.add(new Item("banana", 27, 60, 3));
		solver.add(new Item("apple", 39, 40, 3));
		solver.add(new Item("cheese", 23, 30, 1));
		solver.add(new Item("beer", 52, 10, 3));
		solver.add(new Item("suntan cream", 11, 70, 1));
		solver.add(new Item("camera", 32, 30, 1));
		solver.add(new Item("t-shirt", 24, 15, 2));
		solver.add(new Item("trousers", 48, 10, 2));
		solver.add(new Item("umbrella", 73, 40, 1));
		solver.add(new Item("waterproof trousers", 42, 70, 1));
		solver.add(new Item("waterproof overclothes", 43, 75, 1));
		solver.add(new Item("note-case", 22, 80, 1));
		solver.add(new Item("sunglasses", 7, 20, 1));
		solver.add(new Item("towel", 18, 12, 2));
		solver.add(new Item("socks", 4, 50, 1));
		solver.add(new Item("book", 30, 10, 2));

		solver.lockInput();

		solver.printInput("maxWeight=" + maxWeight);

		final KnapsakSolution solution = solver.find(maxWeight);
		solution.print();

	}

	static class KnapsakSolution {

		public void print () {
		}
	}

	static class λKnapsakSolver {

		final List<Item> input = Collections.newList();
		final HashMap<Integer, KnapsakSolution> memo = new HashMap<Integer, KnapsakSolution>();
		boolean locked = false;

		public void add (final Item item) {
			if (this.locked) {
				throw new Error();
			}
			this.input.add(item);
		}

		public void lockInput () {
			this.input.sort();
		}

		public KnapsakSolution find (final int maxWeight) {
			KnapsakSolution solution = this.memo.get(maxWeight);
			if (solution == null) {
				solution = this.compute(maxWeight);
				this.memo.put(maxWeight, solution);
			}
			return solution;
		}

		private KnapsakSolution compute (final int maxWeight) {
			return null;
		}

		public void printInput (final String tag) {
			this.input.print(tag);
		}

	}

	static class Item implements Comparable<Item> {
		private final double weight;
		private final double value;
		private final String ID;
		private final int limit;

		public Item (final String ID, final double weight, final double value, final int limit) {
			this.ID = ID;
			this.limit = limit;
			this.weight = weight;
			this.value = value;
		}

		public int getMaxOccurences () {
			return this.limit;
		}

		@Override
		public int compareTo (final Item other) {
			return Double.compare(this.value, other.value);
		}

		@Override
		public String toString () {
			return String.format("%28s", "<" + this.ID + ">") + " this.weight=" + this.weight + ", this.value=" + this.value
				+ " this.limit=" + this.limit;
		}

	}
}
