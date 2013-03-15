package experiment;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class IntBoardTests {
	private IntBoard ib;
	
	@Before
	public void SetUp() {
		 ib = new IntBoard();	
		 ib.calcAdjacencies();
	}
	
	@Test
	public void testCalcIndex() {
		Assert.assertEquals(0, ib.calcIndex(0, 0));
		Assert.assertEquals(12, ib.calcIndex(3, 0));
		Assert.assertEquals(7, ib.calcIndex(1, 3));
		Assert.assertEquals(10, ib.calcIndex(2, 2));
		Assert.assertEquals(15, ib.calcIndex(3, 3));
	}

	@Test
	public void testAdjacency0()
	{
		LinkedList<Integer> testList = ib.getAdjList(0);
		Assert.assertTrue(testList.contains(4));
		Assert.assertTrue(testList.contains(1));
		Assert.assertEquals(2, testList.size());
	}

	@Test
	public void testAdjacency1()
	{
		LinkedList<Integer>  testList = ib.getAdjList(7);
		Assert.assertTrue(testList.contains(11));
		Assert.assertTrue(testList.contains(3));
		Assert.assertTrue(testList.contains(6));
		Assert.assertEquals(3, testList.size());
	}

	@Test
	public void testAdjacency2()
	{
		LinkedList<Integer>  testList = ib.getAdjList(8);
		Assert.assertTrue(testList.contains(12));
		Assert.assertTrue(testList.contains(9));
		Assert.assertTrue(testList.contains(4));
		Assert.assertEquals(3, testList.size());
	}

	@Test
	public void testAdjacency3()
	{
		LinkedList<Integer>  testList = ib.getAdjList(10);
		Assert.assertTrue(testList.contains(14));
		Assert.assertTrue(testList.contains(11));
		Assert.assertTrue(testList.contains(6));
		Assert.assertTrue(testList.contains(9));
		Assert.assertEquals(4, testList.size());
	}

	@Test
	public void testAdjacency4()
	{
		LinkedList<Integer>  testList = ib.getAdjList(15);
		Assert.assertTrue(testList.contains(14));
		Assert.assertTrue(testList.contains(11));
		Assert.assertEquals(2, testList.size());
	}
	
	@Test
	public void testAdjacency5()
	{
		LinkedList<Integer>  testList = ib.getAdjList(5);
		Assert.assertTrue(testList.contains(4));
		Assert.assertTrue(testList.contains(9));
		Assert.assertTrue(testList.contains(6));
		Assert.assertTrue(testList.contains(1));
		Assert.assertEquals(4, testList.size());
	}
	
	@Test
	public void testTargets0_1()
	{
		ib.startTargets(0, 1);
		Set<Integer> targets= ib.getTargets();
		Assert.assertEquals(2, targets.size());
		Assert.assertTrue(targets.contains(1));
		Assert.assertTrue(targets.contains(4));
	}

	@Test
	public void testTargets5_1()
	{
		ib.startTargets(5, 1);
		Set<Integer> targets= ib.getTargets();
		Assert.assertEquals(4, targets.size());
		Assert.assertTrue(targets.contains(1));
		Assert.assertTrue(targets.contains(4));
		Assert.assertTrue(targets.contains(6));
		Assert.assertTrue(targets.contains(9));
	}

	@Test
	public void testTargets5_2()
	{
		ib.startTargets(5, 2);
		Set<Integer> targets= ib.getTargets();
		Assert.assertEquals(6, targets.size());
		Assert.assertTrue(targets.contains(0));
		Assert.assertTrue(targets.contains(2));
		Assert.assertTrue(targets.contains(7));
		Assert.assertTrue(targets.contains(13));
		Assert.assertTrue(targets.contains(10));
		Assert.assertTrue(targets.contains(8));
	}

	@Test
	public void testTargets9_3()
	{
		ib.startTargets(9, 3);
		Set<Integer> targets= ib.getTargets();
		Assert.assertEquals(8, targets.size());
		Assert.assertTrue(targets.contains(0));
		Assert.assertTrue(targets.contains(2));
		Assert.assertTrue(targets.contains(7));
		Assert.assertTrue(targets.contains(5));
		Assert.assertTrue(targets.contains(8));
		Assert.assertTrue(targets.contains(10));
		Assert.assertTrue(targets.contains(15));
		Assert.assertTrue(targets.contains(13));
	}
	
	@Test
	public void testTargets15_3()
	{
		ib.startTargets(15, 3);
		Set<Integer> targets= ib.getTargets();
		Assert.assertEquals(6, targets.size());
		Assert.assertTrue(targets.contains(11));
		Assert.assertTrue(targets.contains(14));
		Assert.assertTrue(targets.contains(6));
		Assert.assertTrue(targets.contains(9));
		Assert.assertTrue(targets.contains(3));
		Assert.assertTrue(targets.contains(12));
	}

	@Test
	public void testTargets3_1()
	{
		ib.startTargets(3,1);
		Set<Integer> targets= ib.getTargets();
		Assert.assertEquals(2, targets.size());
		Assert.assertTrue(targets.contains(2));
		Assert.assertTrue(targets.contains(7));
	}

}
