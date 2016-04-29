
package BeautifulTriplets;

import java.util.Scanner;
import java.io.InputStream;
import java.io.PrintStream;

import com.jfixby.hrank.AbstractSolution;

public class Solution extends AbstractSolution {

	@Override
	public void run (final String[] args) {
		main(args);
	}

	public static void main (final String[] args) {
		final Scanner in = new Scanner(input);

		final int arraySize = in.nextInt();
		final long distance = in.nextLong();
		final long[] numbers = new long[arraySize];
		for (int i = 0; i < arraySize; i++) {
			final int value = in.nextInt();
			numbers[i] = value;
		}

// log(Arrays.toString(numbers));
		long counter = 0;
		for (int i = 0; i < arraySize - 2; i++) {
			for (int j = i + 1; j < arraySize - 1; j++) {
				final long distanceIJ = numbers[j] - numbers[i];
				if (distanceIJ != distance) {
					continue;
				}
				for (int k = j + 1; k < arraySize; k++) {

					final long distanceJK = numbers[k] - numbers[j];
					if (distanceJK != distance) {
						continue;
					}

					if (distanceIJ == distanceJK) {
						counter++;
					}
				}
			}
		}
		log(counter);

	}

}
