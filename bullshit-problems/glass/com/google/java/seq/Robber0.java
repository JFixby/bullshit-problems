
package com.google.java.seq;

import java.util.HashMap;

import com.jfixby.scarabei.api.desktop.ScarabeiDesktop;
import com.jfixby.scarabei.api.log.L;

public class Robber0 {

	public interface λ {
		long compute (int house);
	}

	public Robber0 (final byte[] arr) {
		this.arr = arr;
	}

	private final byte[] arr;

	final λ robResult = this.memo(this.setupExpression());

	private final boolean useMemoization = !true;

	private λ setupExpression () {
		return h -> {
			if (h == 0) {
				return this.arr[0];
			}
			if (h == 1) {
				return max(this.arr[0], this.arr[1]);
			}
			// h==n+1
			return max(this.robResult.compute(h - 1), this.robResult.compute(h - 2) + this.arr[h]);
		};
	}

	private λ memo (final λ expression) {
		if (!this.useMemoization) {
			return expression;
		}
		final HashMap<String, Long> memo = new HashMap<>();
		return h -> {
			final String key = this.key(h);
			Long stored = memo.get(key);
			if (stored == null) {
				stored = expression.compute(h);
				memo.put(key, stored);
			}
			return stored;
		};
	}

	private String key (final int h) {
		return h + "";
	}

	public static void main (final String[] x) {
		ScarabeiDesktop.deploy();
// final byte arr[] = {50, 1, 1, 50};
		final byte arr[] = {10, 13, 23, 17, 6, 11, 18, 16};

		final Robber0 robber = new Robber0(arr);

		L.d("", robber.solve());

	}

	private long solve () {
		return this.robResult.compute(this.arr.length - 1);
	}

	private static long max (final long x, final long y) {
		return x > y ? x : y;
	}

}
