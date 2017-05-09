
package com.jfixby.hrank;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class Solution {

	public static InputStream input = System.in;
	public static PrintStream output = System.out;

	public static void main (final String[] args) {
		final Scanner in = new Scanner(input);

		final int a = in.nextInt();
		final int b = in.nextInt();
		final int sum = solveMeFirst(a, b);

		final PrintStream out = output;

		out.println(sum);
	}

	static int solveMeFirst (final int a, final int b) {
		return a + b;
	}

}
