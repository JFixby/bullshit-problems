
import java.io.IOException;
import java.util.ArrayList;

public class Solution {

	public static void main(String[] args) throws IOException {
		String data = Input.readLine();
		L.d("input", data);
		List<String> nums = JUtils.split(data, " ", "\r");
		nums.print("print");

		// L.d(args.length);
		// List<String> input=Collections.toList(args);
		// L.d(input);
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
	public static final String readAll() throws IOException {
		int read = 0;
		String data = "";
		for (;;) {
			read = System.in.read();
			if (read == -1) {
				return data;
			}
			data = data + (char) read;
		}

	}

	public static String readLine() throws IOException {
		int read = 0;
		String data = "";
		for (;;) {
			read = System.in.read();
			if (read == -1 || read == '\n') {
				return data;
			}
			data = data + (char) read;
		}
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

	public static final <T> List<T> newList() {
		return new List<T>();
	}

}

class List<T> extends ArrayList<T> {

	public void print(String string) {
		L.d(string + " > " + listToString(string.length() + 3, this));
	}

	private String listToString(int indent, List<?> array) {
		String canonocal_name = "Collection[]";
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