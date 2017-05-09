
package Lovely;

import com.jfixby.hrank.SolutionRunner;
import com.jfixby.scarabei.api.desktop.ScarabeiDesktop;

public class RunLovely {

	public static void main (final String[] args) throws Throwable {
		ScarabeiDesktop.deploy();
		SolutionRunner.run(Lovely.Solution.class);
	}

}
