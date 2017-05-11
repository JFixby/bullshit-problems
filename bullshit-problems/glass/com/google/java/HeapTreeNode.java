
package com.google.java;

import com.jfixby.scarabei.api.err.Err;

public class HeapTreeNode implements TreeNode {

	private final Heap heap;
	private final int index;

	public HeapTreeNode (final Heap heap, final int i) {
		this.heap = heap;
		this.index = i;
	}

	@Override
	public String getRootName () {
		return this.heap.data[this.index] + "";
	}

	@Override
	public int numOfChildren () {
		return this.heap.childrenSize(this.index);
	}

	@Override
	public TreeNode getChild (final int i) {
		if (i == 0) {
			return new HeapTreeNode(this.heap, Heap.indexOfLeftChild(this.index));
		}
		if (i == 1) {
			return new HeapTreeNode(this.heap, Heap.indexOfRightChild(this.index));
		}
		Err.reportError("");
		return null;
	}

	@Override
	public TreeNode getLastChild () {
		return this.getChild(this.numOfChildren() - 1);
	}

}
