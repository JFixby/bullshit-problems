
package com.google.java.t;

import com.jfixby.cmns.api.err.Err;
import com.jfixby.cmns.api.log.L;
import com.jfixby.cmns.api.util.JUtils;
import com.jfixby.red.desktop.DesktopSetup;

public class SortedListToBinaryTree {

	public static void main (final String[] args) {
		DesktopSetup.deploy();
		final int N = 9;
		final int off = 100;
		final Integer[] array = new Integer[N];
		for (int i = 0; i < N; i++) {
			array[i] = i + off;
		}

		// λVector is a λ-function
		{
			final λVector<Integer> input = i -> array[i];// case: array

			final BinaryTreeNode<Integer> root = toBinaryTree(input, N);
			root.print();
		}
		{
			final LinkedListNode<Integer> linkedList = copy(array); // case linked list
			linkedList.print();
			final λVector<Integer> input = linkedListToλVector(linkedList);

			final BinaryTreeNode<Integer> root = toBinaryTree(input, N);
			root.print();
		}
	}

	/*
	 * Converts λVector<T> input to binary tree and returns BinaryTreeNode<T>
	 *
	 */
	private static <T extends Comparable<T>> BinaryTreeNode<T> toBinaryTree (final λVector<T> input, final int numberOfElements) {
		return toBinaryTree(0, numberOfElements - 1, input);
	}

	static class LinkedListNode<T extends Comparable<T>> {
		T data;
		public LinkedListNode<T> next = null;

		@Override
		public String toString () {
			LinkedListNode<T> x = this;
			final StringBuilder tmp = new StringBuilder();
			do {
				tmp.append("(").append(x.data).append(")->");
				x = x.next;
			} while (x != null);
			tmp.append("X");
			return tmp.toString();
		}

		public void print () {
			L.d(this);
		}
	}

	static class BinaryTreeNode<T extends Comparable<T>> {
		T data;
		public BinaryTreeNode<T> leftChild = null;
		public BinaryTreeNode<T> rightChild = null;

		enum NODE_PRINT_TYPE {
			ROOT, MIDDLE, LAST
		}

		public void print () {
			printNode("", this, NODE_PRINT_TYPE.ROOT);
		}

		static private <T extends Comparable<T>> void printNode (final String globalPrefix, final BinaryTreeNode<T> node,
			final NODE_PRINT_TYPE type) {

			final String nodeHeader;
			if (node == null) {
				return;
			} else {
				final T e = node.data;
				nodeHeader = "[" + e + "]";
			}

			String nextPrefix;

			final int offset = nodeHeader.length() / 2;

			if (type == NODE_PRINT_TYPE.ROOT) {
				nextPrefix = globalPrefix + JUtils.prefix(" ", offset);
				L.d(globalPrefix + nodeHeader);
			} else if (type == NODE_PRINT_TYPE.LAST) {
				nextPrefix = globalPrefix + JUtils.prefix(" ", offset);
				L.d(globalPrefix + "└" + nodeHeader);
			} else { // (type == NODE_PRINT_TYPE.MIDDLE) {
				nextPrefix = globalPrefix + "│" + JUtils.prefix(" ", offset);
				L.d(globalPrefix + "├" + nodeHeader);
			}
			if (node != null) {
				printNode(nextPrefix, node.rightChild, NODE_PRINT_TYPE.MIDDLE);
				printNode(nextPrefix, node.leftChild, NODE_PRINT_TYPE.LAST);
			}

		}

	}

	public interface λVector<T> {
		public T elementAt (int index);
	}

	private static <T extends Comparable<T>> LinkedListNode<T> copy (final T[] array) {
		return copy(array, 0);
	}

	private static <T extends Comparable<T>> LinkedListNode<T> copy (final T[] array, final int index) {
		if (index >= array.length) {
			return null;
		}
		final LinkedListNode<T> node = new LinkedListNode<T>();
		node.data = array[index];
		node.next = copy(array, index + 1);
		return node;
	}

	private static <T extends Comparable<T>> λVector<T> linkedListToλVector (final LinkedListNode<T> root) {
		return new λVector<T>() {
			int lastCalled = -1;
			LinkedListNode<T> lastReturned = null;// cache previous call result here

			@Override
			public T elementAt (final int index) {// O(1)
				if (index == 0) {
					this.lastReturned = root;
				} else {
					if (this.lastCalled + 1 != index) { // ensure O(1)
						Err.reportError("Performance leak!");
					}
					this.lastReturned = this.lastReturned.next;// O(1);
				}
				this.lastCalled = index;
				return this.lastReturned.data;
			}
		};

	}

	private static <T extends Comparable<T>> BinaryTreeNode<T> toBinaryTree (final int fromIndex, final int toIndex,
		final λVector<T> input) {
		if (fromIndex > toIndex) {
			return null;
		}
		final int mid = fromIndex + (toIndex - fromIndex) / 2;
		final BinaryTreeNode<T> node = new BinaryTreeNode<T>();

		node.leftChild = toBinaryTree(fromIndex, mid - 1, input);// "miraculously" number of elements in the left child is == mid,
																					// thus the last call returned element with index (mid-1)
		node.data = input.elementAt(mid);// get the mid element
		node.rightChild = toBinaryTree(mid + 1, toIndex, input);

		return node;
	}

}
