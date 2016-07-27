
package com.google.java;

import java.util.HashMap;

import com.jfixby.cmns.api.debug.Debug;
import com.jfixby.cmns.api.debug.DebugTimer;
import com.jfixby.cmns.api.random.Random;
import com.jfixby.red.desktop.DesktopSetup;

public class CircularString {

	public static void main (final String[] args) {
		DesktopSetup.deploy();
		Random.setSeed(0);
		final String A = generate(200000);
		final String B = rotate(A, 100);
// System.out.println(A);
		final DebugTimer timer = Debug.newTimer();
		timer.reset();
		final int offsetMy = findOffset(A, B);
		timer.printTime("offsetMy: " + offsetMy);
		timer.reset();
		final int offsetJava = (B + B).indexOf(A);
		timer.printTime("offsetJava: " + offsetJava);

		System.out.println(A.substring(0, 500));
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
			final int M = Random.newInt(0, 100);
			final char C = (char)('A' + Random.newInt(0, 1));
			for (int m = 0; m < M; m++) {
				tmp.append(C);
			}

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
		return compare(a, 0, 0 + N, b, offsetB, (offsetB + N));
	}

	private static boolean compare (final String a, final int a_min, final int a_max, final String b, final int b_min,
		final int b_max) {
//// final Key key = keyOf(a_min, a_max, b_min % a.length(), b_max % a.length());
// final String key = a_min + "#" + a_max + "#" + b_min % a.length() + "#" + b_max % a.length();
//// L.d("compare", key);
// Boolean result = reg.get(key);
// if (result == null) {
// result = compareCompute(a, a_min, a_max, b, b_min, b_max);
// reg.put(key, result);
// } else {
// L.d(key, result);
// }
// return result;
		return compareCompute(a, a_min, a_max, b, b_min, b_max);

	}

	private static boolean compareCompute (final String a, final int a_min, final int a_max, final String b, final int b_min,
		final int b_max) {
		final int N = a_max - a_min;
		if (N > 0) {
			final int midA = a_min + N / 2;
			final int midB = b_min + N / 2;
			return compare(a, a_min, midA, b, b_min, midB) && compare(a, midA + 1, a_max, b, midB + 1, b_max);
		} else {
			return compareComputeFinal(a, a_min, a_max, b, b_min, b_max);
		}
	}

	private static boolean compareComputeFinal (final String a, final int a_min, final int a_max, final String b, final int b_min,
		final int b_max) {
		final int N = a_max - a_min;

// final Key key = keyOf(a_min, a_max, b_min, b_max);
// L.d(key);

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

	static class Key {

		private final String string;
		private final int hashCode;

		public Key (final String string) {
			this.string = string;
			this.hashCode = string.hashCode();
		}

		@Override
		public int hashCode () {
			return this.hashCode;
		}

		@Override
		public String toString () {
			return this.string;
		}

		@Override
		public boolean equals (final Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (this.getClass() != obj.getClass()) {
				return false;
			}
			final Key other = (Key)obj;
			if (this.string == null) {
				if (other.string != null) {
					return false;
				}
			} else if (!this.string.equals(other.string)) {
				return false;
			}
			return true;
		}

	}

	private static Key keyOf (final int a_min, final int a_max, final int b_min, final int b_max) {
		return new Key(a_min + "#" + a_max + "#" + b_min + "#" + b_max);
	}

}
