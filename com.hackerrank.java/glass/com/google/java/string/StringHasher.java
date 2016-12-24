
package com.google.java.string;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;

import org.junit.Test;

import com.jfixby.scarabei.api.desktop.DesktopSetup;
import com.jfixby.scarabei.api.log.L;

public class StringHasher {

	int size = 0;
	long P = 937;
// long P = 31;
	long Q = 256;
	long value = 0;
	final HashMap<Integer, Long> chachedPowersOfQ = new HashMap<Integer, Long>();
// final StringBuilder buffer = new StringBuilder();

// public StringHasher (final String a) {
// this.appendRight(a);
// }

	public StringHasher () {
	}

	@Override
	public int hashCode () {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int)(this.P ^ (this.P >>> 32));
		result = prime * result + (int)(this.Q ^ (this.Q >>> 32));
		result = prime * result + this.size;
		result = prime * result + (int)(this.value ^ (this.value >>> 32));
		return result;
	}

	@Override
	public boolean equals (final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		final StringHasher other = (StringHasher)obj;
		if (this.value != other.value) {
			return false;
		}
		if (this.P != other.P) {
			return false;
		}
		if (this.Q != other.Q) {
			return false;
		}
		if (this.size != other.size) {
			return false;
		}
		return true;
	}

	@Override
	public String toString () {
		return "StringHasher[size=" + this.size + ", P=" + this.P + ", Q=" + this.Q + "] value = " + this.value;
// return "StringHasher[size=" + this.size + ", P=" + this.P + ", Q=" + this.Q + "] value = " + this.value + " :: " + "<"
// + this.buffer + ">";
	}

	public void appendLeft (final char c) {
		this.value = (this.value * this.Q + c) % this.P;
// this.value = this.value % this.P;
		this.size++;
// this.buffer.insert(0, c);
	}

	public void discardLeft (final String string) {
		for (int i = 0; i < string.length(); i++) {
			this.discardLeft(string.charAt(i));
		}
	}

	public void discardLeft (final char c) {
		this.value = this.P + (this.value - c);
		this.value = this.value * this.powQ(this.P - 2);
		this.value = this.value % this.P;
		this.size--;
// this.buffer.deleteCharAt(0);
	}

	public void discardRight (final char c) {
		this.size--;
		this.value = this.value - c * this.powQ(this.size);
// this.buffer.deleteCharAt(this.size);
		if (this.value < 0) {
			this.value = this.P + this.value % this.P;
		}

	}

	public void appendRight (final char c) {
		this.value = this.value + c * this.powQ(this.size);
// L.d(this.value);
		this.size++;
		this.value = this.value % this.P;
// this.buffer.append(c);
// L.d(" >>" + this.value);
	}

	public StringHasher appendRight (final String string) {
		for (int i = 0; i < string.length(); i++) {
			this.appendRight(string.charAt(i));
		}
		return this;
	}

	public StringHasher appendLeft (final String string) {
		for (int i = string.length() - 1; i >= 0; i--) {
			this.appendLeft(string.charAt(i));
		}
		return this;
	}

	private long powQ (long powerOfQ) {
		powerOfQ = powerOfQ % (this.P - 1);
		if (powerOfQ == 0) {
			return 1L;
		}
		if (powerOfQ == 1) {
			return this.Q % this.P;
		}
		long result = 1;
		for (int k = 0; k < powerOfQ; k++) {
			result = result * this.Q;
			result = result % this.P;
		}
		return result;
// Long value = this.chachedPowersOfQ.get(powerOfQ);
// if (value != null) {
// return value.longValue();
// }
// value = this.computePowQ(powerOfQ);
// this.chachedPowersOfQ.put((int)powerOfQ, value);
// return value;

	}

	private long computePowQ (final long powerOfQ) {
// powerOfQ = powerOfQ % (this.P - 1);
		if (powerOfQ == 0) {
			return 1L;
		}
		if (powerOfQ == 1) {
			return this.Q % this.P;
		}
		return (this.Q * this.powQ(powerOfQ - 1)) % this.P;
	}

	@Test
	public void test () {
// DesktopSetup.deploy();
		final StringHasher hashA = new StringHasher();
		final StringHasher hashB = new StringHasher();
		hashA.appendRight("ABCD");
		hashB.appendLeft("BC");
		hashA.discardRight('D');
		hashA.discardLeft('A');
// hashB.discardRight('D');

		L.d(hashA);
		L.d(hashB);
		assertTrue(hashA.equals(hashB));
	}

	@Test
	public void test4 () {
// DesktopSetup.deploy();
		final StringHasher hashA = new StringHasher();
		final StringHasher hashB = new StringHasher();
		hashA.appendRight("ABCD");
		hashB.appendLeft("ABC");
		hashA.discardRight('D');

// hashB.discardRight('D');

		L.d(hashA);
		L.d(hashB);
		assertTrue(hashA.equals(hashB));
	}

	@Test
	public void test5 () {

		final StringHasher hashA = new StringHasher();
		final StringHasher hashB = new StringHasher();
		hashA.appendRight("ABCD");
		hashB.appendLeft("BCD");
		hashA.discardLeft('A');

// hashB.discardRight('D');

		L.d(hashA);
		L.d(hashB);
		assertTrue(hashA.equals(hashB));
	}

	@Test
	public void test3 () {

		final StringHasher hashA = new StringHasher();
		final StringHasher hashB = new StringHasher();
		hashA.appendRight("ABCD");
		hashB.appendLeft("ABCD");

		L.d(hashA);
		L.d(hashB);
		assertTrue(hashA.equals(hashB));
	}

	@Test
	public void testPQ () {
		DesktopSetup.deploy();
		final StringHasher hashA = new StringHasher();
		L.d(hashA.powQ(this.P - 1));

	}

	public static void main (final String[] args) {
		DesktopSetup.deploy();

		final String A = "ABCD";
		final String B = "DABC";
		final int len = A.length();
		final StringHasher hashA = new StringHasher();
		L.d("hashA", hashA);
		hashA.appendLeft("CCBB");
		L.d("hashA", hashA);
		hashA.discardLeft("CCBB");
		L.d("hashA", hashA);

// hasher.appendRight("ABC");
	}

}
