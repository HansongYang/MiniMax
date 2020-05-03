package assignment2;

public class board {
	public static final String RED = "\033[0;31m"; 
	public static final String BLUE = "\033[0;34m";
	public static final String RESET = "\033[0m";
	 
	public void printBoard(player p1, player p2) {
		System.out.println("\n            (1) (2) (3) (4) (5) (6) \n");
		System.out.println("                   ---------");
		System.out.println("                   |   " + BLUE + p2.getMancalaOne() + RESET + "   |");
		System.out.println("                   ---------");
		System.out.println("(1)                | " + BLUE + p2.getStones(0) + " | " + p2.getStones(9) + RESET+ " |");
		System.out.println("                   ---------");
		System.out.println("(2)                | " + BLUE + p2.getStones(1) + " | " + p2.getStones(8) + RESET +" |");
		System.out.println("     -------------------------------------");
		System.out.println("(3)  |     | " + RED + p1.getStones(9) + " | " + p1.getStones(8) + " | " + p1.getStones(7) + RESET + " | " 
				+ BLUE + p2.getStones(7) + RESET + " | " + RED + p1.getStones(6) + " | " + p1.getStones(5)  + RESET + " |     |");
		System.out.println("     |  " + RED + p1.getMancalaOne() +  RESET + "  |-----------------------|  " + RED + p1.getMancalaTwo() + RESET + "  | ");
		System.out.println("(4)  |     | " + RED + p1.getStones(0) + " | " + p1.getStones(1) + RESET + " | " + BLUE + p2.getStones(2) + RESET + " | " 
				+ RED + p1.getStones(2) + " | " + p1.getStones(3) + " | " + p1.getStones(4) + RESET + " |     | ");
		System.out.println("     -------------------------------------");
		System.out.println("(5)                | " + BLUE + p2.getStones(3) + " | " + p2.getStones(6) + RESET + " |");
		System.out.println("                   ---------");
		System.out.println("(6)                | " + BLUE + p2.getStones(4) + " | " + p2.getStones(5) + RESET + " |");
		System.out.println("                   ---------");
		System.out.println("                   |   " + BLUE + p2.getMancalaTwo() + RESET + "   |");
		System.out.println("                   ---------\n");
	}
	
	public void cleanUp(player p1, player p2) {
		int total = 0;
		for(int i = 0; i < p1.stones.length; i++) {
			if(p1.stones[i] > 0) {
				total += p1.stones[i];
				p1.stones[i] = 0;
			}
		}
		
		if(total == 0) {
			for(int i = 0; i < p2.stones.length; i++) {
				if(p2.stones[i] > 0) {
					total += p2.stones[i];
					p2.stones[i] = 0;
				}
			}
			int num = p2.getMancalaOne() + total;
			p2.setMancalaOne(num);
		} else {
			int num = p1.getMancalaOne() + total;
			p1.setMancalaOne(num);
		}
	}
}
