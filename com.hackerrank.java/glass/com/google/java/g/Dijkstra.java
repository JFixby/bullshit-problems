
package com.google.java.g;

import java.util.Comparator;

import com.jfixby.scarabei.api.collections.Collection;
import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.Heap;
import com.jfixby.scarabei.api.collections.List;
import com.jfixby.scarabei.api.collections.Map;
import com.jfixby.scarabei.api.collections.Set;
import com.jfixby.scarabei.api.desktop.DesktopSetup;
import com.jfixby.scarabei.api.log.L;
import com.jfixby.scarabei.api.util.JUtils;

public class Dijkstra {

	public interface Graph<T> {

		void addVertex (String vertexID);

		void print (String tag);

		void addEdge (String string, String string2, T i);

		Collection<String> listVertices ();

		Collection<String> listAjacentVertices (String vertexFrom);

		T getAttachment (String vertexFrom, String vertexTo);

	}

	public static class AjacentListGraph<T> implements Graph<T> {

		final Map<String, List<String>> ajacentList = Collections.newMap();
		final Map<String, T> attachments = Collections.newMap();

		@Override
		public void addVertex (final String vertexID) {
			this.ajacentList.put(vertexID, Collections.newList());
		}

		@Override
		public void print (final String tag) {

			final StringBuilder string = new StringBuilder();
// final String canonocal_name = "Map[]";
			final int n = this.ajacentList.size();

			final int indent = tag.length() + 3;
			string.append("---[" + tag + "](" + this.ajacentList.size() + ")-------------------------\n");
			final String indent_str = JUtils.prefix(" ", indent);
			for (int i = 0; i < n; i++) {
				string.append(indent_str + "(" + i + ") " + this.ajacentList.getKeyAt(i) + " :-> "
					+ this.toString(this.ajacentList.getKeyAt(i), this.ajacentList.getValueAt(i)) + "\n");
			}

			L.d(string.toString());
		}

		static private String edge (final String vertexFrom, final String vertexTo) {
			return vertexFrom + "-" + vertexTo;
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
		public void addEdge (final String vertexFrom, final String vertexTo, final T attachemtn) {
			this.ajacentList.get(vertexFrom).add(vertexTo);
			this.attachments.put(edge(vertexFrom, vertexTo), attachemtn);
		}

		@Override
		public Collection<String> listVertices () {
			return this.ajacentList.keys();
		}

		@Override
		public Collection<String> listAjacentVertices (final String vertexFrom) {
			return this.ajacentList.get(vertexFrom);
		}

		@Override
		public T getAttachment (final String vertexFrom, final String vertexTo) {
			return this.attachments.get(edge(vertexFrom, vertexTo));
		}

	}

	private final Graph<Integer> graph;

	public Dijkstra (final Graph<Integer> graph) {
		this.graph = graph;
	}

	public static void main (final String[] args) {
		DesktopSetup.deploy();

		final Graph<Integer> graph = new AjacentListGraph<Integer>();

		graph.addVertex("A");
		graph.addVertex("B");
		graph.addVertex("C");
		graph.addVertex("D");
		graph.addVertex("E");

		graph.addEdge("A", "B", 10);
		graph.addEdge("A", "C", 3);
		graph.addEdge("B", "C", 1);
		graph.addEdge("C", "B", 4);
		graph.addEdge("B", "D", 2);
		graph.addEdge("C", "D", 8);
		graph.addEdge("C", "E", 2);
		graph.addEdge("D", "E", 7);
		graph.addEdge("E", "D", 9);

		graph.print("input");

		final Dijkstra dijkstra = new Dijkstra(graph);

		final Graph<Integer> result = dijkstra.findShortestPathTree("A");

		result.print("result");

	}

	public Graph<Integer> findShortestPathTree (final String startVertex) {
		final Graph<Integer> result = new AjacentListGraph<Integer>();
		final Map<String, Integer> distanceToVertex = Collections.newMap();
		final Map<String, String> directionToVertex = Collections.newMap();

		final Comparator<String> comparator = new Comparator<String>() {
			@Override
			public int compare (final String o1, final String o2) {
				final int distance1 = Dijkstra.this.notNull(distanceToVertex.get(o1));
				final int distance2 = Dijkstra.this.notNull(distanceToVertex.get(o2));
				return -Integer.compare(distance1, distance2);
			}
		};
		distanceToVertex.put(startVertex, 0);

		final Set<String> paintedNodes = Collections.newSet();
		final Heap<String> queue = Collections.newHeap(comparator);
		queue.addAll(this.graph.listVertices());

		while (queue.size() > 0) {
			final String vertexFrom = queue.remove();
			if (paintedNodes.contains(vertexFrom)) {
				continue;
			}

			paintedNodes.add(vertexFrom);
			result.addVertex(vertexFrom);

			final String incomming = directionToVertex.get(vertexFrom);
			if (incomming != null) {
				result.addEdge(incomming, vertexFrom, distanceToVertex.get(vertexFrom));
			}

			final Collection<String> ajacent = this.graph.listAjacentVertices(vertexFrom);
			for (int i = 0; i < ajacent.size(); i++) {
				final String vertexTo = ajacent.getElementAt(i);
				final Integer edgeDistance = this.graph.getAttachment(vertexFrom, vertexTo);
				final int oldDistance = this.notNull(distanceToVertex.get(vertexTo));
				final int currentDistance = this.notNull(distanceToVertex.get(vertexFrom));
				final int newDistance = currentDistance + edgeDistance;
				if (newDistance < oldDistance) {
					distanceToVertex.put(vertexTo, newDistance);
					directionToVertex.put(vertexTo, vertexFrom);
					queue.add(vertexTo);
				}
			}

		}

		return result;
	}

	private int notNull (final Integer integer) {
		return integer == null ? 0x00ffffff : integer;
	}

}
