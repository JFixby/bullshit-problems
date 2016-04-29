
package FibTree;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

import com.jfixby.hrank.AbstractSolution;

public class Solution extends AbstractSolution {

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
		tree.normalize();
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
				final BigInteger pathValue = path.getPathValue();
				log(rest(pathValue));
			} else if (operation.type == OPERATION_TYPE.U) {
				tree.update(operation.X, operation.K);
// tree.print();
			}

//
		}

	}

	static BigInteger MODULO = new BigInteger("1000000007");

	private static BigInteger rest (final BigInteger pathValue) {
// return pathValue.remainder(MODULO);
		return pathValue.mod(MODULO);
	}

	static class Fibonacci {
		private static final BigInteger ONE = BigInteger.ONE;
		private static final BigInteger TWO = ONE.add(ONE);
		static HashMap<BigInteger, BigInteger> numbers = new HashMap<BigInteger, BigInteger>();

		public static BigInteger valueOf (final BigInteger input) {
			BigInteger value = numbers.get(input);
			if (value == null) {
				value = calculate(input);
				numbers.put(input, value);
			}
			return value;
		}

		public static BigInteger valueOf (final int i) {
			return valueOf(new BigInteger(i + ""));
		}

		private static BigInteger calculate (final BigInteger input) {
			if (input.compareTo(TWO) <= 0) {
				return ONE;
			}

			final BigInteger input_m1 = input.subtract(ONE);
			final BigInteger input_m2 = input_m1.subtract(ONE);

			final BigInteger f_m1 = valueOf(input_m1);
			final BigInteger f_m2 = valueOf(input_m2);

			final BigInteger result = f_m1.add(f_m2);

			return result;
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
			x.value = x.value.add(Fibonacci.valueOf(k));
			for (int i = 0; i < x.children.size(); i++) {
				final Node child = x.children.get(i);
				this.update(child, k + 1);
			}
		}

		public void normalize () {
			for (int i = 0; i < this.nodes.length; i++) {
				final Node node = this.nodes[i];
				node.normalize();
			}
		}

		final HashMap<String, Path> knownPaths = new HashMap<String, Path>();

		public Path findPath (final Node x, final Node y) {
			if (x.name.compareTo(y.name) > 0) {
				return this.findPath(y, x);
			}
			final String pathName = this.pathName(x, y);
			final Path knownPath = this.knownPaths.get(pathName);
			if (knownPath != null) {
				return knownPath;
			}

			final Path result = new Path();
			result.addState(x);
			if (x != y) {
				this.findPath(x, y, result);
			}
			this.knownPaths.put(pathName, result);
// this.knownPaths.put(this.pathName(y, x), result);
			return result;
		}

		private String pathName (final Node x, final Node y) {
			return x.name + ":" + y.name;
		}

		private boolean findPath (final Node x, final Node y, final Path currentPath) {
			final ArrayList<Node> directions = x.neighbours;
			for (int i = 0; i < directions.size(); i++) {
				final Node candidate = directions.get(i);
				if (candidate == y) {
					currentPath.addState(y);
					return true;
				}
				if (currentPath.contains(candidate)) {
					continue;
				} else {
					currentPath.addState(candidate);
					final boolean success = this.findPath(candidate, y, currentPath);
					if (success) {
						return true;
					} else {
						currentPath.removeState(candidate);
						continue;
					}
				}
			}
			return false;
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
			this.neighbours.clear();
			this.children.clear();
			Node.addNeighbour(this.childLeft, this.neighbours);
			Node.addNeighbour(this.childRight, this.neighbours);
			Node.addNeighbour(this.parent, this.neighbours);

			Node.addNeighbour(this.childLeft, this.children);
			Node.addNeighbour(this.childRight, this.children);

		}

		static private void addNeighbour (final Node candicate, final ArrayList<Node> list) {
			if (candicate != null) {
				list.add(candicate);
			}
		}

		final ArrayList<Node> neighbours = new ArrayList<Node>();
		final ArrayList<Node> children = new ArrayList<Node>();

		Node parent;
		Node childLeft;
		Node childRight;

		public BigInteger value = BigInteger.ZERO;

		@Override
		public String toString () {
			return "Node(" + this.name + ")>" + this.value + "< L=" + name(this.childLeft) + " R=" + name(this.childRight)
				+ " parent=" + name(this.parent) + ">";
		}

	}

	static class Path {
		final ArrayList<Node> steps = new ArrayList<Node>();
		private BigInteger value;

		public void addState (final Node x) {
			this.steps.add(x);
		}

		public BigInteger getPathValue () {

			this.value = BigInteger.ZERO;
			for (int i = 0; i < this.steps.size(); i++) {
				final Node step = this.steps.get(i);
				this.value = step.value.add(this.value);
				this.value = rest(this.value);
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
