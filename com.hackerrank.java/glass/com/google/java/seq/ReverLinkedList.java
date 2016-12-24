
package com.google.java.seq;

import com.jfixby.scarabei.api.desktop.DesktopSetup;
import com.jfixby.scarabei.api.log.L;

public class ReverLinkedList {

	static class Node<T> {
		Node<T> next;
		T object;

		@Override
		public String toString () {
			return "(" + this.object + ")->" + toString(this.next);
		}

		public static <T> String toString (final Node<T> next) {
			if (next == null) {
				return "X";
			}
			return next.toString();
		}

	}

	public static void main (final String[] args) {
		DesktopSetup.deploy();
		final int N = 10;
		Node<Integer> input = null;

		for (int i = 0; i < N; i++) {
			final int k = N - i - 1;
			final Node<Integer> newNode = new Node<Integer>();
			newNode.object = k;
			newNode.next = input;
			input = newNode;
		}
		L.d("input", Node.toString(input));

		final Node<Integer> reverse = reverse(input);
		L.d("reverse", Node.toString(reverse));
	}

	private static <T> Node<T> reverse (final Node<T> input) {
		Node<T> inputNode = input;
		Node<T> tmp = null;
		Node<T> result = null;
		while (inputNode != null) {
			tmp = inputNode.next;
			inputNode.next = result;
			result = inputNode;
			inputNode = tmp;
			tmp = null;
		}

		return result;
	}

}
