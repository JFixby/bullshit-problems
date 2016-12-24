
package Coins;

import com.jfixby.hrank.SolutionRunner;
import com.jfixby.scarabei.api.desktop.DesktopSetup;

public class RunCoins {

	public static void main (final String[] args) throws Throwable {
		DesktopSetup.deploy();
		SolutionRunner.run(Coins.Solution.class);
	}

}
