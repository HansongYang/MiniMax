package assignment2;

import java.util.Scanner;

public class humanplayer extends player{
	private boolean turn;
	
	public humanplayer() {
		super.stones = new int[10];
	}
	
	public int getTotalpoint() {
		return super.getMancalaOne() + super.getMancalaTwo();
	}
	
	public void setMancalaOne(int mancala) {
		super.setMancalaOne(mancala);
	}
	
	public void setMancalaTwo(int mancala) {
		super.setMancalaTwo(mancala);
	}
	
	public void setStones(int index, int value) {
		super.setStones(index, value);
	}
	
	public void setTurn(boolean turn){
		this.turn = turn; 
	}
	
	public boolean getTurn() {
		return turn;
	}

	public boolean play(aiPlayer p1, int row, int column, int choice, Scanner sc) {
		int numStones = 0;
		boolean anotherRound = false;
		int[] board = new int[24];
		int index = row;
		
		if(column == 3) {
			if(row > 3) {
				row -= 2;
				numStones = super.stones[row];
				super.stones[row] = 0;
				index -= 1;
			} else {
				row--;
				numStones = super.stones[row];
				super.stones[row] = 0;
				index += 18;
			}
		} else if(column == 4){
			if(row > 4) {
				row = super.stones.length+1-row;
				numStones = super.stones[row];
				super.stones[row] = 0;
				if(row == 5) {
					index = 7;
				} else {
					index = 8;
				}
			} else {
				row = super.stones.length-row;
				numStones = super.stones[row];
				super.stones[row] = 0;
				index = 18 - index;
			}
		}
		mergeBoard(board, p1);		
		
		if(choice == 2) {
			while(true) {
				index++;
				if(index == board.length || index == 12) {
					if(index == board.length) {
						index = 0;
					}
					System.out.println("You are reaching your opponent's Mancala, do you want to (1) skip over your opponent's Mancala or (2) Place a stone in your" +
							"opponent's Mancala and then you can take at most 2 stones from your opponent's Mancala and place them directly in your Mancalas.");
					int input = sc.nextInt();
					if(input == 2) {
						while(true) {
							System.out.println("How many stones do you want to take from your opponent's Mancala?");
							input = sc.nextInt();
							if(input > 0 && input < 3) {
								if(input > board[index]) {
									System.out.println("There is only one stone in your oppoent's Mancala, so you can only take one stone.");
									input = 1;
								}
								break;
							}
							System.out.println("Invalid input, please enter again.");
						}
						int mancala = board[index];
						mancala = mancala + 1 -input;
						board[index] = mancala;
						numStones--;
						if(numStones == 0) {
							break;
						}
					} else {
						if(index == board.length) {
							index = 1;
						}else {
							index++;
						}
						int num = board[index];
						num++;
						board[index] = num;
						numStones--;
						if(numStones == 0) {
							break;
						}
					}
				} else {
					int num = board[index];
					num++;
					board[index] = num;
					numStones--;
					if(numStones == 0) {
						if(index == 6 || index == 18) {
							anotherRound = true;
						}
						break;
					}
				}
				if(numStones == 0) {
					if(index == 6 || index == 18) {
						anotherRound = true;
					}
					break;
				}
			}
		} else if(choice == 1) {
			while(true) {
				index--;
				if(index == 0 || index == 12) {
					System.out.println("You are reaching your opponent's Mancala, do you want to (1) skip over your opponent's Mancala or (2) Place a stone in your" +
							"opponent's Mancala and then you can take at most 2 stones from your opponent's Mancala and place them directly in your Mancalas.");
					int input = sc.nextInt();
					if(input == 2) {
						while(true) {
							System.out.println("How many stones do you want to take from your opponent's Mancala?");
							input = sc.nextInt();
							if(input > 0 && input < 3) {
								break;
							}
							System.out.println("Invalid input, please enter again.");
						}
						int mancala = board[index];
						mancala = mancala + 1 -input;
						board[index] = mancala;
						numStones--;
						if(numStones == 0) {
							break;
						}
						if(index <= 0) {
							index = 23;
						}
					} else {
						if(index <= 0) {
							index = 23;
						} else {
							index--;
						}
						int num = board[index];
						num++;
						board[index] = num;
						numStones--;
						if(numStones == 0) {
							break;
						}
					}
				} else {
					int num = board[index];
					num++;
					board[index] = num;
					numStones--;
					if(numStones == 0) {
						if(index == 6 || index == 18) {
							anotherRound = true;
						}
						break;
					}
				}
				if(numStones == 0) {
					if(index == 6 || index == 18) {
						anotherRound = true;
					}
					break;
				}
			}
		}
		seperateBoard(board, p1);
		return anotherRound;
	}
	
	public void mergeBoard(int[] board, aiPlayer p1) {
		board[0] = p1.getMancalaOne();
		board[1] = p1.stones[0];
		board[2] = p1.stones[1];
		board[3] = super.stones[2];
		board[4] = super.stones[3];
		board[5] = super.stones[4];
		board[6] = super.getMancalaTwo();
		board[7] = super.stones[5];
		board[8] = super.stones[6];
		board[9] = p1.stones[2];
		board[10] = p1.stones[3];
		board[11] = p1.stones[4];
		board[12] = p1.getMancalaTwo();
		board[13] = p1.stones[5];
		board[14] = p1.stones[6];
		board[15] = super.stones[7];
		board[16] = super.stones[8];
		board[17] = super.stones[9];
		board[18] = super.getMancalaOne();
		board[19] = super.stones[0];
		board[20] = super.stones[1];
		board[21] = p1.stones[7];
		board[22] = p1.stones[8];
		board[23] = p1.stones[9];
	}
	
	public void seperateBoard(int[] board, aiPlayer p1) {
		p1.setMancalaOne(board[0]);
		p1.stones[0] = board[1];
		p1.stones[1] = board[2];
		super.stones[2] = board[3];
		super.stones[3] = board[4];
		super.stones[4] = board[5];
		super.setMancalaTwo(board[6]);
		super.stones[5] = board[7];
		super.stones[6] = board[8];
		p1.stones[2] = board[9];
		p1.stones[3] = board[10];
		p1.stones[4] = board[11];
		p1.setMancalaTwo(board[12]);
		p1.stones[5] = board[13];
		p1.stones[6] = board[14];
		super.stones[7] = board[15];
		super.stones[8] = board[16];
		super.stones[9] = board[17];
		super.setMancalaOne(board[18]);
		super.stones[0] = board[19];
		super.stones[1] = board[20];
		p1.stones[7] = board[21];
		p1.stones[8] = board[22];
		p1.stones[9] = board[23];
	}
}
