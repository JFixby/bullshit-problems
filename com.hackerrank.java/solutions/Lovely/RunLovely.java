
package Lovely;

import com.jfixby.cmns.api.desktop.DesktopSetup;
import com.jfixby.hrank.SolutionRunner;

public class RunLovely {

	public static void main (final String[] args) throws Throwable {
		DesktopSetup.deploy();
		SolutionRunner.run(Lovely.Solution.class);
	}

}
