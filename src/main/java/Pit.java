public class Pit {
	
	private int counter;
	
	/**
	 * creates a new Pit object with a defined number of stones in it
	 * @param initAmount initial amount of stones to put in the pit
	 */
	public Pit(int initAmount) {
		if(initAmount < 0)
			throw new IllegalArgumentException("Initial amount can't be negative");
		counter = initAmount;
	}
	
	/**
	 * returns current amount of stones in the pit
	 * @return current amount of stones
	 */
	int getCount() {
		return counter;
	}
	
	/**
	 * sets the amount of stones in pit to zero
	 */
	void purge() {
		counter = 0;
	}
	
	/**
	 * adds additional stone to current amount
	 */
	void addStone() {
		counter++;
	}
	/**
	 * adds defined amount of stones to current amount
	 * @param amount number of stones to add
	 */
	void addStones(int amount) {
		counter += amount;
	}
}
