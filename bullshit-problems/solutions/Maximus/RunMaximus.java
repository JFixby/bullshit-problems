
package Maximus;

import com.jfixby.hrank.SolutionRunner;
import com.jfixby.scarabei.api.desktop.ScarabeiDesktop;

public class RunMaximus {

	public static void main (final String[] args) throws Throwable {
		ScarabeiDesktop.deploy();
		SolutionRunner.run(Maximus.Solution.class);
	}

}
