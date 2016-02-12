
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;

public class Solution {

	public static void main(String[] args) throws IOException {
		int N = Input.readInt();
		List<BigInteger> ints = Input.readIntegers(N);
		// ints.print("ints");
		BigInteger result = new BigInteger("0");
		for (int i = 0; i < ints.size(); i++) {
			BigInteger add = ints.get(i);
			result = result.add(add);
		}
		L.d(result.toString());

	}
}

class JUtils {
	public static List<String> split(String input, String separator) {
		return Collections.newList(input.split(separator));
	}

}

class L {
	public static final void d(Object message) {
		System.out.println("" + message);
	}

	public static final void d(String tag, Object message) {
		d(tag + " > " + message);
	}
}

class Input {

	private static String readSection(char... c) throws IOException {
		List<Integer> list_of_termenators = Collections.newCharList(c);
		Integer read = 0;
		String data = "";
		for (;;) {
			read = System.in.read();
			if (read == -1 || list_of_termenators.contains(read)) {
				return data;
			}
			data = data + (char) (int) read;
		}
	}

	public static List<BigInteger> readIntegers(int n) throws IOException {
		List<BigInteger> result = Collections.newList();
		for (int i = 0; i < n; i++) {
			String int_string = readSection(' ', '\n', '\r');
			// L.d("int_string", int_string);
			BigInteger e = new BigInteger(int_string);
			result.add(e);
		}
		return result;
	}

	public static int readInt() throws NumberFormatException, IOException {
		return Integer.parseInt(readLine());
	}

	public static String readLine() throws IOException {
		return readSection('\n');
	}
}

class Collections {
	public static final <T> List<T> newList(T... array) {
		List<T> result = new List<T>();
		for (int i = 0; i < array.length; i++) {
			result.add(array[i]);
		}
		return result;
	}

	public static List<Integer> newCharList(char[] array) {
		List<Integer> result = new List<Integer>();
		for (int i = 0; i < array.length; i++) {
			result.add((int) array[i]);

		}
		return result;
	}

	public static final <T> List<T> newList() {
		return new List<T>();
	}

}

class List<T> extends ArrayList<T> {

	private static final long serialVersionUID = 6518468021004909830L;

	public void print(String string) {
		L.d(string + " > " + listToString(string.length() + 3, this));
	}

	private String listToString(int indent, List<?> array) {
		String canonocal_name = "List[]";
		final int n = array.size();
		if (n == 0) {
			return canonocal_name;
		}

		String t = canonocal_name.substring(0, canonocal_name.length() - 1) + n + "]\n";
		String indent_str = indent(indent);
		for (int i = 0; i < n; i++) {
			t = t + indent_str + "(" + i + ") " + array.get(i) + "\n";
		}
		return t;
	}

	public String indent(int indent) {
		String r = "";
		for (int i = 0; i < indent; i++) {
			r = r + " ";
		}
		return r;
	}
}