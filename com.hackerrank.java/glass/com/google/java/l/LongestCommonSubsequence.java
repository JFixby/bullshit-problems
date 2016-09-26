
package com.google.java.l;

import java.util.Arrays;
import java.util.HashMap;

import com.jfixby.cmns.api.log.L;
import com.jfixby.red.desktop.DesktopSetup;

public class LongestCommonSubsequence {

	public interface λ {
		public int evaluate (int x, int y, Object[] X, Object[] Y);
	}

	public static void main (final String[] args) {
		DesktopSetup.deploy();
		final Integer[] A = new Integer[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};
		final Integer[] B = new Integer[] {0, 2, 0, 4, 0, 6, 0, 8, 0, 10, 00, 12, 00, 14, 00, 16, 00, 18, 00, 20};

		final LongestCommonSubsequence subsequence = new LongestCommonSubsequence();
		subsequence.setUseMemoization(true);

		final int result = subsequence.solve(A, B);

		L.d(result, "Memory usage: " + subsequence.getCacheSize() + " Calls: " + subsequence.getCallsDone());
		subsequence.reset();

	}

	final λ λFunction = this.memoization(this.setupExpression());
	final HashMap<LCSSInput, Integer> cache = new HashMap<LCSSInput, Integer>();

	/*
	 * Does O(2^(M+N)) operations when false and O(M*N) when true
	 *
	 * Example: 9 244 444 806 calls required to solve 20+20 elements problem (hint: Integer.Max = 2 147 483 647)
	 *
	 * while 400 calls is enough with memoization
	 *
	 */

	boolean useMemoization = true;

	long calls = 0;

	private λ setupExpression () {
		return (x, y, X, Y) -> {
			this.calls++;
			if (x < 0 || y < 0) {
				return 0;
			}
			final Object xi = X[x];
			final Object yi = Y[y];

			return equals(xi, yi) ? this.λFunction.evaluate(x - 1, y - 1, X, Y) + 1
				: Math.max(this.λFunction.evaluate(x - 1, y, X, Y), this.λFunction.evaluate(x, y - 1, X, Y));
		};
	}

	private λ memoization (final λ expression) {
		return (x, y, X, Y) -> {
			if (!this.useMemoization) {
				return expression.evaluate(x, y, X, Y);
			}

			final LCSSInput key = keyOf(x, y, X, Y);
			Integer value = this.cache.get(key);
			if (value == null) {
				value = expression.evaluate(x, y, X, Y);
				this.cache.put(key, value);
			}
			return value;
		};
	}

	public int solve (final Object[] A, final Object[] B) {
		this.calls = 0;
		return this.λFunction.evaluate(A.length - 1, B.length - 1, A, B);
	}

	public void reset () {
		this.cache.clear();
		this.calls = 0;
	}

	public void setUseMemoization (final boolean useMemoization) {
		this.useMemoization = useMemoization;
	}

	public long getCallsDone () {
		return this.calls;
	}

	public int getCacheSize () {
		return this.cache.size();
	}

	static private boolean equals (final Object x, final Object y) {
		if (x == null && y == null) {
			return true;
		}
		if (x != null && y == null) {
			return false;
		}
		if (x == null && y != null) {
			return false;
		}
		return x.equals(y);
	}

	static final class LCSSInput {
		private final int x;
		private final int y;
		private final Object[] X;
		private final Object[] Y;

		public LCSSInput (final int x, final int y, final Object[] X, final Object[] Y) {
			this.x = x;
			this.y = y;
			this.X = X;
			this.Y = Y;
		}

		@Override
		public int hashCode () {
			final int prime = 31;
			int result = 1;
// result = prime * result + Arrays.hashCode(this.X);
// result = prime * result + Arrays.hashCode(this.Y);
			result = prime * result + this.x;
			result = prime * result + this.y;
			return result;
		}

		@Override
		public boolean equals (final Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (this.getClass() != obj.getClass()) {
				return false;
			}
			final LCSSInput other = (LCSSInput)obj;

			if (this.x != other.x) {
				return false;
			}
			if (this.y != other.y) {
				return false;
			}
			if (!Arrays.equals(this.X, other.X)) {
				return false;
			}
			if (!Arrays.equals(this.Y, other.Y)) {
				return false;
			}
			return true;
		}
	}

	private static LCSSInput keyOf (final int x, final int y, final Object[] X, final Object[] Y) {
		return new LCSSInput(x, y, X, Y);
	}

}
