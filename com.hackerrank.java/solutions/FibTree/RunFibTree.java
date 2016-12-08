
package FibTree;

import com.jfixby.cmns.api.desktop.DesktopSetup;
import com.jfixby.hrank.SolutionRunner;

public class RunFibTree {

	public static void main (final String[] args) throws Throwable {
		DesktopSetup.deploy();
		SolutionRunner.run(FibTree.Solution.class);
	}

}
