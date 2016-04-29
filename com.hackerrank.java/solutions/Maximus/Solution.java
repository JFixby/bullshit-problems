
package Maximus;

import java.util.Scanner;

import com.jfixby.hrank.AbstractSolution;

public class Solution extends AbstractSolution {

	@Override
	public void run (final String[] args) {
		main(args);
	}

	public static void main (final String[] args) {
		final Scanner in = new Scanner(input);

		final int arraySize = in.nextInt();
		final int numberOfQuries = in.nextInt();

		final int[] A = new int[arraySize];
		for (int i = 0; i < arraySize; i++) {
			final int value = in.nextInt();
			A[i] = value;
		}

// log(Arrays.toString(A));
		for (int i = 0; i < numberOfQuries; i++) {
			final int L = in.nextInt();
			final int R = in.nextInt();
			final long value = F(A, L, R);
			log(value);
		}

	}

	private static long F (final int[] A, final int L, final int R) {
		long result = 0;
		for (int l = L; l <= R; l++) {
			for (int r = l; r <= R; r++) {
				result = result + max(A, l, r);
			}
		}

		return result;
	}

	private static long max (final int[] A, final int l, final int r) {
		long max = A[l - 1];
		for (int i = l - 1; i <= r - 1; i++) {
			max = Math.max(max, A[i]);
		}
		return max;
	}

}
