
package com.jfixby.hrank;

import com.jfixby.cmns.api.log.L;

public class TestResult {

	private final String expected;
	private final String actual;
	private final String input;

	public TestResult (final String input, final String expected, final String actual) {
		this.input = input;
		this.expected = expected;
		this.actual = actual;
	}

	public void print (final String testName) {
		final boolean pass = this.isPassed();
		L.d("---Test[" + testName + "] " + this.ok(pass) + "---------------------------------------");
		L.d("---[Input]---");
		L.d(this.wrap(this.input));

		if (!pass) {
			L.d("---[Expected]---");
			L.d(this.wrap(this.expected));
			L.d("---[Output]---");
			L.d(this.wrap(this.actual));
		} else {
			L.d("---[Output]---");
			L.d(this.wrap(this.actual));
		}
	}

	private String ok (final boolean pass) {
		if (pass) {
			return "OK";
		}
		return "!FAILED! !!!!!!!!!!!!!!!!!!!!!!!!!";
	}

	private String wrap (final String msg) {
// return "<" + msg + ">";
		return "" + msg + "";
	}

	public boolean isPassed () {
		return this.expected.equals(this.actual);
	}

}
