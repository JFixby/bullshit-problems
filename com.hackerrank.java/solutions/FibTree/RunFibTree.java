
package FibTree;

import com.jfixby.hrank.SolutionRunner;
import com.jfixby.red.desktop.DesktopAssembler;

public class RunFibTree {

	public static void main (final String[] args) throws Throwable {
		DesktopAssembler.setup();
		SolutionRunner.run(FibTree.Solution.class);
	}

}
