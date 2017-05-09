
package com.google.java;

import java.util.ArrayList;
import java.util.LinkedHashSet;

import com.jfixby.scarabei.api.desktop.ScarabeiDesktop;
import com.jfixby.scarabei.api.log.L;

public class Kosaraju {

	public static void main (final String[] args) {
		ScarabeiDesktop.deploy();

		final Graph graph = new Graph(13);
		graph.addEdge(0, 6);
		graph.addEdge(0, 2);
		graph.addEdge(1, 0);
		graph.addEdge(5, 0);
		graph.addEdge(4, 5);
		graph.addEdge(3, 4);
		graph.addEdge(3, 2);
		graph.addEdge(2, 3);
		graph.addEdge(2, 4);
		graph.addEdge(4, 6);
		graph.addEdge(5, 3);
		graph.addEdge(4, 11);
		graph.addEdge(6, 7);
		graph.addEdge(7, 8);
		graph.addEdge(8, 7);
		graph.addEdge(9, 6);
		graph.addEdge(9, 8);
		graph.addEdge(10, 9);
		graph.addEdge(11, 9);
		graph.addEdge(12, 10);
		graph.addEdge(12, 11);
		graph.addEdge(9, 12);

// graph.print("graph");

		final Graph G = graph.reverse();
		final Graph R = G.reverse();

		G.print("G");
		R.print("R");
		final ArrayList<Integer> queue;
		{
			final DFS dfs = new DFS(R);
			dfs.scanAll();
			dfs.printExitQueue("Exit R");
			queue = dfs.getExitQueue();
		}

		{
			final DFS dfs = new DFS(G);
			final ArrayList<ArrayList<Integer>> groups = dfs.kosarajuCollect(queue);
			for (int i = 0; i < groups.size(); i++) {
				L.d("strong[" + i + "]", groups.get(i));
			}
		}
	}

	static class DFS {
		private final Graph graph;
		private final LinkedHashSet<Integer> painted = new LinkedHashSet<Integer>();
		final ArrayList<Integer> exitQueue = new ArrayList<Integer>();

		public DFS (final Graph graph) {
			this.graph = graph;
		}

		public ArrayList<ArrayList<Integer>> kosarajuCollect (final ArrayList<Integer> queue) {
			this.painted.clear();
			final ArrayList<ArrayList<Integer>> strongGroups = new ArrayList<ArrayList<Integer>>();
			ArrayList<Integer> currentGroup = new ArrayList<Integer>();
			for (int i = 0; i < queue.size(); i++) {
				final Integer vertex = queue.get(i);
				if (!this.painted.contains(vertex)) {
					this.painted.add(vertex);
					this.kosarajuCollect(vertex, currentGroup);
					strongGroups.add(currentGroup);
					currentGroup = new ArrayList<Integer>();
				}
			}
			return strongGroups;
		}

		private void kosarajuCollect (final Integer vertex, final ArrayList<Integer> currentGroup) {
			currentGroup.add(vertex);
			final ArrayList<Integer> children = this.graph.getEdges(vertex);
			for (final Integer child : children) {
				if (!this.painted.contains(child)) {
					this.painted.add(child);
					this.kosarajuCollect(child, currentGroup);
				}
			}
		}

		public ArrayList<Integer> getExitQueue () {
			return this.exitQueue;
		}

		public void printExitQueue (final String string) {
			L.d(string, this.exitQueue);
		}

		public void scanAll () {
			this.painted.clear();
			for (int vertex = 0; vertex < this.size(); vertex++) {
				if (!this.painted.contains(vertex)) {
					this.painted.add(vertex);
					this.scan(vertex);
				}
			}
		}

		public void scan (final int vertex) {
			final ArrayList<Integer> list = this.graph.getEdges(vertex);
			for (final Integer child : list) {
				if (!this.painted.contains(child)) {
					this.painted.add(child);
					this.scan(child);
				}
			}
			this.exitQueue.add(0, vertex);
		}

		private int size () {
			return this.graph.size;
		}
	}

	static class Graph {
		private final int size;
		ArrayList<Integer>[] vertices;

		public Graph (final int size) {
			this.size = size;
			this.vertices = new ArrayList[size];
			for (int i = 0; i < size; i++) {
				this.vertices[i] = new ArrayList<Integer>();
			}
		}

		public Graph reverse () {
			final Graph reversed = new Graph(this.size);
			for (int i = 0; i < this.size; i++) {
				final ArrayList<Integer> e = this.getEdges(i);
				reversed.addAll(e, i);
			}
			return reversed;
		}

		private void addAll (final ArrayList<Integer> from, final int to) {
			for (final Integer f : from) {
				this.addEdge(f, to);
				this.vertices[f].sort(null);
			}

		}

		public ArrayList<Integer> getEdges (final int vertex) {
			return this.vertices[vertex];
		}

		public void print (final String string) {
			L.d("---< " + string + " >[" + this.size + "]----------------");
			for (int i = 0; i < this.size; i++) {
				L.d("   [" + i + "] ", this.vertices[i]);
			}
		}

		public void addEdge (final int vertexFrom, final int vertexTo) {
			this.vertices[vertexFrom].add(vertexTo);
		}

	}

}
