
package com.google.java.seq;

import java.util.HashMap;

import com.jfixby.scarabei.api.desktop.ScarabeiDesktop;
import com.jfixby.scarabei.api.log.L;

public class Robber0 {

	public interface λ {
		long compute (int house);
	}

	public Robber0 () {

	}

	private byte[] arr;
	private λ robResult;

	public boolean useMemoization = !true;
	private int fromHouse;
	private int toHouse;

	private λ setupExpression () {
		return h -> {
			if (h < this.fromHouse) {
				return 0;
			}
			if (h > this.toHouse) {
				return 0;
			}

			if (h == this.fromHouse) {
				return this.arr[this.fromHouse];
			}

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
// final byte arr[] = new byte[1000];

		final Robber0 robber = new Robber0();
		robber.useMemoization = true;
		L.d("result", robber.solve(arr, 0, arr.length - 1));

	}

	public long solve (final byte[] arr, final int fromHouse, final int toHouse) {
		this.arr = arr;
		this.fromHouse = fromHouse;
		this.toHouse = toHouse;
		this.robResult = this.memo(this.setupExpression());
		return this.robResult.compute(toHouse);
	}

	private static long max (final long x, final long y) {
		return x > y ? x : y;
	}

}
