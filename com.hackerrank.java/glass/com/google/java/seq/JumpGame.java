
package com.google.java.seq;

import java.util.HashMap;

import com.jfixby.cmns.api.log.L;
import com.jfixby.red.desktop.DesktopSetup;

public class JumpGame {

	private final int[] field;
	private final λHop hops;

	public interface λHop {
		RoadSign solve (int fromIndex, int toIndex);
	}

	public JumpGame (final int[] field) {
		this.field = field;
		this.hops = this.memo(this.setupExpression());
	}

	private λHop memo (final λHop expr) {
		final HashMap<String, RoadSign> cache = new HashMap<String, RoadSign>();
		return (from, to) -> {
			final String key = from + "->" + to;
			RoadSign value = cache.get(key);
			if (value == null) {
				value = expr.solve(from, to);
				cache.put(key, value);
			}
			return value;
		};
	}

	λHop setupExpression () {
		return (from, to) -> {
			RoadSign solution;
			if (from >= to) {
				solution = new RoadSign(from, 0);
			} else {
				final int g = this.field[from];
				solution = new RoadSign(from, Integer.MAX_VALUE);
				for (int k = 1; k <= g; k++) {
					final int via = from + k;
					RoadSign solveK = this.hops.solve(via, to);
					solveK = new RoadSign(via, solveK.distance + 1);
					if (solveK.distance < solution.distance) {
						solution = solveK;
					}
					if (solveK.distance == 0) {
						break;
					}
				}

			}
			L.d("distance from <" + from + "> to <" + to + "> is " + solution.distance + " km");
			return solution;
		};
	}

	static class RoadSign {
		private final int direction;
		private final long distance;

		public RoadSign (final int direction, final long distance) {
			this.direction = direction;// next index
			this.distance = distance;// hops
		}

		@Override
		public String toString () {
			return "=> <" + this.direction + "> (" + this.distance + " km left)";
		}

	}

	public static void main (final String[] args) {
		DesktopSetup.deploy();

		final int[] field = new int[] {3, 4, 1, 1, 0, 2, 2, 0, 1, 1};

		final int startIndex = 0;
		final int endIndex = field.length - 1;

		final JumpGame game = new JumpGame(field);
		final RoadSign direction = game.solve(startIndex, endIndex);
		L.d();
		game.printSolution(startIndex, endIndex, direction);
	}

	private void printSolution (final int startIndex, final int endIndex, RoadSign direction) {
		int currentIndex = startIndex;
		L.d("To get from <" + startIndex + "> to the <" + endIndex + ">");
		while (currentIndex < endIndex) {
			L.d("From <" + currentIndex + "> head to " + direction);
			currentIndex = direction.direction;
			direction = this.solve(currentIndex, endIndex);
		}
	}

	private RoadSign solve (final int startIndex, final int endIndex) {
		return this.hops.solve(startIndex, endIndex);
	}

}
