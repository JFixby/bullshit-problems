
package com.jfixby.hrank.run;

import com.jfixby.hrank.SolutionRunner;
import com.jfixby.red.desktop.DesktopAssembler;

public class RunSolveMeFirst {

	public static void main (final String[] args) throws Throwable {
		DesktopAssembler.setup();
		final SolutionRunner runner = new SolutionRunner(SolveMeFirst.Solution.class);
		runner.run();
	}

}
