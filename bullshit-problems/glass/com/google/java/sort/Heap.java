
package com.google.java.sort;

public class Heap {

	private final byte[] data;
	int size = 0;

	public Heap (final int length) {
		this.data = new byte[length];
	}

	public void add (final byte value) {
		this.data[this.size] = value;
		this.swimUp(this.size);
		this.size++;

	}

	public byte remove () {
		this.size--;
		this.swap(0, this.size);
		this.swimDown(0);
		return this.data[this.size];
	}

	private void swap (final int x, final int y) {
		final byte tmp = this.data[x];
		this.data[x] = this.data[y];
		this.data[y] = tmp;
	}

	private void swimDown (final int i) {
		final int leftChildIndex = leftChildIndex(i);
		final int rightChildIndex = rightChildIndex(i);
		if (leftChildIndex < this.size) {
		}

	}

	static private int rightChildIndex (final int i) {
		return 2 * i + 2;
	}

	static private int leftChildIndex (final int i) {
		return 2 * i + 1;
	}

	private void swimUp (final int i) {
	}

}
