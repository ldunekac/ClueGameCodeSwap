package clueTests;

import java.util.ArrayList;
import java.util.Set;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.Card;
import clueGame.CardType;
import clueGame.ClueGame;
import clueGame.ComputerPlayer;
import clueGame.HumanPlayer;
import clueGame.Player;
import clueGame.Solution;

public class GameActionTests {
	private ClueGame game;
	
	@Before
	public void setup()
	{
		game = new ClueGame();
	}
	
	@Test
	public void testAccusation()
	{
		// Test card uniqueness for solution
		Solution solution = game.getSolution();
		
		ArrayList<Player> players = game.getPlayers();
		
		for (Player p : players)
		{
			Assert.assertFalse(p.containsCard(solution.person));
			Assert.assertFalse(p.containsCard(solution.weapon));
			Assert.assertFalse(p.containsCard(solution.room));
		}
		Assert.assertTrue(game.getDeck().contains(solution.person));
		Assert.assertTrue(game.getDeck().contains(solution.weapon));
		Assert.assertTrue(game.getDeck().contains(solution.room));
		
		boolean correct;
		// Player accusation correct
		game.setSolution("The Hacker", "SQL Injection", "Ball Room");
		Player p = new HumanPlayer();
		correct = p.makeAccusation("The Hacker", "SQL Injection", "Ball Room");
		Assert.assertTrue(correct);
		
		// wrong person
		correct = p.makeAccusation("Mr. Cuddles", "SQL Injection", "Ball Room");
		Assert.assertFalse(correct);
		
		// wrong weapon
		correct = p.makeAccusation("The Hacker", "Shrodinger's Cat", "Ball Room");
		Assert.assertFalse(correct);
		
		// wrong room
		correct = p.makeAccusation("The Hacker", "SQL Injection", "Kitchen");
		Assert.assertFalse(correct);
		
	}
	
	@Test
	public void selectATargetLocation()
	{
		// Will go In room
		ComputerPlayer player = new ComputerPlayer();
		player.setLastVisited("Ball Room");
		Board board = game.getBoard();
		board.calcTargets(8, 0, 2);
		int loc_10_0 = 0;
		int loc_9_1 = 0;
		int loc_8_2 = 0;
		int loc_7_1 = 0;
		
		int total = 120;
		for(int i = 0; i < total; i++)
		{
			BoardCell selected = player.pickLocation(board.getTargets());
			if (selected == board.getCellAt(board.calcIndex(10, 0)))
				loc_10_0++;
			else if (selected == board.getCellAt(board.calcIndex(9, 1)))
				loc_9_1++;
			else if (selected == board.getCellAt(board.calcIndex(8, 2)))
				loc_8_2++;
			else if (selected == board.getCellAt(board.calcIndex(7, 1)))
				loc_7_1++;
		}
		
		Assert.assertTrue(loc_7_1 == total);
		
		// Random one
		player = new ComputerPlayer();
		board = game.getBoard();
		board.calcTargets(2, 0, 1);
		int loc_3_0 = 0;
		int loc_2_1 = 0;

		total = 120;
		for(int i = 0; i < total; i++)
		{
			BoardCell selected = player.pickLocation(board.getTargets());
			if (selected == board.getCellAt(board.calcIndex(3, 0)))
				loc_3_0++;
			else if (selected == board.getCellAt(board.calcIndex(2, 1)))
				loc_2_1++;
		}
		Assert.assertEquals(total, loc_3_0+loc_2_1);
		
		Assert.assertTrue(loc_3_0 > 30);
		Assert.assertTrue(loc_2_1 > 30);
		
		// Equal chance to go in room
		player = new ComputerPlayer();
		player.setLastVisited("Hall");
		board = game.getBoard();
		board.calcTargets(8, 0, 2);
		 loc_10_0 = 0;
		 loc_9_1 = 0;
		 loc_8_2 = 0;
		 loc_7_1 = 0;
		
		total = 120;
		for(int i = 0; i < total; i++)
		{
			BoardCell selected = player.pickLocation(board.getTargets());
			if (selected == board.getCellAt(board.calcIndex(10, 0)))
				loc_10_0++;
			else if (selected == board.getCellAt(board.calcIndex(9, 1)))
				loc_9_1++;
			else if (selected == board.getCellAt(board.calcIndex(8, 2)))
				loc_8_2++;
			else if (selected == board.getCellAt(board.calcIndex(7, 1)))
				loc_7_1++;
		}
		
		Assert.assertEquals(total, loc_10_0+loc_9_1+loc_8_2+loc_7_1);
		
		Assert.assertTrue(loc_10_0 > 15);
		Assert.assertTrue(loc_9_1 > 15);
		Assert.assertTrue(loc_8_2 > 15);
		Assert.assertTrue(loc_7_1 > 15);

		
		
	}
	
	@Test
	public void makeSuggestion()
	{
		game = null;
		game = new ClueGame(true); // creates a stacked deck
		
		Player human = game.MrCuddles; // ball room SQL injection own
		
		// One Player one match
			String ans = game.handleSuggestion("Mr. Cuddles", "SQL Injection", "Kitchen", human);
			Assert.assertEquals("Kitchen", ans);
		
		// one Player multiple matches
			int hackerCount = 0;
			int kitchenCount = 0;
			for(int i = 0; i < 100; i++)
			{
				ans = game.handleSuggestion("The Hacker", "SQL Injection", "Kitchen", human);
				if(ans.equals("Kitchen"))
					kitchenCount++;
				else if(ans.equals("The Hacker"))
					hackerCount++;
			}
			Assert.assertTrue(kitchenCount > 20);
			Assert.assertTrue(hackerCount > 20);
			
		// No matches
		ans = game.handleSuggestion("Mr. Cuddles", "SQL Injection", "Ball Room", human);
		Assert.assertTrue(ans == null);
			
		Player computer = game.theHacker;
		
		ArrayList<String> suggestion = computer.makeSuggestion();
		Assert.assertTrue(suggestion.size() == 3);
		int suggestionCount = 0;
		for(String s : suggestion)
		{
			if(game.getCard(s).getType() == CardType.PERSON)
				suggestionCount++;
			else if(game.getCard(s).getType() == CardType.WEAPON)
				suggestionCount++;
			else if(game.getCard(s).getType() == CardType.ROOM)
				suggestionCount++;
		}
		Assert.assertEquals(3, suggestionCount);
		
		int handCount = 0;
		ArrayList<Card> hackerHand = computer.getHand();
		for(Card c : hackerHand)
		{
			if(c.isA(suggestion.get(0)) || c.isA(suggestion.get(1)) || c.isA(suggestion.get(2)))
			{
				handCount++;
			}
		}
		Assert.assertTrue(handCount <= 1);
	}
	
	@Test
	public void computerSuggestion()
	{
		game.stackDeck(1);
		
		Player p = new ComputerPlayer();
		
		ArrayList<String> ans = p.makeSuggestion();
		// Only one suggestion available
		Assert.assertTrue(ans.get(0).equals("The Hacker") && ans.get(1).equals("SQL Injection") && ans.get(2).equals("Hall") );
		
		// multiple suggestion available
		game.stackDeck(2);
		int choiceA = 0;
		int choiceB = 0;
		
		for (int i = 0; i < 50; i++)
		{
			ans = p.makeSuggestion();
			if(ans.get(0).equals("The Hacker") && ans.get(1).equals("SQL Injection") && ans.get(2).equals("Hall"))
				choiceA++;
			else if (ans.get(0).equals("Mr. Cuddles") && ans.get(1).equals("SQL Injection") && ans.get(2).equals("Hall"))
				choiceB++;
		}
		Assert.assertTrue(choiceA > 0);
		Assert.assertTrue(choiceB > 0);
	}
}
