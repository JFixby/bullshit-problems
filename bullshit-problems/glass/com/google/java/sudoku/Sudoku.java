
package com.google.java.sudoku;

import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.List;
import com.jfixby.scarabei.api.collections.Map;
import com.jfixby.scarabei.api.desktop.ScarabeiDesktop;
import com.jfixby.scarabei.api.log.L;

public class Sudoku {
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

	public static void main (final String[] arg) {
		ScarabeiDesktop.deploy();

		final Sudoku sudoku = new Sudoku();

		sudoku.printBoard();

		sudoku.resetSolver();
		sudoku.printCandidates();
// sudoku.resetSolver();

		while (sudoku.reduceOptions()) {
			sudoku.printCandidates();
		}

	}

	private boolean reduceOptions () {
		boolean changed = false;
		for (int r = 0; r < 9; r++) {
			for (int c = 0; c < 9; c++) {
				final String key = this.key(c, r);
				final List<Character> list = this.candidates.get(key);
				final int sizeBefore = list.size();
				final List<Character> neighbours = this.collect(c, r);
				list.removeAll(neighbours);
				final int sizeAfter = list.size();
				changed = changed || (sizeAfter != sizeBefore);
			}
		}
		return changed;
	}

	private List<Character> collect (final int x, final int y) {
		final List<Character> result = Collections.newList();
		for (int i = 0; i < 9; i++) {
			if (i == x) {
				continue;
			}
			final String key = this.key(i, y);
			final List<Character> list = this.candidates.get(key);
			if (list.size() == 1) {
				result.add(list.getLast());
			}
		}

		for (int i = 0; i < 9; i++) {
			if (i == y) {
				continue;
			}
			final String key = this.key(x, i);
			final List<Character> list = this.candidates.get(key);
			if (list.size() == 1) {
				result.add(list.getLast());
			}
		}

		return result;
	}

	final Map<String, List<Character>> candidates = Collections.newMap();

	private void resetSolver () {
		for (int r = 0; r < 9; r++) {
			for (int c = 0; c < 9; c++) {
				final String key = this.key(c, r);
				final List<Character> list = Collections.newList();
				this.candidates.put(key, list);
				final char C = this.board[c][r];
				if (C != '.') {
					list.add(C);
				} else {
					for (int i = 1; i <= 9; i++) {
						final char cc = (char)('0' + i);
						list.add(cc);
					}
				}
			}
		}

	}

	private boolean columnContains (final char cc, final int x, final int y) {
		for (int c = 0; c < 9; c++) {
			if (c == x) {
				break;
			}
			if (this.board[c][y] == cc) {
				return true;
			}
		}
		return false;
	}

	private boolean rawContains (final char cc, final int x, final int y) {
		for (int c = 0; c < 9; c++) {
			if (c == y) {
				break;
			}
			if (this.board[x][c] == cc) {
				return true;
			}
		}
		return false;
	}

	private String key (final int c, final int r) {
		return c + "-" + r;
	}

	private void printBoard () {
		for (int r = 0; r < 9; r++) {
			for (int c = 0; c < 9; c++) {
				final char value = this.board[c][r];
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

	private void printCandidates () {
		L.d("==============================================");
		for (int r = 0; r < 9; r++) {
			for (int c = 0; c < 9; c++) {
				final String key = this.key(c, r);
				final String value = this.candidates.get(key).toString();
				L.d_appendChars(value);
				if (c % 3 == 2) {
					L.d_appendChars("|");
				} else {
					L.d_appendChars(",");
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

}
