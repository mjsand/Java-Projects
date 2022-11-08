package battleship;

import java.util.Random;

public class Ocean {

	//setting up instance variables
	
	//size of the ocean
	static final int ocean_size = 10;
	
	//used to determine which ship is at given location
	private Ship[][] ships = new Ship[Ocean.ocean_size][Ocean.ocean_size];
	//total number of shots fired
	private int shotsFired;
	//total number of hits
	private int hitCount;
	//number of ships sunk
	private int shipsSunk;
	
	//number of each type of ship
	static final int num_battleships = 1;
	static final int num_cruisers = 2;
	static final int num_destroyers = 3;
	static final int num_submarines = 4;
	
	
	/**
	 * creates an empty ocean and initializes game variables
	 */
	public Ocean() {
		
		//initializing game variables
		this.shotsFired = 0;
		this.hitCount = 0;
		this.shipsSunk = 0;
		//creating empty sea with ship
		this.populateEmptyOcean();
	}
	/**
	 * initializes the game by creating the starting empty ocean
	 */
	private void populateEmptyOcean() {
		
		for (int i=0; i<this.ships.length; i++) {
			for (int j=0; j<this.ships[i].length; j++) {
				Ship ship = new EmptySea();
				ship.placeShipAt(i, j, true, this);
			}
		}
	}
	/**
	 * places all ships randomly on the initially empty ocean
	 * places ships in the order of largest to smallest
	 * cannot place ships adjacent to each other
	 */
	void placeAllShipsRandomly() {
		//creating random instance, and initializing row, column, and horizontal variables
		Random rand = new Random();
		int row;
		int column;
		boolean horizontal;
		
		//placing the battleship
		for (int i=0; i<Ocean.num_battleships; i++) {
			Ship battleship = new Battleship();
			row = rand.nextInt(10);
			column = rand.nextInt(10);
			
			horizontal = rand.nextInt(2) == 0 ? false : true;
			
			while(!battleship.okToPlaceShipAt(row, column, horizontal, this)) {
				row = rand.nextInt(10);
				column = rand.nextInt(10);
				horizontal = rand.nextInt(2) == 0 ? false : true;
			}
			
			battleship.placeShipAt(row, column, horizontal, this);
		}
		//placing cruisers
		for (int i=0; i<Ocean.num_cruisers; i++) {
			Ship cruiser = new Cruiser();
			row = rand.nextInt(10);
			column = rand.nextInt(10);
			
			horizontal = rand.nextInt(2) == 0 ? false : true;
			
			while(!cruiser.okToPlaceShipAt(row, column, horizontal, this)) {
				row = rand.nextInt(10);
				column = rand.nextInt(10);
				horizontal = rand.nextInt(2) == 0 ? false : true;
			}
			
			cruiser.placeShipAt(row, column, horizontal, this);
		}
		//placing destroyers
		for (int i=0; i<Ocean.num_destroyers; i++) {
			Ship destroyer = new Destroyer();
			row = rand.nextInt(10);
			column = rand.nextInt(10);
			
			horizontal = rand.nextInt(2) == 0 ? false : true;
			
			while(!destroyer.okToPlaceShipAt(row, column, horizontal, this)) {
				row = rand.nextInt(10);
				column = rand.nextInt(10);
				horizontal = rand.nextInt(2) == 0 ? false : true;
			}
			
			destroyer.placeShipAt(row, column, horizontal, this);
		}
		//placing submarines
		for (int i=0; i<Ocean.num_submarines; i++) {
			Ship submarine = new Submarine();
			row = rand.nextInt(10);
			column = rand.nextInt(10);
			
			horizontal = rand.nextInt(2) == 0 ? false : true;
			
			while(!submarine.okToPlaceShipAt(row, column, horizontal, this)) {
				row = rand.nextInt(10);
				column = rand.nextInt(10);
				horizontal = rand.nextInt(2) == 0 ? false : true;
			}
			
			submarine.placeShipAt(row, column, horizontal, this);
		}
	}
	/**
	 * returns boolean for whether a specified location is occupied by a ship
	 * @param row
	 * @param column
	 * @return
	 */
	boolean isOccupied(int row, int column) {
		//creating shipArray variable
		Ship[][] shipArray = this.getShipArray();
		//checks if location contains EmptySea; if yes, returns false. if not, returns true.
		return !("empty".equals(shipArray[row][column].getShipType()));
	}
	/**
	 * returns true if location contains a real ship that is still afloat, false if it does not.
	 * also updates the number of shots fired and shots hit
	 * @param row
	 * @param column
	 * @return
	 */
	boolean shootAt(int row, int column) {
		//creating instance of ship
		Ship[][] shipArray = this.getShipArray();
		Ship ship = shipArray[row][column];
		//incrementing shotsFired every time this method is accessed
		this.shotsFired++;
		
		//first checks if row or column is out of bounds
		if ((row < 0 || row >= Ocean.ocean_size) || (column < 0 || column >= Ocean.ocean_size)){
			System.out.println("Out of bounds.");
			return false;
		}
		//if the ship is not yet sunk
		if (!ship.isSunk()) {
			if (ship.shootAt(row, column)) {
			}
			//verifies it's an actual ship, updates hitCount, returns true
			if (this.isOccupied(row, column)) {
				this.hitCount++;
				return true;
			}
		}
			return false;
	}
	/**
	 * return the number of shots fired in the game
	 * @return
	 */
	int getShotsFired() {
		return shotsFired;
	}
	/**
	 * returns the number of hits landed
	 * @return
	 */
	int getHitCount() {
		return hitCount;
	}
	/**
	 * returns the number of ships sunk
	 * @return
	 */
	int getShipsSunk() {
		return shipsSunk;
	}
	/**
	 * updates the shipsSunk number for the BattleshipGame main file
	 * @return
	 */
	void setShipsSunk() {
		this.shipsSunk++;
	}
	/**
	 * returns true if all ships have been sunk
	 * @return
	 */
	boolean isGameOver() {
		if (getShipsSunk() == 10) {
			return true;
		}
		else {
			return false;
		}
	}
	/**
	 * returns the 10x10 array of Ships
	 * @return
	 */
	Ship[][] getShipArray(){
		return this.ships;
	}
	/**
	 * prints the game board with updated symbols at each location to indicate whether its unknown, empty, contains a ship
	 * that has been hit, or a ship that has been sunk
	 */
	void print() {
		//printing the column numbers and top left space
		System.out.print("  ");
		for (int j=0; j< this.ships.length; j++) {
			System.out.print(j + " ");
		}
		System.out.println("");
		
		//Printing row numbers and ocean game symbols
		Ship ship;
		for (int i=0; i < this.ships.length; i++) {
			System.out.print(i + " ");
			//ship values
			for (int g=0; g < this.ships[i].length; g++) {
				ship = this.ships[i][g];
				
				//check if ship has been sunk, location has been shot at and hit, or missed
				if (ship.isSunk() || ship.getLocationHit(i, g)) {
					System.out.print(ship + " ");
				}
				else {
					System.out.print("." + " ");
				}
			}
			System.out.println("");
		}
	}
	/**
	 * for debugging purposes, prints placement of ships with letters corresponding to their name
	 */
	void printWithShip() {
		//top left corner
		System.out.println("  ");
		
		//displaying column numbers
		for (int i=0; i<this.ships.length; i++) {
			System.out.print(i + " ");
		}
		System.out.println("");
		
		//printing row numbers and corresponding ship letters
		Ship ship;
		for (int i=0; i<this.ships.length; i++) {
			System.out.print(i + " ");
			//creating ship instance
			for (int j=0; j<this.ships.length; j++) {
				ship = this.ships[i][j];
				//checking each location to see if it is occupied by one of the 4 ship types
				//if it is, prints a letter corresponding to the ship type in that location on the grid
				//otherwise, prints a . like normal
				if (ship.getShipType() == "battleship") {
					System.out.print("b" + " ");
				}
				else if (ship.getShipType() == "cruiser") {
					System.out.print("c" + " ");
				}
				else if (ship.getShipType() == "destroyer") {
					System.out.print("d" + " ");
				}
				else if (ship.getShipType() == "submarine") {
					System.out.print("s" + " ");
				}
				else {
					System.out.print("." + " ");
				}
			}
			System.out.println("");
		}
	}
}
