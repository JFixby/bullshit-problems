
package KMP;

import com.jfixby.cmns.api.desktop.DesktopSetup;
import com.jfixby.hrank.SolutionRunner;

public class RunKMP {

	public static void main (final String[] args) throws Throwable {
		DesktopSetup.deploy();
		SolutionRunner.run(KMP.Solution.class);
	}

}
