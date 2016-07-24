
package com.google.java;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collections;

import org.junit.Test;

import com.jfixby.cmns.api.debug.Debug;
import com.jfixby.cmns.api.debug.DebugTimer;
import com.jfixby.cmns.api.log.L;
import com.jfixby.cmns.api.random.Random;
import com.jfixby.red.desktop.DesktopSetup;

public class SortedDoubleLinkedList<E extends Comparable<E>> {
	public SortedDoubleLinkedList () {
		this.clear();
	}

	int size = 0;
	final Node<E> left = new Node<E>();
	final Node<E> right = new Node<E>();

	public void clear () {
		this.size = 0;
		this.left.right = this.right;
		this.right.left = this.left;
	}

	static class Node<T> {
		Node<T> left = null;
		Node<T> right = null;
		T content;

		@Override
		public String toString () {
			return "<" + this.content + ">";
		}

	}

	public void addElement (final E element) {
		final Node<E> preNode = this.findPreNode(element);
		final Node<E> newNode = this.insertNodeAfter(preNode);
		newNode.content = element;
	}

	private Node<E> findPreNode (final E element) {
		Node<E> current = this.left;
		while (current != this.right && this.compare(element, current.content) > 0) {
			current = current.right;
		}
		current = current.left;
		return current;
	}

	private Node<E> insertNodeAfter (final Node<E> current) {
		final Node<E> newNode = new Node<E>();
		newNode.left = current;
		newNode.right = current.right;
		current.right.left = newNode;
		current.right = newNode;
		this.size++;
		return newNode;
	}

	private int compare (final E a, final E b) {
		if (a == null && b == null) {
			return 0;
		}
		if (a == null && b != null) {
			return -1;
		}
		if (a != null && b == null) {
			return 1;
		}
		return a.compareTo(b);
	}

	public void removeElement (final E element) {
		if (this.size == 0) {
			return;
		}
		final Node<E> preNode = this.findPreNode(element);
		if (preNode.right == this.right) {
			return;
		}
		if (this.compare(element, preNode.right.content) == 0) {
			this.removeNode(preNode.right);
		}
	}

	public E getElementAt (final int index) {
		if (index < 0 || index >= this.size) {
			throw new Error("Index outbound exception: " + index + " size=(" + index + ")");
		}
		final Node<E> targetNode = this.findNodeAt(index);

		return targetNode.content;
	}

	private Node<E> findNodeAt (final int index) {
		Node<E> result = this.left;
		for (int k = 0; k <= index; k++) {
			result = result.right;
		}
		return result;
	}

	public E removeElementAt (final int index) {
		if (index < 0 || index >= this.size) {
			throw new Error("Index outbound exception: " + index + " size=(" + index + ")");
		}
		final Node<E> targetNode = this.findNodeAt(index);
		this.removeNode(targetNode);
		return targetNode.content;
	}

	private void removeNode (final Node<E> targetNode) {
		targetNode.left.right = targetNode.right;
		targetNode.right.left = targetNode.left;
		this.size--;
	}

	public void print (final String tag) {
		L.d("---[" + tag + "](" + this.size + ")----------------------");
		Node<E> current = this.left;
		int k = 0;
		while (current != null) {
			if (current != this.left && current != this.right) {
				L.d("[" + k + "]", current);
				k++;
			}
			current = current.right;

		}
		L.d();

	}

	public int size () {
		return this.size;
	}

	@Test
	public void testa () {
		DesktopSetup.deploy();
		final DebugTimer timer = Debug.newTimer();
		final int N = 1000;
		timer.reset();
		final ArrayList<Integer> checker = new ArrayList<Integer>();
		final SortedDoubleLinkedList<Integer> toTest = new SortedDoubleLinkedList<Integer>();
		for (int k = 0; k < N; k++) {
			for (int i = 0; i < N; i++) {
				final int add = Random.newInt32();
				checker.add(add);
				toTest.addElement(add);
			}
			assertTrue(this.compareLists(checker, toTest));
			int size = checker.size();
			for (; size > 0;) {
				final int index = Random.newInt(0, size - 1);
				size--;
				final Integer toRemoveA = checker.remove(index);
				final Integer toRemoveB = toTest.removeElementAt(index);
				assertTrue(equals(toRemoveA, toRemoveB));
			}
			assertTrue(this.compareLists(checker, toTest));
			checker.clear();
			toTest.clear();
			assertTrue(this.compareLists(checker, toTest));

		}
		timer.printTime("test done: N=" + N);
	}

	static boolean equals (final Object a, final Object b) {
		if (a == null && b == null) {
			return true;
		}
		if (a == null && b != null) {
			return false;
		}
		if (a != null && b == null) {
			return false;
		}
		return a.equals(b);

	}

	private boolean compareLists (final ArrayList<Integer> checker, final SortedDoubleLinkedList<Integer> toTest) {
		Collections.sort(checker);
		if (checker.size() != toTest.size()) {
			return false;
		}
		for (int i = 0; i < checker.size(); i++) {
			final Integer A = checker.get(i);
			final Integer B = toTest.getElementAt(i);
			if (!equals(A, B)) {
				L.d(A, B);
				L.d(checker);
				toTest.print("toTest");
				return false;
			}
		}
		return true;
	}

	public static void main (final String[] args) {
		DesktopSetup.deploy();

		final SortedDoubleLinkedList<Integer> list = new SortedDoubleLinkedList<Integer>();
		for (int i = 0; i < 10; i++) {
			final int add = Random.newInt(0, 100);
// list.print("list: " + i);
// L.d("add", add);
			list.addElement(add);

		}

		list.print("list");

// while (list.size() > 0) {
// final Integer element = list.removeElementAt(list.size() - 1);
// L.d("remove", element);
// list.print("list");
// }

		while (list.size() > 0) {
			final Integer element = list.getElementAt(list.size() - 1);
			list.removeElement(element);
			L.d("remove", element);
			list.print("list");
		}

	}

}
