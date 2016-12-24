
package KMP;

import com.jfixby.hrank.SolutionRunner;
import com.jfixby.scarabei.api.desktop.DesktopSetup;

public class RunKMP {

	public static void main (final String[] args) throws Throwable {
		DesktopSetup.deploy();
		SolutionRunner.run(KMP.Solution.class);
	}

}
