
package Lovely;

import com.jfixby.hrank.SolutionRunner;
import com.jfixby.red.desktop.DesktopSetup;

public class RunLovely {

	public static void main (final String[] args) throws Throwable {
		DesktopSetup.deploy();
		SolutionRunner.run(Lovely.Solution.class);
	}

}
