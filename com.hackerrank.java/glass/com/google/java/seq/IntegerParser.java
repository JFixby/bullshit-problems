
package com.google.java.seq;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.jfixby.cmns.api.desktop.DesktopSetup;
import com.jfixby.cmns.api.log.L;

public class IntegerParser {

	@Test
	public void test () {
		DesktopSetup.deploy();

		final long[] values = new long[] {1, 2, 1000, +1000, -1000, +5123, +50000, Integer.MAX_VALUE * 4L};
		for (int i = 0; i < values.length; i++) {
			L.d(values[i]);
			assertTrue(values[i] == parse(values[i] + ""));
		}

	}

	private static int parse (final String string) {
		final int plus = 1;
		int minus = 1;

		int pointer = 0;
		if (string.startsWith("+")) {
			pointer++;
		}
		if (string.startsWith("-")) {
			pointer++;
			minus = -1;
		}

		long value = 0;

		for (; pointer < string.length(); pointer++) {
			final char c = string.charAt(pointer);
			if (!isDigit(c)) {
				throw new NumberFormatException();
			}

			value = value * 10L + toDigit(c);

		}

		value = value * minus;

		if (value > Integer.MAX_VALUE) {
			return Integer.MAX_VALUE;
		}
		if (value < Integer.MIN_VALUE) {
			return Integer.MIN_VALUE;
		}
		return (int)value;

// return Integer.parseInt(string);
	}

	private static long toDigit (final char c) {
		return Character.getNumericValue(c);
	}

	private static boolean isDigit (final char c) {
		return Character.isDigit(c);
	}

}
