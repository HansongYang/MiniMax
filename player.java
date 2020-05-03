package assignment2;

public class player {
	private int mancalaOne;
	private int mancalaTwo;
	protected int [] stones;
		
	public int getStones(int index) {
		return stones[index];
	}
	
	public void startUp(int num) {
		for(int i = 0; i < stones.length; i++) {
			stones[i] = num;
		}
	}
	
	public void setStones(int index, int value) {
		stones[index] = value;
	}
	
	public int getMancalaOne() {
		return mancalaOne;
	}
	
	public int getMancalaTwo() {
		return mancalaTwo;
	}
	
	public int getTotalPoint() {
		return getMancalaOne() + getMancalaTwo();
	}
	
	public void setMancalaOne(int mancala) {
		mancalaOne = mancala;
	}
	
	public void setMancalaTwo(int mancala) {
		mancalaTwo = mancala;
	}
}
