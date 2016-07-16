
package com.google.java;

import java.math.BigInteger;
import java.util.HashMap;

public class Solution {

	public static interface lFunction {
		public BigInteger val (Integer arg);
	}

	public static final lFunction FIBONACCI = memoization(setupExpression());

	public static final lFunction memoization (final lFunction function) {// wraping function!
// return function;//O(2^N)

		final lFunction memo = new lFunction() {

			final HashMap<Integer, BigInteger> map = new HashMap<Integer, BigInteger>();

			@Override
			public BigInteger val (final Integer arg) { // retrive 2
				BigInteger result = this.map.get(arg);// retrive result?
				if (result == null) {// if not presented?
					result = function.val(arg);// compute //
					this.map.put(arg, result);// store it
				}
				return result;
			}

		};

		return memo; // O(N);

	}

	public static final lFunction setupExpression () {
		return arg -> {// input is int

			if (arg == 0) {
				return BigInteger.ONE;
			}
			if (arg == 1) {
				return BigInteger.ONE;
			}

			final int i1 = arg - 1; // previous index (1)
			final int i2 = i1 - 1; // previous index -1 (0)

			final BigInteger f1 = FIBONACCI.val(i1);// one
			final BigInteger f2 = FIBONACCI.val(i2);// ONE

			return f1.add(f2);// ONE+ONE ->2

		};
	}

	public static final void main (final String[] args) {

		int n = 2; // read value

		// F(0) = 1;
		// F(1) = 1;
		// F(n) = F(n-1)+F(n-2);

		System.out.println("F(" + n + ") = " + FIBONACCI.val(n));
		n = 5;
		for (n = 0; n < 100000; n++) {
			System.out.println("F(" + n + ") = " + FIBONACCI.val(n));
		}

	}
}
