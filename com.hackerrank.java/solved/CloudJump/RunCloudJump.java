
package CloudJump;

import com.jfixby.hrank.SolutionRunner;
import com.jfixby.scarabei.api.desktop.DesktopSetup;

public class RunCloudJump {

	public static void main (final String[] args) throws Throwable {
		DesktopSetup.deploy();
		SolutionRunner.run(CloudJump.Solution.class);
	}

}
