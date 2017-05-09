
package SolveMeFirst;

import java.io.PrintStream;
import java.util.Scanner;

import com.jfixby.hrank.AbstractSolution;

public class Solution extends AbstractSolution {

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

	@Override
	public void run (final String[] args) {
		main(args);
	}

}
