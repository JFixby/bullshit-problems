
package com.google.java.g;

import java.util.Comparator;

import com.jfixby.cmns.api.collections.Collection;
import com.jfixby.cmns.api.collections.Collections;
import com.jfixby.cmns.api.collections.Heap;
import com.jfixby.cmns.api.collections.List;
import com.jfixby.cmns.api.collections.Map;
import com.jfixby.cmns.api.collections.Set;
import com.jfixby.cmns.api.err.Err;
import com.jfixby.red.desktop.DesktopSetup;

public class PrimSpanningTree {

	public interface Graph<T> {

		void addVertex (String vertexID);

		void print ();

		void addEdge (String vertexFrom, String vertexTo, T attachment);

		void addEdges (String vertexA, String vertexB, T attachment);

		Collection<String> listVerices ();

		Collection<String> ajacentVertices (String vertexID);

	}

	static class AdjacencyListGreaph<T> implements Graph<T> {

		final Map<String, List<String>> list = Collections.newMap();
		final Map<String, T> attachments = Collections.newMap();

		@Override
		public void addVertex (final String vertexID) {
			if (this.list.containsKey(vertexID)) {
				Err.reportError("Duplicate vertex name! " + vertexID);
			}
			this.list.put(vertexID, Collections.newList());
		}

		@Override
		public void print () {
			this.list.print("graph");
		}

		@Override
		public void addEdge (final String vertexFrom, final String vertexTo, final T attachment) {
			final List<String> list = this.getListFor(vertexFrom);
			list.add(vertexTo);
			this.attachments.put(edge(vertexFrom, vertexTo), attachment);
		}

		static private String edge (final String vertexFrom, final String vertexTo) {
			return vertexFrom + "-" + vertexTo;
		}

		private List<String> getListFor (final String vertexID) {
			return this.list.get(vertexID);
		}

		@Override
		public void addEdges (final String vertexA, final String vertexB, final T attachment) {
			this.addEdge(vertexA, vertexB, attachment);
			this.addEdge(vertexB, vertexA, attachment);
		}

		@Override
		public Collection<String> listVerices () {
			return this.list.keys();
		}

		@Override
		public Collection<String> ajacentVertices (final String vertexID) {
			final List<String> list = this.getListFor(vertexID);
			return list;
		}

	}

	public static final void main (final String[] args) {

		DesktopSetup.deploy();

		final Graph<Integer> graph = new AdjacencyListGreaph<Integer>();

		graph.addVertex("A");
		graph.addVertex("B");
		graph.addVertex("C");
		graph.addVertex("D");
		graph.addVertex("E");
		graph.addVertex("F");
		graph.addVertex("G");
		graph.addVertex("H");

		graph.addEdges("A", "B", 6);
		graph.addEdges("C", "B", 14);
		graph.addEdges("D", "B", 8);
		graph.addEdges("D", "C", 3);
		graph.addEdges("A", "F", 12);
		graph.addEdges("B", "F", 5);
		graph.addEdges("E", "F", 7);
		graph.addEdges("E", "D", 10);
		graph.addEdges("F", "G", 9);
		graph.addEdges("E", "H", 15);

// graph.print();

		final PrimSpanningTree prim = new PrimSpanningTree(graph);
		final Graph<Integer> result = prim.findMinSpanningTree("A");

	}

	private final Graph<Integer> graph;
	private final Set<String> paintedVertices = Collections.newSet();
	private final Heap<VertexEntry> queue = Collections.newHeap(VertexEntry.comparator);

	public Graph<Integer> findMinSpanningTree (final String startVertexID) {
		this.paintedVertices.clear();

		final Graph<Integer> result = new AdjacencyListGreaph<Integer>();
		this.queue.clear();
		final Collection<String> vertices = result.listVerices();
		Collections.scanCollection(vertices,
			(t, i) -> this.queue.add(new VertexEntry(t, startVertexID.equals(t) ? 0 : Integer.MAX_VALUE)));

		while (this.queue.size() > 0) {
			final VertexEntry element = this.queue.remove();
			this.paintedVertices.add(element.vertexID);// paint
			final Collection<String> ajacentVertices = this.graph.ajacentVertices(element.vertexID);
		}

		return result;
	}

	public PrimSpanningTree (final Graph<Integer> graph) {
		this.graph = graph;
	}

	static class VertexEntry {

		protected int distance = Integer.MAX_VALUE;
		private final String vertexID;

		public VertexEntry (final String vertexID) {
			this.vertexID = vertexID;

		}

		public VertexEntry (final String vertexID, final int distance) {
			this.vertexID = vertexID;
			this.distance = distance;
		}

		private static final Comparator<VertexEntry> comparator = new Comparator<VertexEntry>() {
			@Override
			public int compare (final VertexEntry o1, final VertexEntry o2) {
				return Integer.compare(o1.distance, o2.distance);
			}
		};
	}

}
