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
		game.loadConfigFiles();
		game.initGame();
	}
	
	@Test
	public void testAccusation()
	{
		// Test card uniqueness for solution
		Solution solution = game.getSolution();
		
		ArrayList<Player> players = game.getPlayers();
		
		for (Player p : players)
		{
			Assert.assertFalse(p.containsCard(solution.person.getName()));
			Assert.assertFalse(p.containsCard(solution.weapon.getName()));
			Assert.assertFalse(p.containsCard(solution.room.getName()));
		}
		Assert.assertTrue(game.getDeck().contains(solution.person));
		Assert.assertTrue(game.getDeck().contains(solution.weapon));
		Assert.assertTrue(game.getDeck().contains(solution.room));
		
		boolean correct;
		// Player accusation correct
		game.setSolution("The Hacker", "SQL Injection", "Ball Room");
		Player p = new HumanPlayer();
		correct = game.checkAccusation(p.makeAccusation("The Hacker", "SQL Injection", "Ball Room"));
		Assert.assertTrue(correct);
		// wrong person
		correct = game.checkAccusation(p.makeAccusation("Mr. Cuddles", "SQL Injection", "Ball Room"));
		Assert.assertFalse(correct);
		
		// wrong weapon
		correct = game.checkAccusation(p.makeAccusation("The Hacker", "Shrodinger's Cat", "Ball Room"));
		Assert.assertFalse(correct);
		
		// wrong room
		correct = game.checkAccusation(p.makeAccusation("The Hacker", "SQL Injection", "Kitchen"));
		Assert.assertFalse(correct);
		
	}
	
	@Test
	public void selectATargetLocation()
	{
		// Will go In room
		ComputerPlayer player = new ComputerPlayer();
		player.setLastVisited('Z');
		Board board = game.getBoard();
		board.calcTargets(4, 5, 2);
		int loc_4_3 = 0;
		
		int total = 120;
		for(int i = 0; i < total; i++)
		{
			BoardCell selected = player.pickLocation(board.getTargets());
			if (selected == board.getCellAt(board.calcIndex(4, 3)))
				loc_4_3++;
		}
		
		Assert.assertTrue(loc_4_3 == total);
		
		// Random one
		player = new ComputerPlayer();
		board = game.getBoard();
		board.calcTargets(0, 4, 2);
		int loc_0_6 = 0;
		int loc_1_5 = 0;

		total = 120;
		for(int i = 0; i < total; i++)
		{
			BoardCell selected = player.pickLocation(board.getTargets());
			if (selected == board.getCellAt(board.calcIndex(0, 6)))
				loc_0_6++;
			else if (selected == board.getCellAt(board.calcIndex(1, 5)))
				loc_1_5++;
			else 
				System.out.println(selected.getRow() + " " +selected.getColumn());
		}
		int temp = loc_0_6+loc_1_5;
		Assert.assertEquals(total, loc_0_6+loc_1_5);
		
		Assert.assertTrue(loc_0_6 > 30);
		Assert.assertTrue(loc_1_5 > 30);
		
		// Equal chance to go in room
		player = new ComputerPlayer();
		player.setLastVisited('C');
		board = game.getBoard();
		board.calcTargets(4,4,1);
		
		int loc_4_5 = 0;
		loc_4_3 = 0;
		int loc_5_4 = 0;
		
		total = 120;
		for(int i = 0; i < total; i++)
		{
			BoardCell selected = player.pickLocation(board.getTargets());
			if (selected == board.getCellAt(board.calcIndex(4, 5)))
				loc_4_5++;
			else if (selected == board.getCellAt(board.calcIndex(4, 3)))
				loc_4_3++;
			else if (selected == board.getCellAt(board.calcIndex(5, 4)))
				loc_5_4++;
		}
		
		Assert.assertEquals(total, loc_4_5+loc_4_3+loc_5_4);
		
		Assert.assertTrue(loc_4_5 > 15);
		Assert.assertTrue(loc_4_3 > 15);
		Assert.assertTrue(loc_5_4 > 15);

		
		
	}
	
	@Test
	public void makeSuggestion()
	{
	//	game = null;
	//	game = new ClueGame(true); // creates a stacked deck
	//	game.loadConfigFiles();
	//	game.initGame();
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
		Assert.assertTrue(ans == "");
			
		Player computer = game.theHacker;
		
		ArrayList<String> suggestion = computer.makeSuggestion(game.getDeck());
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
		
	}
	
	@Test
	public void computerSuggestion()
	{
		
		Player p = new ComputerPlayer();
		p.seenAlmostEverything(1, game.getDeck());
		ArrayList<String> ans = p.makeSuggestion(game.getDeck());
		// Only one suggestion available
		
		Assert.assertTrue(ans.contains("The Hacker") && ans.contains("SQL Injection") && ans.contains("Hall") );
		
		game = new ClueGame();
		game.loadConfigFiles();
		game.initGame();
		// multiple suggestion available
		int choiceA = 0;
		int choiceB = 0;
		p.seenAlmostEverything(2, game.getDeck());
		for (int i = 0; i < 50; i++)
		{
			ans = p.makeSuggestion(game.getDeck());
			if(ans.contains("The Hacker") && ans.contains("SQL Injection") && ans.contains("Hall"))
				choiceA++;
			else if (ans.contains("Mr. Cuddles") && ans.contains("SQL Injection") && ans.contains("Hall"))
				choiceB++;
		}
		Assert.assertTrue(choiceA > 0);
		Assert.assertTrue(choiceB > 0);
	}
}
