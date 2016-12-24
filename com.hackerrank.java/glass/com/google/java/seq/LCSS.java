
package com.google.java.seq;

import java.util.Arrays;
import java.util.HashMap;

import com.jfixby.scarabei.api.desktop.DesktopSetup;
import com.jfixby.scarabei.api.log.L;

public class LCSS {

	private boolean useMemoization = true;
	long calls = 0;
	final HashMap<String, Integer> cache = new HashMap<String, Integer>();
	private final Object[] X;
	private final Object[] Y;
	λ λ;

	public interface λ {
		int eval (int x, int y);
	}

	public LCSS (final Object[] a, final Object[] b) {
		this.X = a;
		this.Y = b;
		this.λ = this.memoization(this.setupλExpression());
	}

	private λ memoization (final λ expression) {
		return (x, y) -> {
			final String key = this.keyFor(x, y);
			Integer value = this.cache.get(key);
			if (value == null) {
				value = expression.eval(x, y);
				if (this.useMemoization) {
					this.cache.put(key, value);
				}
			}
			return value;
		};
	}

	private String keyFor (final int x, final int y) {
		return x + ":" + y;
	}

	private λ setupλExpression () {
		return (x, y) -> {
			this.calls++;
			if (x < 0 || y < 0) {
				return 0;
			}
			final Object a = this.X[x];
			final Object b = this.Y[y];
			if (equals(a, b)) {
				return this.λ.eval(x - 1, y - 1) + 1;
			}
			final int L = this.λ.eval(x - 1, y);
			final int R = this.λ.eval(x, y - 1);
			return Math.max(L, R);
		};
	}

	public static final boolean equals (final Object a, final Object b) {
		if (a == null) {
			if (b == null) {
				return true;
			}
			return false;
		}
		return a.equals(b);
	}

	public static void main (final String[] args) {

		DesktopSetup.deploy();
		{
			L.d();
			final Character[] A = new Character[] {'A', 'B', 'C', 'D', 'A'};
			final Character[] B = new Character[] {'A', 'C', 'B', 'D', 'E', 'A'};

			L.d("A", Arrays.toString(A));
			L.d("B", Arrays.toString(B));

			final LCSS lccs = new LCSS(A, B);
			lccs.setUseMemoization(true);

			final int result = lccs.solve();

			L.d("LCSS len: " + result, "Memory usage: " + lccs.getCacheSize() + " Calls: " + lccs.getCallsDone());
			lccs.printSolution();

		}
		{
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

			final LCSS subsequence = new LCSS(A, B);
			subsequence.setUseMemoization(true);

			final int result = subsequence.solve();

			L.d("LCSS len: " + result, "Memory usage: " + subsequence.getCacheSize() + " Calls: " + subsequence.getCallsDone());
			subsequence.printSolution();

		}

	}

	private Object[] getSolution () {
		final Object[] result = new Object[this.solve()];
		int index = result.length - 1;
		int x = this.X.length - 1;
		int y = this.Y.length - 1;
		while (index >= 0) {
			final Object a = this.X[x];
			final Object b = this.Y[y];
			if (equals(a, b)) {
				result[index] = a;
				x--;
				y--;
				index--;
				continue;
			}

			final int xy = this.λ.eval(x, y);
			final int xmy = this.λ.eval(x - 1, y);
			if (xy == xmy) {
				x--;
				continue;
			}

			y--;
		}

		return result;
	}

	public int solve () {
		return this.λ.eval(this.X.length - 1, this.Y.length - 1);
	}

	public long getCallsDone () {
		return this.calls;
	}

	public int getCacheSize () {
		return this.cache.size();
	}

	private void setUseMemoization (final boolean useMemoization) {
		this.useMemoization = useMemoization;
	}

	public void printSolution () {
		L.d("solution", Arrays.toString(this.getSolution()));
	}

}
