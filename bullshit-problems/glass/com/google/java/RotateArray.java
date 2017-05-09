
package com.google.java;

public class RotateArray {

	public interface λ {
		public int value (int x, int y);
	}

	public static void main (final String[] args) {
		final int W = 10;
		final int H = 5;
		final int[][] a = new int[W][H];
		int k = 0;
		for (int j = 0; j < H; j++) {
			for (int i = 0; i < W; i++) {
				a[i][j] = k;
				k++;
			}
		}

		final λ array = (x, y) -> a[x][y];
		print(array, W, H);

		final λ turnRight = turnRight(array, W, H);
		System.out.println();
		print(turnRight, H, W);

		final λ turnLeft = turnLeft(array, W, H);
		System.out.println();
		print(turnLeft, H, W);

	}

	private static λ turnRight (final λ array, final int W, final int H) {
		final λ swapXY = (x, y) -> array.value(y, x);
		final λ mirror = (x, y) -> swapXY.value(H - 1 - x, y);
		return mirror;
	}

	private static λ turnLeft (final λ array, final int W, final int H) {
		final λ swapXY = (x, y) -> array.value(y, x);
		final λ mirror = (x, y) -> swapXY.value(x, W - 1 - y);
		return mirror;
	}

	private static void print (final λ array, final int W, final int H) {
		for (int j = 0; j < H; j++) {
			for (int i = 0; i < W; i++) {
				System.out.print(String.format("%02d", array.value(i, j)) + " ");
			}
			System.out.println();
		}
	}

}
