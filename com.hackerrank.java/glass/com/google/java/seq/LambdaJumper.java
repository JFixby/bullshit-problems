
package com.google.java.seq;

import java.util.HashMap;

import com.jfixby.scarabei.api.desktop.DesktopSetup;
import com.jfixby.scarabei.api.log.L;

public class LambdaJumper {

	public interface λ1 {
		public int eval (int to);
	}

	public interface λ2 {
		public long eval (int k, int x);
	}

	private final λ2 H;
	private final λ1 array;
	private int N;

	public LambdaJumper (final int[] map) {
		this.N = map.length;
		final λ1 array = i -> {
			if (i < 0) {
				return -i;
			}
			if (i >= map.length) {
				return Integer.MAX_VALUE;
			}
			return map[i];
		};
		this.array = array;
		this.H = this.memoization(this.debug(this.setupExpression()));
		this.tails.put(-1, 0);
	}

	final HashMap<String, Long> cache = new HashMap<String, Long>();
	final HashMap<Integer, Integer> tails = new HashMap<Integer, Integer>();

	private λ2 memoization (final λ2 setupExpression) {

		return (k, x) -> {
			final String key = "(" + k + "," + x + ")";
			Long value = this.cache.get(key);
			if (value == null) {
				value = setupExpression.eval(k, x);
				this.cache.put(key, value);
			} else {
				L.d("reuse", key);
			}
			return value;
		};
	}

	private λ2 debug (final λ2 setupExpression) {
		return (k, x) -> {
			L.d("compute H(" + k + "," + x + ")");
			final long result = setupExpression.eval(k, x);
			L.d("        H(" + k + "," + x + ") = " + result);
			return result;
		};
	}

	long depth = 0;

	private λ2 setupExpression () {
		return (k, x) -> {
			if (k < 0) {
				return x <= 0 ? 0 : Integer.MAX_VALUE;
			}
// final Integer tail = this.tails.get(k);
// if (tail == null) {
// Err.reportError("tail not found " + k);
// }
// if (k > tail) {
// return Integer.MAX_VALUE;
// }
			if (x <= k) {
				L.d("  previous", k - 1);
				return this.H.eval(k - 1, x);
			}
			if (x - k <= this.array.eval(k)) {
// final int tail_km1 = k + this.array.eval(k);
				L.d("  branch  ", k - 1);
				final long K = this.H.eval(k - 1, k) + 1;
				final long X = this.H.eval(k - 1, x);

				return Math.min(K, X);
			}
			return this.H.eval(k - 1, x);
		};
	}

	public static void main (final String[] args) {
		DesktopSetup.deploy();

		final int[] map = new int[] {3, 4, 1, 1, 0, 2, 2, 0, 1, 1};
// final int[] map = new int[1000];
// final int[] map = new int[] {1, 1, 1, 1};

		final int startIndex = 0;
		final int endIndex = map.length - 1;

		final LambdaJumper jumper = new LambdaJumper(map);

// Step step = Step.EMPTY;
// for (int i = 0; i <= map.length; i++) {
// jumper.solve(i);
// }
		final long hops = jumper.solve(endIndex);
		L.d("hops", hops);
		jumper.printStats();
	}

	private void printStats () {
		L.d("cache.size", this.cache.size());
	}

	public long solve (final int endIndex) {
		return this.H.eval(endIndex + 1, endIndex);
	}

// public static class Step {
// public static final Step EMPTY = new Step();
// final private λ hop;
// final private int maxJump;
// final int index;
// final Step previous;
//
// private Step () {// empty
// this.hop = x -> x <= 0 ? 0 : Integer.MAX_VALUE;
// this.maxJump = 0;
// this.index = -1;
// this.previous = null;
// }
//
// public Step (final int maxJump, final Step previous) {
// this.maxJump = maxJump;
// this.index = 1 + previous.index;
// this.previous = previous;
// this.hop = x -> {
// L.d("F-" + this.index, x);
// if (x <= this.index) {
// return previous.hop.eval(x);
// }
// // x>index
// if (x <= this.index + maxJump) {
// return Math.min(previous.hop.eval(this.index - 1) + 1, previous.hop.eval(x));
// }
// return Integer.MAX_VALUE;
// };
// }
//
// }

}
