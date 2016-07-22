
package com.google.java;

import java.util.ArrayList;

import com.jfixby.cmns.api.log.L;
import com.jfixby.cmns.api.random.Random;
import com.jfixby.red.desktop.DesktopSetup;

public class SortedLinkedList<E extends Comparable<E>> {

	public SortedLinkedList () {
		super();
		this.clear();
	}

	public void clear () {
		this.tail = null;
		this.linkedListRoot.next = null;
		this.size = 0;
		this.maxLevel = 0;
		this.head.clear();
		this.head.targetNode = this.linkedListRoot;
	}

	public long size () {
		return this.size;
	}

	long size = 0;
	int maxLevel = 0;
	ContentNode<E> linkedListRoot = new ContentNode<E>();
	ContentNode<E> tail = null;
	final SkipNode<E> head = new SkipNode<E>(this.linkedListRoot);

	public void addElement (final E e) {
		int currentLevel = this.maxLevel;
		SkipNode<E> previousShortcut = this.head;
		SkipNode<E> currentShortcut = null;
		while (currentLevel >= 0) {
			currentShortcut = previousShortcut.getShortcut(currentLevel);
			while (currentShortcut != null && this.compare(e, currentShortcut.targetNode.content) > 0) {
				previousShortcut = currentShortcut;
			}
			currentLevel--;
		}

		final SkipNode<E> newNode = this.insertNode(previousShortcut, e);

	}

	private SkipNode<E> insertNode (final SkipNode<E> previousShortcut, final E e) {

		ContentNode<E> previousNode = previousShortcut.targetNode;
		ContentNode<E> currentNode = previousNode.next;
		while (currentNode != null && this.compare(e, currentNode.content) > 0) {
			previousNode = currentNode;
			currentNode = currentNode.next;
		}
		final ContentNode<E> newNode = this.insertNode(previousNode);
		newNode.content = e;
		if (newNode.next == null) {
			this.tail = newNode;
		}
		this.size++;

		final boolean coin = Random.newCoin();
		if (coin) {
			final SkipNode<E> newSkip = new SkipNode<E>(newNode);
			newSkip.addSkipNode(previousShortcut.getShortcut(0));
			previousShortcut.setSkipNode(0, newSkip);
		}
		final int currentLevel = 0;
// while(currentLevel<this.)
		return null;

// return newNode;

	}

// private void addElement (final E e) {

//
// }

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

	private ContentNode<E> insertNode (final ContentNode<E> previousNode) {
		final ContentNode<E> newNode = new ContentNode<E>();
		newNode.next = previousNode.next;
		previousNode.next = newNode;
		return newNode;
	}

	public E getElementAt (final int index) {
		return this.findNode(index).content;
	}

	public E removeElementAt (final long index) {
		if (index >= this.size || index < 0) {
			throw new Error("Index outbound exception: " + index + " >= size(" + this.size + ")");
		}
		final ContentNode<E> previous = this.findNode(index - 1);
		final ContentNode<E> toRemove = previous.next;
		if (index == this.size - 1) {
			this.tail = previous;
		}
		previous.next = toRemove.next;
		this.size--;
		return toRemove.content;
	}

	private ContentNode<E> findNode (final long index) {
		if (index > this.size) {
			throw new Error("Index outbound exception: " + index + " >= size(" + this.size + ")");
		}
		ContentNode<E> currentNode = this.linkedListRoot;
		for (int k = 0; k <= index; k++) {
			currentNode = currentNode.next;
		}
		return currentNode;
	}

	public E getTail () {
		if (this.size == 0) {
			throw new Error("List is empty!");
		}
		return this.tail.content;
	}

	public void print (final String tag) {
		L.d("---[" + tag + "](" + this.size + ")----------------------");
		for (int i = 0; i < this.size(); i++) {
			final E lement = this.getElementAt(i);
			L.d("[" + i + "]", lement);
		}
		L.d();

	}

	class SkipNode<T extends Comparable<T>> {
		final ArrayList<SkipNode<T>> next = new ArrayList<SkipNode<T>>(SortedLinkedList.this.maxLevel + 1);
		ContentNode<T> targetNode = null;

		public SkipNode (final ContentNode<T> targetNode) {
			this.targetNode = targetNode;
		}

		public void setSkipNode (final int index, final SkipNode<T> element) {
			this.next.set(index, element);
		}

		public void addSkipNode (final SkipNode<T> skipNode) {
			this.next.add(skipNode);
		}

		public SkipNode<T> getShortcut (final int currentLevel) {
			return this.next.get(currentLevel);
		}

		public void clear () {
			this.next.clear();
		}
	}

	class ContentNode<T extends Comparable<T>> {
		T content;
		ContentNode<T> next = null;

		public ContentNode (final T e) {
			this.content = e;
		}

		public ContentNode () {
		}
	}

	public static void main (final String[] args) {
		DesktopSetup.deploy();

		final SortedLinkedList<Integer> list = new SortedLinkedList<Integer>();
		for (int i = 0; i < 10; i++) {
			final int add = Random.newInt(0, 100);
			L.d("add", add);
			list.addElement(add);
		}

		list.print("list");

		while (list.size() > 0) {
			list.removeElementAt(list.size() - 1);
			list.print("list");
		}

	}

}
