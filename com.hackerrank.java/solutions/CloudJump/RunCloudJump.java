
package CloudJump;

import com.jfixby.hrank.SolutionRunner;
import com.jfixby.red.desktop.DesktopAssembler;

public class RunCloudJump {

	public static void main (final String[] args) throws Throwable {
		DesktopAssembler.setup();
		SolutionRunner.run(CloudJump.Solution.class);
	}

}
