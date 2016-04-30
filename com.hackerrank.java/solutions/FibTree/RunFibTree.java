
package FibTree;

import com.jfixby.hrank.SolutionRunner;
import com.jfixby.red.desktop.DesktopSetup;

public class RunFibTree {

	public static void main (final String[] args) throws Throwable {
		DesktopSetup.deploy();
		SolutionRunner.run(FibTree.Solution.class);
	}

}
