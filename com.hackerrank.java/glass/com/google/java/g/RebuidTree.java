
package com.google.java.g;

import com.google.java.g.PrimSpanningTree.AdjacencyListGreaph;
import com.google.java.g.PrimSpanningTree.Graph;
import com.jfixby.cmns.api.collections.Collection;
import com.jfixby.cmns.api.collections.Collections;
import com.jfixby.cmns.api.collections.List;
import com.jfixby.cmns.api.collections.Set;
import com.jfixby.red.desktop.DesktopSetup;

public class RebuidTree {

	public static void main (final String[] args) {
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
		graph.addEdges("B", "C", 1);
		graph.addEdges("B", "H", 1);
		graph.addEdges("C", "G", 1);
		graph.addEdges("A", "D", 1);
		graph.addEdges("D", "E", 1);
		graph.addEdges("D", "F", 1);

		graph.print("input");

		final Set<String> painted = Collections.newSet();
		final List<String> E = Collections.newList();
		final List<String> X = Collections.newList();
		final List<String> C = Collections.newList();

		DFS(graph, "A", E, X, painted);
		E.print("E");
		X.print("X");

		final Graph<Integer> restored = new AdjacencyListGreaph<Integer>();
		UNDFS(restored, C, E, X, painted);
		restored.print("restored == graph is " + restored.equals(graph));
	}

	private static void DFS (final Graph<Integer> graph, final String node, final List<String> E, final List<String> X,
		final Set<String> painted) {
		if (painted.contains(node)) {
			return;
		}
		painted.add(node);
		E.add(node);
		final Collection<String> ajacent = graph.ajacentVertices(node);
		for (int i = 0; i < ajacent.size(); i++) {
			final String child = ajacent.getElementAt(i);

			DFS(graph, child, E, X, painted);
		}
		X.add(node);

	}

	private static void UNDFS (final Graph<Integer> graph, final List<String> C, final List<String> E, final List<String> X,
		final Set<String> painted) {

		while (X.size() > 0) {
			if (C.size() == 0) {
				C.add(E.removeElementAt(0));
				graph.addVertex(C.getLast());
			}
			if (X.getElementAt(0).equals(C.getLast())) {
				C.removeLast();
				if (C.size() > 0) {
					graph.addEdges(X.getElementAt(0), C.getLast(), 1);
				}
				X.removeElementAt(0);
			} else {
				C.add(E.removeElementAt(0));
				graph.addVertex(C.getLast());
			}
		}
	}

}
