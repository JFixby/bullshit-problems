
package SolveMeFirst;

import com.jfixby.hrank.SolutionRunner;
import com.jfixby.red.desktop.DesktopSetup;

public class RunSolveMeFirst {

	public static void main (final String[] args) throws Throwable {
		DesktopSetup.deploy();
		SolutionRunner.run(SolveMeFirst.Solution.class);
	}

}
