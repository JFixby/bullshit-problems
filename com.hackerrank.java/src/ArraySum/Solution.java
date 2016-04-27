
package ArraySum;

import java.math.BigInteger;
import java.util.Scanner;

public class Solution {

	public static void main (String[] args) {
		Scanner scanner = new Scanner(System.in);

		int N = scanner.nextInt();
		BigInteger sum = new BigInteger("0");
		for (int i = 0; i < N; i++) {
			BigInteger value_i = scanner.nextBigInteger();
			sum = sum.add(value_i);
		}

		L.d(sum);
	}
}

class L extends Logger {

}

class Logger {
	public static final void d (Object message) {
		System.out.println("" + message);
	}

	public static final void d (String tag, Object message) {
		d(tag + " > " + message);
	}
}
