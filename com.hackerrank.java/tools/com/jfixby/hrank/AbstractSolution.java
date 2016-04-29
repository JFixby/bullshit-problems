
package com.jfixby.hrank;

import java.io.InputStream;
import java.io.PrintStream;

public abstract class AbstractSolution {
	public static InputStream input = System.in;
	public static PrintStream output = System.out;

	public static void log (final Object msg) {
		output.println(msg);
	}

	public abstract void run (String[] strings);
}
