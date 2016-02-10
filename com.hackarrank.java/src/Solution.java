
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Solution {

	public static void main(String[] args) throws IOException {
		String data = Input.readAll();
		L.d("input", data);
		List<String> nums = JUtils.split(data, " ");

		// L.d(args.length);
		// List<String> input=Collections.toList(args);
		// L.d(input);
	}
}

class JUtils {
	
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
}

class Collections {

	public static final <T> List<T> toList(T[] array) {
		List<T> result = new ArrayList<T>();
		for (int i = 0; i < array.length; i++) {
			result.add(array[i]);

		}
		return result;
	}

}