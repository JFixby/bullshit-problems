
package com.google.java;

import java.util.ArrayList;
import java.util.HashMap;

import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.debug.Debug;
import com.jfixby.scarabei.api.desktop.ScarabeiDesktop;
import com.jfixby.scarabei.api.err.Err;
import com.jfixby.scarabei.api.log.L;
import com.jfixby.scarabei.api.math.IntegerMath;
import com.jfixby.scarabei.api.random.Random;

public class HuffmanCompression {

	public static void main (final String[] args) {
		ScarabeiDesktop.deploy();
		Random.setSeed(0);
		final String original = generateString(1000);// chars

		final HuffmanCompression compressor = new HuffmanCompression();
		compressor.collectOccurences(original);
		compressor.print();
		compressor.buildTree();
		compressor.print();
		compressor.buildCodingTable();
		compressor.print();
		final String result = compressor.compress(original);

		final int maxPrint = 1000;
		L.d("original: " + original.length(), original.substring(0, Math.min(maxPrint, original.length() - 1)));
		L.d("result  : " + result.length(), result.substring(0, Math.min(maxPrint, result.length() - 1)));
		final long expectedSize = original.length();
// compressor.print();
		final String restored = compressor.decompress(result, expectedSize);
		L.d("restored: " + restored.length(), restored.substring(0, Math.min(maxPrint, restored.length() - 1)));

	}

	private String decompress (final String compressed, final long expectedSize) {
		final StringBuilder buffer = new StringBuilder();
		final StringBuilder result = new StringBuilder();
		final int bitsToConvert = 16;
		int index = 0;
// buffer.append(compressed);
// while () {
		while (result.length() < expectedSize) {
			final Character chr = this.recognizeChar(buffer);
			if (chr != null) {
				result.append(chr);
			} else {
				if (index == compressed.length()) {
					break;
				}
				final char c = compressed.charAt(index);
				final int code = c - '@' - 1;
				final String binary = Integer.toBinaryString(code);
				buffer.append(binary);
				index++;
			}
		}

// final int pointer = 0;
// for (final int i = 0; i < expectedSize;) {
//
// final char c = compressed.charAt(pointer);
//// final char c = (char)(value + '@' + 1);
//
// while (buffer.length() % bitsToConvert == 0 && buffer.length() >= bitsToConvert) {
//
// if (chr == null) {
// L.d("buffer " + buffer.length(), buffer.toString().subSequence(0, Math.min(1000, buffer.length() - 1)) + "...");
// L.d("result " + result.length(), result.toString().subSequence(0, Math.min(1000, result.length() - 1)) + "...");
// Err.reportError("");
// } else {
// L.d("buffer " + buffer.length(), buffer.toString().subSequence(0, Math.min(1000, buffer.length() - 1)) + "...");
// L.d("result " + result.length(), result.toString().subSequence(0, Math.min(1000, result.length() - 1)) + "...");
// }
//
// }
//
// }

		return result.toString();
	}

	private Character recognizeChar (final StringBuilder buffer) {
		for (final String bin : this.binarytoCharacter.keySet()) {
			if (buffer.indexOf(bin) == 0) {
// L.d("buffer", buffer);
// L.d(" bin", bin);
				buffer.delete(0, bin.length());
				return this.binarytoCharacter.get(bin);
			}
		}
		return null;
	}

	private String compress (final String original) {
		final StringBuilder buffer = new StringBuilder();
		final StringBuilder result = new StringBuilder();
		final int bitsToConvert = 16;
		for (int i = 0; i < original.length(); i++) {
			final Character next = original.charAt(i);
// L.d("next", next);
			final String binaryCode = this.characterToBinaryForm.get(next);
// L.d("binaryCode", binaryCode);
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
			final char code = this.consumeNbits(buffer, bitsToConvert);
			result.append(code);
		}

// L.d("result", result);
		return result.toString();
	}

	private void print () {
		Collections.newMap(this.orrucences).print("orrucences");
		Collections.newMap(this.characterToBinaryForm).print("characterToBinaryForm");
	}

	final HashMap<Character, String> characterToBinaryForm = new HashMap<>();
	final HashMap<String, Character> binarytoCharacter = new HashMap<>();

	private void buildCodingTable () {

		this.DFS(this.root, this.characterToBinaryForm, this.binarytoCharacter, "1");
//
	}

	private void DFS (final Node node, final HashMap<Character, String> characterToBinaryForm,
		final HashMap<String, Character> binarytoCharacter, final String prefix) {
		if (node == null) {
			return;
		}
		if (node.isFinal) {
			characterToBinaryForm.put(node.character.charAt(0), prefix);
			binarytoCharacter.put(prefix, node.character.charAt(0));
			return;
		}

		this.DFS(node.L, characterToBinaryForm, binarytoCharacter, prefix + "0");
		this.DFS(node.R, characterToBinaryForm, binarytoCharacter, prefix + "1");
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
		final ArrayList<Node> ranking = new ArrayList<>();
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
			return IntegerMath.compare(this.weight, o.weight);
		}

	}

	final HashMap<Character, Long> orrucences = new HashMap<>();

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
