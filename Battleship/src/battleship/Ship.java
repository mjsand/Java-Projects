package battleship;

public abstract class Ship {
	

	//creating class variables
	
	//row of ship that contains the bow
	private int bowRow;
	//column of ship that contains the bow
	private int bowColumn;
	//length of ship
	private int length;
	//defines whether ship lies horizontally or not
	private boolean horizontal;
	//boolean that defines whether a given part of a ship was hit or not
	private boolean[] hit;
	
	/**
	 * constructor for ship class
	 * sets length of the given ship, and initializes hit array based on the length of the ship
	 * @param length
	 */
	public Ship (int length) {
		//setting length of the ship
		this.length = length;
		 //initialize hit array based on length
		this.hit = new boolean [length];
	}
	//returns the ship length
	public int getLength() {
		return this.length;
	}
	//returns the ship's bow row
	public int getBowRow() {
		return this.bowRow;
	}
	//returns the ship's bow column
	public int getBowColumn() {
		return this.bowColumn;
	}
	//returns hit array for given ship
	public boolean[] getHit() {
		return this.hit;
	}
	//returns whether the ship is placed horizontally or not
	public boolean getHorizontal() {
		return this.horizontal;
	}
	//sets the bow row of the given ship
	public void setBowRow(int row) {
		this.bowRow = row;
	}
	//sets the bow column of the given ship
	public void setBowColumn(int column) {
		this.bowColumn = column;
	}
	//sets whether ship is horizontal or not
	public void setHorizontal(boolean horizontal) {
		this.horizontal = horizontal;
	}
	/**
	 * returns the type of ships as a string
	 * each ship subclass overrides and uses this method
	 * @return
	 */
	public abstract String getShipType();
	/**
	 * returns whether it is ok to place ship at designated spot, based on given row and column
	 * ships cannot overlap each other, be adjacent to each other, and must be either vertical or horizontal
	 * @param row
	 * @param column
	 * @param horizontal
	 * @param ocean
	 * @return returns true or false
	 */
	boolean okToPlaceShipAt(int row, int column, boolean horizontal, Ocean ocean) {
		//defining length of ship
		int shipLength = this.getLength();
		Ship[][] shipArray = ocean.getShipArray();
		
		//checking if ship is horizontal, then setting the stern of the ship 
		if (horizontal) {
			int stern = column - (shipLength - 1);
			if (stern<0) {
				return false;
			}
			//checking around the ship for any adjacent ships
			for (int i=column; i>=stern; i--) {
				//verifying location is empty
				if (!this.isEmpty(shipArray[row][i])) {
					return false;
				}
				//check if it's the bow
				if (i==column) {
					//adjacent right of the bow
					if ((column+1) <= (Ocean.ocean_size - 1)) {
						if (!this.isEmpty(shipArray[row][column+1])) {
							return false;
						}
					}
					//check if it's the top
					if ((row-1) >= 0) {
						//adjacent top right
						if ((i+1) <= (Ocean.ocean_size - 1)) {
							if (!this.isEmpty(shipArray[row-1][i+1])) {
								return false;
							}
						}
						//if it's adjacent top
						if (!this.isEmpty(shipArray[row - 1][i])) {
							return false;
						}
					}
					//if it's bottom
					if ((row + 1) <= (Ocean.ocean_size - 1)) {
						//adjacent bottom right
						if ((i+1) <= (Ocean.ocean_size - 1)) {
							if (!this.isEmpty(shipArray[row+1][i+1])) {
								return false;
						}
					}
					//adjacent bottom
					if (!this.isEmpty(shipArray[row+1][i])) {
						return false;
					}
				}
			}
				//checking if its the stern
				if (i == stern) {
					//adjacent left of stern
					if ((i-1) >= 0) {
						if (!this.isEmpty(shipArray[row][i-1])) {
							return false;
						}
					}
					//top of stern
					if ((row-1) >= 0) {
						//adjacent top left
						if ((i-1) >= 0) {
							if (!this.isEmpty(shipArray[row-1][i-1])) {
								return false;
							}
						}
						//adjacent top
						if (!this.isEmpty(shipArray[row-1][i])) {
							return false;
						}
					}
					//bottom
					if ((row+1) <= (Ocean.ocean_size - 1)) {
						//adjacent bottom left
						if ((i-1) >= 0) {
							if (!this.isEmpty(shipArray[row+1][i-1])) {
								return false;
							}
						}
						//adjacent bottom
						if (!this.isEmpty(shipArray[row+1][i])) {
							return false;
						}
					}
				}
				//every other location
				if ((i<column) && (i>stern)) {
					//adjacent top
					if ((row-1) >= 0) {
						if (!this.isEmpty(shipArray[row-1][i])) {
							return false;
						}
					}
					//adjacent bottom
					if ((row+1) <= (Ocean.ocean_size - 1)) {
						if (!this.isEmpty(shipArray[row+1][i])) {
							return false;
						}
					}
				}
			}
		}
		else if (!horizontal) {
			int stern = row - (shipLength - 1);
			if (stern<0) {
				return false;
			}
			//checking around the ship for any adjacent ships
			for (int i=row; i>=stern; i--) {
				//verifying location is empty
				if (!this.isEmpty(shipArray[i][column])) {
					return false;
				}
				//check if it's the bow
				if (i==row) {
					//adjacent bottom of the bow
					if ((i+1) <= (Ocean.ocean_size - 1)) {
						if (!this.isEmpty(shipArray[i+1][column])) {
							return false;
						}
					}
					//check if it's left
					if ((column-1) >= 0) {
						//adjacent bottom left
						if ((i+1) <= (Ocean.ocean_size - 1)) {
							if (!this.isEmpty(shipArray[i+1][column-1])) {
								return false;
							}
						}
						//adjacent left
						if (!this.isEmpty(shipArray[i][column-1])) {
							return false;
						}
					}
					//adjacent bottom right
					if ((i + 1) <= (Ocean.ocean_size - 1) && (column+1) <= (Ocean.ocean_size - 1)) {
						if (!this.isEmpty(shipArray[i+1][column+1])) {
							return false;
						}
					}
					//adjacent right
					if ((column+1) <= (Ocean.ocean_size - 1)) {
						if (!this.isEmpty(shipArray[i][column+1])) {
							return false;
					}
					
				}
			}
				//checking if its the stern
				if (i == stern) {
					//adjacent top
					if ((i-1) >= 0) {
						if (!this.isEmpty(shipArray[i-1][column])) {
							return false;
						}
					}
					//left of stern
					if ((column-1) >= 0) {
						//adjacent top  left
						if ((i-1) >= 0) {
							if (!this.isEmpty(shipArray[i-1][column-1])) {
								return false;
							}
						}
						//adjacent left
						if (!this.isEmpty(shipArray[i][column-1])) {
							return false;
						}
					}
					//right
					if ((column+1) <= (Ocean.ocean_size - 1)) {
						//adjacent top right
						if ((i-1) >= 0) {
							if (!this.isEmpty(shipArray[i-1][column+1])) {
								return false;
							}
						}
						//adjacent right
						if (!this.isEmpty(shipArray[i][column])) {
							return false;
						}
					}
				}
				//every other location
				if ((i<row) && (i>stern)) {
					//adjacent top
					if ((column-1) >= 0) {
						if (!this.isEmpty(shipArray[column-1][i])) {
							return false;
						}
					}
					//adjacent bottom
					if ((column+1) <= (Ocean.ocean_size - 1)) {
						if (!this.isEmpty(shipArray[column+1][i])) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}
	/**
	 * checks if given location is empty, which would mean an empty ship type is occupying it
	 * returns true or false depending on whether the space's ship type is empty or something else
	 * @param ship
	 * @return
	 */
	private boolean isEmpty(Ship ship) {
		return "empty".equals(ship.getShipType());
	}
	/**
	 * places ship at designated location
	 * @param row
	 * @param column
	 * @param horizontal
	 * @param ocean
	 */
	void placeShipAt(int row, int column, boolean horizontal, Ocean ocean) {
		//setting bowRow and bowColumn
		this.setBowRow(row);
		this.setBowColumn(column);
		this.setHorizontal(horizontal);
		
		int shipLength = this.getLength();
		if (this.isHorizontal()) {
			int stern = column - (shipLength - 1);
			for (int i=column; i>=stern; i--) {
				ocean.getShipArray()[row][i] = this;
			}
		}
		else if (!this.isHorizontal()) {
			int stern = row - (shipLength - 1);
			for (int i=row; i>=stern; i--) {
				ocean.getShipArray()[i][column] = this;
			}
		}
	}
	/**
	 * returns true if part of ship is hit by shot, otherwise returns false
	 * @param row
	 * @param column
	 * @return
	 */
	boolean shootAt(int row, int column) {
		//first verifying ship is not sunk, then creating shipSpaces variable, and checking if ship is horizontal
		//if ship is horizontal, creates variable stern for the back of the ship and sets it
		//next, checks if row is the bowRow of the ship; if it is, iterates backwards from the bowColumn value
		//sets shipSpaces as bowColumn minus the value of i, returns true
		if (!this.isSunk()) {
			int shipSpaces = 0;
			if (this.isHorizontal()) {
				int stern = this.getBowColumn() - (this.getLength() - 1);
				if (row == this.getBowRow()) {
					for (int i = this.getBowColumn(); i>=stern; i--) {
						if (column == i) {
							shipSpaces = this.getBowColumn() - i;
							this.getHit()[shipSpaces] = true;
							return true;
						}
					}
				}
			}
			//else if the ship is not horizontal
			else if (!this.isHorizontal()) {
				int stern = this.getBowRow() - (this.getLength() - 1);
				if (column == this.getBowColumn()) {
					for (int i = this.getBowRow(); i>=stern; i--) {
						if (row == i) {
							shipSpaces = this.getBowRow() - i;
							this.getHit()[shipSpaces] = true;
							return true;
						}
					}
				}
			}
		}
		//if ship is not hit, returns false. or if ship is already sunk returns false
		return false;
	}
	/**
	 * returns either true or false for whether a given ship has been hit in a specified location
	 * @param row
	 * @param column
	 * @return
	 */
	boolean getLocationHit(int row, int column) {
		//creating variable shipSpaces to represent number of spaces in given ship
		int shipSpaces = 0;
		if (this.isHorizontal()) {
			shipSpaces = this.getBowColumn() - column;
			return this.getHit()[shipSpaces];
		}
		else if (!this.isHorizontal()){
			shipSpaces = this.getBowRow() - row;
			return this.getHit()[shipSpaces];
		}
		return false;
	}
	/**
	 * returns true if ship is sunk from latest shot, otherwise returns false
	 * @return
	 */
	boolean isSunk() {
		//iterating over the length of the specified ship, and checking to see if every location has been hit
		//if not, returns false - otherwise returns true.
		for (int i=0; i <= this.getLength() - 1; i++) {
			if (!this.getHit()[i]) {
				return false;
			}
		}
		return true;
	}
	/**
	 * returns given argument as a string
	 */
	@Override
	public String toString() {
		String shipChar = "";
		if (this.isSunk()) {
			shipChar = "s";
		}
		else {
			shipChar = "x";
		}
		return shipChar;
	}
	public boolean equals(Object o) {
		//first checking to see o is indeed an instance of Ship
		if (!(o instanceof Ship)) {
			return false;
		}
		
		//otherwise casting to ship
		Ship otherShip = (Ship) o;
		
		//comparing to the given ship
		return this.hashCode() == otherShip.hashCode();
	}
	/**
	 * returns true if ship is horizontal, false if vertical
	 * @return
	 */
	public boolean isHorizontal() {
		if (this.horizontal == true) {
			return true;
		}
		else {
			return false;
		}
	}
	
}
