package clueGame;

import java.util.LinkedList;
import java.util.Set;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class AdjTests {
	private static Board board;
	
	@BeforeClass
	public static void setUp() {
		// uses custom board layout and legend
		board = new Board("ClueMap.csv","rooms.txt");
		board.loadConfigFiles();
		board.calcAdjacencies();
	}
	
	// tests adj with only walkways
	@Test
	public void TestAdjWithAllWalkways() {
		LinkedList<Integer> testList = board.getAdjList(board.calcIndex(3, 2));
		Assert.assertEquals(4, testList.size());
		Assert.assertTrue(testList.contains(board.calcIndex(2, 2)));
		Assert.assertTrue(testList.contains(board.calcIndex(3, 3)));
		Assert.assertTrue(testList.contains(board.calcIndex(4, 2)));
		Assert.assertTrue(testList.contains(board.calcIndex(3, 1)));
	}
	
	// tests each edge of board
	@Test
	public void TestAdjTopEdge() {
		LinkedList<Integer> testList = board.getAdjList(board.calcIndex(0, 9));
		Assert.assertEquals(3, testList.size());
		Assert.assertTrue(testList.contains(board.calcIndex(0, 8)));
		Assert.assertTrue(testList.contains(board.calcIndex(0, 10)));
		Assert.assertTrue(testList.contains(board.calcIndex(1, 9)));
	}
	
	@Test
	public void TestAdjRightEdge() {
		LinkedList<Integer> testList = board.getAdjList(board.calcIndex(12, 25));
		Assert.assertEquals(3, testList.size());
		Assert.assertTrue(testList.contains(board.calcIndex(12, 24)));
		Assert.assertTrue(testList.contains(board.calcIndex(11, 25)));
		Assert.assertTrue(testList.contains(board.calcIndex(13, 25)));
	}
	
	@Test
	public void TestAdjBottomEdge() {
		LinkedList<Integer> testList = board.getAdjList(board.calcIndex(19, 1));
		Assert.assertEquals(3, testList.size());
		Assert.assertTrue(testList.contains(board.calcIndex(19, 0)));
		Assert.assertTrue(testList.contains(board.calcIndex(19, 2)));
		Assert.assertTrue(testList.contains(board.calcIndex(18, 1)));
		}
	
	@Test
	public void TestAdjLeftEdge() {
		LinkedList<Integer> testList = board.getAdjList(board.calcIndex(10, 0));
		Assert.assertEquals(3, testList.size());
		Assert.assertTrue(testList.contains(board.calcIndex(9, 0)));
		Assert.assertTrue(testList.contains(board.calcIndex(11, 0)));
		Assert.assertTrue(testList.contains(board.calcIndex(10, 1)));
	}
	
	// tests along room walls
	
	@Test
	public void TestAdjAlongLoungeWall() {
		LinkedList<Integer> testList = board.getAdjList(board.calcIndex(1, 8));
		Assert.assertEquals(3, testList.size());
		Assert.assertTrue(testList.contains(board.calcIndex(0, 8)));
		Assert.assertTrue(testList.contains(board.calcIndex(2, 8)));
		Assert.assertTrue(testList.contains(board.calcIndex(1, 9)));
	}
	
	@Test
	public void TestAdjAlongLibraryWall() {
		LinkedList<Integer> testList = board.getAdjList(board.calcIndex(17, 16));
		Assert.assertEquals(3, testList.size());
		Assert.assertTrue(testList.contains(board.calcIndex(16, 16)));
		Assert.assertTrue(testList.contains(board.calcIndex(18, 16)));
		Assert.assertTrue(testList.contains(board.calcIndex(17, 17)));
	}
	
	// test adj along that include a doorway
	@Test
	public void TestAdjWithKitchenDoor() {
		LinkedList<Integer> testList = board.getAdjList(board.calcIndex(17, 3));
		Assert.assertEquals(4, testList.size());
		Assert.assertTrue(testList.contains(board.calcIndex(16, 3)));
		Assert.assertTrue(testList.contains(board.calcIndex(18, 3)));
		Assert.assertTrue(testList.contains(board.calcIndex(17, 2)));
		Assert.assertTrue(testList.contains(board.calcIndex(17, 4)));
	}
	
	@Test
	public void TestAdjWithLoungeDoor() {
		LinkedList<Integer> testList = board.getAdjList(board.calcIndex(10, 5));
		Assert.assertEquals(4, testList.size());
		Assert.assertTrue(testList.contains(board.calcIndex(9, 5)));
		Assert.assertTrue(testList.contains(board.calcIndex(11, 5)));
		Assert.assertTrue(testList.contains(board.calcIndex(10, 4)));
		Assert.assertTrue(testList.contains(board.calcIndex(10, 6)));
		}
	
	// test adj from doorways as the starting point
	@Test
	public void TestAdjFromKitchenDoor() {
		LinkedList<Integer> testList = board.getAdjList(board.calcIndex(16, 3));
		Assert.assertEquals(1, testList.size());
		Assert.assertTrue(testList.contains(board.calcIndex(17, 3)));
	}
	
	@Test
	public void TestAdjFromLoungeDoor() {
		LinkedList<Integer> testList = board.getAdjList(board.calcIndex(9, 5));
		Assert.assertEquals(1, testList.size());
		Assert.assertTrue(testList.contains(board.calcIndex(10, 5)));
	}
	
	// test targets along walkways, at varying distances
	@Test
	public void TestTargetsWalkwaysOneStep() {
		board.calcTargets(8, 9, 1);
		Set<BoardCell> targets = board.getTargets();
		Assert.assertEquals(4, targets.size());
		//System.out.println(targets.contains(//o));
		
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(9, 9))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(7, 9))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(8, 10))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(8, 8))));
	}
	
	@Test 
	public void TestTargetsWalkwaysTwoSteps() {
		board.calcTargets(8, 9, 2);
		Set<BoardCell> targets = board.getTargets();
		Assert.assertEquals(6, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(7, 8))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(6, 9))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(7, 10))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(9, 8))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(10, 9))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(9, 10))));
	}
	
	@Test
	public void TestTargetsWalkwaysThreeSteps() {
		board.calcTargets(8, 9, 3);
		Set<BoardCell> targets = board.getTargets();
		Assert.assertEquals(10, targets.size());		
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(9, 9))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(7, 9))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(8, 10))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(8, 8))));

		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(6, 8))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(5, 9))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(6, 10))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(10, 8))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(11, 9))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(10, 10))));
	}
	
	@Test
	public void TestTargetsWalkwaysFourSteps() {
		board.calcTargets(8, 9, 4);
		Set<BoardCell> targets = board.getTargets();
		Assert.assertEquals(13, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(7, 8))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(6, 9))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(7, 10))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(9, 8))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(10, 9))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(9, 10))));		

		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(10, 7))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(11, 8))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(12, 9))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(11, 10))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(5, 8))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(4, 9))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(5, 10))));
	}
	
	// test targets entering rooms
	@Test
	public void TestTargetsEnteringDiningRoom() {
		board.calcTargets(13, 8, 1);
		Set<BoardCell> targets = board.getTargets();
		Assert.assertEquals(4, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(14, 8))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(13, 7))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(13, 9))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(12, 8))));
	}
	
	@Test
	public void TestTargetsEnteringKitchen() {
		board.calcTargets(18, 3, 2);
		Set<BoardCell> targets = board.getTargets();
		Assert.assertEquals(7, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(19, 2))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(18, 1))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(17, 2))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(16, 3))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(17, 4))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(18, 5))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(19, 4))));		
	}
	
	// test targets when leaving a room
	@Test
	public void TestTargetsLeavingBilliardRoom() {
		board.calcTargets(12, 14, 2);
		Set<BoardCell> targets = board.getTargets();
		Assert.assertEquals(3, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(13, 13))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(14, 14))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(13, 15))));
	}
	
	@Test
	public void TestTargetsLeavingStudy() {
		board.calcTargets(16, 21, 2);
		Set<BoardCell> targets = board.getTargets();
		Assert.assertEquals(3, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(15, 20))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(16, 19))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(17, 20))));
	}
}

