public class Player {
	
	private Pit[] pits;
	
	/**
	 * base constructor for the Player class
	 * @param pitCount amount of small pits
	 * @param stoneCount initial amount of stones in each small pit
	 */
	public Player(int pitCount, int stoneCount) {
		pits = new Pit[pitCount + 1];
		// initialize each small pit 
		for(int i = 0; i < pits.length - 1; i++) {
			pits[i] = new Pit(stoneCount);
		}
		// "Big" pits are initialized empty
		pits[pits.length - 1] = new Pit(0);
	}
	
	/**
	 * checks if a pit is empty
	 * @param idx index of the pit to check
	 * @return true if empty, false otherwise
	 */
	public boolean isPitEmpty(int idx) {
		return pits[idx].getCount() == 0;
	}
	
	/**
	 * perform a seed operation for a Player
	 * @param idx index of a Pit chosen for seeding
	 */
	public void seed(int idx) {
		int count = pits[idx].getCount();
		pits[idx].purge();
		int seedIdx = idx;
		while (count > 0) {
			seedIdx++;
			// ring the seeding order if arriving at the end of array
			seedIdx = seedIdx % pits.length;
			pits[seedIdx].addStone();
			count--;
		}
	}
	
	/**
	 * empty a pit and return the amount of stones that it held
	 * @param idx index of pit to loose
	 * @return
	 */
	public int loosePitStones(int idx) {
		int count = pits[idx].getCount();
		pits[idx].purge();
		return count;
	}
	
	/**
	 * adds an amount of stones to chosen pit
	 * @param idx index of pit that receives the stones
	 * @param amount amount of stones to add
	 */
	public void capturePitStones(int idx, int amount) {
		pits[idx].addStones(amount);
	}
	
	/**
	 * returns amount of stones in chosen pit
	 * @param idx index of a pit being checked
	 * @return amount of stones in chosen pit
	 */
	public int getStoneAmount(int idx) {
		return pits[idx].getCount();
	}
	
	/**
	 * checks if Player can make another move
	 * @return true if at least one of player pits is not empty, false otherwise 
	 */
	public boolean canPerformAction() {
		//if there is at least one non empty pit - player can make a move
		for(int i = 0; i < pits.length - 1; i++) {
			if(pits[i].getCount() > 0) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * prints current state of Player layout to the console
	 */
	public void printCurrentState() {
		for (int i = 0; i < pits.length; i++) {
			System.out.print(pits[i].getCount() + " ");
		}
	}
	
	public int getTotalCount() {
		int sum = 0;
		for(Pit p : pits) {
			sum += p.getCount();
		}
		return sum;		
	}
}
