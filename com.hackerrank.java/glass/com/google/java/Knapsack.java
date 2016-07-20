
package com.google.java;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class Knapsack {

	public static void main (final String[] args) {

		final double[] values = new double[] {15, 10, 9, 5};
		final double[] weights = new double[] {1, 5, 3, 4};
		final int items = values.length;
		final double maxWeight = 8;
		final StartSet<Item> base = new StartSet<Item>(items);

		for (int i = 0; i < items; i++) {
			base.addElement(new Item(i + 1, weights[i], values[i]));
		}
		base.print("maxWeight=" + maxWeight);
		final SetMask startMask = new SetMask(items);

		final HashSet<SetMask> testedCases = new HashSet<SetMask>();

		final LinkedList<SetMask> queue = new LinkedList<SetMask>();
		queue.add(startMask);

		while (queue.size() > 0) {

			final SetMask currentMask = queue.removeFirst();

			if (testedCases.contains(currentMask)) {
				continue;
			}
			testedCases.add(currentMask);

			final ConfigValue value = valueOf(currentMask, base);
			if (!value.isWithIn(maxWeight)) {
				continue;
			}

// spreadBFS(currentMask, queue, testedCases);
// spreadDFS(currentMask, queue, testedCases);
			spreadSmart(currentMask, queue, testedCases);

		}

		final SetMask best = getBest(base, testedCases, maxWeight);
		print(best, base, testedCases);
	}

	private static void spreadBFS (final SetMask currentMask, final LinkedList<SetMask> queue,
		final HashSet<SetMask> testedCases) {
		for (int i = 0; i < currentMask.size(); i++) {
			if (currentMask.elementIsIncluded(i)) {
				continue;
			}

			final SetMask subcase = currentMask.include(i);
			queue.add(subcase);

		}
	}

	private static void spreadDFS (final SetMask currentMask, final LinkedList<SetMask> queue,
		final HashSet<SetMask> testedCases) {
		for (int i = 0; i < currentMask.size(); i++) {
			if (currentMask.elementIsIncluded(i)) {
				continue;
			}

			final SetMask subcase = currentMask.include(i);
			queue.add(0, subcase);

		}
	}

	private static void spreadSmart (final SetMask currentMask, final LinkedList<SetMask> queue,
		final HashSet<SetMask> testedCases) {
		for (int i = 0; i < currentMask.size(); i++) {
			if (currentMask.elementIsIncluded(i)) {
				continue;
			}
			final SetMask subcase = currentMask.include(i);
			if (testedCases.contains(subcase)) {
				continue;
			}

			if (isGoodPre(subcase, i)) {
				queue.add(0, subcase);
			} else {
				queue.add(subcase);
			}
		}

	}

	private static boolean isGoodPre (final SetMask subcase, final int i) {
		return i % 2 == 0;
	}

	private static SetMask getBest (final StartSet<Item> base, final HashSet<SetMask> testedCases, final double maxWeight) {

		SetMask best = null;
		ConfigValue bestValue = null;
		for (final SetMask mask : testedCases) {
			final ConfigValue value = valueOf(mask, base);
			if (best == null || (value.isBetterThan(bestValue) && value.isWithIn(maxWeight))) {
				best = mask;
				bestValue = value;
			}
		}

		return best;
	}

	private static void print (final SetMask caseMask, final StartSet<Item> base, final HashSet<SetMask> testedCases) {

		final List<Item> includedItems = base.filter(caseMask);
		final ConfigValue value = valueOf(caseMask, base);

		value.print("value");

		for (int i = 0; i < includedItems.size(); i++) {
			final Item item = includedItems.get(i);
			System.out.println("  [" + i + "] " + item);
		}
		System.out.println();
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
