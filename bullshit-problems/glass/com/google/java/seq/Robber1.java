
package com.google.java.seq;

import com.jfixby.scarabei.api.desktop.ScarabeiDesktop;
import com.jfixby.scarabei.api.log.L;

public class Robber1 {

	public static void main (final String[] x) {
		ScarabeiDesktop.deploy();
		final byte arr[] = {50, 1, 1, 40};
// final byte arr[] = {10, 13, 23, 17, 6, 11, 18, 16};

// circle
// final byte arr[] = new byte[1000];

		final Robber0 robber0 = new Robber0();
		robber0.useMemoization = true;
		L.d("", robber0.solve(arr, 0, arr.length - 2));
		L.d("", robber0.solve(arr, 1, arr.length - 1));

// final Robber0 robber1 = new Robber0();
// robber1.useMemoization = true;
// L.d("", robber0.solve(arr, 0, arr.length - 2));
// L.d("", robber0.solve(arr, 1, arr.length - 1));

	}

}
