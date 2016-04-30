
package Hanoi;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Vector;

import com.jfixby.hrank.AbstractSolution;

public class Solution extends AbstractSolution {

	@Override
	public void run (final String[] args) {
		main(args);
	}

	static class Disk {

// static HashMap<Integer, Disk> ALL_DISKS = new HashMap<Integer, Disk>();

		private final int size;

		public Disk (final int size) {
			this.size = size;
// if (ALL_DISKS.containsKey(size)) {
// throw new Error(ALL_DISKS.toString());
// }
// ALL_DISKS.put(size, this);
		}

		@Override
		public String toString () {
			if (this.currentTower == null) {
				throw new Error("Bad disk: " + this.size);
			}
			return "(" + this.size + ")[" + this.currentTower.name + "]";
		}

		Tower currentTower;

// public static Tower towerOf (final int diskSize) {
// final Disk disk = ALL_DISKS.get(diskSize);
// if (disk == null) {
// throw new Error("Bad disk: " + diskSize);
// }
// return disk.currentTower;
// }

		public static int indexToSize (final int i) {
			return i + 1;
		}

		public static int sizeToIndex (final int diskSize) {
			return diskSize - 1;
		}

	}

	static class Tower {
		public Tower (final int number, final String name) {
			this.number = number;
			this.name = name;

		}

		final Vector<Disk> disks = new Vector<Disk>();

		final int number;
		final String name;

		@Override
		public String toString () {
			return "Tower<" + this.name + "> disks=" + this.disks;
		}

		public void fill (final int numberOfDisks) {
			for (int i = 0; i < numberOfDisks; i++) {
				final int size = numberOfDisks - 1 - i + 1;
				final Disk disk = new Disk(size);
				this.addDisk(disk);
			}
		}

		public Disk removeTopDisk () {
			final Disk disk = this.disks.remove(0);
			disk.currentTower = this;
			return disk;
		}

		public void addDisk (final Disk disk) {
			if (this.willCorrupt(disk.size)) {
				throw new Error(this.corruptMessage(disk));
			}

			disk.currentTower = this;
			this.disks.insertElementAt(disk, 0);

		}

		private String corruptMessage (final Disk disk) {
			return "Tower is corrupted: " + this + " , disk=" + disk;
		}

		public boolean willCorrupt (final int size) {
			if (this.disks.size() > 0) {
				final Disk nextDisk = this.disks.get(0);
				if (nextDisk.size < size) {
					return true;
				}
			}
			return false;
		}

		public void insertDisk (final Disk disk) {
			disk.currentTower = this;
			this.disks.add(disk);

		}

		public static int nameToIndex (final int diskName) {
			return diskName - 1;
		}

		public int indexOfDiskSize (final int diskSize) {
			for (int i = 0; i < this.disks.size(); i++) {
				final Disk disk = this.disks.get(i);
				if (disk.size == diskSize) {
					return i;
				}
			}
			return -1;
		}

	}

	static class Towers {

		private final Tower[] array;

		public Towers (final Tower[] array) {
			this.array = array;

		}

		public Tower getTowerByName (final int towerName) {
			final int towerIndex = Tower.nameToIndex(towerName);
			final Tower targetTower = this.array[towerIndex];
			return targetTower;
		}

		public void print () {
			log("---Towers------------------");
			for (int i = 0; i < this.array.length; i++) {
				final Tower tower = this.array[i];
				log("   " + tower);
			}
			log("---------------------------");
		}

		public Tower towerOf (final int diskSize) {
			for (int i = 0; i < this.array.length; i++) {
				final Tower tower = this.array[i];
				final int index = tower.indexOfDiskSize(diskSize);
				if (index != -1) {
					return tower;
				}
			}
			return null;
		}
	}

	static class History {

		static class Move {

			@Override
			public String toString () {
				return "Move [disk=" + this.disk + ", from=" + this.from.name + ", to=" + this.to.name + "]";
			}

			private final Tower from;
			private Tower to;
			private final Disk disk;

			public Move (final Tower from, final Tower to, final Disk disk) {
				this.from = from;
				this.to = to;
				this.disk = disk;
			}

		}

		final ArrayList<Move> steps = new ArrayList<Move>();

		public void addMove (final Tower from, final Tower to, final Disk disk) {
			final Move nextMove = new Move(from, to, disk);
// if (this.steps.size() > 0) {
// final Move lastMove = this.steps.get(this.size() - 1);
// if (lastMove.disk == nextMove.disk) {
// this.merge(nextMove, lastMove);
// return;
// }
// }

			this.steps.add(nextMove);
		}

		private void merge (final Move next, final Move last) {
			if (next.from != last.to) {
				throw new Error("Failed to merge: " + next + " " + last);
			}
			last.to = next.to;
		}

		public void print () {
			log("---History------------------");
			for (int i = 0; i < this.steps.size(); i++) {
				final Move step = this.steps.get(i);
				log("   " + step);
			}
			log("---------------------------");
		}

		public int size () {
			return this.steps.size();
		}
	}

	public static void main (final String[] args) {
		final Scanner in = new Scanner(input);
		final int numberOfDisks = in.nextInt();

		final Tower A = new Tower(0, "A");
		final Tower B = new Tower(1, "B");
		final Tower C = new Tower(2, "C");
		final Tower D = new Tower(3, "D");
		final Towers towers = new Towers(new Tower[] {A, B, C, D});
		A.fill(numberOfDisks);
// towers.print();

		final int[] targetStateSizeLocation = new int[numberOfDisks];
		for (int i = 0; i < numberOfDisks; i++) {
			final int towerName = in.nextInt();
			final int diskSize = Disk.indexToSize(i);
			targetStateSizeLocation[Disk.sizeToIndex(diskSize)] = towerName;
// final Disk disk = new Disk(diskSize);
// towers[diskLocation].insertDisk(disk);

		}
// log("targetStateSizeLocation=" + Arrays.toString(targetStateSizeLocation));

// move(A, C, numberOfDisks, towers);
		final History history = new History();
		for (int i = 0; i < numberOfDisks; i++) {
			final int diskIndex = numberOfDisks - 1 - i;
			final int diskSize = Disk.indexToSize(diskIndex);
			final Tower towerFrom = towers.towerOf(diskSize);

			final int towerName = targetStateSizeLocation[diskIndex];

			final Tower targetTower = towers.getTowerByName(towerName);

			final int positionInTower = towerFrom.indexOfDiskSize(diskSize);
			move(towerFrom, targetTower, positionInTower + 1, towers, history);
		}
		log(history.size());
		history.print();
// towers.print();

// A.fill(numberOfDisks);
// move(A, C, numberOfDisks, B);
// for (int i = 0; i < numberOfDisks; i++) {
// final int diskIndex = numberOfDisks - 1 - i;
// final int diskSize = Disk.indexToSize(diskIndex);
// final Tower tower = Disk.towerOf(diskSize);
// log("disk:" + diskSize + " is at " + tower);
//// move(A, A, numberOfDisks, B);
// }

// move()

	}

	private static void move (final Tower from, final Tower to, final int numberOfDisks, final Towers towers,
		final History history) {
		if (from == to) {
			return;
		}
		if (numberOfDisks == 1) {

			final Disk disk = from.removeTopDisk();
			if (to.willCorrupt(disk.size)) {
				history.print();
				towers.print();
				throw new Error(to.corruptMessage(disk));
			}

			to.addDisk(disk);
			history.addMove(from, to, disk);
			return;
		}

		if (numberOfDisks == 2) {

			final Tower buffer = findBuffer(towers, from, to);
			final Disk disk1 = from.removeTopDisk();
			buffer.addDisk(disk1);
			history.addMove(from, buffer, disk1);

			final Disk disk2 = from.removeTopDisk();
			to.addDisk(disk2);
			history.addMove(from, to, disk2);

			buffer.removeTopDisk();
			to.addDisk(disk1);
			history.addMove(buffer, to, disk1);

			return;
		}

		if (numberOfDisks > 2) {
			final int disksToMoveTop = numberOfDisks - 2;
			final Tower buffer1 = findBuffer(towers, from, to);
			move(from, buffer1, disksToMoveTop, towers, history);

			final Tower buffer2 = findBuffer(towers, from, to, buffer1);
			move(from, buffer2, 1, towers, history);

			move(buffer1, to, disksToMoveTop, towers, history);

			move(buffer2, to, 1, towers, history);
			return;
		}
	}

	public static <T> boolean contains2 (final T[] array, final T v) {
		if (v == null) {
			for (final T e : array) {
				if (e == null) {
					return true;
				}
			}
		} else {
			for (final T e : array) {
				if (e == v || v.equals(e)) {
					return true;
				}
			}
		}

		return false;
	}

	private static Tower findBuffer (final Towers towers, final Tower... used) {
		for (int i = 0; i < towers.array.length; i++) {
			final int index = towers.array.length - 1 - i;
			final Tower tower = towers.array[index];
			if (contains2(used, tower)) {
				continue;
			}

// tower.disks.get(disksToMove - 1);
			return tower;

		}
		throw new Error("Good tower not found");
	}

}
