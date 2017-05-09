
package com.google.java.sort;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Random;

import org.junit.Test;

import com.jfixby.scarabei.api.desktop.ScarabeiDesktop;

public class InsertAndMergeSort {

	public static void main (final String[] args) {

		ScarabeiDesktop.deploy();

	}

	@Test
	public void testReverse () {
		ScarabeiDesktop.deploy();
		assertTrue(this.checkSorting(new byte[] {}));
		assertTrue(this.checkSorting(new byte[] {1}));
		assertTrue(this.checkSorting(new byte[] {1, 2}));
		assertTrue(this.checkSorting(new byte[] {2, 1}));
		assertTrue(this.checkSorting(new byte[] {1, 2, 1}));
		assertTrue(this.checkSorting(randomArray(10)));
		for (int k = 0; k < 100; k++) {
			assertTrue(this.checkSorting(randomArray(k)));
		}

	}

	private boolean checkSorting (final byte[] list) {
// System.out.println("checkSorting " + "(" + Arrays.toString(list) + ")");

		final byte[] java_array = this.copy(list);
		final byte[] merge_array = this.copy(list);

		Arrays.sort(java_array);
// this.insertSort(merge_array);
		this.heapSort(merge_array);

		final boolean areEqual = Arrays.equals(java_array, merge_array);
		if (!areEqual) {
			System.out.println("Failed to sort " + "(" + Arrays.toString(list) + ")");
			System.out.println("        result" + "(" + Arrays.toString(merge_array) + ")");
			System.out.println("     should be" + "(" + Arrays.toString(java_array) + ")");
		} else {
// System.out.println("Success to sort " + "(" + Arrays.toString(list) + ")");
// System.out.println(" result" + "(" + Arrays.toString(merge_array) + ")");
// System.out.println(" should be" + "(" + Arrays.toString(java_array) + ")");
// System.out.println();
		}
		return areEqual;
	}

	private void heapSort (final byte[] array) {
		final Heap heap = new Heap(array.length);
		for (int i = 0; i < array.length; i++) {
			heap.add(array[i]);
		}

// heap.print();
// Sys.exit();
		for (int i = 0; i < array.length; i++) {
			array[array.length - i - 1] = heap.remove();
		}
	}

	private void mergeSort (final byte[] merge_array) {
		final byte[] buffer = new byte[merge_array.length / 2 + 1];
		this.mergeSort(merge_array, 0, merge_array.length - 1, buffer);
	}

	private void mergeSort (final byte[] array, final int from, final int to, final byte[] buffer) {
		final int len = to - from + 1;
		if (len < 2) {
			return;
		}
		if (len == 2) {
			if (array[from] > array[to]) {
				this.swap(from, to, array);
			}
			return;
		}

		final int middle = (int)((long)from + to) / 2;

		this.mergeSort(array, from, middle, buffer);
		this.mergeSort(array, middle + 1, to, buffer);
		this.merge(array, from, middle, to, buffer);
	}

	private void merge (final byte[] array, final int from, final int middle, final int to, final byte[] buffer) {
		this.copy(array, from, middle, buffer);
		final int bufferSize = middle - from + 1;
		int indexA = 0;
		int indexB = middle + 1;
		int indexM = from;
		while (indexA < bufferSize && indexB <= to) {
			final byte a = buffer[indexA];
			final byte b = array[indexB];
			if (a < b || indexA == bufferSize) {
				array[indexM] = a;
				indexA++;
				indexM++;
			} else {
				array[indexM] = b;
				indexB++;
				indexM++;
			}
		}

		while (indexA < bufferSize) {
			final byte a = buffer[indexA];
			{
				array[indexM] = a;
				indexA++;
				indexM++;
			}
		}

	}

	private void copy (final byte[] array, final int from, final int middle, final byte[] buffer) {
		int k = 0;
		for (int i = from; i <= middle; i++) {
			buffer[k] = array[i];
			k++;
		}
	}

	private void insertSort (final byte[] array) {
		if (array.length == 0) {
			return;
		}
		if (array.length == 1) {
			return;
		}

		for (int key = 1; key < array.length; key++) {
			this.swapAndShift(key, array);
		}
	}

	private void swapAndShift (final int x, final byte[] array) {
		for (int i = x; i > 0; i--) {
			if (array[i - 1] < array[i]) {
				return;
			}
			this.swap(i - 1, i, array);
			System.out.println("swap" + "(" + Arrays.toString(array) + ")");
		}
	}

	private void swap (final int x, final int y, final byte[] array) {
		final byte tmp = array[x];
		array[x] = array[y];
		array[y] = tmp;
	}

	private byte[] copy (final byte[] src) {
		final byte[] copy = new byte[src.length];
		System.arraycopy(src, 0, copy, 0, src.length);
		return copy;
	}

	public static byte[] randomArray (final int N) {
		final byte[] random = new byte[N]; // create the Array with N slots
		final Random rnd = new Random(); // create a local variable for Random
		for (int i = 0; i < random.length; i++) // filling with randoms
		{
			random[i] = ((byte)rnd.nextInt());
		}
		return random;
	}

}
