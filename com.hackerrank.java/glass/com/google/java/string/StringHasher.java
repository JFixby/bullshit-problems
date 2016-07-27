
package com.google.java.string;

import java.util.HashMap;

import com.jfixby.cmns.api.debug.Debug;
import com.jfixby.cmns.api.log.L;
import com.jfixby.red.desktop.DesktopSetup;

public class StringHasher {

	int size = 0;
// long P = Integer.MAX_VALUE;
	long P = 937;
	long Q = 1 << (8 * 2);
	long value = 0;
	final HashMap<Integer, Long> chachedPowersOfQ = new HashMap<Integer, Long>();

	@Override
	public String toString () {
		return "StringHasher[size=" + this.size + ", P=" + this.P + ", Q=" + this.Q + "] value = " + this.value;
	}

	public void appendLeft (final char c) {
		this.value = this.value * this.Q + c;
		this.value = this.value % this.P;
		this.size++;
	}

	public void discardLeft (final char c) {
		Debug.checkTrue("valid", (this.value - c) % this.Q == 0);
		this.value = (this.value - c) / this.Q;
		this.size--;
	}

	public void appendRight (final char c) {
		this.size++;
		this.value = this.value + c * this.powQ(this.size);
		this.value = this.value % this.P;
	}

	public void discardRight (final char c) {
		this.value = this.value - c * this.powQ(this.size);
		if (this.value < 0) {
			this.value = this.P + this.value % this.P;
		}
		this.size--;
	}

	public void appendRight (final String string) {
		for (int i = 0; i < string.length(); i++) {
			this.appendRight(string.charAt(i));
		}
	}

	public void appendLeft (final String string) {
		for (int i = string.length() - 1; i >= 0; i--) {
			this.appendLeft(string.charAt(i));
		}
	}

	private long powQ (final int powerOfQ) {
		Long value = this.chachedPowersOfQ.get(powerOfQ);
		if (value != null) {
			return value.longValue();
		}
		value = this.computePowQ(powerOfQ);
		this.chachedPowersOfQ.put(powerOfQ, value);
		return value;
	}

	private long computePowQ (final int powerOfQ) {
		if (powerOfQ == 0) {
			return 1L;
		}
		if (powerOfQ == 1) {
			return this.Q % this.P;
		}
		return (this.Q * this.powQ(powerOfQ - 1)) % this.P;
	}

	public static void main (final String[] args) {
		DesktopSetup.deploy();
		final StringHasher hasher = new StringHasher();
		L.d("hasher", hasher);
		hasher.appendRight('c');
		L.d("hasher", hasher);
		hasher.appendLeft('a');
		L.d("hasher", hasher);
		hasher.discardRight('c');
		L.d("hasher", hasher);
		hasher.discardLeft('a');
		L.d("hasher", hasher);
		hasher.appendRight("ABC");
	}

}
