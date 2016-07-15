
package com.google.java;

public class StringCompressor2 {

	public static void main (final String[] args) {

		final String input = "aaaaa   aaabbbbbcdddddaaaaxyzaaaabbbbsssss";

		final StringCompressor2 compressor = new StringCompressor2();
		compressor.reset();
		compressor.append(input);
		System.out.println(input + " >>> " + compressor.toString());
		compressor.reset();
	}

	boolean isNew = true;
	StringBuilder tmp = new StringBuilder();
	char currentChar;
	int counter;

	public void reset () {
		this.tmp.setLength(0);
		this.isNew = true;
	}

	public void append (final String input) {
		for (int i = 0; i < input.length(); i++) {
			this.append(input.charAt(i));
		}
	}

	public void append (final char charAt) {
		if (this.isNew) {
			this.currentChar = charAt;
			this.counter = 1;
			this.isNew = false;
			return;
		}

		if (charAt == this.currentChar) {
			this.currentChar = charAt;
			this.counter++;
			return;
		} else {
			this.flush();
			this.currentChar = charAt;
			this.counter = 1;
		}

	}

	private void flush () {
		if (this.isNew) {
			return;
		}
		this.tmp.append(this.currentChar);
		if (this.counter > 1) {
			this.tmp.append(this.counter);
		}
	}

	@Override
	public final String toString () {
		this.flush();
		return this.tmp.toString();
	}

}
