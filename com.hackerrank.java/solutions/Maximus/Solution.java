
package Maximus;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

import com.jfixby.hrank.AbstractSolution;

public class Solution extends AbstractSolution {

	@Override
	public void run (final String[] args) {
		main(args);
	}

	public static class Segment {

		private final int L;
		private final int R;

		public Segment (final int x, final int y) {
			this.L = x;
			this.R = y;
		}

		public Segment[] intersect (final Segment segment) {
			if (this.R < segment.L) {
				return null;
			}
			if (this.L > segment.R) {
				return null;
			}
			return null;
		}

	}

	public static class SigmaAlgebra {

		final LinkedList<Segment> base = new LinkedList<Segment>();
		final LinkedList<Segment> all = new LinkedList<Segment>();

		public void add (final int x, final int y) {
			final Segment segment = new Segment(x, y);
			this.base.add(segment);
			this.all.add(segment);
			final int N = Math.min(0, this.base.size());
			for (int i = this.base.size(); i < N; i++) {
				final Segment other = this.base.get(i);
				final Segment[] parts = other.intersect(segment);
			}

		}

		public Segment getBase (final int i) {
			return this.base.get(i);
		}
	}

	public static void main (final String[] args) {
		final Scanner in = new Scanner(input);
		memoization.clear();
		final int arraySize = in.nextInt();
		final int numberOfQuries = in.nextInt();

		final int[] A = new int[arraySize];
		for (int i = 0; i < arraySize; i++) {
			final int value = in.nextInt();
			A[i] = value;
		}
		final SigmaAlgebra algebra = new SigmaAlgebra();
// log(Arrays.toString(A));
		for (int i = 0; i < numberOfQuries; i++) {
			final int L = in.nextInt();
			final int R = in.nextInt();
			algebra.add(L, R);

		}

		for (int i = 0; i < numberOfQuries; i++) {
			final Segment LR = algebra.getBase(i);
			final int L = LR.L;
			final int R = LR.R;
			clounter = 0;
			final long value = F(A, L, R);
			log(value);
		}
		log("clounter " + clounter);
	}

	private static long F (final int[] A, final int L, final int R) {
		long result = 0;
		for (int l = L; l <= R; l++) {
// final int tL = R - l + L;
			final int tL = l;

			for (int r = tL; r <= R; r++) {

// final int tR = R - r + tL;
				final int tR = r;
// result = result + max(A, l, r);
				result = result + max(A, tL, tR);
			}
		}

		return result;
	}

	static long clounter = 0;

	private static long max (final int[] A, final int l, final int r) {
		log("->max(" + l + "," + r + ")");
		clounter++;

		final int d = 1 + r - l;
		long mid = -1;
		long max = A[l - 1];
		final int N = 6;
		if (d == 1) {
			return max;
		} else if (d < N) {
			for (int i = 0; i < d; i++) {
				max = Math.max(A[l - 1 + i], max);
			}
			return max;
		}

		// (d > 3)
		{
// mid = l + 1;
			final Long memo = getMemo(l, r);
			if (memo != null) {
				return memo;
			}
			log("HEAVY->max(" + l + "," + r + ")");
			mid = (l + r) / 2;
			final int midi = (int)mid;
			if (false) {
				final long m1 = max(A, l, l + 1);
				final long m3 = max(A, l + 1, r - 1);
				final long m2 = max(A, r - 1, r);
				max = Math.max(m1, m2);
				max = Math.max(max, m3);
			}
			if (!false) {
				final long m1 = max(A, l, midi);
				final long m2 = max(A, midi + 1, r);

				max = Math.max(m1, m2);

			}
			addMemo(max, l, r);

			return max;
		}
	}

	private static void addMemo (final long max, final int x, final int y) {
		final String key = key(x, y);
// log("memo " + key);
		if (memoization.containsKey(key)) {
			throw new Error(key);
		}
		memoization.put(key, max);
	}

	static final HashMap<String, Long> memoization = new HashMap<String, Long>();

	private static Long getMemo (final int x, final int y) {
// log("max(" + x + "," + y + ")");
		final String key = key(x, y);
		final Long value = memoization.get(key);
		return value;
	}

	private static String key (final int x, final int y) {
		return x + "#" + y;
	}

}
