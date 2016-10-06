
package com.google.java.seq;

import java.util.HashMap;

import com.jfixby.cmns.api.log.L;
import com.jfixby.red.desktop.DesktopSetup;

public class Navigator {

	public static void main (final String[] args) {
		DesktopSetup.deploy();

		final int[] map = new int[] {3, 4, 1, 1, 0, 2, 2, 0, 1, 1};

		final int startIndex = 0;
		final int endIndex = map.length - 1;

		final Navigator navigator = new Navigator(map);

		int currentIndex = startIndex;
		L.d("To get from the <" + startIndex + "> to the <" + endIndex + ">");
		while (currentIndex < endIndex) {
			final RoadSign roadSign = navigator.getDirection(currentIndex, endIndex);
			L.d(" from <" + currentIndex + "> follow the sign '" + roadSign + "'");
			currentIndex = roadSign.direction;
		}
	}

	private final int[] map;
	private final λ hops;

	public Navigator (final int[] map) {
		this.map = map;
		this.hops = memoization(this.setupλExpression());
	}

	public RoadSign getDirection (final int startIndex, final int endIndex) {
		return this.hops.evaluate(startIndex, endIndex);
	}

	λ setupλExpression () {
		return (from, to) -> {
			if (from >= to) {
				return new RoadSign(from, 0);// arrived
			}

			final int mapDirections = this.map[from];

			final RoadSign worstOption = new RoadSign(from, Integer.MAX_VALUE);
			int via = to;// not from!
			RoadSign bestOption = worstOption;
			for (int k = 1; k <= mapDirections; k++) {
				final RoadSign option = this.hops.evaluate(from + k, to);
				if (option.distance < bestOption.distance) {
					bestOption = option;
					via = from + k;
				}
			}

			final RoadSign solution = new RoadSign(via, bestOption.distance + 1);
			L.e("    computed distance from <" + from + "> to <" + to + "> is " + solution.distance + " hops");
			return solution;
		};
	}

	static λ memoization (final λ expr) {
		final HashMap<String, RoadSign> cache = new HashMap<String, RoadSign>();
		return (from, to) -> {
			final String key = from + "->" + to;
			RoadSign value = cache.get(key);
			if (value == null) {
				value = expr.evaluate(from, to);
				cache.put(key, value);
			}
			return value;
		};
	}

	public interface λ {
		RoadSign evaluate (int fromIndex, int toIndex);
	}

	public static class RoadSign {
		private final int direction;
		private final long distance;

		public RoadSign (final int direction, final long distance) {
			this.direction = direction;
			this.distance = distance;
		}

		@Override
		public String toString () {
			return "=> <" + this.direction + "> (" + this.distance + " hops left)";
		}
	}

}
