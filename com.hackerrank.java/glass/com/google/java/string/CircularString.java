
package com.google.java.string;

import com.jfixby.cmns.api.debug.Debug;
import com.jfixby.cmns.api.debug.DebugTimer;
import com.jfixby.cmns.api.random.Random;
import com.jfixby.red.desktop.DesktopSetup;

public class CircularString {

	public static void main (final String[] args) {
		DesktopSetup.deploy();
		Random.setSeed(0);
		final String A = generate(20000);
		final String B = rotate(A, A.length() - 1);
// System.out.println(A);
// System.out.println(B);
		final DebugTimer timer = Debug.newTimer();
		timer.reset();
		final int offsetMy = findOffset(A, B);
		timer.printTime("offsetMy: " + offsetMy);
		timer.reset();
		final int offsetHash = findOffsetHash(A, B);
		timer.printTime("offsetHash: " + offsetHash);
		timer.reset();
		final int offsetJava = (B + B).indexOf(A);
		timer.printTime("offsetJava: " + offsetJava);

	}

	private static int findOffsetHash (final String A, final String B) {
		final int len = A.length();
		final StringHasher hashA = new StringHasher().appendRight(A);

		final StringHasher hashB = new StringHasher().appendRight(B);

		int offsetB = 0;
// L.d("hashA", hashA);
// L.d("hashB +" + offsetB, hashB);
		while (!hashA.equals(hashB) && offsetB < len) {
			hashB.appendRight(B.charAt(offsetB % len));
			hashB.discardLeft(B.charAt(offsetB % len));
			offsetB++;
// L.d("hashB +" + offsetB, hashB);
		}
		return offsetB;
	}

	private static String rotate (final String a, final int N) {
		return a.substring(N, a.length()) + a.substring(0, N);
	}

	private static String generate (final int N) {
		final StringBuilder tmp = new StringBuilder();
		for (int i = 0; i < N; i++) {
			final int M = Random.newInt(0, 100);
			final char C = (char)('A' + Random.newInt(0, 1));
			for (int m = 0; m < M; m++) {
				tmp.append(C);
			}

		}
		return tmp.toString();

// return "ABCD";

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

	final private static boolean compare (final String a, final String b, final int offsetB) {
		final int N = a.length() - 1;
		return compare(a, 0, 0 + N, b, offsetB, (offsetB + N));
	}

	private static boolean compare (final String a, final int a_min, final int a_max, final String b, final int b_min,
		final int b_max) {
		return compareComputeFinal(a, a_min, a_max, b, b_min, b_max);

	}

	private static boolean compareComputeFinal (final String a, final int a_min, final int a_max, final String b, final int b_min,
		final int b_max) {
		final int N = a_max - a_min;
		{
			final char Ai = a.charAt(a_max % a.length());
			final char Bi = b.charAt(b_max % b.length());
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
