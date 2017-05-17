
package com.google.java.seq;

import java.util.ArrayList;
import java.util.Arrays;

import com.jfixby.scarabei.api.desktop.ScarabeiDesktop;
import com.jfixby.scarabei.api.log.L;

public class SmallestLeft {

	public static void main (final String[] args) {
		ScarabeiDesktop.deploy();
		final byte arr[] = {1, 6, 4, 10, 2, 5};

		final Byte[] result = solve(arr);
		L.d("", Arrays.toString(result));

	}

	private static Byte[] solve (final byte[] arr) {
		final ArrayList<Byte> stack = new ArrayList<>();
		final Byte[] result = new Byte[arr.length];

		for (int i = 0; i < arr.length; i++) {
			final byte a = arr[i];

			if (i == 0) {
				result[0] = null;
				stack.add(a);
				continue;
			}
			Byte s = null;
			while (stack.size() > 0) {
				s = touch(stack);
				if (isBiggerThan(a, s)) {
					break;
				}
				remove(stack);
			}

			stack.add(a);
			result[i] = s;

		}
		return result;
	}

	private static void remove (final ArrayList<Byte> stack) {
		if (stack.size() == 0) {
			return;
		}
		stack.remove(stack.size() - 1);
	}

	private static boolean isBiggerThan (final byte a, final Byte s) {
		return s == null || a > s;
	}

	private static Byte touch (final ArrayList<Byte> stack) {
		if (stack.size() == 0) {
			return null;
		}
		return stack.get(stack.size() - 1);
	}

}
