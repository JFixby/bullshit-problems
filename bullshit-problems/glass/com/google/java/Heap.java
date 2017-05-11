
package com.google.java;

import java.util.Random;

public class Heap {

	private final int max_size;
	private int current_size = 0;
	final byte[] data;

	public Heap (final int max_size) {
		this.max_size = max_size;
		this.data = new byte[max_size];
	}

	public static void main (final String[] args) {
		final Random r = new Random(System.currentTimeMillis());
		final byte[] input = new byte[16];
		final Heap heap = new Heap(input.length);
		for (byte i = 0; i < input.length; i++) {
			heap.add(i);
		}

		heap.print();
		final TreeNode toPrint = new HeapTreeNode(heap, 0);
		TreePrinter.print(toPrint);
	}

	private void print () {

		final double maxLevel = this.log(2, this.current_size) + 1;
		print(this, 0, "", true);
// final int maxLevel = (float);

	}

	private static void print (final Heap heap, final int index, final String prefix, final boolean isTail) {
		final int value = heap.value(index);
		System.out.println(prefix + (isTail ? "└─── " : "├── ") + "[" + value + "]");
		for (int i = 0; i < heap.childrenSize(index) - 1; i++) {
			print(heap, heap.child_i(index, i), prefix + (isTail ? "      " : "│   "), false);

		}
		if (heap.childrenSize(index) > 0) {
			print(heap, heap.child_i(index, heap.childrenSize(index) - 1), prefix + (isTail ? "      " : "│   "), true);
		}
	}

	private int child_i (final int nodeIndex, final int i) {
		final int l = indexOfLeftChild(nodeIndex);
		final int r = indexOfRightChild(nodeIndex);
		if (i == 0) {
			return l;
		}
		return r;
	}

	public int childrenSize (final int index) {
		int size = 0;

		final int l = indexOfLeftChild(index);
		final int r = indexOfRightChild(index);
		if (this.current_size > l) {
			size++;
		}
		if (this.current_size > r) {
			size++;
		}
		return size;
	}

	private int value (final int index) {
		return this.data[index];
	}

	private double log (final int base, final int target) {
		return (Math.log(target) / Math.log(base));
	}

	private void println () {
		System.out.println();
	}

	private void println (final Object space) {
		System.out.println(space);
	}

	private void print (final Object space) {
		System.out.print(space);
	}

	private void log (final String string) {
		System.out.println(string);
	}

	private String space (final int N) {
		String t = "";
		for (int i = 0; i < N; i++) {
			t = t + " ";
		}
		return t;
	}

	public void add (final byte value) {
		final int nodeIndex = this.current_size;
		this.data[nodeIndex] = value;
		this.current_size++;
		this.heapUp(nodeIndex);

	}

	private void heapUp (final int nodeIndex) {
		if (nodeIndex == 0) {
			return;
		}
		final int parentIndex = indexOfParent(nodeIndex);

		final byte parent = this.data[parentIndex];
		final byte node = this.data[nodeIndex];

		if (node > parent) {
			swap(this.data, parentIndex, nodeIndex);
			this.heapUp(parentIndex);
		}

	}

	public static void swap (final byte[] A, final int x, final int y) {
		final byte tmp = A[x];
		A[x] = A[y];
		A[y] = tmp;
	}

	public static int indexOfParent (final int nodID) {
		return (nodID - 1) / 2;
	}

	public static int indexOfLeftChild (final int nodID) {
		return nodID * 2 + 1;
	}

	public static int indexOfRightChild (final int nodID) {
		return nodID * 2 + 2;
	}

}
