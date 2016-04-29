
package Hanoi;

import com.jfixby.hrank.SolutionRunner;
import com.jfixby.red.desktop.DesktopAssembler;

public class RunHanoi {

	public static void main (final String[] args) throws Throwable {
		DesktopAssembler.setup();
		SolutionRunner.run(Hanoi.Solution.class);
	}

}
