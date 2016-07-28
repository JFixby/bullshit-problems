
package com.google.java;

import java.util.ArrayList;
import java.util.HashMap;

import com.jfixby.cmns.api.collections.Collections;
import com.jfixby.cmns.api.debug.Debug;
import com.jfixby.cmns.api.err.Err;
import com.jfixby.cmns.api.log.L;
import com.jfixby.cmns.api.random.Random;
import com.jfixby.red.desktop.DesktopSetup;

public class HuffmanCompression {

	public static void main (final String[] args) {
		DesktopSetup.deploy();
		Random.setSeed(0);
		final String original = generateString(500);// chars

		final HuffmanCompression compressor = new HuffmanCompression();
		compressor.collectOccurences(original);
		compressor.buildTree();
		compressor.buildCodingTable();
		final String result = compressor.compress(original);
		compressor.print();
		final int maxPrint = 4000;
		L.d("original: " + original.length(), original.substring(0, Math.min(maxPrint, original.length() - 1)));
		L.d("result  : " + result.length(), result.substring(0, Math.min(maxPrint, result.length() - 1)));
	}

	private void print () {
		Collections.newMap(this.orrucences).print("orrucences");
		Collections.newMap(this.characterToBinaryForm).print("characterToBinaryForm");
	}

	final HashMap<Character, String> characterToBinaryForm = new HashMap<Character, String>();

	private void buildCodingTable () {

		this.DFS(this.root, this.characterToBinaryForm, "");
//
	}

	private void DFS (final Node node, final HashMap<Character, String> characterToBinaryForm, final String prefix) {
		if (node == null) {
			return;
		}
		if (node.isFinal) {
			characterToBinaryForm.put(node.character.charAt(0), prefix);
			return;
		}

		this.DFS(node.L, characterToBinaryForm, prefix + "0");
		this.DFS(node.R, characterToBinaryForm, prefix + "1");
	}

	private String compress (final String original) {
		final StringBuilder buffer = new StringBuilder();
		final StringBuilder result = new StringBuilder();
		final int bitsToConvert = 16;
		for (int i = 0; i < original.length(); i++) {
			final Character next = original.charAt(i);
			final String binaryCode = this.characterToBinaryForm.get(next);
			buffer.append(binaryCode);
			while (buffer.length() % bitsToConvert == 0 && buffer.length() != 0) {
				final char code = this.consumeNbits(buffer, bitsToConvert);
				result.append(code);
			}
		}
		if (buffer.length() > 0) {// append zeroes to finish the stream;
			while (buffer.length() % bitsToConvert != 0) {
				buffer.append("0");
			}
		}
		final char code = this.consumeNbits(buffer, bitsToConvert);
		result.append(code);

// L.d("result", result);
		return result.toString();
	}

	private char consumeNbits (final StringBuilder buffer, final int bitsToConvert) {
// L.d("buffer: " + buffer.length() + " " + buffer);
		final String binary = buffer.substring(0, bitsToConvert);// take 7 bits;
		buffer.delete(0, bitsToConvert);// remove them;
// L.d("binary: " + binary.length() + " " + binary);
// L.d(" : " + buffer.length() + " " + buffer);
		final int value = Integer.parseInt(binary, 2);
		final char c = (char)(value + '@' + 1);
		return c;
	}

	private Node root;

	private void buildTree () {
		if (this.orrucences.size() < 1) {
			Err.reportError("nothing to compress");
		}
		final ArrayList<Node> ranking = new ArrayList<Node>();
		for (final Character chr : this.orrucences.keySet()) {
			final Long occurerncesOfChr = this.orrucences.get(chr);
			final Node node = new Node();
			ranking.add(node);
			node.setCharacter(chr);
			node.setWeight(occurerncesOfChr);
		}
		java.util.Collections.sort(ranking);
// Collections.newList(ranking).print("ranking");

		while (ranking.size() > 1) {
			final Node currentlyMerging = ranking.remove(0);
			final Node other = ranking.remove(0);
			final Node newNode = this.merge(currentlyMerging, other);
			this.insertNodeRespectingTheRank(ranking, newNode);
// Collections.newList(ranking).print("ranking");
		}
		// should have 1 element in the ranking list = root of the tree

		this.root = ranking.get(0);
	}

	private void insertNodeRespectingTheRank (final ArrayList<Node> ranking, final Node newNode) {
		int index = 0;
		for (; index < ranking.size(); index++) {
			final Node listNode = ranking.get(index);
			if (newNode.compareTo(listNode) <= 0) {
				break;
			}
		}
		ranking.add(index, newNode);
	}

	private Node merge (final Node currentlyMerging, final Node other) {
		return new Node(currentlyMerging, other);
	}

	static class Node implements Comparable<Node> {

		private static final long PENALTY = 0;
		private Node L;
		private Node R;
		boolean isFinal = true;

		public Node (final Node L, final Node R) {
			this.L = L;
			this.R = R;
			this.character = L.character + "+" + R.character;
			this.weight = L.weight + R.weight + PENALTY;
			this.isFinal = false;
		}

		public Node () {

		}

		@Override
		public String toString () {
			return "<" + this.character + ">:" + this.weight;

		}

		private String character;
		private long weight;

		public void setCharacter (final Character chr) {
			this.character = chr + "";
		}

		public void setWeight (final Long occurerncesOfChr) {
			this.weight = occurerncesOfChr;
		}

		@Override
		public int compareTo (final Node o) {
			return Long.compare(this.weight, o.weight);
		}

	}

	final HashMap<Character, Long> orrucences = new HashMap<Character, Long>();

	private void collectOccurences (final String original) {

		collectOccurences(original, this.orrucences);

//

	}

	private static void collectOccurences (final String original, final HashMap<Character, Long> orrucences) {
		Debug.checkNull(original);
		Debug.checkNull(orrucences);
		for (int i = 0; i < original.length(); i++) {
			final Character key = original.charAt(i);
			final Long value = orrucences.get(key);
			if (value == null) {
				orrucences.put(key, 0L);
			} else {
				orrucences.put(key, value + 1);
			}
		}
	}

	private static String generateString (final int N) {
		final StringBuilder tmp = new StringBuilder();
		for (int i = 0; tmp.length() < N; i++) {
			final char C = (char)('A' + Random.newInt(0, 20));
			final int P = (1 - 'A' + C);
			final int M = Random.newInt(0, P * P);
			for (int m = 0; m < M; m++) {
				tmp.append(C);
			}

		}
		return tmp.toString();
	}

};
