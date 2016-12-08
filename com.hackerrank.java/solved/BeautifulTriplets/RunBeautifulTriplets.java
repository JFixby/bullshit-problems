
package BeautifulTriplets;

import com.jfixby.cmns.api.desktop.DesktopSetup;
import com.jfixby.hrank.SolutionRunner;

public class RunBeautifulTriplets {

	public static void main (final String[] args) throws Throwable {
		DesktopSetup.deploy();
		SolutionRunner.run(BeautifulTriplets.Solution.class);
	}

}
