package clueGame;

import java.util.ArrayList;

public class ClueGame {
	private ArrayList<Player> players;
	private ArrayList<Card> deck;
	// Testing 
	public Player MrCuddles;
	public Player theHacker;
	
	public ClueGame()
	{
		
	}
	
	// Stack the Deck constructor Not to be used in Game
	public ClueGame(boolean b) {
		// TODO Auto-generated constructor stub

		
	}

	public void loadConfigFiles()
	{
		
	}
	
	public void selectAnswer()
	{
		
	}
	
	public String handleSuggestion(String person, String room, String weapon, Player accusingPerson)
	{
		// TODO
		return "";
	}
	
	public boolean checkAccusation(Solution solution)
	{
		return false;
	}

	// TESTING ONLY
	public ArrayList<Player> getPlayers() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	public ArrayList<Card> getDeck() {
		// TODO Auto-generated method stub
		return null;
	}

	public Solution getSolution() {
		// TODO Auto-generated method stub
		return null;
	}

	public Card getCard(String s) {
		// TODO Auto-generated method stub
		return null;
	}

	public Board getBoard() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setSolution(String person, String weapon, String room) {
		// TODO Auto-generated method stub
		
	}

	public void stackDeck(int stackNumber) {
		// TODO Auto-generated method stub
		
	}
}
