
package com.google.java;

public class StringCompressor {

	public static void main (final String[] args) {

		final String input = "aaaaaaaabbbbbcdddddaaaaxyzaaaabbbbsssss";

		final StringBuilder buffer = new StringBuilder();
		STATE state = STATE.NEW;
		int counter = 0;
		char current = ' ';
		for (int i = 0; i < input.length(); i++) {
			final char c = input.charAt(i);

			if (state == STATE.COLLECTING) {
				if (c == current) {
					counter++;
					continue;
				} else {
					state = STATE.NEW;
					flush(buffer, current, counter);
				}
			}

			if (state == STATE.NEW) {
				counter = 1;
				state = STATE.COLLECTING;
				current = c;
				continue;
			}

		}

		flush(buffer, current, counter);

		log(input + " >>> " + buffer.toString());

	}

	public static final void flush (final StringBuilder buffer, final char current, final int counter) {
		buffer.append(current);
		if (counter > 1) {
			buffer.append(counter);
		}
	}

	public static final void log (final Object message) {
		System.out.println(message);
	}

	static enum STATE {
		NEW, COLLECTING

	}

}
