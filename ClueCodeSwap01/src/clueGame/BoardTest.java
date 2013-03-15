package clueGame;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.*;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class BoardTest {

	private static Board b;
	public static final int NUM_ROOMS = 11;
	public static final int NUM_ROWS = 20;
	public static final int NUM_COLUMNS = 26;
	public static final String clueLayoutFile = "ClueMap.csv";
	public static final String clueLegendFile = "rooms.txt";
	
	@BeforeClass
	public static void setUp() {
		b = new Board(clueLayoutFile, clueLegendFile);
		b.loadConfigFiles();
	}
	
	@Test
	public void testRoomsGood() {
		Map<Character, String> r = b.getRooms();
		 
		assertEquals(NUM_ROOMS, r.size());
		assertEquals("Walkway", r.get('W'));
		assertEquals("Conservatory", r.get('C'));
		assertEquals("Kitchen", r.get('K'));
		assertEquals("Closet", r.get('X'));
		assertEquals("Dining room", r.get('D'));
		
	}
	
	@Test
	public void testRoomsMap() {
		HashMap<Character, String> rooms = (HashMap<Character, String>) b.getRooms();
			
		assertTrue(rooms.containsKey('B'));
		assertEquals("Ballroom", rooms.get('B'));
		assertTrue(rooms.containsKey('C'));
		assertEquals("Conservatory", rooms.get('C'));
		assertTrue(rooms.containsKey('K'));
		assertEquals("Kitchen", rooms.get('K'));
		assertTrue(rooms.containsKey('R'));
		assertEquals("Billiard room", rooms.get('R'));
		assertTrue(rooms.containsKey('L'));
		assertEquals("Library", rooms.get('L'));
		assertTrue(rooms.containsKey('S'));
		assertEquals("Study", rooms.get('S'));		
		assertTrue(rooms.containsKey('D'));
		assertEquals("Dining room", rooms.get('D'));
		assertTrue(rooms.containsKey('O'));
		assertEquals("Lounge", rooms.get('O'));
		assertTrue(rooms.containsKey('H'));
		assertEquals("Hall", rooms.get('H'));
		assertTrue(rooms.containsKey('X'));
		assertEquals("Closet", rooms.get('X'));
		assertTrue(rooms.containsKey('W'));
		assertEquals("Walkway", rooms.get('W'));
	}

	@Test 
	public void testRowsAndColumns() {
		assertEquals(NUM_ROWS, b.getNumRows());
		assertEquals(NUM_COLUMNS, b.getNumColumns());		
	}

	@Test 
	public void testDoors() {
	
		BoardCell br = b.getCellAt(3, 3);
		assertFalse(br.isDoorway());

		RoomCell r = b.getRoomCellAt(7, 1);
		assertTrue(r.isDoorway());
		
		r = b.getRoomCellAt(4, 11);
		assertEquals(RoomCell.DoorDirection.LEFT, r.getDoorDirection());
		r = b.getRoomCellAt(3, 22);
		assertEquals(RoomCell.DoorDirection.DOWN, r.getDoorDirection());
		r = b.getRoomCellAt(9, 18);
		assertEquals(RoomCell.DoorDirection.RIGHT, r.getDoorDirection());
		r = b.getRoomCellAt(14, 8);
		assertEquals(RoomCell.DoorDirection.UP, r.getDoorDirection());
	}

	@Test (expected=BadConfigFormatException.class)
	public void testExceptionFailing() {
		Board b = new Board("ClueMapBAD.csv", "rooms.txt");
		b.loadRoomConfig();
		b.loadBoardConfig();
	}
	
	@Test
	public void testCalcIndex() {
		Assert.assertEquals(0, b.calcIndex(0, 0));
		Assert.assertEquals(78, b.calcIndex(3, 0));
		Assert.assertEquals(412, b.calcIndex(15, 22));
		Assert.assertEquals(519, b.calcIndex(19, 25));		
	}
}
