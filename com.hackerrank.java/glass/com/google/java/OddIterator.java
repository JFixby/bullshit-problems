
package com.google.java;

import java.util.ArrayList;
import java.util.Iterator;

import com.jfixby.cmns.api.debug.Debug;
import com.jfixby.cmns.api.log.L;
import com.jfixby.red.desktop.DesktopSetup;

public class OddIterator<T> implements Iterator<T> {
// original [0,#1,2,3,>#4,5,6,#7>] window size = 3, index in window == 1 (middle)
//

	private final Iterator<T> origignal;
	int windowSize = 2;
// int indexInWindow = this.windowSize - 1;// +index in window, last
	int indexInWindow = 0;// +index in window, last
	boolean hasNext = true;
	T nextElement = null;
	// +queue -queue, +windowSize +hasNex -hasNext

	public OddIterator (final Iterator<T> iterator, final int windowSize, final int indexInWindow) {
		this.origignal = iterator;
		this.windowSize = windowSize;// check >0
		this.indexInWindow = indexInWindow;

		Debug.checkTrue(this.windowSize > 0);
		Debug.checkTrue(this.indexInWindow >= 0);
		Debug.checkTrue(this.windowSize > this.indexInWindow);

		this.shiftWindow(this.windowSize);
	}

	private void shiftWindow (final int windowSize) {
		this.hasNext = false;
		for (int i = 0; i < windowSize; i++) {// absorb next windowSize or less elements
			if (this.origignal.hasNext()) {
				final T e = this.origignal.next();
				if (i == this.indexInWindow) {
					this.nextElement = e;
					this.hasNext = true;
				}
			} else {
				break;
			}
		}
	}

	@Override
	public T next () {
		if (!this.hasNext()) {// error? throw exceprion?
			this.throwError();
		}
		final T next = this.nextElement;// get0//getlast// O(1)
		this.shiftWindow(this.windowSize);
		return next;
	}

	@Override
	public boolean hasNext () {
		return this.hasNext;
	}

	private void throwError () {
		throw new Error();
	}

	public static void main (final String[] args) {
		DesktopSetup.deploy();
		final ArrayList<Float> original = new ArrayList<Float>();
		for (int i = 0; i < 7; i++) {
			original.add(i * 1f);
		}

		for (final Iterator<Float> it = original.iterator(); it.hasNext();) {
			L.d("x = " + it.next());
		}

		for (final Iterator<Float> it = wrap(original.iterator()); it.hasNext();) {
			L.d("y = " + it.next());
		}

	}

	private static Iterator<Float> wrap (final Iterator<Float> iterator) {
		return new OddIterator<Float>(iterator, 3, 1);
	}

}
