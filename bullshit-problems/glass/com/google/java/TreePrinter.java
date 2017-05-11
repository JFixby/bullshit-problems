
package com.google.java;

import org.junit.Test;

import com.jfixby.scarabei.api.debug.Debug;

public class TreePrinter {
	@Test
	public void test () {

	}

	public static final void print (final TreeNode toPrint) {
		print(toPrint, "", true);
	}

	private static void print (final TreeNode toPrint, final String prefix, final boolean isTail) {
		Debug.checkNull("toPrint", toPrint);
		System.out.println(prefix + (isTail ? "└─── " : "├── ") + "[" + toPrint.getRootName() + "]");
		for (int i = 0; i < toPrint.numOfChildren() - 1; i++) {
			final TreeNode child = toPrint.getChild(i);
			print(child, prefix + (isTail ? "      " : "│   "), false);

		}
		if (toPrint.numOfChildren() > 0) {
			final TreeNode child = toPrint.getLastChild();
			print(child, prefix + (isTail ? "      " : "│   "), true);
		}
	}

}
