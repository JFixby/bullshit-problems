
package Maximus;

import java.util.ArrayList;
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

	static class A {

		public A (final int[] array) {
		}

	}

	static class SigmaAlgebra {
		final ArrayList<Segment> base = new ArrayList<Segment>();

		public Segment getBase (final int i) {
			return this.base.get(i);
		}

	}

	public static void main (final String[] args) {
		final Scanner in = new Scanner(input);
		final int arraySize = in.nextInt();
		final int numberOfQuries = in.nextInt();

		final int[] array = new int[arraySize];
		for (int i = 0; i < arraySize; i++) {
			final int value = in.nextInt();
			array[i] = value;
		}
		final SigmaAlgebra algebra = new SigmaAlgebra();
// log(Arrays.toString(A));
		for (int i = 0; i < numberOfQuries; i++) {
			final int L = in.nextInt();
			final int R = in.nextInt();

		}
		final A A = new A(array);

		for (int i = 0; i < numberOfQuries; i++) {
			final Segment LR = algebra.getBase(i);
			final int L = LR.L;
			final int R = LR.R;
			final long value = F(A, L, R);
			log(value);
		}
	}

	private static long F (final A A, final int L, final int R) {
		final long result = 0;
		for (int x = L; x <= R; x++) {
			for (int y = L; y <= R; y++) {
// result = result+A()

			}
		}
		return result;

	}

}
