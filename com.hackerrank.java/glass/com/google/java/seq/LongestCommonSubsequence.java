
package com.google.java.seq;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashMap;

import org.junit.Test;

import com.jfixby.scarabei.api.desktop.ScarabeiDesktop;
import com.jfixby.scarabei.api.err.Err;
import com.jfixby.scarabei.api.log.L;

public class LongestCommonSubsequence {

	public interface λ {
		public int evaluate (int x, int y, Object[] X, Object[] Y);
	}

	private Object[] A;
	private Object[] B;
	private Object[] solution;

	@Test
	public void checkNxN () {
		ScarabeiDesktop.deploy();
		L.d();
		final int N = 100;
		final int common = 50;

		final Integer[] A = new Integer[N];
		final Integer[] B = new Integer[N];

		for (int i = 0; i < N; i++) {
			A[i] = i * 3 + 1;
			B[i] = i * 3 + 2;
		}

		for (int c = 0; c < common; c++) {
			final int i = c * N / common;
			A[i] = B[i] = i * 3 + 0;
		}

		L.d("A", Arrays.toString(A));
		L.d("B", Arrays.toString(B));

		final LongestCommonSubsequence subsequence = new LongestCommonSubsequence();
		subsequence.setUseMemoization(true);

		final int result = subsequence.solve(A, B);

		L.d("LCSS len: " + result, "Memory usage: " + subsequence.getCacheSize() + " Calls: " + subsequence.getCallsDone());
		subsequence.printSolution();
		subsequence.reset();

		assertTrue(common == result);

	}

	@Test
	public void checkABCDA () {
		L.d();
		final Character[] A = new Character[] {'A', 'B', 'C', 'D', 'A'};
		final Character[] B = new Character[] {'A', 'C', 'B', 'D', 'E', 'A'};

		L.d("A", Arrays.toString(A));
		L.d("B", Arrays.toString(B));

		final LongestCommonSubsequence subsequence = new LongestCommonSubsequence();
		subsequence.setUseMemoization(true);

		final int result = subsequence.solve(A, B);

		L.d("LCSS len: " + result, "Memory usage: " + subsequence.getCacheSize() + " Calls: " + subsequence.getCallsDone());
		subsequence.printSolution();
		subsequence.reset();

	}

	public void printSolution () {
		L.d("solution", Arrays.toString(this.getSolution()));
	}

	public Object[] getSolution () {
		if (this.solution != null) {
			return this.solution;
		}
		final int S = this.getResult();
		if (S < 0) {
			Err.reportError("No solution found");
		}
		this.solution = new Object[S];
		int a_i = this.A.length - 1;
		int b_i = this.B.length - 1;
		int s_i = S - 1;
		while (s_i >= 0) {
			final Object a = this.A[a_i];
			final Object b = this.B[b_i];
			if (equals(a, b)) {
				this.solution[s_i] = a;
				a_i--;
				b_i--;
				s_i--;
				continue;
			}

			final int value = this.λFunction.evaluate(a_i, b_i, this.A, this.B);

			final int value_T = this.λFunction.evaluate(a_i, b_i - 1, this.A, this.B);
			if (value_T == value) {
				b_i--;
				continue;
			}

			a_i--;

		}
		return this.solution;
	}

	private int getResult () {
		return this.result;
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
	private int result = -1;

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

			final LCSSInput key = keyOf(x, y);
			Integer value = this.cache.get(key);
			if (value == null) {
				value = expression.evaluate(x, y, X, Y);
				this.cache.put(key, value);
			}
			return value;
		};
	}

	public int solve (final Object[] A, final Object[] B) {
		this.A = A;
		this.B = B;
		this.calls = 0;
		return this.result = this.λFunction.evaluate(this.A.length - 1, this.B.length - 1, this.A, this.B);

	}

	public void reset () {
		this.cache.clear();
		this.calls = 0;
		this.result = -1;
		this.solution = null;
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

		public LCSSInput (final int x, final int y) {
			this.x = x;
			this.y = y;

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

			return true;
		}
	}

	private static LCSSInput keyOf (final int x, final int y) {
		return new LCSSInput(x, y);
	}

}
