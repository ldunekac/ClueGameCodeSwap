package clueGame;

import java.awt.Point;
import java.util.ArrayList;

public abstract class Player {
	protected String name;
	protected String color;
	protected ArrayList<Card> hand;
	protected Point point;

	public Player(){}
	public Player(String name, String color, int locx, int locy)
	{
		this.name = name;
		this.color = color;
		point = new Point(locx, locy);
	}
	
	public Card disproveSuggestion(String person, String room, String weapon)
	{
		return new Card();
	}
	// FOR TESTING ONLY
	abstract public boolean isComputer();
	abstract public boolean isHuman();

	
	
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}
	public String getColor() {
		// TODO Auto-generated method stub
		return null;
	}
	public Point getLocation() {
		// TODO Auto-generated method stub
		return null;
	}
	public ArrayList<Card> getHand() {
		// TODO Auto-generated method stub
		return null;
	}
	public boolean containsCard(Card person) {
		// TODO Auto-generated method stub
		return false;
	}
	public ArrayList<String> makeSuggestion() {
		// TODO Auto-generated method stub
		return null;
	}
	public boolean makeAccusation(String string, String string2, String string3) {
		// TODO Auto-generated method stub
		return false;
	}	
	
}
