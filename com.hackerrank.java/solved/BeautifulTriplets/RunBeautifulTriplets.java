
package BeautifulTriplets;

import com.jfixby.hrank.SolutionRunner;
import com.jfixby.red.desktop.DesktopSetup;

public class RunBeautifulTriplets {

	public static void main (final String[] args) throws Throwable {
		DesktopSetup.deploy();
		SolutionRunner.run(BeautifulTriplets.Solution.class);
	}

}
