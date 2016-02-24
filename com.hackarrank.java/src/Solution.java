import java.util.ArrayList;
import java.util.Scanner;

public class Solution {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int n = scanner.nextInt();
		for (int i = 0; i < n; i++) {
			test(scanner);
		}

	}

	private static void test(Scanner scanner) {
		int N = scanner.nextInt();
		ArrayList<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < N; i++) {
			int e = scanner.nextInt();
			list.add(e);
			// for (int k = 0; k < list.size(); k++) {
			// if (list.get(k) >= e) {
			// list.insertElementAt(e, k);
			//
			// }
			// }

		}
	}
}