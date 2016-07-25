
package com.google.java;

import java.util.HashMap;

import com.jfixby.cmns.api.debug.Debug;
import com.jfixby.cmns.api.debug.DebugTimer;
import com.jfixby.cmns.api.random.Random;
import com.jfixby.red.desktop.DesktopSetup;

public class CircularString {

	public static void main (final String[] args) {
		DesktopSetup.deploy();
		final String A = generate(20_000_000);
		final String B = rotate(A, 100);
// System.out.println(A);
		final DebugTimer timer = Debug.newTimer();
		timer.reset();
		final int offsetMy = findOffset(A, B);
		timer.printTime("offsetMy: " + offsetMy);
		timer.reset();
		final int offsetJava = (B + B).indexOf(A);
		timer.printTime("offsetJava: " + offsetJava);

// System.out.println(A);
// System.out.println(B.substring(0, offsetMy) + " -> " + B.substring(offsetMy, A.length()));
//
// System.out.println(A);
// System.out.println(B.substring(0, offsetJava) + " -> " + B.substring(offsetJava, A.length()));

	}

	private static String rotate (final String a, final int N) {
		return a.substring(N, a.length()) + a.substring(0, N);
	}

	private static String generate (final int N) {
		final StringBuilder tmp = new StringBuilder();
		for (int i = 0; i < N; i++) {
			tmp.append((char)('A' + Random.newInt(0, 1)));
		}
		return tmp.toString();
	}

	private static int findOffset (final String A, final String B) {
		final int len = A.length();
		for (int offsetB = 0; offsetB < len; offsetB++) {
			if (compare(A, B, offsetB)) {
				return offsetB;
			}
		}
		throw new Error();
	}

	static final HashMap<String, Boolean> reg = new HashMap<String, Boolean>();

	private static boolean compare (final String a, final String b, final int offsetB) {
		final int N = a.length() - 1;
		return compareCompute(a, 0, 0 + N, b, offsetB, (offsetB + N) % a.length());
	}

	private static boolean compareCompute (final String a, final int a_min, final int a_max, final String b, final int b_min,
		final int b_max) {
		final int N = a_max - a_min;

		{
			final char Ai = a.charAt(a_max);
			final char Bi = b.charAt(b_max);
			if (Ai != Bi) {
				return false;
			}
		}
		int ai = 0;
		int bi = 0;
		for (int k = 0; k <= N; k++) {
			ai = (a_min + k) % a.length();
			bi = (b_min + k) % b.length();

			final char Ai = a.charAt(ai);
			final char Bi = b.charAt(bi);
			if (Ai != Bi) {
				return false;
			}
		}
		return true;
	}

}
