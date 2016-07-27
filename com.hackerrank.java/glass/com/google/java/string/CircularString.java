
package com.google.java.string;

import java.util.HashMap;

import com.jfixby.cmns.api.debug.Debug;
import com.jfixby.cmns.api.debug.DebugTimer;
import com.jfixby.cmns.api.log.L;
import com.jfixby.cmns.api.random.Random;
import com.jfixby.red.desktop.DesktopSetup;

public class CircularString {

	public static void main (final String[] args) {
		DesktopSetup.deploy();
		Random.setSeed(0);
		final String A = generate(20000);
		final String B = rotate(A, A.length() - 1);
		System.out.println(A);
		System.out.println(B);
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

// System.out.println(A.substring(0, 500));
// System.out.println(B.substring(0, offsetMy) + " -> " + B.substring(offsetMy, A.length()));
//
// System.out.println(A);
// System.out.println(B.substring(0, offsetJava) + " -> " + B.substring(offsetJava, A.length()));

	}

	private static int findOffsetHash (final String A, final String B) {
		final int len = A.length();
		final long hashA = longHash(P, Q, A, 0);

		final long hashB0 = longHash(P, Q, B, 0);
		final long hashB1 = longHash(P, Q, B, 1);

		L.d("B", B);
// L.d("hashA ", hashA);
// L.d("hashB0", hashB0);
// L.d("hashB1", hashB1);
// final long hashB1m = shiftHashWindow(hashB0, P, Q, B, 0);
// L.d("hashB1m", hashB1m);
// Sys.exit();
		long hashBm = hashB0;
		for (int offsetB = 0; offsetB <= len; offsetB++, hashBm = shiftHashWindow(hashBm, P, Q, B, 0)) {
			if (hashA == hashBm) {
				return offsetB;
			}
		}

		throw new Error();
	}

	private static long shiftHashWindow (final long hashB0, final long P, final long Q, final String B, final int hashB0Offset) {
		final int indexOfCharToRemove = hashB0Offset % B.length();
		final int indexOfCharToAppend = (hashB0Offset + B.length()) % B.length();

		L.d("indexOfCharToRemove", indexOfCharToRemove);
		L.d("indexOfCharToAppend ", indexOfCharToAppend);

		final char charToAppend = B.charAt(indexOfCharToAppend);
		final char charToRemove = B.charAt(indexOfCharToRemove);

		L.d("charToAppend", charToAppend);
		L.d("charToRemove", charToRemove);

		long hashB1 = hashB0;

		hashB1 = hashB1 - charToRemove * power(Q, 0, P);// remove char
		if (hashB1 % Q != 0) {
			L.d("hashB0", hashB0);
			L.d("charToRemove", charToRemove);
			L.d("sunstract", charToRemove * power(Q, 0, P));
			L.d("hashB1", hashB1);
			throw new Error();
		}
		L.d();
		L.d("hashB0", hashB0);
		L.d("charToRemove", charToRemove);
		L.d("sunstract", charToRemove * power(Q, 0, P));
		L.d("hashB1", hashB1);

		hashB1 = hashB1 / Q;// shift All
		hashB1 = hashB1 + charToAppend * power(Q, B.length() - 1, P);// append char
		hashB1 = hashB1 % P;

		return hashB1;
	}

	static final long Q = 10;//
	static final long P = 997000000L;// 0xFFFFFFFFFFFFFFC5L;// Largest 64 bit prime. //huge prime;

	private static long longHash (final long P, final long Q, final String string, final int offset) {
		final int N = string.length();
		long result = 0;
		for (int k = N - 1; k >= 0; k--) {
			final char c = string.charAt((k + offset) % N);
// final long pow = power(Q, k, P);
// final long add = (c * pow) % P;
			result = (result * Q + c) % P;
			L.d("add char " + c + " = " + (int)c);
			L.d("result", result);
		}
		L.d();
		return result;
	}

	static HashMap<Integer, Long> kToBigInteger = new HashMap<Integer, Long>();

	public static final long power (final long Q, final int k, final long P) {
		Long result = kToBigInteger.get(k);
		if (result != null) {
			return result.longValue();
		}

		result = powerCompute(Q, k, P);
		kToBigInteger.put(k, result);
		return result;

	}

	private static long powerCompute (final long Q, final int k, final long P) {
		if (k == 0) {
			return 1L;
		}
		if (k == 1) {
			return Q;
		}
		final long next = power(Q, k - 1, P) * Q;
		if (next < 0) {
			throw new Error();
		}
// return next;
		return next % P;
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
// return tmp.toString();
		return "ABCD";
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
