
package Maximus;

import com.jfixby.hrank.SolutionRunner;
import com.jfixby.red.desktop.DesktopAssembler;

public class RunMaximus {

	public static void main (final String[] args) throws Throwable {
		DesktopAssembler.setup();
		SolutionRunner.run(Maximus.Solution.class);
	}

}
