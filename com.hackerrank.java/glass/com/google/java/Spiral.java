
package com.google.java;

public class Spiral {

	public static void main (final String[] args) {
		final int W = 10;
		final int H = 7;
		final int[][] array = new int[W][H];

		print(array, W, H);

		final FillSettings settings = new FillSettings();
		settings.array = array;
		settings.totalCells = W * H;
		settings.fillIndex = 0;
		settings.currentDirection = Direction.LeftToRight;
		settings.leftCornerIndex = 0;
		settings.rightCornerIndex = W - 1;
		settings.topCornerIndex = 0;
		settings.bottomCornerIndex = H - 1;

		while (settings.leftToFill() > 0) {
			fill(settings);
			print(array, W, H);
		}
		print(array, W, H);
	}

	private static void fill (final FillSettings settings) {
		if (settings.currentDirection == Direction.LeftToRight) {
			fillToRight(settings);
			settings.topCornerIndex++;
			settings.currentDirection = Direction.TopToBottom;
			return;
		}
		if (settings.currentDirection == Direction.TopToBottom) {
			fillToBottom(settings);
			settings.rightCornerIndex--;
			settings.currentDirection = Direction.RightToLeft;
			return;
		}
		if (settings.currentDirection == Direction.RightToLeft) {
			fillToLeft(settings);
			settings.bottomCornerIndex--;
			settings.currentDirection = Direction.BottomToTop;
			return;
		}
		if (settings.currentDirection == Direction.BottomToTop) {
			fillToTop(settings);
			settings.leftCornerIndex++;
			settings.currentDirection = Direction.LeftToRight;
			return;
		}
	}

	private static void fillToTop (final FillSettings settings) {
		for (int y = settings.bottomCornerIndex; y >= settings.topCornerIndex; y--) {
			settings.array[settings.leftCornerIndex][y] = settings.fillIndex;// error prone
			settings.fillIndex++;
			settings.filled++;
		}
	}

	private static void fillToLeft (final FillSettings settings) {
		for (int x = settings.rightCornerIndex; x >= settings.leftCornerIndex; x--) {
			settings.array[x][settings.bottomCornerIndex] = settings.fillIndex;// error prone
			settings.fillIndex++;
			settings.filled++;
		}
	}

	private static void fillToBottom (final FillSettings settings) {
		for (int y = settings.topCornerIndex; y <= settings.bottomCornerIndex; y++) {
			settings.array[settings.rightCornerIndex][y] = settings.fillIndex;// error prone
			settings.fillIndex++;
			settings.filled++;
		}
	}

	private static void fillToRight (final FillSettings settings) {
		for (int x = settings.leftCornerIndex; x <= settings.rightCornerIndex; x++) {
			settings.array[x][settings.topCornerIndex] = settings.fillIndex;// error prone
			settings.fillIndex++;
			settings.filled++;
		}

	}

	public enum Direction {
		LeftToRight, TopToBottom, RightToLeft, BottomToTop
	}

	static class FillSettings {

		public int rightCornerIndex;
		public int leftCornerIndex;
		public int bottomCornerIndex;
		public int topCornerIndex;
		public Direction currentDirection;
		public int fillIndex;
		public long totalCells;
		public long filled;
		public int[][] array;

		public long leftToFill () {
			return this.totalCells - this.filled;
		}

	}

	private static void print (final int[][] array, final int W, final int H) {
		for (int y = 0; y < H; y++) {
			for (int x = 0; x < W; x++) {
				System.out.print(String.format("%02d", array[x][y]) + " ");
			}
			System.out.println();
		}
		System.out.println();
	}

}
