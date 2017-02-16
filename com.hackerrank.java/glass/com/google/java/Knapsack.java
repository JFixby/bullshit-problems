
package com.google.java;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.jfixby.scarabei.api.desktop.ScarabeiDesktop;
import com.jfixby.scarabei.api.log.L;

public class Knapsack {

	private static SetMask bestMask;

	public static void main (final String[] args) {
		ScarabeiDesktop.deploy();
		final double maxWeight = 8;
		final StartSet<Item> base = new StartSet<Item>();

		base.addElement(new Item("1", 1, 15, 1));
		base.addElement(new Item("2", 5, 10, 1));
		base.addElement(new Item("3", 3, 9, 1));
		base.addElement(new Item("4", 4, 5, 1));

// base.addElement(new Item("map", 9, 150, 1));
// base.addElement(new Item("compass", 13, 35, 1));
// base.addElement(new Item("water", 153, 200, 3));
// base.addElement(new Item("sandwich", 50, 60, 2));
// base.addElement(new Item("glucose", 15, 60, 2));
// base.addElement(new Item("tin", 68, 45, 3));
// base.addElement(new Item("banana", 27, 60, 3));
// base.addElement(new Item("apple", 39, 40, 3));
// base.addElement(new Item("cheese", 23, 30, 1));
// base.addElement(new Item("beer", 52, 10, 3));
// base.addElement(new Item("suntan cream", 11, 70, 1));
// base.addElement(new Item("camera", 32, 30, 1));
// base.addElement(new Item("t-shirt", 24, 15, 2));
// base.addElement(new Item("trousers", 48, 10, 2));
// base.addElement(new Item("umbrella", 73, 40, 1));
// base.addElement(new Item("waterproof trousers", 42, 70, 1));
// base.addElement(new Item("waterproof overclothes", 43, 75, 1));
// base.addElement(new Item("note-case", 22, 80, 1));
// base.addElement(new Item("sunglasses", 7, 20, 1));
// base.addElement(new Item("towel", 18, 12, 2));
// base.addElement(new Item("socks", 4, 50, 1));
// base.addElement(new Item("book", 30, 10, 2));

		base.print("maxWeight=" + maxWeight);
		final SetMask startMask = new SetMask(base.size());

		final HashMap<SetMask, ConfigValue> testedCases = new HashMap<SetMask, ConfigValue>();

		final ArrayList<SetMask> queue = new ArrayList<SetMask>();
		queue.add(startMask);

		processAll(queue, testedCases, maxWeight, base);
		// O(N * W / w) where: N - number of items, W - capacity, w - smallest item weight

		final SetMask best = getBest(base, testedCases, maxWeight);
		print(best, base, testedCases);
	}

	private static void processAll (final List<SetMask> queue, final HashMap<SetMask, ConfigValue> testedCases,
		final double maxWeight, final StartSet<Item> base) {
		bestMask = queue.get(0);
		ConfigValue bestValue = valueOf(bestMask, base, testedCases);
		testedCases.clear();
		long totalStepsDone = 0;
		while (queue.size() > 0) {
			final SetMask currentMask = queue.remove(0);
			totalStepsDone++;

			if (testedCases.containsKey(currentMask)) {
				continue;
			}

// testedCases.put(currentMask, null);

			final ConfigValue value = valueOf(currentMask, base, testedCases);

			if (!value.isWithIn(maxWeight)) {
				continue;
			}

			if (value.isBetterThan(bestValue)) {
				bestMask = currentMask;
				bestValue = value;
// print(bestMask, base, testedCases);
			}

			spread(currentMask, queue, testedCases, base);
		}

		L.d("totalStepsDone", totalStepsDone);
	}

	private static final boolean DFS = true;
	private static final boolean BFS = !true;

	private static void spread (final SetMask currentMask, final List<SetMask> queue,
		final HashMap<SetMask, ConfigValue> testedCases, final StartSet<Item> base) {
		for (int i = 0; i < currentMask.size(); i++) {

			final Item item = base.getElement(i);

			final int maxPossibleOccurencesOfi = item.getMaxOccurences();
			final int currentOccurences = currentMask.getOccurencesOf(i);
			if (currentOccurences >= maxPossibleOccurencesOfi) {
				continue;
			}

			final SetMask subcase = currentMask.include(i);
			if (testedCases.containsKey(subcase)) {
// L.d(" skip");
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
		if (DFS) {
			return true;
		}
		if (BFS) {
			return false;
		}
		return i % 2 == 0;
	}

	private static SetMask getBest (final StartSet<Item> base, final HashMap<SetMask, ConfigValue> testedCases,
		final double maxWeight) {

// SetMask best = null;
// ConfigValue bestValue = null;
// for (final SetMask mask : testedCases.keySet()) {
// final ConfigValue value = valueOf(mask, base, testedCases);
// if (best == null || (value.isBetterThan(bestValue) && value.isWithIn(maxWeight))) {
// best = mask;
// bestValue = value;
// }
// }

		return bestMask;
	}

	private static void print (final SetMask caseMask, final StartSet<Item> base,
		final HashMap<SetMask, ConfigValue> testedCases) {

		final List<Item> includedItems = base.filter(caseMask);
		final ConfigValue value = valueOf(caseMask, base, testedCases);

		value.print("value");

		for (int i = 0; i < includedItems.size(); i++) {
			final Item item = includedItems.get(i);
			System.out.println("  [" + i + "] " + item);
		}
		System.out.println();
	}

	private static ConfigValue valueOf (final SetMask subcase, final StartSet<Item> base,
		final HashMap<SetMask, ConfigValue> testedCases) {

		ConfigValue valueOf = testedCases.get(subcase);
		if (valueOf != null) {
			return valueOf;
		}

		valueOf = new ConfigValue();
		for (int i = 0; i < subcase.size(); i++) {
			final Item element = base.getElement(i);
			valueOf.add(element, subcase.getOccurencesOf(i));
		}
		testedCases.put(subcase, valueOf);
// System.out.println("processed " + testedCases.size());
		return valueOf;
	}

	static class ConfigValue {
		double totalValue = 0;
		int items = 0;
		double totalWeight = 0;

		public void add (final Item element, final int nTimes) {
			this.items = this.items + nTimes;
			this.totalValue = this.totalValue + element.value * nTimes;
			this.totalWeight = this.totalWeight + element.weight * nTimes;
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
		public StartSet () {
			this.content.ensureCapacity(0);
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

				final T element = this.getElement(i);
				final int occurences = mask.getOccurencesOf(i);
				if (occurences == 0) {
					continue;
				}
				this.add(result, element, occurences);
			}
			return result;
		}

		private void add (final ArrayList<T> result, final T element, final int occurences) {
			for (int i = 0; i < occurences; i++) {
				result.add(element);
			}
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
		int[] occurenceOfithElement;

		public SetMask (final int maxSize) {
			this.maxSize = maxSize;
			this.occurenceOfithElement = new int[maxSize];
		}

		public SetMask (final SetMask parent) {
			this.maxSize = parent.maxSize;
			this.occurenceOfithElement = Arrays.copyOf(parent.occurenceOfithElement, this.maxSize);
		}

		public SetMask include (final int i) {
			final SetMask next = new SetMask(this);
			next.occurenceOfithElement[i]++;
			return next;
		}

		public int getOccurencesOf (final int i) {
			return this.occurenceOfithElement[i];
		}

		public int size () {
			return this.maxSize;
		}

		@Override
		public int hashCode () {
			final int prime = 31;
			int result = 1;
			result = prime * result + this.maxSize;
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
			if (this.maxSize != other.maxSize) {
				return false;
			}
			if (!Arrays.equals(this.occurenceOfithElement, other.occurenceOfithElement)) {
				return false;
			}
			return true;
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
			return Double.compare(this.weight, other.weight);
		}

		@Override
		public String toString () {
			return "<" + this.ID + "> weight=" + this.weight + ", value=" + this.value + " limit=" + this.limit;
		}

	}
}
