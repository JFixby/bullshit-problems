
package com.google.java;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Knapsack {

	public static void main (final String[] args) {

		final int items = 5;
		final double[] values = new double[] {1, 6, 4, 7, 6};
		final double[] weights = new double[] {3, 4, 5, 8, 9};
		final double maxWeight = 13;
		final StartSet<Item> base = new StartSet<Item>(items);

		for (int i = 0; i < items; i++) {
			base.addElement(new Item(i, weights[i], values[i]));
		}
		base.print(" maxWeight=" + maxWeight);
		final SetMask startMask = new SetMask(items);

		final HashMap<SetMask, ConfigValue> testedCases = new HashMap<SetMask, ConfigValue>();
		final boolean inclusiveSearch = true;
		final SetMask result = searchOptimalKnapsack(base, testedCases, startMask, null, inclusiveSearch, maxWeight);

		print(result, base, testedCases);

	}

	private static void print (final SetMask caseMask, final StartSet<Item> base,
		final HashMap<SetMask, ConfigValue> testedCases) {
		final List<Item> includedItems = base.filter(caseMask);
		ConfigValue value = testedCases.get(caseMask);
		if (value == null) {
			value = valueOf(caseMask, base);
		}

		value.print("value");

		for (int i = 0; i < includedItems.size(); i++) {
			final Item item = includedItems.get(i);
			System.out.println("  [" + i + "] " + item);
		}
		System.out.println();
	}

	private static SetMask searchOptimalKnapsack (final StartSet<Item> base, final HashMap<SetMask, ConfigValue> testedCases,
		final SetMask currentMask, SetMask best, final boolean inclusiveSearch, final double maxWeight) {
		if (best == null) {
			best = currentMask;
			final ConfigValue value = valueOf(best, base);
			testedCases.put(best, value);
		}
		ConfigValue bestValue = testedCases.get(best);

		if (inclusiveSearch) {
			for (int i = 0; i < currentMask.size(); i++) {
				if (currentMask.elementIsIncluded(i)) {
					continue;
				}
				{
					final SetMask subcase = currentMask.include(i);

					print(subcase, base, testedCases);

					if (testedCases.containsKey(subcase)) {
						continue;
					}
					final ConfigValue value = valueOf(subcase, base);
					testedCases.put(subcase, value);

					if (!value.isWithIn(maxWeight)) {
						continue;
					}

					if (!value.isBetterThan(bestValue)) {
						continue;
					}

					best = subcase;
					bestValue = value;

					final SetMask bestSubcase = searchOptimalKnapsack(base, testedCases, subcase, best, inclusiveSearch, maxWeight);
					final ConfigValue bestSubcaseValue = testedCases.get(bestSubcase);

					if (bestSubcaseValue.isBetterThan(bestValue)) {
						best = bestSubcase;
						bestValue = bestSubcaseValue;
					}
				}
			}
		} else {
			throw new Error("Not implemented yet!");
		}
		return best;
	}

	private static ConfigValue valueOf (final SetMask subcase, final StartSet<Item> base) {
		final ConfigValue valueOf = new ConfigValue();
		for (int i = 0; i < subcase.size(); i++) {
			if (subcase.elementIsIncluded(i)) {
				final Item element = base.getElement(i);
				valueOf.add(element);
			}
		}
		return valueOf;
	}

	static class ConfigValue {
		double totalValue = 0;
		int items = 0;
		double totalWeight = 0;

		public void add (final Item element) {
			this.items++;
			this.totalValue = this.totalValue + element.value;
			this.totalWeight = this.totalWeight + element.weight;
		}

		public void print (final String tag) {
			System.out.println(tag + " > " + this.toString());
		}

		public boolean isBetterThan (final ConfigValue bestValue) {
			return this.totalValue > bestValue.totalValue;
		}

		public boolean isWithIn (final double maxWeight) {
			return this.totalWeight <= maxWeight;
		}

		@Override
		public String toString () {
			return "ConfigValue[items=" + this.items + ", totalValue=" + this.totalValue + ", totalWeight=" + this.totalWeight + "]";
		}

	}

	static class StartSet<T> {
		public StartSet (final int ensureCapacity) {
			this.content.ensureCapacity(ensureCapacity);
			this.content.trimToSize();
		}

		public void print (final String tag) {
			System.out.println("StartSet[" + tag + "] size: " + this.size());
			for (int i = 0; i < this.size(); i++) {
				final T e = this.getElement(i);
				System.out.println("    [" + i + "] " + e);
			}
			System.out.println();
		}

		public int size () {
			return this.content.size();
		}

		public List<T> filter (final SetMask mask) {
			final ArrayList<T> result = new ArrayList<T>();
			for (int i = 0; i < mask.size(); i++) {
				if (mask.elementIsIncluded(i)) {
					final T element = this.getElement(i);
					result.add(element);
				}
			}
			return result;
		}

		public T getElement (final int i) {
			return this.content.get(i);
		}

		public void addElement (final T item) {
			this.content.add(item);
		}

		final ArrayList<T> content = new ArrayList<T>();
	}

	static class SetMask {

		private final int maxSize;
		boolean[] occurenceOfithElement;

		public SetMask (final int maxSize) {
			this.maxSize = maxSize;
			this.occurenceOfithElement = new boolean[maxSize];
		}

		public SetMask (final SetMask parent) {
			this.maxSize = parent.maxSize;
			this.occurenceOfithElement = Arrays.copyOf(parent.occurenceOfithElement, this.maxSize);
		}

		public SetMask include (final int i) {
			final SetMask next = new SetMask(this);
			next.occurenceOfithElement[i] = true;
			return next;
		}

		public boolean elementIsIncluded (final int i) {
			return this.occurenceOfithElement[i];
		}

		public int size () {
			return this.maxSize;
		}

		@Override
		public int hashCode () {
			final int prime = 31;
			int result = 1;
			result = prime * result + Arrays.hashCode(this.occurenceOfithElement);
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
			final SetMask other = (SetMask)obj;
			if (!Arrays.equals(this.occurenceOfithElement, other.occurenceOfithElement)) {
				return false;
			}
			return true;
		}

	}

	static class Item implements Comparable<Item> {
		private final double weight;
		private final double value;
		private final int ID;

		public Item (final int ID, final double weight, final double value) {
			this.ID = ID;
			this.weight = weight;
			this.value = value;
		}

		@Override
		public int compareTo (final Item other) {
			return Double.compare(this.weight, other.weight);
		}

		@Override
		public String toString () {
			return "Item[" + this.ID + "] weight=" + this.weight + ", value=" + this.value + "";
		}

	}
}
