
package CloudJump;

import com.jfixby.hrank.SolutionRunner;
import com.jfixby.scarabei.api.desktop.ScarabeiDesktop;

public class RunCloudJump {

	public static void main (final String[] args) throws Throwable {
		ScarabeiDesktop.deploy();
		SolutionRunner.run(CloudJump.Solution.class);
	}

}
