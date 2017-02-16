
package FibTree;

import com.jfixby.hrank.SolutionRunner;
import com.jfixby.scarabei.api.desktop.ScarabeiDesktop;

public class RunFibTree {

	public static void main (final String[] args) throws Throwable {
		ScarabeiDesktop.deploy();
		SolutionRunner.run(FibTree.Solution.class);
	}

}
