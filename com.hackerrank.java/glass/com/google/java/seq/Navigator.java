
package com.google.java.seq;

import java.util.HashMap;

import com.jfixby.scarabei.api.desktop.ScarabeiDesktop;
import com.jfixby.scarabei.api.log.L;

public class Navigator {

	public static void main (final String[] args) {
		ScarabeiDesktop.deploy();

		final int[] map = new int[] {3, 4, 1, 1, 0, 2, 2, 0, 1, 1};
// final int[] map = new int[] {2, 2, 2, 1, 1};
// final int[] map = new int[] {2, 3, 1, 1, 4};

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
		navigator.printStats();

		L.d("alt", jump(map));
	}

	static public int jump (final int[] nums) {
		if (nums == null || nums.length == 0) {
			return 0;
		}

		int lastReach = 0;
		int reach = 0;
		int step = 0;

		for (int i = 0; i <= reach && i < nums.length; i++) {
			// when last jump can not read current i, increase the step by 1
			if (i > lastReach) {
				step++;
				lastReach = reach;
			}
			// update the maximal jump
			reach = Math.max(reach, nums[i] + i);
		}

		if (reach < nums.length - 1) {
			return 0;
		}

		return step;
	}

	private void printStats () {
		L.d("calls done", this.CALL);
		L.d("memory used", this.MEMORY);
		int sum = 0;
		for (int i = 0; i < this.map.length; i++) {
			sum = sum + this.map[i];
		}
		L.d("sum", sum);

	}

	private final int[] map;
	private final λ hops;
	private long CALL;
	private long MEMORY;

	public Navigator (final int[] map) {
		this.map = map;
		this.hops = this.memoization(this.setupLazyλExpression());
	}

	public RoadSign getDirection (final int startIndex, final int endIndex) {

		return this.hops.evaluate(startIndex, endIndex);
	}

	λ setupLazyλExpression () {
		return (from, to) -> {
			if (from >= to) {
				return new RoadSign(from, 0);// arrived
			}
			this.CALL++;
			final int mapDirections = this.map[from];

			final RoadSign worstOption = new RoadSign(from, Integer.MAX_VALUE);
			int bestConnection = to;// not from!
			RoadSign bestOption = worstOption;
			for (int k = 1; k <= mapDirections; k++) {
				final int via = from + k;
				final RoadSign option = this.getDirection(via, to);
				if (option.distance < bestOption.distance) {
					bestOption = option;
					bestConnection = via;
					if (bestOption.distance == 0) {
						break;
					}
				}
			}

			final RoadSign solution = new RoadSign(bestConnection, bestOption.distance + 1);
			L.e("    computed distance from <" + from + "> to <" + to + "> is " + solution.distance + " hops");
			return solution;
		};
	}

	λ memoization (final λ expr) {
		final HashMap<String, RoadSign> cache = new HashMap<String, RoadSign>();
		return (from, to) -> {

			final String key = from + "->" + to;
			RoadSign value = cache.get(key);
			if (value == null) {

				value = expr.evaluate(from, to);
				cache.put(key, value);
				L.e("    store", key + " : " + value);
				this.MEMORY++;
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
