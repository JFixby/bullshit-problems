
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
		L.d("sort " + fromIndex, toIndex);
		if (fromIndex >= toIndex) {
			return;
		}

// int middleIndex = fromIndex + (toIndex - fromIndex) / 2;

		final int pivot = array[fromIndex];

		int leftIndex = fromIndex;
		int rightIndex = toIndex;

		while (leftIndex < rightIndex) {
			final int leftValue = array[leftIndex];
			if (leftValue < pivot) {
				leftIndex++;
			} else {
				swap(leftIndex, rightIndex, array);
				rightIndex--;
			}
		}

		final int middleIndex = leftIndex;

		sort(array, fromIndex, middleIndex);
		sort(array, middleIndex, toIndex);

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
			result[i] = r.nextInt();
		}
		return result;
	}

}
