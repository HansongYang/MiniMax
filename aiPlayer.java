package assignment2;

public class aiPlayer extends player{
	private boolean turn;
	private String name;
	private int[] one = {1,2,9,10,11,13,14,21,22,23};
	private int[] two = {3,4,5,7,8,15,16,17,19,20};
	private int min = -10000000;
	private int max = 10000000;
	private int direction;
	private int nodeCount;
	
	public aiPlayer(String name) {
		super.stones = new int[10];
		this.name = name;
		nodeCount = 0;
		direction = 0;
	}
	
	public void setMancalaOne(int mancala) {
		super.setMancalaOne(mancala);
	}
	
	public void setDirection(int d) {
		direction = d;
	}
	
	public void setMancalaTwo(int mancala) {
		super.setMancalaTwo(mancala);
	}
	
	public void setStones(int index, int value) {
		super.setStones(index, value);
	}
	
	public String getName() {
		return name;
	}
	
	public void setTurn(boolean turn){
		this.turn = turn; 
	}
	
	public boolean getTurn() {
		return turn;
	}
	
	public int heuristicOne(int[] board) {
		if(name.equals("one")) {
			return (board[0]+ board[12]) - (board[6] + board[18]);
		} else {
			return (board[6] + board[18]) - (board[0]+ board[12]);
		}
	}
	
	public int heuristicTwo(player p2, int[] board) {
		int one = 0, two = 0; 
		for(int i = 0; i < super.stones.length; i++) {
			one += super.stones[i];
		}
		
		for(int i = 0; i < p2.stones.length; i++) {
			two += p2.stones[i];
		}
		if(name.equals("one")) {
			return (heuristicOne(board) + (one-two));
		} else {
			return (heuristicOne(board) + (two-one));
		}
	}
	
	public int miniMax(player p2, int[] board) {
      int num = min;
      int index = 0;
      int[] possibleMoves = new int[10];
      if(name.equals("one")) {
    	  possibleMoves = one;
      } else if(name.equals("two")) {
    	  possibleMoves = two;
      }
      for(int i = 0; i < possibleMoves.length; i++) {
    	  if(board[possibleMoves[i]] == 0) {
    		  continue;
    	  }
    	  int[] copy = copyBoard(board);
    	  simulateMoves(copy, possibleMoves[i]);
    	  int value = maxValue(min, max, 5, p2, copy); //The depth of the search is 4.
    	  if(value > num) {
    		  index = possibleMoves[i];
    		  num = value;
    	  }
      }
      return index;
    }
	
	public int maxValue( int alpha, int beta, int depth, player p2, int[] board) {
		if(finished(p2) || depth == 0) {
			if(name.equals("one")) {
				return heuristicOne(board);
			} else {
				return heuristicTwo(p2,board);
			}
		}
		
		int num = min;
		int[] possibleMoves = new int[10];
	    if(name.equals("one")) {
	    	possibleMoves = one;
	    } else if(name.equals("two")) {
	    	possibleMoves = two;
	    }

	    for(int i = 0; i < possibleMoves.length; i++) {
	    	if(board[possibleMoves[i]] == 0) {
	    		continue;
	    	}
	    	int[] copy = copyBoard(board);
	    	boolean nextMove = simulateMoves(copy, possibleMoves[i]);
	    	if(nextMove) {
	    		num = Math.max(num, maxValue(alpha,beta,depth-1, p2, copy));
	    	} else {
	    		num = Math.max(num, minValue(alpha,beta,depth-1, p2, copy));
	    	}
	    	
	    	alpha = Math.max(alpha, num);
	    	if(alpha >= beta) {
	    		break;
	    	}
	    }
	    return num;
	}
	
	public int minValue(int alpha, int beta, int depth, player p2, int[] board) {
		if(finished(p2) || depth == 0) {
			if(name.equals("one")) {
				return heuristicOne(board);
			} else {
				return heuristicTwo(p2,board);
			}
		}
		
		int num = max;
		int[] possibleMoves = new int[10];
	      if(name.equals("one")) {
	    	  possibleMoves = one;
	      } else if(name.equals("two")) {
	    	  possibleMoves = two;
	      }
	      for(int i = 0; i < possibleMoves.length; i++) {
	    	  if(board[possibleMoves[i]] == 0) {
	    		  continue;
	    	  }
	    	  int[] copy = copyBoard(board);
	    	  boolean nextMove = simulateMoves( copy, possibleMoves[i]);
	    	  if(nextMove) {
	    		  num = Math.min(num, minValue(alpha,beta,depth-1, p2, copy));
	    	  }else {
	    		  num = Math.min(num, maxValue(alpha,beta,depth-1, p2, copy));
	    	  }
	    	  beta = Math.min(beta, num);
	    	  if(beta <= alpha) {
	    		  break;
	    	  }
	      }
	      return num;
	}

	public boolean play(player p2, board b) {
		boolean anotherRound = false;
		int [] board = new int[24];
		mergeBoard(board, p2);
		
		int index = -1;
		boolean illeageMove = true;
		while(true) {
			index = miniMax(p2, board);
			if(name.equals("one")) {
				for(int i = 0; i < one.length; i++) {
					if(index == one[i]) {
						illeageMove = false;
					}
				}
			}else if(name.equals("two")) {
				for(int i = 0; i < two.length; i++) {
					if(index == two[i]) {
						illeageMove = false;
					}
				}
			}
			if(!illeageMove) {
				break;
			}
		}

		System.out.println("Node count for AI player: " + nodeCount);
		nodeCount = 0;
		if(direction == 2) {
			int num = (index+1) % 24;
			for(int i = 0; i < board[index]; i++) {
				if(name.equals("two") && (num == 0 || num == 12)) {
					if(board[num] > 2) {
						board[num] = board[num] - 2;
						board[6] = board[6] + 2;
					}
				} else if(name.equals("one") && (num == 6 || num == 18)) {
					if(board[num] > 2) {
						board[num] = board[num] - 2;
						board[0] = board[0] + 2;
					}
				}
				board[num]++;
				num = (num+1) % 24;
			}
			
			num--;	
			board[index] = 0;
			if(name.equals("one") && (num == 0 || num == 12)) {
				anotherRound = true;
				setTurn(true);
			} else if(name.equals("two") && (num == 6 || num == 18)) {
				anotherRound = true;
				setTurn(true);
			}
		} else if(direction == 1) {
			int num = (index-1) % 24;
			for(int i = board[index]; i > 0; i--) {
				if(name.equals("two") && (num == 0 || num == 12)) {
					if(board[num] > 2) {
						board[num] = board[num] - 2;
						board[6] = board[6] + 2;
					}
				} else if(name.equals("one") && (num == 6 || num == 18)) {
					if(board[num] > 2) {
						board[num] = board[num] - 2;
						board[0] = board[0] + 2;
					}
				}
				board[num]++;
				num = (num-1) % 24;
				if(num == -1) {
					num = 23;
				}
			}
			
			num++;
			if(num == 24) {
				num = 0;
			}
			board[index] = 0;
			if(name.equals("one") && (num == 0 || num == 12)) {
				anotherRound = true;
				setTurn(true);
			} else if(name.equals("two") && (num == 6 || num == 18)) {
				anotherRound = true;
				setTurn(true);
			}
		}

		seperateBoard(board, p2);
		return anotherRound;
	}
	
	public boolean finished(player p2) {
		boolean finish = true;
		for(int i = 0; i < super.stones.length; i++) {
			if(super.stones[i] > 0) {
				finish = false;
				break;
			}
		}
		if(finish) {
			return finish;
		}
		finish = true;
		
		for(int i = 0; i < p2.stones.length; i++) {
			if(p2.stones[i] > 0) {
				finish = false;
				break;
			}
		}
		return finish;
	}

	public void mergeBoard(int[] board, player p2) {
		if(name.equals("one")) {
			board[0] = super.getMancalaOne();
			board[1] = super.stones[0];
			board[2] = super.stones[1];
			board[3] = p2.stones[2];
			board[4] = p2.stones[3];
			board[5] = p2.stones[4];
			board[6] = p2.getMancalaTwo();
			board[7] = p2.stones[5];
			board[8] = p2.stones[6];
			board[9] = super.stones[2];
			board[10] = super.stones[3];
			board[11] = super.stones[4];
			board[12] = super.getMancalaTwo();
			board[13] = super.stones[5];
			board[14] = super.stones[6];
			board[15] = p2.stones[7];
			board[16] = p2.stones[8];
			board[17] = p2.stones[9];
			board[18] = p2.getMancalaOne();
			board[19] = p2.stones[0];
			board[20] = p2.stones[1];
			board[21] = super.stones[7];
			board[22] = super.stones[8];
			board[23] = super.stones[9];
		} else if(name.equals("two")) {
			board[0] = p2.getMancalaOne();
			board[1] = p2.stones[0];
			board[2] = p2.stones[1];
			board[3] = super.stones[2];
			board[4] = super.stones[3];
			board[5] = super.stones[4];
			board[6] = super.getMancalaTwo();
			board[7] = super.stones[5];
			board[8] = super.stones[6];
			board[9] = p2.stones[2];
			board[10] = p2.stones[3];
			board[11] = p2.stones[4];
			board[12] = p2.getMancalaTwo();
			board[13] = p2.stones[5];
			board[14] = p2.stones[6];
			board[15] = super.stones[7];
			board[16] = super.stones[8];
			board[17] = super.stones[9];
			board[18] = super.getMancalaOne();
			board[19] = super.stones[0];
			board[20] = super.stones[1];
			board[21] = p2.stones[7];
			board[22] = p2.stones[8];
			board[23] = p2.stones[9];
		}
	}
	
	public void seperateBoard(int[] board, player p2) {
		if(name.equals("one")) {
			super.setMancalaOne(board[0]);
			super.stones[0] = board[1];
			super.stones[1] = board[2];
			p2.stones[2] = board[3];
			p2.stones[3] = board[4];
			p2.stones[4] = board[5];
			p2.setMancalaTwo(board[6]);
			p2.stones[5] = board[7];
			p2.stones[6] = board[8];
			super.stones[2] = board[9];
			super.stones[3] = board[10];
			super.stones[4] = board[11];
			super.setMancalaTwo(board[12]);
			super.stones[5] = board[13];
			super.stones[6] = board[14];
			p2.stones[7] = board[15];
			p2.stones[8] = board[16];
			p2.stones[9] = board[17];
			p2.setMancalaOne(board[18]);
			p2.stones[0] = board[19];
			p2.stones[1] = board[20];
			super.stones[7] = board[21];
			super.stones[8] = board[22];
			super.stones[9] = board[23];
		} else if(name.equals("two")) {
			p2.setMancalaOne(board[0]);
			p2.stones[0] = board[1];
			p2.stones[1] = board[2];
			super.stones[2] = board[3];
			super.stones[3] = board[4];
			super.stones[4] = board[5];
			super.setMancalaTwo(board[6]);
			super.stones[5] = board[7];
			super.stones[6] = board[8];
			p2.stones[2] = board[9];
			p2.stones[3] = board[10];
			p2.stones[4] = board[11];
			p2.setMancalaTwo(board[12]);
			p2.stones[5] = board[13];
			p2.stones[6] = board[14];
			super.stones[7] = board[15];
			super.stones[8] = board[16];
			super.stones[9] = board[17];
			super.setMancalaOne(board[18]);
			super.stones[0] = board[19];
			super.stones[1] = board[20];
			p2.stones[7] = board[21];
			p2.stones[8] = board[22];
			p2.stones[9] = board[23];
		}
	}
	
	public int[] copyBoard(int[] board) {
		int[] copy = new int[24];
		for(int i = 0; i < board.length; i++) {
			copy[i] = board[i];
		}
		return copy;
	}
	
	public boolean simulateMoves(int[] oldBoard, int index) {
		boolean turnOne = false;
		boolean turnTwo = false;
		boolean anotherMoveOne = false;
		boolean anotherMoveTwo = false;
		int [] board = oldBoard.clone();

		if(direction == 2) {
			//conterclockwise
			int num = (index+1) % 24;
			for(int i = 0; i < board[index]; i++) {
				nodeCount++;
				if(name.equals("two") && (num == 0 || num == 12)) {
					if(board[num] > 2) {
						board[num] = board[num] - 2;
						board[6] = board[6] + 2;
					}
				} else if(name.equals("one") && (num == 6 || num == 18)) {
					if(board[num] > 2) {
						board[num] = board[num] - 2;
						board[0] = board[0] + 2;
					}
				}
				board[num]++;
				num = (num+1) % 24;
			}
			
			num--;	
			board[index] = 0;
			
			if(name.equals("one") && (num == 0 || num == 12)) {
				anotherMoveOne = true;
			} else if(name.equals("two") && (num == 6 || num == 18)) {
				anotherMoveTwo = true;
			} else {
				anotherMoveOne = false;
				anotherMoveTwo = false;
			}
		} else {
			//clockwise
			int num = (index-1) % 24;
			for(int i = board[index]; i > 0; i--) {
				nodeCount++;
				if(name.equals("two") && (num == 0 || num == 12)) {
					if(board[num] > 2) {
						board[num] = board[num] - 2;
						board[6] = board[6] + 2;
					}
				} else if(name.equals("one") && (num == 6 || num == 18)) {
					if(board[num] > 2) {
						board[num] = board[num] - 2;
						board[0] = board[0] + 2;
					}
				}
				board[num]++;
				num = (num-1) % 24;
				if(num == -1) {
					num = 23;
				}
			}
			
			num++;
			board[index] = 0;
			if(name.equals("one") && (num == 0 || num == 12)) {
				turnOne = true;
			} else if(name.equals("two") && (num == 6 || num == 18)) {
				turnTwo = true;
			} else {
				turnOne = false;
				turnTwo = false;
			}
		}
		
		oldBoard = board.clone();
		if(direction == 2) {
			if(name.equals("one") && anotherMoveOne) {
				return true;
			} else if(name.equals("two") && anotherMoveTwo) {
				return true;
			}
		} else {
			 if(name.equals("one") && turnOne) {
				return true;
			} else if(name.equals("two") && turnTwo) {
				return true;
			}
		}
		return false;
	}
}
