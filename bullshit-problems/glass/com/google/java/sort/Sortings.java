
package com.google.java.sort;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Random;

import org.junit.Test;

public class Sortings {

	public static void main (final String[] args) {

// ScarabeiDesktop.deploy();

	}

	@Test
	public void test () {
// ScarabeiDesktop.deploy();
		assertTrue(this.checkSorting(new byte[] {}));
		assertTrue(this.checkSorting(new byte[] {1}));
		assertTrue(this.checkSorting(new byte[] {1, 2}));
		assertTrue(this.checkSorting(new byte[] {2, 1}));
		assertTrue(this.checkSorting(new byte[] {1, 2, 1}));
		assertTrue(this.checkSorting(new byte[] {3, 2, 1, 0}));
		assertTrue(this.checkSorting(new byte[] {0, 1, 2, 3}));
		assertTrue(this.checkSorting(new byte[] {52, -3, -49}));
		assertTrue(this.checkSorting(randomArray(10)));
		for (int k = 0; k < 100; k++) {
			assertTrue(this.checkSorting(randomArray(k)));
		}

	}

	private boolean checkSorting (final byte[] list) {
// System.out.println("checkSorting " + "(" + Arrays.toString(list) + ")");

		final byte[] check_array = this.copy(list);
		final byte[] target_array = this.copy(list);

		Arrays.sort(check_array);
// this.countingSort(target_array);// uncomment this for testing
		this.radixSort(target_array);// uncomment this for testing
// this.insertSort(target_array);// uncomment this for testing
// this.heapSort(target_array);// uncomment this for testing

		final boolean areEqual = Arrays.equals(check_array, target_array);
		if (!areEqual) {
			System.out.println("Failed to sort " + "(" + Arrays.toString(list) + ")");
			System.out.println("        result" + "(" + Arrays.toString(target_array) + ")");
			System.out.println("     should be" + "(" + Arrays.toString(check_array) + ")");
		} else {
// System.out.println("Success to sort " + "(" + Arrays.toString(list) + ")");
// System.out.println(" result" + "(" + Arrays.toString(target_array) + ")");
// System.out.println(" should be" + "(" + Arrays.toString(check_array) + ")");
// System.out.println();
		}
		return areEqual;
	}

	private void radixSort (final byte[] bytes) {
		if (bytes.length < 2) {
			return;
		}
		byte min = Byte.MAX_VALUE;
		for (int i = 0; i < bytes.length; i++) {
			min = this.min(bytes[i], min);
		}

		final IntOrdered[] toSort = new IntOrdered[bytes.length];

		for (int i = 0; i < bytes.length; i++) {
			final int order = bytes[i] - min;
			toSort[i] = new IntOrdered() {
				final int value = order;

				@Override
				public String toString () {
					return this.value + "";
				}

				@Override
				public int order (final int mode) {

					if (mode == -1) {
						return order;
					}
					final int digit = digitAt(order, mode);

					return digit;

				}
			};
		}
		final int counterSize = 10;

		this.countingSort(toSort, counterSize, 2);
// System.out.println(" test 2" + "(" + Arrays.toString(toSort) + ")");

		this.countingSort(toSort, counterSize, 1);
// System.out.println(" test 1" + "(" + Arrays.toString(toSort) + ")");

		this.countingSort(toSort, counterSize, 0);
// System.out.println(" test 0" + "(" + Arrays.toString(toSort) + ")");

		for (int i = 0; i < bytes.length; i++) {
			bytes[i] = (byte)(toSort[i].order(-1) + min);
		}
	}

	private void countingSort (final byte[] bytes) {
		if (bytes.length < 2) {
			return;
		}
		byte min = Byte.MAX_VALUE;
		byte max = Byte.MIN_VALUE;
		for (int i = 0; i < bytes.length; i++) {
			min = this.min(bytes[i], min);
			max = this.max(bytes[i], max);
		}

		final IntOrdered[] toSort = new IntOrdered[bytes.length];

		for (int i = 0; i < bytes.length; i++) {
			final int order = bytes[i] - min;
			toSort[i] = mode -> order;
		}
		final int counterSize = max - min + 1;
		this.countingSort(toSort, counterSize, -1);
		for (int i = 0; i < bytes.length; i++) {
			bytes[i] = (byte)(toSort[i].order(-1) + min);
		}
	}

	static private int digitAt (final int order, final int digit) {
		if (digit == 0) {
			return order / 100;
		}
		if (digit == 1) {
			return (order - digitAt(order, 0) * 100) / 10;
		}
		if (digit == 2) {
			return (order - digitAt(order, 0) * 100 - digitAt(order, 1) * 10);
		}
		throw new Error();
	}

	public interface IntOrdered {
		public int order (int mode);
	}

	private void countingSort (final IntOrdered[] output, final int counterSize, final int mode) {
		final IntOrdered[] input = new IntOrdered[output.length];
		System.arraycopy(output, 0, input, 0, output.length);

		final int[] counter = new int[counterSize];
		for (int i = 0; i < input.length; i++) {
			final IntOrdered value = input[i];
			final int key = this.keyOf(value, mode);
			counter[key]++;
		}
		int total = 0;
		for (int i = 0; i < counter.length; i++) {
			final int oldValue = counter[i];
			counter[i] = total;
			total = total + oldValue;
		}
		for (int i = 0; i < input.length; i++) {
			final IntOrdered value = input[i];
			final int key = this.keyOf(value, mode);
			output[counter[key]] = value;
			counter[key]++;
		}

	}

	private int keyOf (final IntOrdered value, final int mode) {
		return value.order(mode);
	}

	private byte max (final byte a, final byte b) {
		return a > b ? a : b;
	}

	private byte min (final byte a, final byte b) {
		return a < b ? a : b;
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
// System.out.println("swap" + "(" + Arrays.toString(array) + ")");
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
