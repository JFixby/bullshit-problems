
package com.google.java.sort;

import java.util.HashMap;
import java.util.Set;

public class Counter<T> {
	final HashMap<T, Integer> counter = new HashMap<>();

	public int count (final T value) {
		Integer num = this.numberOf(value);
		num++;
		this.counter.put(value, num);
		return num;
	}

	public int numberOf (final T value) {
		Integer num = this.counter.get(value);
		if (num == null) {
			num = 0;
		}
		return num;
	}

	public Set<T> keys () {
		return this.counter.keySet();
	}

}
