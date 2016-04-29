
package SolveMeFirst;

import com.jfixby.hrank.SolutionRunner;
import com.jfixby.red.desktop.DesktopAssembler;

public class RunSolveMeFirst {

	public static void main (final String[] args) throws Throwable {
		DesktopAssembler.setup();
		SolutionRunner.run(SolveMeFirst.Solution.class);
	}

}
