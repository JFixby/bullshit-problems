
package com.google.java.g;

import java.util.Comparator;

import com.jfixby.cmns.api.collections.Collection;
import com.jfixby.cmns.api.collections.Collections;
import com.jfixby.cmns.api.collections.Heap;
import com.jfixby.cmns.api.collections.List;
import com.jfixby.cmns.api.collections.Map;
import com.jfixby.cmns.api.collections.Set;
import com.jfixby.cmns.api.err.Err;
import com.jfixby.cmns.api.log.L;
import com.jfixby.cmns.api.util.JUtils;
import com.jfixby.red.desktop.DesktopSetup;

public class PrimSpanningTree {

	public interface Graph<T> {

		void addVertex (String vertexID);

		void print (String tag);

		void addEdge (String vertexFrom, String vertexTo, T attachment);

		void addEdges (String vertexA, String vertexB, T attachment);

		Collection<String> listVerices ();

		Collection<String> ajacentVertices (String vertexID);

		T getAttachment (String vertexFrom, String vertexTo);

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
		public void print (final String tag) {

			final StringBuilder string = new StringBuilder();
// final String canonocal_name = "Map[]";
			final int n = this.list.size();

			final int indent = tag.length() + 3;
			string.append("---[" + tag + "](" + this.list.size() + ")-------------------------\n");
			final String indent_str = JUtils.prefix(" ", indent);
			for (int i = 0; i < n; i++) {
				string.append(indent_str + "(" + i + ") " + this.list.getKeyAt(i) + " :-> "
					+ this.toString(this.list.getKeyAt(i), this.list.getValueAt(i)) + "\n");
			}

			L.d(string.toString());
		}

		private String toString (final String vertexFrom, final List<String> verticesTo) {
			final int iMax = verticesTo.size() - 1;
			if (iMax == -1) {
				return "[]";
			}

			final StringBuilder b = new StringBuilder();
			b.append('[');
			for (int i = 0;; i++) {
				final String vertexTo = verticesTo.getElementAt(i);

				b.append(vertexTo);
				b.append("(");
				final String key = edge(vertexFrom, vertexTo);
				b.append(this.attachments.get(key));
				b.append(")");
				if (i == iMax) {
					return b.append(']').toString();
				}
				b.append(", ");
			}

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

		@Override
		public T getAttachment (final String vertexFrom, final String vertexTo) {
			final String key = edge(vertexFrom, vertexTo);
			return this.attachments.get(key);
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

		graph.addEdges("A", "B", 1);
		graph.addEdges("C", "B", 1);
		graph.addEdges("D", "B", 1);
		graph.addEdges("D", "C", 1);
		graph.addEdges("A", "F", 1);
		graph.addEdges("B", "F", 1);
		graph.addEdges("E", "F", 1);
		graph.addEdges("E", "D", 1);
		graph.addEdges("F", "G", 1);
		graph.addEdges("E", "H", 1);

		graph.print("input");

		final PrimSpanningTree prim = new PrimSpanningTree(graph);
		final Graph<Integer> result = prim.findMinSpanningTree("A");

		result.print("result");

	}

	private final Graph<Integer> graph;
	private final Set<String> paintedVertices = Collections.newSet();
	private final Map<String, Integer> distances = Collections.newMap();
	private final Map<String, String> direction = Collections.newMap();

	private final Comparator<String> comparator = new Comparator<String>() {
		@Override
		public int compare (final String o1, final String o2) {
			final Integer o1_distance = PrimSpanningTree.this.notNull(PrimSpanningTree.this.distances.get(o1));
			final Integer o2_distance = PrimSpanningTree.this.notNull(PrimSpanningTree.this.distances.get(o2));
			final int compareDistance = -Integer.compare(o1_distance, o2_distance);
			if (compareDistance != 0) {
				return compareDistance;
			}

			final int compareString = -o1.compareTo(o2);
			return compareString;
		}

	};

	private Integer notNull (final Integer integer) {
		if (integer == null) {
			return Integer.MAX_VALUE;
		}
		return integer;
	}

	private final Heap<String> queue = Collections.newHeap(this.comparator);

	public Graph<Integer> findMinSpanningTree (final String startVertexID) {
		this.paintedVertices.clear();

		final Graph<Integer> result = new AdjacencyListGreaph<Integer>();
		this.queue.clear();

		final Collection<String> vertices = this.graph.listVerices();
		this.distances.put(startVertexID, 0);
		this.queue.addAll(vertices);

		while (this.queue.size() > 0) {
			this.queue.print("queue");

			final String vertexFrom = this.queue.remove();
			if (this.paintedVertices.contains(vertexFrom)) {
				continue;
			}

			this.distances.print("distances");
			this.direction.print("direction");

			result.addVertex(vertexFrom);
			this.paintedVertices.add(vertexFrom);// paint

			final String arrow = this.direction.get(vertexFrom);
			if (arrow != null) {
				result.addEdge(arrow, vertexFrom, this.graph.getAttachment(vertexFrom, arrow));
			}

			final Collection<String> ajacentVertices = this.graph.ajacentVertices(vertexFrom);
			for (int i = 0; i < ajacentVertices.size(); i++) {
				final String vertexTo = ajacentVertices.getElementAt(i);

				final Integer attachment = this.graph.getAttachment(vertexFrom, vertexTo);
				final Integer distanceToVertex = this.notNull(this.distances.get(vertexTo));
				if (distanceToVertex > attachment) {
// this.queue.print("remove? " + vertexTo);
// this.queue.remove(vertexTo);
// this.queue.print("remove! " + vertexTo);
					this.distances.put(vertexTo, attachment);
					this.queue.add(vertexTo);
// this.queue.print("add! " + vertexTo);

					this.direction.put(vertexTo, vertexFrom);
				}
			}

// this.graph.print("original");
			result.print("result");
		}

		return result;
	}

	public PrimSpanningTree (final Graph<Integer> graph) {
		this.graph = graph;
	}

}
