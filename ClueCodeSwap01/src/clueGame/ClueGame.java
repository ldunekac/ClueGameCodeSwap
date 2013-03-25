package clueGame;

import java.util.ArrayList;
import java.util.Scanner;

public class ClueGame {
	private ArrayList<Player> players;
	private ArrayList<Card> deck;
	// Testing 
	public Player MrCuddles;
	public Player theHacker;
	
	public ClueGame()
	{
		loadConfigFiles();
	}
	
	// Stack the Deck constructor Not to be used in Game
	public ClueGame(boolean b) {
		// TODO Auto-generated constructor stub

		
	}
	// Loading Files
	 
	public void loadConfigFiles()
	{
		loadPeople();
	}
	
	private void loadPeople()
	{
		Scanner scan = new Scanner("Peopleconfig.txt");
		String line;
		String[] split;
		boolean first = false;
		while(scan.hasNext())
		{
			line = scan.nextLine().replace(" ", "");
			split = line.split(",");
			if (first)
				players.add(new HumanPlayer(split[0], split[1], Integer.parseInt(split[2]), Integer.parseInt(split[3])));
			else
				players.add(new ComputerPlayer(split[0], split[1], Integer.parseInt(split[2]), Integer.parseInt(split[3])));
		}
	}
	
	// Action Functions
	
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
