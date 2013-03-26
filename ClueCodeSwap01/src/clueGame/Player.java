package clueGame;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public abstract class Player {
	protected String name;
	protected String color;
	protected ArrayList<Card> hand;
	protected Set<String> seen;
	protected Point point;

	public Player(){}
	public Player(String name, String color, int locx, int locy)
	{
		this.name = name;
		this.color = color;
		point = new Point(locx, locy);
		hand = new ArrayList<Card>();
		seen = new HashSet<String>();
	}
	
	public Card disproveSuggestion(String person, String room, String weapon)
	{
		return new Card("None", CardType.PERSON);
	}
	// FOR TESTING ONLY
	abstract public boolean isComputer();
	abstract public boolean isHuman();
	abstract public Solution makeAccusation(String person, String weapon, String room);
	
	public void addCardToHand(Card card) {
		hand.add(card);
	}	
	
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}
	public String getColor() {
		// TODO Auto-generated method stub
		return color;
	}
	public Point getLocation() {
		// TODO Auto-generated method stub
		return point;
	}
	public ArrayList<Card> getHand() {
		// TODO Auto-generated method stub
		return hand;
	}
	public boolean containsCard(String card) {
		for(Card c : hand)
		{
			if(c.getName().equals(card))
				return true;
		}
		return false;
	}
	public ArrayList<String> makeSuggestion(ArrayList<Card> deck) 
	{
		ArrayList<String> ans = new ArrayList<String>();
		Random r = new Random();
		int current = r.nextInt(deck.size());
		boolean seenRoom = false;
		boolean seenWeapon = false;
		boolean seenPerson = false;

		for(int i = 0; i < deck.size(); i++)
		{
			if(!seen.contains(deck.get(current).name))
			{


				switch(deck.get(current).getType())
				{
					case PERSON: 
						if(!seenPerson)
						{
							seenPerson = true;
							ans.add(deck.get(current).name);
						}
						break;
					case WEAPON:
						if(!seenWeapon)
						{
							seenWeapon = true;
							ans.add(deck.get(current).name);
						}
						break;
					case ROOM:
						if(!seenRoom)
						{
							seenRoom = true;
							ans.add(deck.get(current).name);
						}
						break;
				}
			}
			current = (current + 1) % deck.size();
		}
		if(!seenRoom)
		{
			for(Card c : deck)
			{
				if(c.getType() == CardType.ROOM)
				{
					ans.add(c.getName());
					break;
				}
			}
		}
		if(!seenWeapon)
		{
			for(Card c : deck)
			{
				if(c.getType() == CardType.WEAPON)
				{
					ans.add(c.getName());
					break;
				}
			}
		}
		if(!seenPerson)
		{
			for(Card c : deck)
			{
				if(c.getType() == CardType.PERSON)
				{
					ans.add(c.getName());
					break;
				}
			}
		}
		return ans;
	}
	public void seenAlmostEverything(int which, ArrayList<Card> deck) {
		if (which == 1)
		{
			for(Card c: deck)
			{
				if(!(c.getName().equals("The Hacker") || c.getName().equals("SQL Injection") || c.getName().equals("Hall")))
					seen.add(c.getName());
			}
		}
		else
		{
			for(Card c: deck)
			{
				if(!(c.getName().equals("The Hacker") || c.getName().equals("SQL Injection") ||
						c.getName().equals("Hall") || c.getName().equals("Mr. Cuddles")))
					seen.add(c.getName());
			}
			seen.remove("Mr. Cuddles");
		}
	}
	
}
