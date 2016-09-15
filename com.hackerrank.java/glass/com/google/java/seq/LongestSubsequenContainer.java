
package com.google.java.seq;

import java.util.ArrayList;
import java.util.Comparator;

import com.google.java.i.Solution.L;
import com.jfixby.cmns.api.collections.Collections;

public class LongestSubsequenContainer {

	final ArrayList<ArrayList<Integer>> array = new ArrayList<ArrayList<Integer>>();
	{
		final ArrayList<Integer> BASE = new ArrayList<Integer>();
		BASE.add(Integer.MIN_VALUE);
		this.array.add(BASE);

	}
	private final Comparator<ArrayList<Integer>> comparator = new Comparator<ArrayList<Integer>>() {
		@Override
		public int compare (final ArrayList<Integer> o1, final ArrayList<Integer> o2) {
// if (o1.size() == 0 && o2.size() == 0) {
// return 0;
// }
// if (o1.size() == 0 && o2.size() != 0) {
// return -1;
// }
// if (o1.size() != 0 && o2.size() == 0) {
// return +1;
// }
			final int compare = Integer.compare(o1.get(o1.size() - 1), o2.get(o2.size() - 1));
			if (compare != 0) {
				return compare;
			}
			return Integer.compare(o1.size(), o2.size());
		}

	};

	public void store (final int[] toStore) {
		final ArrayList<Integer> key = new ArrayList<Integer>();
		this.addAll(key, toStore);

		final int index = java.util.Collections.binarySearch(this.array, key, this.comparator);
		this.array.add(index, key);
		Collections.newList(this.array).print("array[" + index + "]");
		final ArrayList<Integer> element = this.array.get(index);
		L.d(element);
	}

	private void addAll (final ArrayList<Integer> key, final int[] toStore) {
		for (int i = 0; i < toStore.length; i++) {
			key.add(toStore[i]);
		}
	}

	public int[] getLongestThatEndsBefore (final int next) {
		final ArrayList<Integer> key = new ArrayList<Integer>();
		key.add(next);
		L.d("key", key);
		final int index = java.util.Collections.binarySearch(this.array, key, this.comparator);
		Collections.newList(this.array).print("array[" + index + "]");
		final ArrayList<Integer> element = this.array.get(index);
		L.d(element);
		return this.toArray(element);
	}

	private int[] toArray (final ArrayList<Integer> element) {
		final int[] array = new int[element.size()];
		for (int i = 0; i < element.size(); i++) {
			array[i] = element.get(i);
		}
		return array;
	}

}
