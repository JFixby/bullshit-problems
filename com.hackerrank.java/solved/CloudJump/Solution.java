
package CloudJump;

import java.util.Scanner;

import com.jfixby.hrank.AbstractSolution;

public class Solution extends AbstractSolution {

	enum CLOUD_TYPE {
		BAD, GOOD;
	}

	public static void main (final String[] args) {
		final Scanner in = new Scanner(input);

		final int numberOfClouds = in.nextInt();
		final Cloud[] clouds = new Cloud[numberOfClouds];
		for (int i = 0; i < numberOfClouds; i++) {
			final int value = in.nextInt();
			clouds[i] = new Cloud(value);
		}

		solve(clouds);
		output.println(clouds[0].antiValue);
	}

	private static void solve (final Cloud[] clouds) {
		for (int i = 0; i < clouds.length; i++) {
			final int index = clouds.length - 1 - i;
			final Cloud cloud = clouds[index];
			if (index == clouds.length - 1) {
				cloud.antiValue = 0;
				continue;
			}
			if (cloud.type == CLOUD_TYPE.BAD) {
				cloud.antiValue = Integer.MAX_VALUE;
				continue;
			}

			final int bestValue = findBestValue(index, clouds);
			if (bestValue < 0) {
				throw new Error();
			}
			cloud.antiValue = bestValue + 1;
		}
// log(Arrays.toString(clouds));
	}

	private static int findBestValue (final int myIndex, final Cloud[] clouds) {
		int best = Integer.MAX_VALUE;
		final int max = Math.min(clouds.length, myIndex + 3);
		for (int i = myIndex + 1; i < max; i++) {
			final Cloud cloud = clouds[i];
			if (cloud.antiValue < best) {
				best = cloud.antiValue;
			}
		}
		return best;
	}

	static class Cloud {
		public Cloud (final int typeId) {
			if (typeId == 0) {
				this.type = CLOUD_TYPE.GOOD;
			}
			if (typeId == 1) {
				this.type = CLOUD_TYPE.BAD;
			}
		}

		CLOUD_TYPE type;
		int antiValue = 0;

		@Override
		public String toString () {
			return "Cloud (" + this.type + " : " + this.antiValue + ")";
		}

	}

	@Override
	public void run (final String[] args) {
		main(args);
	}

}
