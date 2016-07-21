
package com.google.java;

import java.util.LinkedHashSet;

public class AllSubstrings {
	static int k = 1;

	public static void main (final String[] args) {

		final String input = "123";

		final StringProcessor processor = new StringProcessor();
		processor.appendAll(input);

		processor.printCollected("collected");

	}

	static class StringProcessor {

		final StringBuilder buffer = new StringBuilder();
		final LinkedHashSet<String> collected = new LinkedHashSet<String>();

		public StringProcessor () {
			this.collected.add("");
		}

		public void printCollected (final String tag) {
			int k = 0;
			for (final String substring : this.collected) {
				log("[" + k + "]", substring);
				k++;
			}
		}

		public void appendAll (final String input) {
			for (int i = 0; i < input.length(); i++) {// N calls
				this.append(input.charAt(i));
			}
		}

		public void append (final char charAt) {
			this.buffer.append(charAt);
			for (int i = 0; i < this.buffer.length(); i++) {// 1+2+3+4+...+N-1 calls
				final String substring = this.buffer.substring(i);
				this.collected.add(substring);
			}
		}

	}

	private static void log (final String tag, final Object msg) {
		System.out.println(tag + " > " + msg);
	}

}
