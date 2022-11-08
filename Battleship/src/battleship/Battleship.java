package battleship;

/**
 * extends ship and defines a battleship; ship of length 4
 * @author mason
 *
 */
public class Battleship extends Ship {
	//defining the ship type
	private static final String ship_type = "battleship";
	//defining the ship length
	 private static final int ship_length = 4;
	
	//constructor for battleship
	public Battleship() {
		super(Battleship.ship_length);
		
	}
	/**
	 * overrides super class of shootAt method
	 * calls super class shootAt method using given row and column
	 */
	@Override
	boolean shootAt(int row, int column) {
		super.shootAt(row, column);
		return false;
	}
	/**
	 * overrides getShipType() inherited from Ship class
	 * returns a string representing the type of ship
	 */
	@Override
	public String getShipType() {
		return Battleship.ship_type;
	}
	@Override
	boolean isSunk() {
		for (int i=0; i <= this.getLength() - 1; i++) {
			if (!this.getHit()[i]) {
				return false;
			}
		}
		return true;
	}
	/**
	 * overrides toString method in Ship class, returns an x when it is hit for the ocean print method
	 */
	@Override
	public String toString() {
		if (this.isSunk()) {
			return "s";
		}
		else {
			return "x";
		}
	}
}
