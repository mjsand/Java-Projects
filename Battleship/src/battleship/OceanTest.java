package battleship;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OceanTest {

	Ocean ocean;
	
	static int NUM_BATTLESHIPS = 1;
	static int NUM_CRUISERS = 2;
	static int NUM_DESTROYERS = 3;
	static int NUM_SUBMARINES = 4;
	static int OCEAN_SIZE = 10;
	
	@BeforeEach
	void setUp() throws Exception {
		ocean = new Ocean();
	}
	
	@Test
	void testEmptyOcean() {
		
		//tests that all locations in the ocean are "empty"
		
		Ship[][] ships = ocean.getShipArray();
		
		for (int i = 0; i < ships.length; i++) {
			for (int j = 0; j < ships[i].length; j++) {
				Ship ship = ships[i][j];
				
				assertEquals("empty", ship.getShipType());
			}
		}
		
		assertEquals(0, ships[0][0].getBowRow());
		assertEquals(0, ships[0][0].getBowColumn());
		
		assertEquals(5, ships[5][5].getBowRow());
		assertEquals(5, ships[5][5].getBowColumn());
		
		assertEquals(9, ships[9][0].getBowRow());
		assertEquals(0, ships[9][0].getBowColumn());
	}
	
	@Test
	void testPlaceAllShipsRandomly() {
		
		//tests that the correct number of each ship type is placed in the ocean
		
		ocean.placeAllShipsRandomly();

		Ship[][] ships = ocean.getShipArray();
		ArrayList<Ship> shipsFound = new ArrayList<Ship>();
		
		int numBattlehips = 0;
		int numCruisers = 0;
		int numDestroyers = 0;
		int numSubmarines = 0;
		int numEmptySeas = 0;
		
		for (int i = 0; i < ships.length; i++) {
			for (int j = 0; j < ships[i].length; j++) {
				Ship ship = ships[i][j];
				if (!shipsFound.contains(ship)) {
					shipsFound.add(ship);
				}
			}
		}
		
		for (Ship ship : shipsFound) {
			if ("battleship".equals(ship.getShipType())) {		
				numBattlehips++;
			} else if ("cruiser".equals(ship.getShipType())) {
				numCruisers++;
			} else if ("destroyer".equals(ship.getShipType())) {
				numDestroyers++;
			} else if ("submarine".equals(ship.getShipType())) {
				numSubmarines++;
			} else if ("empty".equals(ship.getShipType())) {
				numEmptySeas++;
			}
		}
		
		assertEquals(NUM_BATTLESHIPS, numBattlehips);
		assertEquals(NUM_CRUISERS, numCruisers);
		assertEquals(NUM_DESTROYERS, numDestroyers);
		assertEquals(NUM_SUBMARINES, numSubmarines);
		
		//calculate total number of available spaces and occupied spaces
		int totalSpaces = OCEAN_SIZE * OCEAN_SIZE; 
		int occupiedSpaces = (NUM_BATTLESHIPS * 4)
				+ (NUM_CRUISERS * 3)
				+ (NUM_DESTROYERS * 2)
				+ (NUM_SUBMARINES * 1);
		
		//test number of empty seas, each with length of 1
		assertEquals(totalSpaces - occupiedSpaces, numEmptySeas);
	}

	@Test
	void testIsOccupied() {

		Destroyer destroyer = new Destroyer();
		int row = 1;
		int column = 5;
		boolean horizontal = false;
		destroyer.placeShipAt(row, column, horizontal, ocean);
		
		Ship submarine = new Submarine();
		row = 0;
		column = 0;
		horizontal = false;
		submarine.placeShipAt(row, column, horizontal, ocean);
		
		assertTrue(ocean.isOccupied(1, 5));
		
		//placing additional ships and verifying if they are there
		Ship battleship = new Battleship();
		battleship.placeShipAt(5, 5, true, ocean);
		//since it is horizontal, it should be all all of these locations
		assertTrue(ocean.isOccupied(5, 5));
		assertTrue(ocean.isOccupied(5, 2));
		assertTrue(ocean.isOccupied(5, 4));
		//and not at this one since the bow is at 5,5
		assertFalse(ocean.isOccupied(5, 6));
		//checking random spot to verify its not occupied
		assertFalse(ocean.isOccupied(9, 9));
	}

	@Test
	void testShootAt() {
	
		assertFalse(ocean.shootAt(0, 1));
		
		Destroyer destroyer = new Destroyer();
		int row = 1;
		int column = 5;
		boolean horizontal = false;
		destroyer.placeShipAt(row, column, horizontal, ocean);
		
		assertTrue(ocean.shootAt(1, 5));
		assertFalse(destroyer.isSunk());
		assertTrue(ocean.shootAt(0, 5));
		
		//shooting at random location first to verify false
		assertFalse(ocean.shootAt(7, 5));
		//creating ships and testing 
		Submarine submarine = new Submarine();
		submarine.placeShipAt(7, 5, true, ocean);
		assertTrue(ocean.shootAt(7, 5));
		//verifying it  misses at 7,6
		assertFalse(ocean.shootAt(7, 6));
		//verifying it sunk the sub after first hit
		assertTrue(submarine.isSunk());
		
	}

	@Test
	void testGetShotsFired() {
		
		//should be all false - no ships added yet
		assertFalse(ocean.shootAt(0, 1));
		assertFalse(ocean.shootAt(1, 0));
		assertFalse(ocean.shootAt(3, 3));
		assertFalse(ocean.shootAt(9, 9));
		assertEquals(4, ocean.getShotsFired());
		
		Destroyer destroyer = new Destroyer();
		int row = 1;
		int column = 5;
		boolean horizontal = false;
		destroyer.placeShipAt(row, column, horizontal, ocean);
		
		Ship submarine = new Submarine();
		row = 0;
		column = 0;
		horizontal = false;
		submarine.placeShipAt(row, column, horizontal, ocean);
		
		assertTrue(ocean.shootAt(1, 5));
		assertFalse(destroyer.isSunk());
		assertTrue(ocean.shootAt(0, 5));
		assertTrue(destroyer.isSunk());
		assertEquals(6, ocean.getShotsFired());
		
		//creating ship, firing shots to verify shotsFires total
		Cruiser cruiser = new Cruiser();
		cruiser.placeShipAt(5, 5, true, ocean);
		assertFalse(ocean.shootAt(5, 6));
		assertTrue(ocean.shootAt(5, 5));
		assertTrue(ocean.shootAt(5, 4));
		assertEquals(9, ocean.getShotsFired());
	}

	@Test
	void testGetHitCount() {
		
		Destroyer destroyer = new Destroyer();
		int row = 1;
		int column = 5;
		boolean horizontal = false;
		destroyer.placeShipAt(row, column, horizontal, ocean);
		
		assertTrue(ocean.shootAt(1, 5));
		assertFalse(destroyer.isSunk());
		assertEquals(1, ocean.getHitCount());
		
		//creating new ship to test hitCount
		Cruiser cruiser = new Cruiser();
		cruiser.placeShipAt(5, 5, true, ocean);
		assertFalse(ocean.shootAt(4, 5));
		assertTrue(ocean.shootAt(5, 5));
		assertEquals(2, ocean.getHitCount());
		
		Submarine submarine = new Submarine();
		submarine.placeShipAt(1, 1, true, ocean);
		assertTrue(ocean.shootAt(1, 1));
		assertEquals(3, ocean.getHitCount());
	}
	
	@Test
	void testGetShipsSunk() {
		
		Destroyer destroyer = new Destroyer();
		int row = 1;
		int column = 5;
		boolean horizontal = false;
		destroyer.placeShipAt(row, column, horizontal, ocean);
		
		assertTrue(ocean.shootAt(1, 5));
		assertFalse(destroyer.isSunk());
		assertEquals(1, ocean.getHitCount());
		assertEquals(0, ocean.getShipsSunk());
		
		//creating ships for additional tests
		Cruiser cruiser = new Cruiser();
		Battleship battleship = new Battleship();
		cruiser.placeShipAt(5, 5, true, ocean);
		battleship.placeShipAt(8, 5, true, ocean);
		
		assertTrue(ocean.shootAt(5, 5));
		assertTrue(ocean.shootAt(8, 5));
		assertFalse(battleship.isSunk());
		assertFalse(cruiser.isSunk());
		assertEquals(0, ocean.getShipsSunk());
		assertEquals(3, ocean.getHitCount());
	}

	@Test
	void testGetShipArray() {
		
		Ship[][] shipArray = ocean.getShipArray();
		assertEquals(OCEAN_SIZE, shipArray.length);
		assertEquals(OCEAN_SIZE, shipArray[0].length);
		
		assertEquals("empty", shipArray[0][0].getShipType());
		
		//additional test cases
		assertEquals("empty", shipArray[9][9].getShipType());
		assertEquals("empty", shipArray[5][5].getShipType());
		//creating submarine to test getShipType
		Submarine submarine = new Submarine();
		submarine.placeShipAt(2, 2, true, ocean);
		assertEquals("submarine", shipArray[2][2].getShipType());
		//creating destroyer to test
		Destroyer destroyer = new Destroyer();
		destroyer.placeShipAt(7, 7, true, ocean);
		assertEquals("destroyer", shipArray[7][7].getShipType());
		
	}
	@Test
	void testIsGameOver() {
		//first testing before any ship has been sunk
		assertFalse(ocean.isGameOver());
		
		//adding ships to re-test
		Submarine submarine = new Submarine();
		submarine.placeShipAt(5, 5, false, ocean);
		assertTrue(ocean.shootAt(5, 5));
		assertTrue(submarine.isSunk());
		assertFalse(ocean.isGameOver());
		
		Cruiser cruiser = new Cruiser();
		cruiser.placeShipAt(1, 5, true, ocean);
		assertTrue(ocean.shootAt(1, 5));
		assertTrue(ocean.shootAt(1, 4));
		assertTrue(ocean.shootAt(1, 3));
		assertTrue(cruiser.isSunk());
		assertFalse(ocean.isGameOver());
	}
	@Test
	void testSetShipsSunk() {
		//each time setShipSunk is called, it increments shipsSunk by 1
		ocean.setShipsSunk();
		assertEquals(1, ocean.getShipsSunk());
		ocean.setShipsSunk();
		assertEquals(2, ocean.getShipsSunk());
		ocean.setShipsSunk();
		assertNotEquals(2, ocean.getShipsSunk());
	}

}
