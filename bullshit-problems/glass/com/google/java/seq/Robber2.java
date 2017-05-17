
package com.google.java.seq;

import java.util.HashMap;

import com.jfixby.scarabei.api.desktop.ScarabeiDesktop;
import com.jfixby.scarabei.api.log.L;

public class Robber2 {

	private boolean useMemoization;

	static class Node {
		Node left;
		Node right;
		long value;

	}

	public interface λ {
		long compute (Node node, boolean rob);
	}

	public static void main (final String[] x) {
		ScarabeiDesktop.deploy();
// final byte arr[] = {10, 13, 23, 17, 6, 11, 18, 16};

// circle
// final byte arr[] = new byte[1000];

		final Robber2 robber = new Robber2();
		robber.useMemoization = true;
		final Node root = new Node();
		L.d("", robber.solve(root));

// final Robber0 robber1 = new Robber0();
// robber1.useMemoization = true;
// L.d("", robber0.solve(arr, 0, arr.length - 2));
// L.d("", robber0.solve(arr, 1, arr.length - 1));

	}

	private long solve (final Node root) {
		this.robResult = this.memo(this.setupExpression());
		return max(this.robResult.compute(root, true), this.robResult.compute(root, !true));
	}

	private λ memo (final λ expression) {
		if (!this.useMemoization) {
			return expression;
		}
		final HashMap<String, Long> memo = new HashMap<>();
		final HashMap<Node, Long> nodeNumerator = new HashMap<>();
		return (n, rob) -> {
			final String key = this.key(n, rob, nodeNumerator);
			Long stored = memo.get(key);
			if (stored == null) {
				stored = expression.compute(n, rob);
				memo.put(key, stored);
			}
			return stored;
		};
	}

	long numer = 0;

	private String key (final Node n, final boolean rob, final HashMap<Node, Long> nodeNumerator) {
		Long nodeName = nodeNumerator.get(n);
		if (nodeName == null) {
			nodeName = this.numer++;
			nodeNumerator.put(n, nodeName);
		}
		return nodeName + "-" + rob;
	}

	private λ setupExpression () {
		return (n, rob) -> {
			if (n == null) {
				return 0;
			}

			if (rob) {
				return n.value + this.robResult.compute(n.left, false) + this.robResult.compute(n.right, false);
			}
// else
			final long optionL = this.robResult.compute(n.left, true) + this.robResult.compute(n.right, false);
			final long optionR = this.robResult.compute(n.left, false) + this.robResult.compute(n.right, true);
			final long optionRL = this.robResult.compute(n.left, true) + this.robResult.compute(n.right, true);

			return max(optionL, optionR, optionRL);
		};
	}

	private λ robResult;

	private static long max (final long x, final long y) {
		return x > y ? x : y;
	}

	private static long max (final long... x) {
		long max = Long.MIN_VALUE;
		for (int i = 0; i < x.length; i++) {
			max = max(x[i], max);
		}
		return max;
	}

}
