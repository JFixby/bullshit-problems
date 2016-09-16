
package com.google.java.seq;

import com.jfixby.cmns.api.collections.Collections;
import com.jfixby.cmns.api.collections.List;

public class LongestSubsequenContainer {

	final Node root = new Node(null, 0, null);
	final List<Node> tails = Collections.newList();
	{

	}

	public static class Node {
		final Node parent;
		final List<Node> children = Collections.newList();
		final Integer data;
		private final int length;

		public Node (final Integer data, final int length, final Node parent) {
			this.data = data;
			this.length = length;
			this.parent = parent;
		}

		public Node spawnChild (final int next) {
			final Node child = new Node(next, this.length + 1, this);
			this.children.add(child);
			return child;
		}
	}

	public void appendLongest (final int next) {
		final Node longest = this.findLongestSequenceWithTailBelowOrEqual(next);
		final Node child = longest.spawnChild(next);
		this.registerTail(child);
	}

	private void registerTail (final Node child) {
	}

	private Node findLongestSequenceWithTailBelowOrEqual (final int value) {
		final Node key = new Node(value, 0, null);

		return null;
	}

	public void printLongest () {
	}

}
