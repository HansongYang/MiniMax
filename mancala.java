package assignment2;

import java.util.*;

public class mancala {
	public static boolean gameFinished(player p1, player p2) {
		boolean finished = true;
		for(int i = 0; i < p1.stones.length; i++) {
			if(p1.stones[i] > 0) {
				finished = false;
				break;
			}
		}

		if(finished) {
			return finished;
		}
		finished = true;
		
		for(int i = 0; i < p2.stones.length; i++) {
			if(p2.stones[i] > 0) {
				finished = false;
				break;
			}
		}
		return finished;
	}
	
	public static void humanPlay(humanplayer p2, Scanner sc, aiPlayer p1, board b) {
		boolean anotherRound = false;
		int row, column;
		sc.nextLine();
		while(true) {
			while(true) {
				System.out.println("Please enter the number of row and column with a space in between. For example, 1 3.");
				String input = sc.nextLine();
				if(input.equals("")) {
					continue;
				}
				String[] in = input.trim().split("\\s+");
				row = Integer.parseInt(in[0]);
				column = Integer.parseInt(in[1]);
				if((row > 0 && row < 7) && (column == 3 || column == 4)) {
					if(column == 3 && row != 3) {
						break;
					} else if(column == 4 && row != 4) {
						break;
					}
				}
				System.out.println("Invalid input. Please enter again.");
			} 
			
			if(column == 3) {
				if(row > 3 && p2.stones[row-2] != 0) {
					 break;
				} else if(row < 3 && p2.stones[row-1] != 0) {
					break;
				}
			} else if(column == 4) {
				if(row > 4 && p2.stones[p2.stones.length+1-row] != 0) {
					break;
				} else if(row < 4 && p2.stones[p2.stones.length-row] != 0) {
					break;
				}
			}
			System.out.println("Invalid input. Please enter again.");
		}
		
		System.out.println("Do you want to play (1) clockwise or (2) counter-clockwise?");
		int choice = sc.nextInt();
		sc.nextLine();
		anotherRound = p2.play(p1, row, column, choice, sc);
		if(anotherRound) {
			b.printBoard(p1, p2);
			System.out.println("You can play again!");
			humanPlay(p2, sc, p1, b);
		}
	}
	
	public static void aiPlay(aiPlayer p1, board b, player p2) throws InterruptedException {
		boolean anotherRound = false;
		anotherRound = p1.play(p2, b);
		
		if(anotherRound) {
			if(gameFinished(p1, p2)) {
				return;
			}
			System.out.println("AI " + p1.getName() + " played!");
			System.out.println("AI " + p1.getName() + " can play again!");
			if(p1.getName().equals("one")) {
				b.printBoard(p1, p2);
			} else {
				b.printBoard(p2, p1);
			}
			Thread.sleep(3000);
			aiPlay(p1, b, p2);
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		board b = new board();
		Random rand = new Random();
		int direction = 0;
		Scanner sc = new Scanner(System.in);
		aiPlayer p1 = new aiPlayer("one");
		humanplayer p2 = new humanplayer();
		aiPlayer p3 = new aiPlayer("two");
		boolean watch = false;
		
		System.out.println("Welcome to the game of Mancala.");
		while(true) {
			System.out.println("Do you want to (1) play with AI or (2) watch two AIs to play with each other?");
			int choice = sc.nextInt();
			if(choice == 1) {
				watch = false;
				break;
			} else if(choice == 2) {
				watch = true;
				break;
			} else {
				System.out.println("Invalid input, please enter again.");
			}
		}
		while(true) {
			System.out.println("How many stones do you want to put in each hole? The upper limit is 6.");
			int num = sc.nextInt();
			if(num < 7 && num > 0) {
				if(!watch) {
					p1.startUp(num);
					p2.startUp(num);
				} else {
					p1.startUp(num);
					p3.startUp(num);
				}
				sc.nextLine();
				break;
			} else {
				System.out.println("Invalid input, please enter again.");
			}
		}
		while(true) {
			System.out.println("Please enter the direction of ai players to play. 1. clockwise. 2. counter-clockwise.");
			int num = sc.nextInt();
			if(num == 1 || num == 2) {
				direction = num;
				p1.setDirection(direction);
				p3.setDirection(direction);
				sc.nextLine();
				break;
			} else {
				System.out.println("Invalid input, please enter again.");
			}
		}
		
		System.out.println("\nGame Board:");
		if(watch) {
			b.printBoard(p1, p3);
		} else {
			b.printBoard(p1, p2);
		}
		
		int x = rand.nextInt(10);
		if (x % 2 == 0) {
			System.out.println("AI player one plays first.");
			p1.setTurn(false);
			if(watch) {
				aiPlay(p1, b, p3);
				p3.setTurn(true);
			} else {
				aiPlay(p1, b, p2);
				p2.setTurn(true);
			}
		} else {
			if(watch) {
				System.out.println("AI Player two plays first.");
				aiPlay(p3, b, p1);
				p3.setTurn(false);
				p1.setTurn(true);
			} else {
				System.out.println("Human player plays first.");
				humanPlay(p2, sc, p1, b);
				p2.setTurn(false);
				p1.setTurn(true);
			}
		}
		System.out.println("\nGame Board:");
		if(watch) {
			b.printBoard(p1, p3);
		} else {
			b.printBoard(p1, p2);
		}
		Thread.sleep(2500);
	
		while(true) {
			if(watch && p3.getTurn()) {
				aiPlay(p3, b, p1);
				System.out.println("AI two Played.");
				p3.setTurn(false);
				p1.setTurn(true);
			} else if(watch && p1.getTurn()) {
				aiPlay(p1, b, p3);
				System.out.println("AI one Played.");
				p1.setTurn(false);
				p3.setTurn(true);
			} else if(!watch && p2.getTurn()) {
				humanPlay(p2, sc, p1, b);
				p2.setTurn(false);
				p1.setTurn(true);
			} else if(!watch && p1.getTurn()) {
				aiPlay(p1, b, p2);
				System.out.println("AI one Played.");
				p1.setTurn(false);
				p2.setTurn(true);
			}
			System.out.println("\nGame Board:");
			if(watch) {
				b.printBoard(p1, p3);
				Thread.sleep(2500);
				if(gameFinished(p1, p3)) {
					b.cleanUp(p1, p3);
					System.out.println("\nThe final Game Board:");
					b.printBoard(p1, p3);
					System.out.println("This game is finsihed.\n");
					if(p1.getTotalPoint() > p3.getTotalPoint()) {
						System.out.println("AI one has " + p1.getTotalPoint()+ " points, so AI one player won the game!!! \nBye!");
						break;
					} else if(p1.getTotalPoint() == p3.getTotalPoint()) {
						System.out.println("Tie game!!! \n Bye");
						break;
					} else {
						System.out.println("AI two has " + p3.getTotalPoint()+ " points, so AI two player won the game!!! \nBye!");
						break;
					}
				}
			} else {
				b.printBoard(p1, p2);
				Thread.sleep(2500);
				if(gameFinished(p1, p2)) {
					b.cleanUp(p1, p2);
					System.out.println("\nThe final Game Board:");
					b.printBoard(p1, p2);
					System.out.println("This game is finsihed.\n");
					if(p1.getTotalPoint() > p2.getTotalpoint()) {
						System.out.println("AI one has " + p1.getTotalPoint()+ " points, AI one player won the game!!! \nBye!");
						break;
					} else if(p1.getTotalPoint() == p2.getTotalpoint()) {
						System.out.println("Tie game!!! \n Bye");
						break;
					} else {
						System.out.println("You have " + p2.getTotalPoint()+ " points, you won the game!!! \nBye!");
						break;
					}
				}
			}

			if(watch && p3.getTurn()) {
				aiPlay(p3, b, p1);
				System.out.println("AI two Played.");
				p3.setTurn(false);
				p1.setTurn(true);
			} else if(watch && p1.getTurn()) {
				aiPlay(p1, b, p3);
				System.out.println("AI one Played.");
				p1.setTurn(false);
				p3.setTurn(true);
			} else if(!watch && p2.getTurn()) {
				humanPlay(p2, sc, p1, b);
				p2.setTurn(false);
				p1.setTurn(true);
			} else if(!watch && p1.getTurn()) {
				aiPlay(p1, b, p2);
				System.out.println("AI one Played.");
				p1.setTurn(false);
				p2.setTurn(true);
			}
			System.out.println("\nGame Board:");
			if(watch) {
				b.printBoard(p1, p3);
				Thread.sleep(2500);
				if(gameFinished(p1, p3)) {
					b.cleanUp(p1, p3);
					System.out.println("\nThe final Game Board:");
					b.printBoard(p1, p3);
					System.out.println("This game is finsihed.\n");
					if(p1.getTotalPoint() > p3.getTotalPoint()) {
						System.out.println("AI one has " + p1.getTotalPoint() + " points, so AI one player won the game!!! \nBye!");
						break;
					} else if(p1.getTotalPoint() == p3.getTotalPoint()) {
						System.out.println("Tie game!!! \n Bye");
						break;
					} else {
						System.out.println("AI two has " + p3.getTotalPoint() + " points, so AI two player won the game!!! \nBye!");
						break;
					}
				}
			} else {
				b.printBoard(p1, p2);
				Thread.sleep(2500);
				if(gameFinished(p1, p2)) {
					b.cleanUp(p1, p2);
					System.out.println("\nThe final Game Board:");
					b.printBoard(p1, p2);
					System.out.println("This game is finsihed.\n");
					if(p1.getTotalPoint() > p2.getTotalpoint()) {
						System.out.println("AI one has " + p1.getTotalPoint() + " points, so AI one player won the game!!! \nBye!");
						break;
					} else if(p1.getTotalPoint() == p2.getTotalpoint()) {
						System.out.println("Tie game!!! \n Bye");
						break;
					} else {
						System.out.println("You have " + p2.getTotalPoint() + " points, you won the game!!! \nBye!");
						break;
					}
				}
			}
		}
	}
}
