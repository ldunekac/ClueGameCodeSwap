package clueTests;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import clueGame.Card;
import clueGame.CardType;
import clueGame.ClueGame;
import clueGame.Player;

public class GameSetupTests {
	ClueGame game;
	
	@Before
	public void setup()
	{
		game = new ClueGame();
	}
	
	@Test
	// Test loading people
	public void LoadPeople()
	{
		ArrayList<Player> players = game.getPlayers();
		System.out.println(players.size());
		Set<Player> playerSet = new HashSet<Player>();
		System.out.println("hello");
		for (Player p : players)
		{
			playerSet.add(p);
		}
		System.out.println(playerSet.size());
		Assert.assertEquals(players.size(), playerSet.size());

		int check = 0;
		// Checks the players Name, Color, and starting location
		for (Player p : players)
		{
			if(p.getName().equals("Mr. Cuddles"))
			{
				check++;
				Point point = new Point(0,2);
				Assert.assertEquals("Blue", p.getColor());
				Assert.assertTrue(p.isHuman());
				Assert.assertTrue(point.equals(p.getLocation()));
			}
			else if (p.getName().equals("The Hacker"))
			{
				check++;
				Point point = new Point(0,20);
				Assert.assertEquals("Green", p.getColor());
				Assert.assertTrue(p.isComputer());
				Assert.assertTrue(point.equals(p.getLocation()));
			}else if (p.getName().equals("The Duck Master"))
			{
				check++;
				Point point = new Point(19,10);
				Assert.assertEquals("Orange", p.getColor());
				Assert.assertTrue(p.isComputer());
				Assert.assertTrue(point.equals(p.getLocation()));
			}
		}
		Assert.assertEquals(3, check);
	}
	
	@Test
	// Test loading Cards
	public void loadingCards()
	{
		// Loading Cards
		// test if each card is unique and have right amount
		ArrayList<Card> cards = game.getDeck();
		Set<Card> cardSet = new HashSet<Card>();
		
		for(Card c : cards)
		{
			cardSet.add(c);
		}
		Assert.assertEquals(21, cardSet.size());
		// Make sure cards are right type
		int check = 0;
		for(Card c : cards)
		{
			if(c.getName().equals("Mr. Cuddles"))
			{
				check++;
				Assert.assertTrue(c.getType() == CardType.PERSON);
			}
			else if (c.getName().equals("SQL Injection"))
			{
				check++;
				Assert.assertTrue(c.getType() == CardType.WEAPON);
			}
			else if (c.getName().equals("Kitchen"))
			{
				check++;
				Assert.assertTrue(c.getType() == CardType.ROOM);
			}
		}
	}
	
	@Test
	public void dealingCards()
	{
		// Dealing
		// We are assuming each player will hold 3 cards
		Set<Card> cardHands = new HashSet<Card>();
		for(Player p: game.getPlayers())
		{
			for(Card c : p.getHand())
			{
				cardHands.add(c);
			}
			Assert.assertEquals(3, p.getHand().size());
		}
		Assert.assertEquals(18, cardHands.size());
	}
}
