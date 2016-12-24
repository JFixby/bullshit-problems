
package Maximus;

import com.jfixby.hrank.SolutionRunner;
import com.jfixby.scarabei.api.desktop.DesktopSetup;

public class RunMaximus {

	public static void main (final String[] args) throws Throwable {
		DesktopSetup.deploy();
		SolutionRunner.run(Maximus.Solution.class);
	}

}
