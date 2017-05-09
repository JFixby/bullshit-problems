
package Coins;

import com.jfixby.hrank.SolutionRunner;
import com.jfixby.scarabei.api.desktop.ScarabeiDesktop;

public class RunCoins {

	public static void main (final String[] args) throws Throwable {
		ScarabeiDesktop.deploy();
		SolutionRunner.run(Coins.Solution.class);
	}

}
