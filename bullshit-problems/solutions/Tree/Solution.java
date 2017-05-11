
package Tree;

import java.util.Scanner;

import com.jfixby.hrank.AbstractSolution;

import util.graph.AjacentGraph;
import util.graph.Graph;

public class Solution extends AbstractSolution {
	public static void main (final String[] args) {
		final Scanner in = new Scanner(input);
		final int nV = in.nextInt();
		final int nE = in.nextInt();
		final Graph<String> graph = new AjacentGraph<String>();

		for (int i = 1; i <= nV; i++) {
			graph.addVertex("" + i);
		}
		graph.print();
		for (int i = 1; i <= nE; i++) {
			final int v = in.nextInt();
			final int u = in.nextInt();
			graph.addEdge("" + v, "" + u, null);
			graph.addEdge("" + u, "" + v, null);
		}

		graph.print();

// log(2);
	}

	@Override
	public void run (final String[] args) {
		main(args);
	}

}
