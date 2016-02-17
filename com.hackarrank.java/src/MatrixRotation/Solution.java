package MatrixRotation;
import java.util.Scanner;

public class Solution {

	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);
		int H = scanner.nextInt();
		int W = scanner.nextInt();
		Vector frame = new Vector(W, H);

		F input = readInput(frame, scanner);
		// print("input", input, frame);
		F output = input;
		if (W < H) {
			output = rotateLeft(output, frame);
		}
		
		long rot = scanner.nextLong();

		// // W>H;
		// int alpha = frame.y;
		// output = rotate(output, rot, alpha, frame);
		//
		if (W < H) {
			output = rotateRight(output, frame);
		}
		print(output, frame);
	}

	private static F rotate(F input, long rot, int alpha, Vector frame) {
		if (rot == 0) {
			return input;
		}
		if (rot == 1) {
			return (x, y) -> {
				if (x >= alpha && x <= frame.x - alpha - 1) {
					if (y > alpha) {
						return input.value(x + 1, y);
					}
					if (y < alpha) {
						return input.value(x - 1, y);
					}
					return input.value(x, y);
				}
				return 0;
			};
		}

		return rotate(rotate(input, rot - 1, alpha, frame), 1, alpha, frame);

	}

	private static F rotateRight(F input, Vector frame) {
		F output = swapXY(input, frame);
		output = flipX(output, frame);
		return output;

	}

	private static F rotateLeft(F input, Vector frame) {
		F output = flipX(input, frame);
		output = swapXY(output, frame);
		return output;
	}

	private static F flipX(F output, Vector frame) {
		return (x, y) -> output.value(frame.x - x - 1, y);
	}

	private static F flipY(F output, Vector frame) {
		return (x, y) -> output.value(x, frame.y - y - 1);
	}

	private static F swapXY(F input, Vector frame) {
		frame.swap();
		return (x, y) -> input.value(y, x);
	}

	private static void print(String string, F input, Vector frame) {
		L.d(string + "[" + frame.x + "," + frame.y + "]");
		print(input, frame);
	}

	private static void print(F input, Vector frame) {
		for (int y = 0; y < frame.y; y++) {
			String tmp = "";
			for (int x = 0; x < frame.x; x++) {
				tmp = tmp + " " + input.value(x, y);
			}
			L.d(tmp);
		}
	}

	private static F readInput(Vector frame, Scanner scanner) {
		long[][] A = new long[frame.x][frame.y];
		for (int y = 0; y < frame.y; y++) {
			for (int x = 0; x < frame.x; x++) {
				A[x][y] = scanner.nextLong();
				// L.d("" + i + "," + j, A[i][j]);
			}
		}
		return (x, y) -> A[x][y];
	}

	public interface F {
		public long value(int x, int y);
	}

}

class Vector {
	public Vector(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void swap() {
		int tmp = this.y;
		this.y = this.x;
		this.x = tmp;
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
