
package FibTree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import com.jfixby.hrank.AbstractSolution;

public class Solution extends AbstractSolution {

// public static java.io.InputStream input = System.in;
// public static java.io.PrintStream output = System.out;
// public static void log (final Object msg) {
// output.println(msg);
// }

	@Override
	public void run (final String[] args) {
		main(args);
	}

	public static void main (final String[] args) {
		final Scanner in = new Scanner(input);
		final int numberOfNodes = in.nextInt();
		final int numberOfOperations = in.nextInt();
		final BinaryTree tree = new BinaryTree(numberOfNodes);

		for (int i = 0; i < numberOfNodes - 1; i++) {
			final int nodeArrayIndex = i + 1;
			final int nodeIndex = BinaryTree.toNodeIndex(nodeArrayIndex);
			final int nodeParentIndex = in.nextInt();

			tree.connect(nodeIndex, nodeParentIndex);
		}
		for (int i = 0; i < numberOfOperations; i++) {

			final String operationName = in.next();

			if (operationName.equals("Q")) {
				final Path path = tree.findPath(tree.getNode(in.nextInt()), tree.getNode(in.nextInt()));
				final Long pathValue = path.getPathValue();
				log(rest(pathValue));
			} else {
				tree.update(tree.getNode(in.nextInt()), in.nextInt());
			}

		}

	}

	private static Long rest (final Long pathValue) {
		return pathValue % MODULO();
	}

	public static int MODULO () {
		if (MODULO == null) {
			MODULO = 1000000007;
		}
		return MODULO;
	}

	public static Integer MODULO = null;

	static class BinaryTree {
		final Node[] nodes;

		public BinaryTree (final int numberOfNodes) {
			this.nodes = new Node[numberOfNodes];
			for (int i = 0; i < numberOfNodes; i++) {
				this.nodes[i] = new Node(BinaryTree.toNodeIndex(i) + "");
			}
		}

		public void update (final Node x, final int k) {
			if (x == null) {
				return;
			}
			x.value = x.value + Fibonacci.valueOf(k);
			x.value = rest(x.value);
			for (int i = 0; i < x.children.size(); i++) {
				this.update(x.children.get(i), k + 1);
			}

		}

		final HashMap<String, Path> knownPaths = new HashMap<>();

		public Path findPath (final Node x, final Node y) {
			final Path result = new Path();

			if (x == y) {
				result.addState(x);
				return result;
			}

			final LinkedList<Node> stepsX = new LinkedList<>();
			final ArrayList<Node> stepsY = new ArrayList<>();
// final HashSet<Node> visited = new HashSet<Node>();

			stepsX.add(x);
			stepsY.add(y);
// visited.add(x);
// visited.add(y);
			final boolean pathFound = false;
			while (!pathFound) {
				{
					final Node lastX = stepsX.getLast();
					if (stepsY.contains(lastX)) {
						result.addAll(stepsX);
						final int start = stepsY.indexOf(lastX);
						for (int i = start + 1; i < stepsY.size(); i++) {
							result.addState(stepsY.get(i));
						}
						return result;
					}
					final Node xParent = lastX.parent;
					if (xParent != null) {
						stepsX.add(xParent);
					}
				}
				{
					final Node lastY = stepsY.get(0);
					if (stepsX.contains(lastY)) {
						final int end = stepsX.indexOf(lastY);
						for (int i = 0; i < end; i++) {
							result.addState(stepsX.get(i));
						}
						result.addAll(stepsY);
						return result;
					}
					final Node yParent = lastY.parent;
					if (yParent != null) {
						stepsY.add(0, yParent);
					}
				}

			}

		}

		private String pathName (final Node x, final Node y) {
			return x.name + ":" + y.name;
		}

		public Node getNode (final int nodeIndex) {
			return this.nodes[BinaryTree.toArrayIndex(nodeIndex)];
		}

		public void print () {
			log(Arrays.toString(this.nodes));
		}

		public void connect (final int nodeIndex, final int nodeParentIndex) {

			final int nodeParentArrayIndex = BinaryTree.toArrayIndex(nodeParentIndex);
			final int nodeArrayIndex = BinaryTree.toArrayIndex(nodeIndex);

			this.nodes[nodeArrayIndex].parent = this.nodes[nodeParentArrayIndex];
			this.nodes[nodeParentArrayIndex].children.add(this.nodes[nodeArrayIndex]);
		}

		public static int toArrayIndex (final int i) {
			return i - 1;
		}

		public static int toNodeIndex (final int i) {
			return i + 1;
		}
	}

	public static String name (final Node child) {
		if (child == null) {
			return "";
		}
		return child.name;
	}

	static class Node {
		private final String name;

		public Node (final String name) {
			this.name = name;
		}

		public void normalize () {

		}

		Node parent;
		final List<Node> children = new ArrayList<>();
		public Long value = 0L;

// @Override
// public String toString () {
// return "Node(" + this.name + ") L=" + name(this.childLeft) + " R=" + name(this.childRight) + " parent="
// + name(this.parent) + ">";
// }

		@Override
		public String toString () {
			return "Node(" + this.name + ") " + this.value;
		}

	}

	static class Path {
		final ArrayList<Node> steps = new ArrayList<>();

		@Override
		public String toString () {
			return "Path" + this.steps + "";
		}

		public void addState (final Node x) {
			if (this.steps.contains(x)) {
				throw new Error();
			}
			this.steps.add(x);
		}

		public void addAll (final List<Node> steps) {
			for (int i = 0; i < steps.size(); i++) {
				this.addState(steps.get(i));
			}
		}

		public Long getPathValue () {
			long value = 0L;
			for (int i = 0; i < this.steps.size(); i++) {
				final Node step = this.steps.get(i);
				value = step.value + (value);
				value = rest(value);
			}
			return value;
		}

		public void print () {
			log("path : " + this.steps);
		}

		public void removeState (final Node candidate) {
			this.steps.remove(candidate);
		}

		public boolean contains (final Node candidate) {
			return this.steps.contains(candidate);
		}
	}

	public static final class Fibonacci extends F {
		public static int N = MODULO();
		static ArrayList<Long> appendix = new ArrayList<>();

		public static Long valueOf (final int k) {
			if (k < values.length) {
				return values[k];
			} else {
				final int appendixIndex = appendixIndex(k);
				if (appendix.size() > appendixIndex) {
					return appendix.get(appendixIndex);
				} else {
					grow(appendixIndex + 1);
					return appendix.get(appendixIndex);
				}
			}
		}

		private static void grow (final int target) {
			while (appendix.size() < target) {
				final Long m2 = appendix.get(appendix.size() - 2);
				final Long m1 = appendix.get(appendix.size() - 1);
				appendix.add(m1 + m2);
			}

		}

		private static int appendixIndex (final int k) {
			return values.length - k;
		}

	}
}
