
package com.google.java;

import java.util.ArrayList;
import java.util.Iterator;

import com.jfixby.cmns.api.log.L;
import com.jfixby.red.desktop.DesktopSetup;

public class OddIterator<T> implements Iterator<T> {
// original [0,1,2,3,4,5,6,7,8]
// window {0,X} {2,X}, {4,X}, {6,X}, {8}//over

	private final Iterator<T> origignal;
	int windowSize = 2;
	int indexInWindow = this.windowSize - 1;// +index in window, last
	boolean hasNext = true;
	T nextElement = null;
	// +queue -queue, +windowSize +hasNex -hasNext

	public OddIterator (final Iterator<T> iterator) {
		this.origignal = iterator;
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

	private void throwError () {
	}

	@Override
	public boolean hasNext () {
		// return this.windowContent.size() > this.indexInWindow;// >0,==window size, >=indexInWindow,///optimise by discarding
		// heads?
		// window contains target index
		// return this.windowContent.size() == 1;// >0,==window size, >=indexInWindow,///optimise by discarding heads?
		// window contains target index
		return this.hasNext;
	}

	public static void main (final String[] args) {
		DesktopSetup.deploy();
		final ArrayList<Float> original = new ArrayList<Float>();
		for (int i = 0; i < 10; i++) {
			original.add(i * 1f);
		}

		for (final Iterator<Float> it = original.iterator(); it.hasNext();) {
			L.d("x = " + it.next());
		}

		for (final Iterator<Float> it = wrap(original.iterator()); it.hasNext();) {
			L.d("x = " + it.next());
		}

	}

	private static Iterator<Float> wrap (final Iterator<Float> iterator) {
		return new OddIterator<Float>(iterator);
	}

}
