package battleship;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * this is the main game file that runs the game logic, and creates the Ocean to play on
 * @author mason
 *
 */
public class BattleshipGame {
	
	

	public static void main(String[] args) {
		
		//creating new empty ocean and placing ships
		Ocean ocean = new Ocean();
		ocean.placeAllShipsRandomly();
		
		Scanner scanner = new Scanner(System.in);
		
		while (!ocean.isGameOver()) {
			
			//printing the ocean and hit count
			ocean.print();
			//ocean.printWithShip();
			System.out.println("Hit count: " + ocean.getHitCount());
			
			//getting user input for row and column to fire at
			System.out.println("Enter row: ");
			int row = scanner.nextInt();
			System.out.println("Enter column: ");
			int col = scanner.nextInt();
			
			//shooting at entered location
			if (ocean.shootAt(row, col)) {
				System.out.println("Hit!");
				
				//creating instance of ship
				Ship[][] shipArray = ocean.getShipArray();
				Ship ship = shipArray[row][col];
				
				if (ship.isSunk()) {
					System.out.println("Congrats, you sank a " + ship.getShipType());
					ocean.setShipsSunk();
				}
			}
			else {
				System.out.println("Miss.");
			}
		}
		scanner.close();
		System.out.println("Congrats, you won! You sank all " + ocean.getShipsSunk() + " ships.");
		System.out.println("Your total number of shots was: " + ocean.getShotsFired() + " and your total number of hits was " + ocean.getHitCount());
	}
}
