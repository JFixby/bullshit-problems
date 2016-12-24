
package com.google.java.seq;

import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.List;
import com.jfixby.scarabei.api.collections.Map;
import com.jfixby.scarabei.api.desktop.DesktopSetup;

public class WordDistance {

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

	final List<String> words = Collections.newList();
	final Map<String, Segment> openSegments = Collections.newMap();
	final Map<String, Segment> closedSegments = Collections.newMap();

	public void add (final String word) {
		this.words.add(word);
		final int currentIndex = this.words.size() - 1;

		Segment segment = this.openSegments.remove(word);

		if (segment != null) {
			segment.closeAt(currentIndex);

			final Segment previous = this.closedSegments.get(word);
			if (previous == null || previous.getDistance() > segment.getDistance()) {
				this.closedSegments.put(word, segment);
			} else {
				// discard
			}
		}

		segment = new Segment(word);
		segment.openAt(currentIndex);
		this.openSegments.put(word, segment);

	}

	public void printAllDistances () {
		this.words.print("positions");
		this.closedSegments.print("closed");
		this.openSegments.print("open");
	}

	static class Segment {

		@Override
		public String toString () {
			return "Segment{" + this.word + "} [" + this.openAt + ", " + this.closeAt + "] = " + this.getDistance() + "]";
		}

		private final String word;
		private int closeAt = Integer.MAX_VALUE;
		private int openAt = Integer.MIN_VALUE;

		public Segment (final String word) {
			this.word = word;
		}

		public void closeAt (final int currentIndex) {
			this.closeAt = currentIndex;
		}

		public void openAt (final int currentIndex) {
			this.openAt = currentIndex;
		}

		public int getDistance () {
			return this.closeAt - this.openAt;
		}

	}

}
