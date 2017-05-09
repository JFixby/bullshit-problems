
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
		final int leftChildIndex = indexOfLeftChild(i);
		final int rightChildIndex = indexOfRightChild(i);
		final byte myValue = this.data[i];
		if (leftChildIndex >= this.size && rightChildIndex >= this.size) {
			return;// no childeren
		}

		if (leftChildIndex < this.size && rightChildIndex >= this.size) {
			final byte leftChildValue = this.data[leftChildIndex];
			if (myValue < leftChildValue) {
				this.swap(i, leftChildIndex);
				this.swimDown(leftChildIndex);
			}
			return;
		}

		final byte leftChildValue = this.data[leftChildIndex];
		final byte rightChildValue = this.data[rightChildIndex];

		int childIndex = 0;

		if (leftChildValue > rightChildValue) {
			childIndex = leftChildIndex;
		} else {
			childIndex = rightChildIndex;
		}
		final byte childValue = this.data[childIndex];
		if (myValue < childValue) {
			this.swap(i, childIndex);
			this.swimDown(childIndex);
		}

	}

	static private int indexOfRightChild (final int i) {
		return 2 * i + 2;
	}

	static private int indexOfLeftChild (final int i) {
		return 2 * i + 1;
	}

	private void swimUp (final int i) {
		if (i == 0) {
			return;
		}
		final int parentIndex = parentIndex(i);
		final byte parent = this.data[parentIndex];
		final byte me = this.data[i];
		if (me > parent) {
			this.swap(i, parentIndex);
			this.swimUp(parentIndex);
		}
	}

	static private int parentIndex (final int i) {
		return (i - 1) / 2;
	}

	public void print () {

		final double maxLevel = this.log(2, this.size) + 1;
		print(this, 0, "", true);
// final int maxLevel = (float);

	}

	private double log (final int base, final int target) {
		return (Math.log(target) / Math.log(base));
	}

	private static void print (final Heap heap, final int index, final String prefix, final boolean isTail) {
		final int value = heap.data[index];
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

	private int childrenSize (final int index) {
		int size = 0;

		final int l = indexOfLeftChild(index);
		final int r = indexOfRightChild(index);
		if (this.size > l) {
			size++;
		}
		if (this.size > r) {
			size++;
		}
		return size;
	}

}
