
package com.google.java;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BoundedKnapsackForTourists {

	public static void main (final String[] args) {
		new BoundedKnapsackForTourists();
	}

	public BoundedKnapsackForTourists () {
		final BoundedKnapsack bok = new BoundedKnapsack(250); // 400 dkg = 400 dag = 4 kg

		// making the list of items that you want to bring
		bok.add("map", 9, 150, 1);
		bok.add("compass", 13, 35, 1);
		bok.add("water", 153, 200, 3);
		bok.add("sandwich", 50, 60, 2);
		bok.add("glucose", 15, 60, 2);
		bok.add("tin", 68, 45, 3);
		bok.add("banana", 27, 60, 3);
		bok.add("apple", 39, 40, 3);
		bok.add("cheese", 23, 30, 1);
		bok.add("beer", 52, 10, 3);
		bok.add("suntan cream", 11, 70, 1);
		bok.add("camera", 32, 30, 1);
		bok.add("t-shirt", 24, 15, 2);
		bok.add("trousers", 48, 10, 2);
		bok.add("umbrella", 73, 40, 1);
		bok.add("waterproof trousers", 42, 70, 1);
		bok.add("waterproof overclothes", 43, 75, 1);
		bok.add("note-case", 22, 80, 1);
		bok.add("sunglasses", 7, 20, 1);
		bok.add("towel", 18, 12, 2);
		bok.add("socks", 4, 50, 1);
		bok.add("book", 30, 10, 2);

		// calculate the solution:
		final List<Item> itemList = bok.calcSolution();

		// write out the solution in the standard output
		if (bok.isCalculated()) {
			final NumberFormat nf = NumberFormat.getInstance();

			System.out.println("Maximal weight           = " + nf.format(bok.getMaxWeight() / 100.0) + " kg");
			System.out.println("Total weight of solution = " + nf.format(bok.getSolutionWeight() / 100.0) + " kg");
			System.out.println("Total value              = " + bok.getProfit());
			System.out.println();
			System.out.println("You can carry te following materials " + "in the knapsack:");
			for (final Item item : itemList) {
				if (item.getInKnapsack() > 0) {
					System.out.format("%1$-10s %2$-23s %3$-3s %4$-5s %5$-15s \n", item.getInKnapsack() + " unit(s) ", item.getName(),
						item.getInKnapsack() * item.getWeight(), "dag  ", "(value = " + item.getInKnapsack() * item.getValue() + ")");
				}
			}
		} else {
			System.out.println("The problem is not solved. " + "Maybe you gave wrong data.");
		}

	}

} // static class

class BoundedKnapsack extends ZeroOneKnapsack {
	public BoundedKnapsack () {
	}

	public BoundedKnapsack (final int _maxWeight) {
		this.setMaxWeight(_maxWeight);
	}

	public BoundedKnapsack (final List<Item> _itemList) {
		this.setItemList(_itemList);
	}

	public BoundedKnapsack (final List<Item> _itemList, final int _maxWeight) {
		this.setItemList(_itemList);
		this.setMaxWeight(_maxWeight);
	}

	@Override
	public List<Item> calcSolution () {
		final int n = this.itemList.size();

		// add items to the list, if bounding > 1
		for (int i = 0; i < n; i++) {
			final Item item = this.itemList.get(i);
			if (item.getBounding() > 1) {
				for (int j = 1; j < item.getBounding(); j++) {
					this.add(item.getName(), item.getWeight(), item.getValue());
				}
			}
		}

		super.calcSolution();

		// delete the added items, and increase the original items
		while (this.itemList.size() > n) {
			final Item lastItem = this.itemList.get(this.itemList.size() - 1);
			if (lastItem.getInKnapsack() == 1) {
				for (int i = 0; i < n; i++) {
					final Item iH = this.itemList.get(i);
					if (lastItem.getName().equals(iH.getName())) {
						iH.setInKnapsack(1 + iH.getInKnapsack());
						break;
					}
				}
			}
			this.itemList.remove(this.itemList.size() - 1);
		}

		return this.itemList;
	}

	// add an item to the item list
	public void add (String name, final int weight, final int value, final int bounding) {
		if (name.equals("")) {
			name = "" + (this.itemList.size() + 1);
		}
		this.itemList.add(new Item(name, weight, value, bounding));
		this.setInitialStateForCalculation();
	}
} // static class

class ZeroOneKnapsack {
	protected List<Item> itemList = new ArrayList<Item>();
	protected int maxWeight = 0;
	protected int solutionWeight = 0;
	protected int profit = 0;
	protected boolean calculated = false;

	public ZeroOneKnapsack () {
	}

	public ZeroOneKnapsack (final int _maxWeight) {
		this.setMaxWeight(_maxWeight);
	}

	public ZeroOneKnapsack (final List<Item> _itemList) {
		this.setItemList(_itemList);
	}

	public ZeroOneKnapsack (final List<Item> _itemList, final int _maxWeight) {
		this.setItemList(_itemList);
		this.setMaxWeight(_maxWeight);
	}

	// calculte the solution of 0-1 knapsack problem with dynamic method:
	public List<Item> calcSolution () {
		final int n = this.itemList.size();

		this.setInitialStateForCalculation();
		if (n > 0 && this.maxWeight > 0) {
			final List<List<Integer>> c = new ArrayList<List<Integer>>();
			List<Integer> curr = new ArrayList<Integer>();

			c.add(curr);
			for (int j = 0; j <= this.maxWeight; j++) {
				curr.add(0);
			}
			for (int i = 1; i <= n; i++) {
				final List<Integer> prev = curr;
				c.add(curr = new ArrayList<Integer>());
				for (int j = 0; j <= this.maxWeight; j++) {
					if (j > 0) {
						final int wH = this.itemList.get(i - 1).getWeight();
						curr
							.add((wH > j) ? prev.get(j) : Math.max(prev.get(j), this.itemList.get(i - 1).getValue() + prev.get(j - wH)));
					} else {
						curr.add(0);
					}
				} // for (j...)
			} // for (i...)
			this.profit = curr.get(this.maxWeight);

			for (int i = n, j = this.maxWeight; i > 0 && j >= 0; i--) {
				final int tempI = c.get(i).get(j);
				final int tempI_1 = c.get(i - 1).get(j);
				if ((i == 0 && tempI > 0) || (i > 0 && tempI != tempI_1)) {
					final Item iH = this.itemList.get(i - 1);
					final int wH = iH.getWeight();
					iH.setInKnapsack(1);
					j -= wH;
					this.solutionWeight += wH;
				}
			} // for()
			this.calculated = true;
		} // if()
		return this.itemList;
	}

	// add an item to the item list
	public void add (String name, final int weight, final int value) {
		if (name.equals("")) {
			name = "" + (this.itemList.size() + 1);
		}
		this.itemList.add(new Item(name, weight, value));
		this.setInitialStateForCalculation();
	}

	// add an item to the item list
	public void add (final int weight, final int value) {
		this.add("", weight, value); // the name will be "itemList.size() + 1"!
	}

	// remove an item from the item list
	public void remove (final String name) {
		for (final Iterator<Item> it = this.itemList.iterator(); it.hasNext();) {
			if (name.equals(it.next().getName())) {
				it.remove();
			}
		}
		this.setInitialStateForCalculation();
	}

	// remove all items from the item list
	public void removeAllItems () {
		this.itemList.clear();
		this.setInitialStateForCalculation();
	}

	public int getProfit () {
		if (!this.calculated) {
			this.calcSolution();
		}
		return this.profit;
	}

	public int getSolutionWeight () {
		return this.solutionWeight;
	}

	public boolean isCalculated () {
		return this.calculated;
	}

	public int getMaxWeight () {
		return this.maxWeight;
	}

	public void setMaxWeight (final int _maxWeight) {
		this.maxWeight = Math.max(_maxWeight, 0);
	}

	public void setItemList (final List<Item> _itemList) {
		if (_itemList != null) {
			this.itemList = _itemList;
			for (final Item item : _itemList) {
				item.checkMembers();
			}
		}
	}

	// set the member with name "inKnapsack" by all items:
	private void setInKnapsackByAll (final int inKnapsack) {
		for (final Item item : this.itemList) {
			if (inKnapsack > 0) {
				item.setInKnapsack(1);
			} else {
				item.setInKnapsack(0);
			}
		}
	}

	// set the data members of static class in the state of starting the calculation:
	protected void setInitialStateForCalculation () {
		this.setInKnapsackByAll(0);
		this.calculated = false;
		this.profit = 0;
		this.solutionWeight = 0;
	}
} // static class

class Item {
	protected String name = "";
	protected int weight = 0;
	protected int value = 0;
	protected int bounding = 1; // the maximal limit of item's pieces
	protected int inKnapsack = 0; // the pieces of item in solution

	public Item () {
	}

	public Item (final Item item) {
		this.setName(item.name);
		this.setWeight(item.weight);
		this.setValue(item.value);
		this.setBounding(item.bounding);
	}

	public Item (final int _weight, final int _value) {
		this.setWeight(_weight);
		this.setValue(_value);
	}

	public Item (final int _weight, final int _value, final int _bounding) {
		this.setWeight(_weight);
		this.setValue(_value);
		this.setBounding(_bounding);
	}

	public Item (final String _name, final int _weight, final int _value) {
		this.setName(_name);
		this.setWeight(_weight);
		this.setValue(_value);
	}

	public Item (final String _name, final int _weight, final int _value, final int _bounding) {
		this.setName(_name);
		this.setWeight(_weight);
		this.setValue(_value);
		this.setBounding(_bounding);
	}

	public void setName (final String _name) {
		this.name = _name;
	}

	public void setWeight (final int _weight) {
		this.weight = Math.max(_weight, 0);
	}

	public void setValue (final int _value) {
		this.value = Math.max(_value, 0);
	}

	public void setInKnapsack (final int _inKnapsack) {
		this.inKnapsack = Math.min(this.getBounding(), Math.max(_inKnapsack, 0));
	}

	public void setBounding (final int _bounding) {
		this.bounding = Math.max(_bounding, 0);
		if (this.bounding == 0) {
			this.inKnapsack = 0;
		}
	}

	public void checkMembers () {
		this.setWeight(this.weight);
		this.setValue(this.value);
		this.setBounding(this.bounding);
		this.setInKnapsack(this.inKnapsack);
	}

	public String getName () {
		return this.name;
	}

	public int getWeight () {
		return this.weight;
	}

	public int getValue () {
		return this.value;
	}

	public int getInKnapsack () {
		return this.inKnapsack;
	}

	public int getBounding () {
		return this.bounding;
	}
} // static class
