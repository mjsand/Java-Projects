package battleship;

public class EmptySea extends Ship {

	//defining the ship type
	private static final String ship_type = "empty";
	//defining the ship length
	private static final int ship_length = 1;
	/**
	 * returns length of cruiser ship
	 */
	public EmptySea() {
		super(EmptySea.ship_length);
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
	 * overrides getShipType method inherited from Ship class
	 * returns ship type string for cruiser
	 */
	@Override
	public String getShipType() {
		return EmptySea.ship_type;
	}
	/**
	 * overrides isSunk method
	 */
	@Override
	boolean isSunk() {
		return false;
	}
	/**
	 * overrides toString method in Ship class - returns a - to print in the ocean's print method
	 */
	@Override
	public String toString() {
		return "-";
	}
}
