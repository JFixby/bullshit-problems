package PlusMinus;

import java.util.Scanner;

public class Solution {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int size = scanner.nextInt();
		int L = 0;
		int R = 0;
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				int value = scanner.nextInt();
				if (i == j) {
					L = L + value;
				}
				if (i == (size - j - 1)) {
					R = R + value;
				}
			}
		}
		System.out.println(Math.abs(R - L));
	}
}