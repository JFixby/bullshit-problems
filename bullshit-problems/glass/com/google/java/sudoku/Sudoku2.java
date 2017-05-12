
package com.google.java.sudoku;

import java.util.Arrays;
import java.util.HashSet;

import com.jfixby.scarabei.api.desktop.ScarabeiDesktop;
import com.jfixby.scarabei.api.log.L;

public class Sudoku2 {
	static int N = 9;
	char[] data = new char[this.N * this.N];

	public static void main (final String[] x) {
		ScarabeiDesktop.deploy();

		final Sudoku2 sudoku = new Sudoku2();

		final char[][] board = {//
			{'.', '2', '6', '5', '.', '.', '.', '9', '.'}, //
			{'5', '.', '.', '.', '7', '9', '.', '.', '4'}, //
			{'3', '.', '.', '.', '1', '.', '.', '.', '.'}, //
			{'6', '.', '.', '.', '.', '.', '8', '.', '7'}, //
			{'.', '7', '5', '.', '2', '.', '.', '1', '.'}, //
			{'.', '1', '.', '.', '.', '.', '4', '.', '.'}, //
			{'.', '.', '.', '3', '.', '8', '9', '.', '2'}, //
			{'7', '.', '.', '.', '6', '.', '.', '4', '.'}, //
			{'.', '3', '.', '2', '.', '.', '1', '.', '.'}//
		};

// final char[][] board = {//
// {'4', '3', '5', '2', '6', '9', '7', '8', '.'}, //
// {'6', '8', '2', '5', '7', '1', '4', '9', '.'}, //
// {'1', '9', '7', '8', '3', '4', '5', '6', '.'}, //
// {'8', '2', '6', '1', '9', '5', '3', '4', '.'}, //
// {'3', '7', '4', '6', '8', '2', '9', '1', '.'}, //
// {'9', '5', '1', '7', '4', '3', '6', '2', '.'}, //
// {'5', '1', '9', '3', '2', '6', '8', '7', '.'}, //
// {'2', '4', '8', '9', '5', '7', '1', '3', '.'}, //
// {'7', '6', '3', '4', '1', '8', '2', '5', '.'}//
// };

// final char[][] board = {//
// {'.', '.', '.', '2', '6', '.', '7', '.', '1'}, //
// {'6', '8', '.', '.', '7', '.', '.', '9', '.'}, //
// {'1', '9', '.', '.', '.', '4', '5', '.', '.'}, //
// {'8', '2', '.', '1', '.', '.', '.', '4', '.'}, //
// {'.', '.', '4', '6', '.', '2', '9', '.', '.'}, //
// {'.', '5', '.', '.', '.', '3', '.', '2', '8'}, //
// {'.', '.', '9', '3', '.', '.', '.', '7', '4'}, //
// {'.', '4', '.', '.', '5', '.', '.', '3', '6'}, //
// {'7', '.', '3', '.', '1', '8', '.', '.', '.'}//
// };

// final char[][] board = {//
// {'3', '2', '3'}, //
// {'.', '.', '3'}, //
// {'.', '.', '.'}, //
// };

		for (int r = 0; r < N; r++) {
			for (int c = 0; c < N; c++) {
				sudoku.set(c, r, board[r][c]);
			}
		}

		final SudokuArg arg = new SudokuArg();
		final HashSet<Sudoku2> failed = new HashSet<Sudoku2>();
// final HashSet<Sudoku2> explored = new HashSet<Sudoku2>();
// arg.failed = failed;
		arg.sudoku = sudoku;
// arg.explored = explored;
		Sudoku2.solve(arg);
		arg.sudoku.printBoard();

	}

	static private boolean solve (final SudokuArg arg) {
// if (arg.failed.contains(arg.sudoku)) {
// return false;
// }
		if (!arg.sudoku.isValid()) {
			return false;
		}
// arg.sudoku.printBoard();
		if (arg.sudoku.isSolved()) {
			return true;
		}

// arg.explored.add(arg.sudoku);
		for (int y = 0; y < N; y++) {
			for (int x = 0; x < N; x++) {
				if (arg.sudoku.charAt(x, y) == '.') {
					final Sudoku2 print = arg.sudoku.copy();
// print.set(x, y, 'X');
// print.printBoard();
					for (char c = '1'; c <= '0' + N; c++) {
						final SudokuArg next = new SudokuArg();
						next.sudoku = arg.sudoku.copy();
						next.sudoku.set(x, y, c);

						if (!next.sudoku.isValid()) {
							continue;
						}
// next.failed = arg.failed;
// next.explored = arg.explored;
						final boolean solved = Sudoku2.solve(next);
						if (solved) {
							arg.sudoku = next.sudoku;
							return true;
						}
					}

// arg.failed.add(arg.sudoku);
					return false;

				}
			}
		}
// arg.failed.add(arg.sudoku);
		return false;

	}

	private boolean isSolved () {
		for (int x = 0; x < N; x++) {
			for (int y = 0; y < N; y++) {
				if (this.charAt(x, y) == '.') {
					return false;
				}
			}
		}
		return true;
	}

	private boolean isValid () {
		for (int x = 0; x < N; x++) {
			if (!this.isValidColumn(x)) {
// L.d("invalid column", x);
				return false;
			}
			if (!this.isValidRow(x)) {
// L.d("invalid row", x);
				return false;
			}
		}
		return true;
	}

	private boolean isValidRow (final int r) {
		final int[] counter = new int[N];

		for (int x = 0; x < N; x++) {
			final char C = this.charAt(x, r);
			if (C == '.') {
				continue;
			}
			final int index = C - '1';
			counter[index]++;
			if (counter[index] > 1) {
				return false;
			}
		}
		return true;
	}

	private boolean isValidColumn (final int c) {
		final int[] counter = new int[N];

		for (int x = 0; x < N; x++) {
			final char C = this.charAt(c, x);
			if (C == '.') {
				continue;
			}
			final int index = C - '1';
			counter[index]++;
			if (counter[index] > 1) {
				return false;
			}

		}
		return true;
	}

	private Sudoku2 copy () {
		final Sudoku2 copy = new Sudoku2();
		copy.data = Arrays.copyOf(this.data, this.data.length);
		return copy;
	}

	private void set (final int x, final int y, final char C) {
		this.data[this.indexOf(x, y)] = C;
	}

	private void printBoard () {
		L.d("======================================");
		for (int r = 0; r < N; r++) {
			for (int c = 0; c < N; c++) {
				final char value = this.charAt(c, r);
				L.d_appendChars(value);
				if (c % 3 == 2) {
					L.d_appendChars("|");
				} else {
					L.d_appendChars(" ");
				}

			}
			if (r % 3 == 2) {
				L.d();
				L.d("------------------");
			} else {
				L.d();
			}
		}

	}

	private char charAt (final int x, final int y) {
		return this.data[this.indexOf(x, y)];
	}

	private int indexOf (final int x, final int y) {
		return x + N * y;
	}

	@Override
	public int hashCode () {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(this.data);
		return result;
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
		final Sudoku2 other = (Sudoku2)obj;
		if (!Arrays.equals(this.data, other.data)) {
			return false;
		}
		return true;
	}

}
