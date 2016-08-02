
package com.google.java;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import com.google.java.Knapsack.SetMask;
import com.jfixby.cmns.api.log.L;
import com.jfixby.red.desktop.DesktopSetup;

public class Knapsack2 {

	private static SetMask bestMask;

	public static void main (final String[] args) {
		DesktopSetup.deploy();
		final int maxWeight = 400;

// solver.add(new Item("1", 1, 15, 1);
// solver.add(new Item("2", 5, 10, 1);
// solver.add(new Item("3", 3, 9, 1);
// solver.add(new Item("4", 4, 5, 1);

		final λKnapsakSolver solver = new λKnapsakSolver();

		solver.add("map", 9, 150, 1);
		solver.add("compass", 13, 35, 1);
		solver.add("water", 153, 200, 3);
		solver.add("sandwich", 50, 60, 2);
		solver.add("glucose", 15, 60, 2);
		solver.add("tin", 68, 45, 3);
		solver.add("banana", 27, 60, 3);
		solver.add("apple", 39, 40, 3);
		solver.add("cheese", 23, 30, 1);
		solver.add("beer", 52, 10, 3);
		solver.add("suntan cream", 11, 70, 1);
		solver.add("camera", 32, 30, 1);
		solver.add("t-shirt", 24, 15, 2);
		solver.add("trousers", 48, 10, 2);
		solver.add("umbrella", 73, 40, 1);
		solver.add("waterproof trousers", 42, 70, 1);
		solver.add("waterproof overclothes", 43, 75, 1);
		solver.add("note-case", 22, 80, 1);
		solver.add("sunglasses", 7, 20, 1);
		solver.add("towel", 18, 12, 2);
		solver.add("socks", 4, 50, 1);
		solver.add("book", 30, 10, 2);

		solver.lockInput();

		solver.printInput("maxWeight=" + maxWeight);

		final double solution = solver.find(maxWeight);

		L.d("solution", solution);

	}

	interface λ {
		public double value (int upToItemIndex, int maxWeight);
	}

	public static class λKnapsakSolver {
		final ArrayList<Item> input = new ArrayList<Item>();
		final HashMap<String, Double> mems = new HashMap<String, Double>();

		λ solution = (i, w) -> {
			final String key = i + "#" + w;
			Double result = this.mems.get(key);
			if (result == null) {
				result = this.computer.value(i, w);
				this.mems.put(key, result);
			}
			return result;
		};

		λ computer = (i, w) -> {
			if (i == 0) {
				return 0;
			}
			if (this.input.get(i - 1).getWeight() > w) {
				return this.solution.value(i - 1, w);
			}
			// m[i, j] := max(m[i-1, j], m[i-1, j-w[i-1]] + v[i-1])
			return Math.max(//
				this.solution.value(i - 1, w), //
				this.solution.value(i - 1, w - this.input.get(i - 1).getWeight()) + this.input.get(i - 1).getValue());
		};

		boolean locked = false;

		public void add (final String ID, final int weight, final double value, final int limit) {
			if (this.locked) {
				throw new Error();
			}
			for (int k = 0; k < limit; k++) {
				final Item item = new Item(ID + "-" + k, weight, value);
				this.input.add(item);
			}
		}

		public void lockInput () {
			Collections.sort(this.input);
			Collections.reverse(this.input);
		}

		public double find (final int maxWeight) {
			return this.find(maxWeight, this.input.size() - 1);
		}

		public double find (final int maxWeight, final int upToItemIndex) {
			return this.solution.value(upToItemIndex, maxWeight);
		}

		public void printInput (final String tag) {
			com.jfixby.cmns.api.collections.Collections.newList(this.input).print(tag);
		}

	}

	static class Item implements Comparable<Item> {
		private final int weight;
		private final double value;
		private final String ID;

		public Item (final String ID, final int weight, final double value) {
			this.ID = ID;
			this.weight = weight;
			this.value = value;
		}

		public double getValue () {
			return this.value;
		}

		public int getWeight () {
			return this.weight;
		}

		@Override
		public int hashCode () {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((this.ID == null) ? 0 : this.ID.hashCode());
			long temp;
			temp = Double.doubleToLongBits(this.value);
			result = prime * result + (int)(temp ^ (temp >>> 32));
			result = prime * result + this.weight;
			return result;
		}

		@Override
		public boolean equals (final Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (this.getClass() != obj.getClass()) {
				return false;
			}
			final Item other = (Item)obj;
			if (this.ID == null) {
				if (other.ID != null) {
					return false;
				}
			} else if (!this.ID.equals(other.ID)) {
				return false;
			}
			if (Double.doubleToLongBits(this.value) != Double.doubleToLongBits(other.value)) {
				return false;
			}
			if (this.weight != other.weight) {
				return false;
			}
			return true;
		}

		@Override
		public int compareTo (final Item other) {
			return Double.compare(this.value, other.value);
		}

		@Override
		public String toString () {
			return String.format("%28s", "<" + this.ID + ">") + " this.weight=" + this.weight + ", this.value=" + this.value;
		}

	}
}
