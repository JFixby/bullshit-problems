
package com.google.java;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

import org.junit.Test;

import com.jfixby.cmns.api.debug.Debug;
import com.jfixby.cmns.api.debug.DebugTimer;
import com.jfixby.cmns.api.desktop.DesktopSetup;
import com.jfixby.cmns.api.log.L;
import com.jfixby.cmns.api.random.Random;

public class SkipList<E extends Comparable<E>> {
	public SkipList () {
		this.clear();
	}

	int size = 0;
	int maxLevel = 0;
	final Node<E> left = new Node<E>();
	final Node<E> right = new Node<E>();

	public void clear () {
		this.size = 0;
		this.maxLevel = 0;
		this.left.clearRight();
		this.right.clearLeft();
		this.ensureMaxLevel();

// this.left.left.add(null);
// this.right.right.add(null);
	}

	class Node<T> {
		private final ArrayList<Node<T>> left = new ArrayList<Node<T>>();
		private final ArrayList<Node<T>> right = new ArrayList<Node<T>>();
		T content;

		public int maxRightLevel () {
			return this.right.size() - 1;
		}

		public int maxLeftLevel () {
			return this.left.size() - 1;
		}

		@Override
		public String toString () {
			return this.toString(0);
		}

		public String toString (final int level) {
			String left = "<-";
			if (this.getLeft(level) == null) {
				left = "x-";
			}
			String right = "->";
			if (this.getRight(level) == null) {
				right = "-x";
			}

			String print = "";

			if (new Integer(96).equals(this.content)) {
				L.d();
				this.printLevels();
			}

			if (level == 0) {
				if (this.content != null) {
					print = this.content + "";
				} else {
					print = "...";
				}
			} else if (level == this.maxNodeLevel()) {
				print = "T ";
			}

			return left + String.format("%3s", print) + right;

		}

		private int maxNodeLevel () {
			return Math.max(this.maxLeftLevel(), this.maxRightLevel());
		}

		public void clearLeft () {
			this.left.clear();

		}

		public void clearRight () {
			this.right.clear();

		}

		public void addLeft (final Node<T> left) {
			this.left.add(left);

		}

		public void addRight (final Node<T> right) {
			this.right.add(right);

		}

		public Node<T> getRight (final int level) {
			if (this.right.size() <= level) {
				return null;
			}
			return this.right.get(level);
		}

		public Node<T> getLeft (final int level) {
			if (this.left.size() <= level) {
				return null;
			}
			return this.left.get(level);
		}

		public void setLeft (final int level, final Node<T> newNode) {
			this.left.set(level, newNode);
		}

		public void setRight (final int level, final Node<T> newNode) {
			this.right.set(level, newNode);
		}

		public void printLevels () {
			for (int i = this.maxNodeLevel(); i >= 0; i--) {
				final String l = this.getLeft(i) + "";
				final String r = this.getRight(i) + "";
				L.d(l + " :" + i + ": " + r);
			}
		}

	}

	public void addElement (final E element) {
		final Node<E> preNode = this.findPreNode(element);
		final Node<E> newNode = this.insertNodeAfter(preNode, 0);
		newNode.content = element;

		int newLevel = 0;
		while (this.expand()) {
			newLevel++;
		}
		this.maxLevel = Math.max(newLevel, this.maxLevel);
		this.ensureMaxLevel();

		for (int level = 1; level <= newLevel; level++) {
			final Node<E> leftNeighbour = this.findLeftNeighbourAtLevel(newNode, level);
			final Node<E> rightNeighbour = leftNeighbour.getRight(level);

			leftNeighbour.setRight(level, newNode);
			newNode.addLeft(leftNeighbour);

			rightNeighbour.setLeft(level, newNode);
			newNode.addRight(rightNeighbour);

		}

	}

	private Node<E> findLeftNeighbourAtLevel (final Node<E> newNode, final int level) {
		Node<E> current = newNode.getLeft(level - 1);
		while (current.maxRightLevel() < level) {
			current = current.getLeft(level - 1);
		}
		return current;
	}

	private void ensureMaxLevel () {
		for (int i = 0; i <= this.maxLevel; i++) {
			if (this.left.getRight(i) == null) {
				this.left.addRight(this.right);
			}
			if (this.right.getLeft(i) == null) {
				this.right.addLeft(this.left);
			}
		}

	}

	private boolean expand () {
		return Random.newCoin();
	}

	private Node<E> findPreNode (final E element) {
		Node<E> current = this.left;
// L.d("searching", element);
		for (int level = this.maxLevel; level >= 0; level--) {
			while (current != this.right && this.compare(element, current.content) > 0) {
				current = current.getRight(level);
// L.d("jump level=" + level, current.toString(level) + " == " + current.content);
// current.printLevels();
			}
			current = current.getLeft(level);
// L.d("return ", current.toString(0));
		}
		return current;
	}

	private Node<E> insertNodeAfter (final Node<E> current, final int level) {
		final Node<E> newNode = new Node<E>();
		newNode.addLeft(current);
		newNode.addRight(current.getRight(level));
		current.getRight(level).setLeft(level, newNode);
		current.setRight(level, newNode);
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
		if (preNode.getRight(0) == this.right) {
			return;
		}
		if (this.compare(element, preNode.getRight(0).content) == 0) {
			this.removeNode(preNode.getRight(0));
		}
	}

	public boolean contains (final E element) {
		if (this.size == 0) {
			return false;
		}
		final Node<E> preNode = this.findPreNode(element);
		if (preNode.getRight(0) == this.right) {
			return false;
		}
		if (this.compare(element, preNode.getRight(0).content) == 0) {
			return true;
		}
		return false;
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
			result = result.getRight(0);
		}
		return result;
	}

	private void removeNode (final Node<E> targetNode) {
		for (int level = 0; level <= targetNode.maxNodeLevel(); level++) {
			targetNode.getLeft(level).setRight(level, targetNode.getRight(level));
			targetNode.getRight(level).setLeft(level, targetNode.getLeft(level));
		}
		this.size--;
		if (this.size == 0) {
			this.clear();
		}
	}

	public void print (final String tag) {
		L.d("---[" + tag + "](" + this.size + ")-----------------------------------------------------");
		for (int level = this.maxLevel; level >= 0; level--) {
			this.print(tag, level);
		}

	}

	private void print (final String tag, final int level) {
// if (level == 0) {
// this.print0(tag, 0);
// return;
// }

		Node<E> current = this.left;
		Node<E> currentL = this.left;

		final StringBuilder t = new StringBuilder();

		while (current != null) {
			if (current == currentL) {
				final String print = current.toString(level);
				t.append("" + String.format("%7s", print));
				currentL = currentL.getRight(level);
			} else {
				t.append("" + String.format("%7s", " "));
			}

			current = current.getRight(0);
		}
		t.insert(0, "(" + level + ") ");
		L.d(t);

	}

	public int size () {
		return this.size;
	}

// @Test
// public void testa () {
// DesktopSetup.deploy();
// Sys.exit();
// final DebugTimer timer = Debug.newTimer();
// final int N = 1000;
//
// timer.reset();
//
// final ArrayList<Integer> toRemove = new ArrayList<Integer>();
// final ArrayList<Integer> checker = new ArrayList<Integer>();
// final SkipList<Integer> toTest = new SkipList<Integer>();
// for (int k = 0; k < N; k++) {
// Random.setSeed(k);
//// L.d("seed", k);
// for (int i = 0; i < N; i++) {
// final int add = Random.newInt32();
// checker.add(add);
// toTest.addElement(add);
//// toTest.print("toTest");
// if (Random.newCoin()) {
// toRemove.add(add);
// }
// }
// assertTrue(compareLists(checker, toTest));
//
// for (int r = 0; r < toRemove.size(); r++) {
// final Integer R = toRemove.get(r);
// checker.remove(R);
// toTest.removeElement(R);
// }
// assertTrue(compareLists(checker, toTest));
// checker.clear();
// toTest.clear();
// toRemove.clear();
// assertTrue(compareLists(checker, toTest));
//
// }
// timer.printTime("test done: N=" + N);
// }

	@Test
	public void testB () {
		DesktopSetup.deploy();
		final DebugTimer timer = Debug.newTimer();
		final int N = 100000;
		final ArrayList<Integer> check = new ArrayList<Integer>(N);
		Random.setSeed(0);
		for (int k = 0; k < N; k++) {
			final int add = Random.newInt32();
			check.add(add);
		}

		{
			final SkipList<Integer> toTest = new SkipList<Integer>();

			timer.reset();
			for (int k = 0; k < N; k++) {
				final int candidate = check.get(k);
				toTest.addElement(candidate);
			}
			timer.printTime("SkipList insert:   N=" + N);

			timer.reset();
			for (int k = 0; k < N; k++) {
				final int candidate = check.get(k);
				toTest.contains(candidate);
				toTest.contains(candidate + 1);
			}
			timer.printTime("SkipList contains: N=" + N);
		}

// {
// final ArrayList<Integer> toTest = new ArrayList<Integer>(N);
//
// timer.reset();
// for (int k = 0; k < N; k++) {
// final int candidate = check.get(k);
// toTest.add(candidate);
// }
// timer.printTime("ArrayList insert: N=" + N);
//
// timer.reset();
// for (int k = 0; k < N; k++) {
// final int candidate = check.get(k);
// if (!toTest.contains(candidate)) {
// assert (false);
// }
// }
// timer.printTime("ArrayList contains: N=" + N);
// }
// {
// final LinkedList<Integer> toTest = new LinkedList<Integer>();
//
// timer.reset();
// for (int k = 0; k < N; k++) {
// final int candidate = check.get(k);
// toTest.add(candidate);
// }
// timer.printTime("LinkedList insert: N=" + N);
//
// timer.reset();
// for (int k = 0; k < N; k++) {
// final int candidate = check.get(k);
// if (!toTest.contains(candidate)) {
// assert (false);
// }
// }
// timer.printTime("LinkedList contains: N=" + N);
// }

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

	static private boolean compareLists (final ArrayList<Integer> checker, final SkipList<Integer> toTest) {
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
		final DebugTimer timer = Debug.newTimer();
		final int N = 100_000;
		final ArrayList<Integer> check = new ArrayList<Integer>(N);
		Random.setSeed(0);
		for (int k = 0; k < N; k++) {
// final int add = Random.newInt(0, 10);
			final int add = Random.newInt32();
			check.add(add);
		}

		{
			final SkipList<Integer> toTest = new SkipList<Integer>();

			timer.reset();
			for (int k = 0; k < N; k++) {
				final int candidate = check.get(k);
// if (!check.contains(candidate)) {
// toTest.addElement(candidate);
// }
				toTest.addElement(candidate);
			}
			timer.printTime("SkipList insert:   N=" + N);
// toTest.print("toTest");
			timer.reset();
			for (int k = 0; k < N; k++) {
				final int candidate = check.get(k);
				toTest.contains(candidate);
				toTest.contains(candidate + 1);
			}
			timer.printTime("SkipList contains: N=" + N);
// toTest.print("toTest");
		}

		{
			final HashSet<Integer> toTest = new HashSet<Integer>();

			timer.reset();
			for (int k = 0; k < N; k++) {
				final int candidate = check.get(k);
				toTest.add(candidate);
			}
			timer.printTime("HashSet insert:   N=" + N);
// toTest.print("toTest");
			timer.reset();
			for (int k = 0; k < N; k++) {
				final int candidate = check.get(k);
				toTest.contains(candidate);
				toTest.contains(candidate + 1);
			}
			timer.printTime("HashSet contains: N=" + N);
// toTest.print("toTest");
		}

// DesktopSetup.deploy();
// Random.setSeed(0L);
// final SkipList<Integer> list = new SkipList<Integer>();
// for (int i = 0; i < 60; i++) {
// final int add = Random.newInt(0, 100);
//
// L.d("add", add);
// list.addElement(add);
// list.print("list: " + i);
//
// }
//
// list.print("list");
//
//// while (list.size() > 0) {
//// final Integer element = list.removeElementAt(list.size() - 1);
//// L.d("remove", element);
//// list.print("list");
//// }
//
// while (list.size() > 0) {
// L.d();
// final Integer element = list.getElementAt(list.size() - 1);
// list.removeElement(element);
// L.d("remove", element);
// list.print("list");
// }

	}

}
