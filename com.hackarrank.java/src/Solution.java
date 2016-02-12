import java.util.Scanner;

public class Solution {

	static class Vector {
		public Vector(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public Vector(Vector position) {
			this(position.x, position.y);
		}

		int x;
		int y;

		public Vector add(Vector addition) {
			return new Vector(x + addition.x, y + addition.y);
		}

		public Vector negate() {
			return new Vector(-x, -y);
		}
	}

	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);
		int X = scanner.nextInt();
		int Y = scanner.nextInt();
		Vector frame = new Vector(X, Y);

		long R = scanner.nextLong();
		F input = readInput(frame, scanner);
		print(input, frame);
		F output = rotate(input, R, frame);
		print(output, frame);
	}

	private static F rotate(F input, long R, Vector frame) {
		final Vector pivot = new Vector(frame.x / 2, frame.y / 2);

		if (R == 0) {
			return input;
		}
		if (R == 1) {
			return (x, y) -> {
				Vector position = new Vector(x, y).add(pivot.negate());
				Vector v_direction = new Vector(0, -index(position.x > 0));
				Vector h_direction = new Vector(index(position.y > 0), 0);

				int layer = layerOf(position, frame, pivot);
				Vector next = position.add(h_direction);
				int layer_of_next = layerOf(position, frame, pivot);
				if (layer_of_next == layer) {
					return input.value(next.x, next.y);
				}

				next = next.add(pivot);
				return input.value(next.x, next.y);
			};
		}
		if (R > 1) {
			return rotate(input, R - 1, frame);
		}
		return null;
	}

	private static int layerOf(Vector position, Vector frame, Vector pivot) {
		return min(position.x, position.y);
	}

	private static int min(int x, int y) {
		return Math.min(x, y);
	}

	private static int index(boolean b) {
		if (b)
			return 1;
		return -1;
	}

	private static void print(F input, Vector frame) {
		for (int i = 0; i < frame.y; i++) {
			String tmp = "";
			for (int j = 0; j < frame.x; j++) {
				tmp = tmp + " " + input.value(i, j);
			}
			L.d(tmp);
		}
	}

	private static F readInput(Vector frame, Scanner scanner) {
		long[][] A = new long[frame.x][frame.y];
		for (int i = 0; i < frame.y; i++) {
			for (int j = 0; j < frame.x; j++) {
				A[i][j] = scanner.nextLong();
				// L.d("" + i + "," + j, A[i][j]);
			}
		}
		return (x, y) -> A[x][y];
	}

	public interface F {
		public long value(int x, int y);
	}

}

class L extends Logger {

}

class Logger {
	public static final void d(Object message) {
		System.out.println("" + message);
	}

	public static final void d(String tag, Object message) {
		d(tag + " > " + message);
	}
}
