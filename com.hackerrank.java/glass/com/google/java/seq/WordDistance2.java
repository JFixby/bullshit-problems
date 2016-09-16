
package com.google.java.seq;

import com.jfixby.red.desktop.DesktopSetup;

public class WordDistance2 {

	public static void main (final String[] args) {
		DesktopSetup.deploy();

		final WordDistance wordDistance = new WordDistance();
		wordDistance.add("A");
		wordDistance.add("B");
		wordDistance.add("C");
		wordDistance.add("D");
		wordDistance.add("A");
		wordDistance.add("D");
		wordDistance.add("A");
		wordDistance.add("C");
		wordDistance.add("X");
		wordDistance.add("Y");
		wordDistance.add("Z");
		wordDistance.add("A");

		wordDistance.printAllDistances();

	}

}
