
package com.google.java.i;

import java.util.ArrayList;

//
public class Solution<T extends Comparable<T>> {
	final Node<T> left = new Node<T>();
	final Node<T> right = new Node<T>();
	int maxLevel = 0;
	int size = 0;

	public Solution () {
		this.clear();
	}

	public void clear () {// (L) <-0-> (R)
		this.maxLevel = 0;
		this.size = 0;
		this.left.clearRight();
		this.right.clearLeft();
		this.left.addRight(this.right);
		this.right.addLeft(this.left);
	}

	public void addElement (final T element) {
		Node<T> current = this.left;
		while (compare(current.value, element) > 0 && current.next() != this.right) {
			current = current.next();
		}
		final Node<T> newNode = this.insertNewNode(current);

	}

	private Node<T> insertNewNode (final Node<T> current) {
		final Node<T> newNode = new Node<T>();

		return newNode;
	}

	public static final int compare (final Comparable A, final Comparable B) {
		if (A == null && B == null) {
			return 0;
		}
		if (A == null && B != null) {
			return -1;
		}
		if (A != null && B == null) {
			return 1;
		}
		return A.compareTo(B);
	}

	public static final void main (final String[] args) {
		L.d("Hello world!");
	}

	static class Node<T> {
		public T value;
		private final ArrayList<Node<T>> leftNeighbours = new ArrayList<Node<T>>();
		private final ArrayList<Node<T>> rightNeighbours = new ArrayList<Node<T>>();

		public void addRight () {
		}

		public void clearRight () {
			this.rightNeighbours.clear();
		}

		public void clearLeft () {
			this.leftNeighbours.clear();
		}

		public void addLeft (final Node<T> e) {
			this.leftNeighbours.add(e);
		}

		public void addRight (final Node<T> e) {
			this.rightNeighbours.add(e);
		}

		public Node<T> next () {
			final Node<T> next = this.getRight();
			return next;
		}

		private Node<T> getRight () {
			return this.rightNeighbours.get(this.rightNeighbours.size() - 1);
		}

	}

	public static class L {
		public static final void d (final Object tag, final Object msg) {
			d(tag + " > " + msg);
		}

		public static final void d (final Object msg) {
// LogServer.log(msg);
			System.err.println("" + msg);
		}
	}

	public static class Err {
		public static final void reportError (final Throwable e) {
			// LogServer.reportErr(e);
			throw new Error(e);
		}
	}
}
