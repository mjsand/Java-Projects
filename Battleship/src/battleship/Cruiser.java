package battleship;

public class Cruiser extends Ship {

	//defining the ship type
	private static final String ship_type = "cruiser";
	//defining the ship length
	private static final int ship_length = 3;
	/**
	 * returns length of cruiser ship
	 */
	public Cruiser() {
		super(Cruiser.ship_length);
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
		return Cruiser.ship_type;
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
