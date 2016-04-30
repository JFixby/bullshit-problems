
package FibTree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;

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

	public enum OPERATION_TYPE {
		Q, U;
	}

	public static class Operation {
		OPERATION_TYPE type;
		public Node X;
		public Node Y;
		public int K;

		@Override
		public String toString () {
			return this.type + "(X=" + name(this.X) + " Y=" + name(this.Y) + " K=" + this.K + ")";
		}

	}

	static {
		final int N = 1000;
// Fibonacci.valueOf(N);
		for (int i = 0; i < N; i++) {
			final Long f = Fibonacci.valueOf(i);

// log(i + " -> " + f);
// log(" ->" + f2);
		}
	}

	public static void main (final String[] args) {
		final Scanner in = new Scanner(input);
		final int numberOfNodes = in.nextInt();
		final int numberOfOperations = in.nextInt();
// log(Fibonacci.valueOf(100));
// log(numberOfOperations);
		final BinaryTree tree = new BinaryTree(numberOfNodes);

		for (int i = 0; i < numberOfNodes - 1; i++) {
			final int nodeArrayIndex = i + 1;
			final int nodeIndex = BinaryTree.toNodeIndex(nodeArrayIndex);
			final int nodeParentIndex = in.nextInt();

			tree.connect(nodeIndex, nodeParentIndex);
		}
// tree.normalize();
// tree.print();

		for (int i = 0; i < numberOfOperations; i++) {
			final Operation operation = new Operation();

			final String operationName = in.next();
			operation.type = OPERATION_TYPE.valueOf(operationName);
			if (operation.type == OPERATION_TYPE.Q) {
				{
					final int nodeIndex = in.nextInt();
					final Node n = tree.getNode(nodeIndex);
					operation.X = n;
				}
				{
					final int nodeIndex = in.nextInt();
					final Node n = tree.getNode(nodeIndex);
					operation.Y = n;
				}
			} else {
				{
					final int nodeIndex = in.nextInt();
					final Node n = tree.getNode(nodeIndex);
					operation.X = n;
				}
				{
					final int K = in.nextInt();
					operation.K = K;

				}
			}

			if (operation.type == OPERATION_TYPE.Q) {
				final Path path = tree.findPath(operation.X, operation.Y);
				final Long pathValue = path.getPathValue();
				log(rest(pathValue));
			} else if (operation.type == OPERATION_TYPE.U) {
				tree.update(operation.X, operation.K);
// tree.print();
			}

//
		}

	}

	private static Long rest (final Long pathValue) {
		return pathValue % MODULO();
	}

	private static Long MODULO () {
		if (MODULO == null) {
			MODULO = 1000000007L;
		}
		return MODULO;
	}

	public static Long MODULO = null;

	static class Fibonacci {

		static ArrayList<Long> numbers = new ArrayList<Long>();
		static {
			numbers.add(1L);
			numbers.add(1L);
			numbers.add(1L);

		}

		public static Long valueOf (final int index) {
			if (index >= numbers.size()) {
				grow(index);
			}

			return numbers.get(index);
		}

		private static void grow (final int input) {

			Long f2 = numbers.get(numbers.size() - 2);
			Long f1 = numbers.get(numbers.size() - 1);
			for (int i = numbers.size() - 1; i < input + 1; i++) {
				Long f0 = f2 + (f1);
				f0 = rest(f0);
				numbers.add(f0);
				f2 = f1;
				f1 = f0;
			}
		}

	}

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
			x.value = x.value + (Fibonacci.valueOf(k));
			this.update(x.childLeft, k + 1);
			this.update(x.childRight, k + 1);
// for (int i = 0; i < x.children.size(); i++) {
// final Node child = x.children.get(i);
// this.update(child, k + 1);
// }
		}

		public void normalize () {
			for (int i = 0; i < this.nodes.length; i++) {
				final Node node = this.nodes[i];
				node.normalize();
			}
		}

		final HashMap<String, Path> knownPaths = new HashMap<String, Path>();

		public Path findPath (final Node x, final Node y) {
			final Path result = new Path();

			if (x == y) {
				result.addState(x);
				return result;
			}

			final LinkedList<Node> stepsX = new LinkedList<Node>();
			final Vector<Node> stepsY = new Vector<Node>();
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
						stepsY.insertElementAt(yParent, 0);
					}
				}

			}

		}

		private String pathName (final Node x, final Node y) {
			return x.name + ":" + y.name;
		}

// private boolean findPath (final Node x, final Node y, final Path currentPath) {
// final ArrayList<Node> directions = x.neighbours;
// for (int i = 0; i < directions.size(); i++) {
// final Node candidate = directions.get(i);
// if (candidate == y) {
// currentPath.addState(y);
// return true;
// }
// if (currentPath.contains(candidate)) {
// continue;
// } else {
// currentPath.addState(candidate);
// final boolean success = this.findPath(candidate, y, currentPath);
// if (success) {
// return true;
// } else {
// currentPath.removeState(candidate);
// continue;
// }
// }
// }
// return false;
// }

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
			if (this.nodes[nodeParentArrayIndex].childLeft == null) {
				this.nodes[nodeParentArrayIndex].childLeft = this.nodes[nodeArrayIndex];
			} else if (this.nodes[nodeParentArrayIndex].childRight == null) {
				this.nodes[nodeParentArrayIndex].childRight = this.nodes[nodeArrayIndex];
			} else {
				throw new Error();
			}
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
// this.neighbours.clear();
// this.children.clear();
// Node.addNeighbour(this.childLeft, this.neighbours);
// Node.addNeighbour(this.childRight, this.neighbours);
// Node.addNeighbour(this.parent, this.neighbours);
//
// Node.addNeighbour(this.childLeft, this.children);
// Node.addNeighbour(this.childRight, this.children);

		}

		static private void addNeighbour (final Node candicate, final ArrayList<Node> list) {
			if (candicate != null) {
				list.add(candicate);
			}
		}

// final ArrayList<Node> neighbours = new ArrayList<Node>();
// final ArrayList<Node> children = new ArrayList<Node>();

		Node parent;
		Node childLeft;
		Node childRight;

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
		final ArrayList<Node> steps = new ArrayList<Node>();
		private Long value;

		@Override
		public String toString () {
			return "Path" + this.steps + "";
		}

		public void addState (final Node x) {
			this.steps.add(x);
		}

		public void addAll (final List<Node> steps) {
			this.steps.addAll(steps);
		}

		public Long getPathValue () {

			this.value = 0L;
			for (int i = 0; i < this.steps.size(); i++) {
				final Node step = this.steps.get(i);
				this.value = step.value + (this.value);
// this.value = rest(this.value);
			}

			return this.value;
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

}
