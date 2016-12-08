
package FibTree;

import java.util.ArrayList;

import com.jfixby.cmns.api.desktop.DesktopSetup;
import com.jfixby.cmns.api.log.L;

public class Fibonator {

	public static void main (final String[] args) {
		DesktopSetup.deploy();
		final StringBuilder builder = new StringBuilder();
		builder.append("static Long[] values = new Long[] {");
		for (int i = 0; i < 3100; i++) {

// L.d(" values[" + i + "] = " + Fibonacci.valueOf(i) + "L;");
			builder.append("0x" + Long.toHexString(Fibonacci.valueOf(i)) + "L,");
		}
		builder.append("};");
		L.d(builder);
	}

	static class Fibonacci {

		static ArrayList<Long> numbers = new ArrayList<Long>();
		static {
			numbers.add(1L);
			numbers.add(1L);
			numbers.add(1L);

		}

		private static Long rest (final Long pathValue) {
			return pathValue % MODULO();
		}

		public static Long MODULO = null;

		private static Long MODULO () {
			if (MODULO == null) {
				MODULO = 1000000007L;
			}
			return MODULO;
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

}
