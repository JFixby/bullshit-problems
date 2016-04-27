
package SumBigInt;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;

public class Solution {

	public static void main (final String[] args) throws IOException {
		final int N = Input.readInt();
		final List<BigInteger> ints = Input.readIntegers(N);
		// ints.print("ints");
		BigInteger result = new BigInteger("0");
		for (int i = 0; i < ints.size(); i++) {
			final BigInteger add = ints.get(i);
			result = result.add(add);
		}
		L.d(result.toString());

	}
}

class L extends Logger {

}

class Logger {
	public static final void d (final Object message) {
		System.out.println("" + message);
	}

	public static final void d (final String tag, final Object message) {
		d(tag + " > " + message);
	}
}

class Input {

	private static String readSection (final char... c) throws IOException {
		final List<Integer> list_of_termenators = Collections.newCharList(c);
		Integer read = 0;
		String data = "";
		for (;;) {
			read = System.in.read();
			if (read == -1 || list_of_termenators.contains(read)) {
				return data;
			}
			data = data + (char)(int)read;
		}
	}

	public static List<BigInteger> readIntegers (final int n) throws IOException {
		final List<BigInteger> result = Collections.newList();
		for (int i = 0; i < n; i++) {
			final String int_string = readSection(' ', '\n', '\r');
			// L.d("int_string", int_string);
			final BigInteger e = new BigInteger(int_string);
			result.add(e);
		}
		return result;
	}

	public static int readInt () throws NumberFormatException, IOException {
		return Integer.parseInt(readLine());
	}

	public static String readLine () throws IOException {
		return readSection('\n');
	}
}

class Collections {
	@SuppressWarnings("unchecked")
	public static final <T> List<T> newList (final T... array) {
		final List<T> result = new List<T>();
		for (int i = 0; i < array.length; i++) {
			result.add(array[i]);
		}
		return result;
	}

	public static List<Integer> newCharList (final char[] array) {
		final List<Integer> result = new List<Integer>();
		for (int i = 0; i < array.length; i++) {
			result.add((int)array[i]);

		}
		return result;
	}

	public static final <T> List<T> newList () {
		return new List<T>();
	}

}

class List<T> extends ArrayList<T> {

	private static final long serialVersionUID = 6518468021004909830L;

	public void print (final String string) {
		L.d(string + " > " + this.listToString(string.length() + 3, this));
	}

	private String listToString (final int indent, final List<?> array) {
		final String canonocal_name = "List[]";
		final int n = array.size();
		if (n == 0) {
			return canonocal_name;
		}

		String t = canonocal_name.substring(0, canonocal_name.length() - 1) + n + "]\n";
		final String indent_str = this.indent(indent);
		for (int i = 0; i < n; i++) {
			t = t + indent_str + "(" + i + ") " + array.get(i) + "\n";
		}
		return t;
	}

	public String indent (final int indent) {
		String r = "";
		for (int i = 0; i < indent; i++) {
			r = r + " ";
		}
		return r;
	}
}

// class JUtils {
// public static List<String> split(String input, String separator) {
// return Collections.newList(input.split(separator));
// }
//
// }
