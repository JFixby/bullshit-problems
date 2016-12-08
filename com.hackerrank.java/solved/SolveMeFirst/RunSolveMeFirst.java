
package SolveMeFirst;

import com.jfixby.cmns.api.desktop.DesktopSetup;
import com.jfixby.hrank.SolutionRunner;

public class RunSolveMeFirst {

	public static void main (final String[] args) throws Throwable {
		DesktopSetup.deploy();
		SolutionRunner.run(SolveMeFirst.Solution.class);
	}

}
