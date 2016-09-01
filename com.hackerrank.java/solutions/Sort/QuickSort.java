
package Sort;

import java.util.Arrays;

import com.google.java.i.Solution.L;

public class QuickSort {

	public static void main (final String[] args) {

		final int[] arrayA = newArray(5);
		final int[] arrayB = copy(arrayA);
		L.d("equal", equal(arrayA, arrayB));
		Arrays.sort(arrayB);

		sort(arrayA);

		L.d(Arrays.toString(arrayA));
		L.d(Arrays.toString(arrayB));

		L.d("equal", equal(arrayA, arrayB));

	}

	private static boolean equal (final int[] a, final int[] b) {
		return Arrays.equals(a, b);
	}

	private static void sort (final int[] array) {
		final int fromIndex = 0;
		final int toIndex = array.length - 1;
		sort(array, fromIndex, toIndex);

	}

	private static void sort (final int[] array, final int fromIndex, final int toIndex) {
// L.d("sort " + fromIndex, toIndex);
// L.d(Arrays.toString(array));
		final int numberOfElements = toIndex - fromIndex + 1;

		if (numberOfElements <= 1) {
			return;
		}

		if (numberOfElements == 2) {
			if (array[fromIndex] >= array[toIndex]) {
				swap(fromIndex, toIndex, array);
			}
			return;
		}

// int middleIndex = fromIndex + (toIndex - fromIndex) / 2;

		final int pivot = array[fromIndex];
// L.d("pivot", pivot);
		int leftIndex = fromIndex + 1;
		int rightIndex = toIndex;

		while (leftIndex <= rightIndex) {
			final int leftValue = array[leftIndex];
			if (leftValue < pivot) {
				leftIndex++;
			} else {
				swap(leftIndex, rightIndex, array);
				rightIndex--;
// L.d(leftValue + " > " + Arrays.toString(array));
			}
		}

		final int middleIndex = rightIndex;
		swap(fromIndex, middleIndex, array);
// L.d(" i " + Arrays.toString(array));
		sort(array, fromIndex, middleIndex);
		sort(array, middleIndex + 1, toIndex);

	}

	private static void swap (final int x, final int y, final int[] array) {
		final int X = array[x];
		final int Y = array[y];
		array[x] = Y;
		array[y] = X;
	}

	public static int[] copy (final int[] arrayA) {
		return Arrays.copyOf(arrayA, arrayA.length);
	}

	public static int[] newArray (final int size) {
		final int[] result = new int[size];
		final java.util.Random r = new java.util.Random(0);
		for (int i = 0; i < size; i++) {
			result[i] = r.nextInt() * 1;
		}
		return result;
	}

}
