package battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShipTest {

	Ocean ocean;
	Ship ship;
	
	@BeforeEach
	void setUp() throws Exception {
		ocean = new Ocean();
	}

	@Test
	void testGetLength() {
		ship = new Battleship();
		assertEquals(4, ship.getLength());
		
		//additional test cases
		Submarine submarine = new Submarine();
		Destroyer destroyer = new Destroyer();
		Cruiser cruiser = new Cruiser();
		assertEquals(1, submarine.getLength());
		assertEquals(2, destroyer.getLength());
		assertEquals(3, cruiser.getLength());
	}

	@Test
	void testGetBowRow() {
		Ship battleship = new Battleship();
		int row = 0;
		int column = 4;
		boolean horizontal = true;
		battleship.placeShipAt(row, column, horizontal, ocean);
		assertEquals(row, battleship.getBowRow());
		
		//additional test cases
		Cruiser cruiser = new Cruiser();
		cruiser.placeShipAt(3, 3, horizontal, ocean);
		assertEquals(3, cruiser.getBowRow());
		assertNotEquals(4, cruiser.getBowRow());
		
		Destroyer destroyer = new Destroyer();
		destroyer.placeShipAt(7, 7, horizontal, ocean);
		assertEquals(7, destroyer.getBowRow());
		assertNotEquals(9, destroyer.getBowRow());
	}

	@Test
	void testGetBowColumn() {
		Ship battleship = new Battleship();
		int row = 0;
		int column = 4;
		boolean horizontal = true;
		battleship.placeShipAt(row, column, horizontal, ocean);
		battleship.setBowColumn(column);
		assertEquals(column, battleship.getBowColumn());	
		
		//additional test case
		Cruiser cruiser = new Cruiser();
		cruiser.placeShipAt(3, 3, horizontal, ocean);
		assertEquals(3, cruiser.getBowColumn());
		assertNotEquals(4, cruiser.getBowColumn());
		
		Destroyer destroyer = new Destroyer();
		destroyer.placeShipAt(7, 7, horizontal, ocean);
		assertEquals(7, destroyer.getBowRow());
		assertNotEquals(9, destroyer.getBowRow());
	}

	@Test
	void testGetHit() {
		ship = new Battleship();
		boolean[] hits = new boolean[4];
		assertArrayEquals(hits, ship.getHit());
		assertFalse(ship.getHit()[0]);
		assertFalse(ship.getHit()[1]);
		
		//additional test cases
		Cruiser cruiser = new Cruiser();
		boolean[] hits2 = new boolean[3];
		cruiser.placeShipAt(5, 5, true, ocean);
		assertArrayEquals(hits2, cruiser.getHit());
		//verifying the bow has not been hit
		assertFalse(cruiser.getHit()[0]);
		//shooting at bow, then re-testing to verify it has now been hit
		ocean.shootAt(5, 5);
		assertTrue(cruiser.getHit()[0]);
		assertFalse(cruiser.getHit()[1]);
	}
	@Test
	void testGetShipType() {
		ship = new Battleship();
		assertEquals("battleship", ship.getShipType());
		
		//additional ship types
		Cruiser cruiser = new Cruiser();
		Submarine submarine = new Submarine();
		Destroyer destroyer = new Destroyer();
		assertEquals("cruiser", cruiser.getShipType());
		assertEquals("submarine", submarine.getShipType());
		assertEquals("destroyer", destroyer.getShipType());
		
	}
	
	@Test
	void testIsHorizontal() {
		Ship battleship = new Battleship();
		int row = 0;
		int column = 4;
		boolean horizontal = true;
		battleship.placeShipAt(row, column, horizontal, ocean);
		assertTrue(battleship.isHorizontal());
		
		//additional test cases
		Cruiser cruiser = new Cruiser();
		Submarine submarine = new Submarine();
		Destroyer destroyer = new Destroyer();
		cruiser.placeShipAt(3, 3, horizontal, ocean);
		submarine.placeShipAt(2, 2, horizontal, ocean);
		destroyer.placeShipAt(5, 5, false, ocean);
		assertTrue(cruiser.isHorizontal());
		assertTrue(submarine.isHorizontal());
		assertFalse(destroyer.isHorizontal());
		
	}
	
	@Test
	void testSetBowRow() {
		Ship battleship = new Battleship();
		int row = 0;
		int column = 4;
		boolean horizontal = true;
		battleship.setBowRow(row);
		assertEquals(row, battleship.getBowRow());
		
		//additional test cases
		Cruiser cruiser = new Cruiser();
		Submarine submarine = new Submarine();
		Destroyer destroyer = new Destroyer();
		cruiser.setBowRow(1);
		submarine.setBowRow(2);
		destroyer.setBowRow(3);
		assertEquals(1, cruiser.getBowRow());
		assertEquals(2, submarine.getBowRow());
		assertEquals(3, destroyer.getBowRow());
	}

	@Test
	void testSetBowColumn() {
		Ship battleship = new Battleship();
		int row = 0;
		int column = 4;
		boolean horizontal = true;
		battleship.setBowColumn(column);
		assertEquals(column, battleship.getBowColumn());
		
		//TODO
		//More tests
	}

	@Test
	void testSetHorizontal() {
		Ship battleship = new Battleship();
		int row = 0;
		int column = 4;
		boolean horizontal = true;
		battleship.setHorizontal(horizontal);
		assertTrue(battleship.isHorizontal());
		
		//additional test cases
				Cruiser cruiser = new Cruiser();
				Submarine submarine = new Submarine();
				Destroyer destroyer = new Destroyer();
				cruiser.setBowColumn(1);
				submarine.setBowColumn(2);
				destroyer.setBowColumn(3);
				assertEquals(1, cruiser.getBowColumn());
				assertEquals(2, submarine.getBowColumn());
				assertEquals(3, destroyer.getBowColumn());
	}

	@Test
	void testOkToPlaceShipAt() {
		
		//test when other ships are not in the ocean
		Ship battleship = new Battleship();
		int row = 0;
		int column = 4;
		boolean horizontal = true;
		boolean ok = battleship.okToPlaceShipAt(row, column, horizontal, ocean);
		assertTrue(ok, "OK to place ship here.");
		
		//additional test cases
		Cruiser cruiser = new Cruiser();
		Submarine submarine = new Submarine();
		Destroyer destroyer = new Destroyer();
		boolean ok2 = cruiser.okToPlaceShipAt(row, column, horizontal, ocean);
		boolean ok3 = submarine.okToPlaceShipAt(row, column, horizontal, ocean);
		boolean ok4 = destroyer.okToPlaceShipAt(row, column, horizontal, ocean);
		assertTrue(ok2, "OK to place ship here.");
		assertTrue(ok3, "OK to place ship here.");
		assertTrue(ok4, "OK to place ship here.");
	}
	
	@Test
	void testOkToPlaceShipAtAgainstOtherShipsOneBattleship() {
		
		//test when other ships are in the ocean
		
		//place first ship
		Battleship battleship1 = new Battleship();
		int row = 0;
		int column = 4;
		boolean horizontal = true;
		boolean ok1 = battleship1.okToPlaceShipAt(row, column, horizontal, ocean);
		assertTrue(ok1, "OK to place ship here.");
		battleship1.placeShipAt(row, column, horizontal, ocean);

		//test second ship
		Battleship battleship2 = new Battleship();
		row = 1;
		column = 4;
		horizontal = true;
		boolean ok2 = battleship2.okToPlaceShipAt(row, column, horizontal, ocean);
		assertFalse(ok2, "Not OK to place ship vertically adjacent below.");
		
		//additional test cases
		Cruiser cruiser1 = new Cruiser();
		Cruiser cruiser2 = new Cruiser();
		boolean ok11 = cruiser1.okToPlaceShipAt(5, 5, horizontal, ocean);
		assertTrue(ok11, "OK to place ship here.");
		cruiser1.placeShipAt(5, 5, horizontal, ocean);
		assertEquals(false, cruiser2.okToPlaceShipAt(5, 5, horizontal, ocean));
	}

	@Test
	void testPlaceShipAt() {
		
		Ship battleship = new Battleship();
		int row = 0;
		int column = 4;
		boolean horizontal = true;
		battleship.placeShipAt(row, column, horizontal, ocean);
		assertEquals(row, battleship.getBowRow());
		assertEquals(column, battleship.getBowColumn());
		assertTrue(battleship.isHorizontal());
		
		assertEquals("empty", ocean.getShipArray()[0][0].getShipType());
		assertEquals(battleship, ocean.getShipArray()[0][1]);
		

		//additional tests
		Cruiser cruiser = new Cruiser();
		Submarine submarine = new Submarine();
		Destroyer destroyer = new Destroyer();
		submarine.placeShipAt(3, 3, horizontal, ocean);
		cruiser.placeShipAt(5, 5, horizontal, ocean);
		destroyer.placeShipAt(7, 7, horizontal, ocean);
		assertEquals(submarine, ocean.getShipArray()[3][3]);
		assertEquals(cruiser, ocean.getShipArray()[5][5]);
		assertEquals(destroyer, ocean.getShipArray()[7][7]);
		
	}

	@Test
	void testShootAt() {
		
		Ship battleship = new Battleship();
		int row = 0;
		int column = 9;
		boolean horizontal = true;
		battleship.placeShipAt(row, column, horizontal, ocean);
		
		assertFalse(battleship.shootAt(1, 9));
		boolean[] hitArray0 = {false, false, false, false};
		assertArrayEquals(hitArray0, battleship.getHit());
		
		//additional tests
				Cruiser cruiser = new Cruiser();
				Submarine submarine = new Submarine();
				Destroyer destroyer = new Destroyer();
				submarine.placeShipAt(3, 3, horizontal, ocean);
				cruiser.placeShipAt(5, 5, horizontal, ocean);
				destroyer.placeShipAt(7, 7, horizontal, ocean);
				assertEquals(true, ocean.shootAt(3, 3));
				assertTrue(ocean.shootAt(5, 5));
				assertTrue(ocean.shootAt(7, 7));
				assertFalse(submarine.shootAt(2, 3));
				
	}
	
	@Test
	void testIsSunk() {
		
		Ship submarine = new Submarine();
		int row = 3;
		int column = 3;
		boolean horizontal = true;
		submarine.placeShipAt(row, column, horizontal, ocean);
		
		assertFalse(submarine.isSunk());
		assertFalse(submarine.shootAt(5, 2));
		assertFalse(submarine.isSunk());
		
		//additional test cases
		//creating new cruiser and placing it
		Cruiser cruiser = new Cruiser();
		cruiser.placeShipAt(5, 5, horizontal, ocean);
		//verifying it is not sunk
		assertFalse(cruiser.isSunk());
		//shooting first shot, then re-verifying
		ocean.shootAt(5, 5);
		assertFalse(cruiser.isSunk());
		//firing 2 additional shots to sink it, then verifying it is sunk
		ocean.shootAt(5, 4);
		ocean.shootAt(5, 3);
		assertTrue(cruiser.isSunk());
		
	}

	@Test
	void testToString() {
		
		Ship battleship = new Battleship();
		assertEquals("x", battleship.toString());
		
		int row = 9;
		int column = 1;
		boolean horizontal = false;
		battleship.placeShipAt(row, column, horizontal, ocean);
		battleship.shootAt(9, 1);
		assertEquals("x", battleship.toString());
		
		//creating new submarine
		Submarine submarine = new Submarine();
		submarine.placeShipAt(5, 5, horizontal, ocean);
		ocean.shootAt(5, 5);
		//verifying it prints s after it is hit and sunk
		assertEquals("s", submarine.toString());
		Cruiser cruiser = new Cruiser();
		cruiser.placeShipAt(7, 7, true, ocean);
		ocean.shootAt(7, 7);
		//verifying x is printed after it is hit once
		assertEquals("x", cruiser.toString());
		//firing two more shots to sink it, then verifying it prints s
		ocean.shootAt(7, 6);
		ocean.shootAt(7, 5);
		assertTrue(cruiser.isSunk());
		assertEquals("s", cruiser.toString());
	}
	
	@Test
	void testGetLocationHit() {
		//creating ship instance, placing it, then verifying it has not been hit
		Battleship battleship = new Battleship();
		battleship.placeShipAt(5, 5, true, ocean);
		assertFalse(battleship.getLocationHit(5,5));
		//shooting at ship, then re-testing to make sure it has been hit
		ocean.shootAt(5, 5);
		assertTrue(battleship.getLocationHit(5, 5));
		//shooting at other location, verifying ship was not hit
		ocean.shootAt(4, 4);
		assertFalse(battleship.getLocationHit(5, 4));
	}
	@Test
	void testEquals() {
		//verifying it returns false with object o is not instance ship
		int o = 5;
		Ship submarine = new Submarine();
		submarine.placeShipAt(5, 5, false, ocean);
		//verifying it returns false when given non-ship instance
		assertFalse(equals(o));
		//verifying "submarine" equals the ship type of instance submarine
		assertTrue("submarine".equals(submarine.getShipType()));
		//verifying it correctly returns false when comparing "battleship" and instance of submarine
		assertFalse("battleship".equals(submarine.getShipType()));
	}

}
