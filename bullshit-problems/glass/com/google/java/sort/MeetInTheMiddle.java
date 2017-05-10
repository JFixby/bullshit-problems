
package com.google.java.sort;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import org.junit.Test;

import com.jfixby.scarabei.api.desktop.ScarabeiDesktop;
import com.jfixby.scarabei.api.log.L;

public class MeetInTheMiddle {

	@Test
	public void test () {
		ScarabeiDesktop.deploy();
		assertTrue(this.checkSorting(new byte[] {}));
		assertTrue(this.checkSorting(new byte[] {0}));
		assertTrue(this.checkSorting(new byte[] {1}));
		assertTrue(this.checkSorting(new byte[] {0, 1}));
		assertTrue(this.checkSorting(new byte[] {1, 0}));
		assertTrue(this.checkSorting(new byte[] {1, 1, 1, 0}));
		assertTrue(this.checkSorting(new byte[] {1, 1, 1, 1}));
		assertTrue(this.checkSorting(new byte[] {0, 0, 1, 0}));
// assertTrue(this.checkSorting(new byte[] {1, 2, 1}));
// assertTrue(this.checkSorting(new byte[] {3, 2, 1, 0}));
// assertTrue(this.checkSorting(new byte[] {0, 1, 2, 3}));
// assertTrue(this.checkSorting(new byte[] {52, -3, -49}));
// assertTrue(this.checkSorting(randomArray(10)));
// for (int k = 0; k < 100; k++) {
// assertTrue(this.checkSorting(randomArray(k)));
// }

	}

	public static byte[] randomArray (final int N) {
		final byte[] random = new byte[N]; // create the Array with N slots
		final Random rnd = new Random(); // create a local variable for Random
		for (int i = 0; i < random.length; i++) // filling with randoms
		{
			random[i] = ((byte)rnd.nextInt());
			if (random[i] == 0) {
				random[i] = 1;
			}
		}
		return random;
	}

	private boolean checkSorting (final byte[] array) {
		L.d("input" + "(" + Arrays.toString(array) + ")");
		final int numOf0 = array.length / 5 + 1;
		int zeros = 0;
		final ArrayList<Byte> list1 = new ArrayList<>();
		for (int i = 0; i < array.length; i++) {
// if (this.isZero(i, numOf0)) {
// array[i] = 0;
// zeros++;
// }
			if (array[i] == 0) {
				zeros++;
			}
			list1.add(array[i]);
		}
		list1.sort( (x, y) -> Byte.compare(x, y));
// L.d("sorted input: " + zeros, list1);

		this.midSort(array);

		final ArrayList<Byte> list2 = new ArrayList<>();
		for (int i = 0; i < array.length; i++) {
			list2.add(array[i]);
		}
		list2.sort( (x, y) -> Byte.compare(x, y));

		final boolean eq = this.equalArrays(list1, list2) && this.firstZeroes(array, zeros);

		if (!eq || true) {
			L.d("zeros", zeros);
			L.d("list1", list1.toString());
			L.d("list2", list2.toString());
			L.d("result" + "(" + Arrays.toString(array) + ")");
			L.d();
		}

		return eq;
	}

	private boolean firstZeroes (final byte[] array, final int zeros) {
		for (int i = 0; i < array.length; i++) {
			final byte val = array[i];
			if (i < zeros) {
				if (val != 0) {
					return false;
				}
			} else {
				if (val == 0) {
					return false;
				}
			}
		}
		return true;
	}

	private void midSort (final byte[] array) {
		int l = 0;
		int r = array.length - 1;
		while (l < r) {
			if (array[l] != 0) {
				this.swap(array, l, r);
				r--;
			} else {
				l++;
			}
		}
	}

	private void swap (final byte[] array, final int l, final int r) {
		final byte tmp = array[l];
		array[l] = array[r];
		array[r] = tmp;
	}

	private boolean equalArrays (final ArrayList<Byte> list1, final ArrayList<Byte> list2) {
		if (list1.size() != list2.size()) {
			return false;
		}
		for (int i = 0; i < list1.size(); i++) {
			if (list1.get(i) != list2.get(i)) {
				return false;
			}
		}
		return true;
	}

	private boolean isZero (final int i, final int n) {
		return i % n == 0;
	}

}
