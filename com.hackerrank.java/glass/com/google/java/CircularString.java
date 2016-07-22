
package com.google.java;

public class CircularString {

	public static void main (final String[] args) {

		final String A = "1211211121111121111112111111112111111111111111112X";
		final String B = "112111111112111111111111111112X1211211121111121111";

		final int len = A.length();
		for (int offsetB = 0; offsetB < len; offsetB++) {
			if (compare(A, B, offsetB)) {
				System.out.println(A);
				System.out.println(B.substring(0, offsetB) + " -> " + B.substring(offsetB, len));

			}
		}

	}

	private static boolean compare (final String a, final String b, final int offsetB) {
		final int len = a.length();
		for (int i = 0; i < len; i++) {
			final char Ai = a.charAt(i);
			final char Bi = b.charAt((i + offsetB) % len);
			if (Ai != Bi) {
				return false;
			}
		}
		return true;
	}

}
