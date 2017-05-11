
package com.jfixby.hrank;

public abstract class AbstractSolution {
	public static java.io.InputStream input = System.in;
	public static java.io.PrintStream output = System.out;

	public static void log (final Object msg) {
		output.println(msg);
	}

	abstract public void run (final String[] args);
}
