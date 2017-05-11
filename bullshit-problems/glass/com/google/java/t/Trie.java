
package com.google.java.t;

import com.google.java.TreeNode;
import com.google.java.TreePrinter;
import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.Map;
import com.jfixby.scarabei.api.collections.Set;
import com.jfixby.scarabei.api.desktop.ScarabeiDesktop;
import com.jfixby.scarabei.api.err.Err;
import com.jfixby.scarabei.api.log.L;

public class Trie {

	public static final String E = "";

	class TrieNode implements TreeNode {

		@Override
		public String toString () {
			return "'" + this.C + "' " + this.storage + "";
		}

		public TrieNode (final Character c) {
			this.C = c;
		}

		final Character C;
		final Map<Character, TrieNode> children = Collections.newMap();
		final Set<String> storage = Collections.newSet();

		public void store (final String e) {
			this.storage.add(e);
		}

		public TrieNode obtainChildFor (final Character c) {
			TrieNode child = this.children.get(c);
			if (child == null) {
				child = new TrieNode(c);
				this.children.put(c, child);
			}
			return child;
		}

		@Override
		public String getRootName () {
			return this.toString();
		}

		@Override
		public int numOfChildren () {
			return this.children.size();
		}

		@Override
		public TreeNode getChild (final int i) {
			final TreeNode child = this.children.getValueAt(i);
			if (child == null) {
				this.children.print(this.toString());
				Err.reportError("");
			}
			return child;
		}

		@Override
		public TreeNode getLastChild () {
			return this.getChild(this.children.size() - 1);
		}
	}

	final TrieNode root;

	public Trie () {
		this.root = new TrieNode(null);

	}

	public static void main (final String[] arg) {
		ScarabeiDesktop.deploy();
		final Trie trie = new Trie();

		trie.add("A", "to", "tea", "ted", "ten", "i", "in", "inn");

		TreePrinter.print(trie.root);

	}

	public void add (final String... string) {
		for (int i = 0; i < string.length; i++) {
			this.add(string[i]);
		}
	}

	public void add (final String string) {
		L.d("add", string);
		if (string == null) {
			return;
		}
		if (E.equals(string)) {
			this.root.store(E);
			return;
		}
		TrieNode target = this.root;
		for (int i = 0; i < string.length(); i++) {
			final Character C = string.charAt(i);
			target = target.obtainChildFor(C);
		}
		target.store(string);

	}

}
