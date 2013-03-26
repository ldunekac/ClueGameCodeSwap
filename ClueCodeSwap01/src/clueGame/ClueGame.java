package clueGame;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class ClueGame {
	private ArrayList<Player> players;
	private ArrayList<Card> deck;
	private Solution solution;
	// Testing 
	public Player MrCuddles;
	public Player theHacker;
	
	public ClueGame()
	{
		init();
	}
	
	// Stack the Deck constructor Not to be used in Game
	public ClueGame(boolean b) {
		// TODO Auto-generated constructor stub
		init();
		
	}
	private void init()
	{
		players = new ArrayList<Player>();
		deck = new ArrayList<Card>();
		solution = new Solution();
	}
	// Loading Files
	 
	public void loadConfigFiles()
	{
		try {
			loadPeople();
			loadCards();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void initGame()
	{
		dealCards();
	}
	
	
	private void loadPeople() throws FileNotFoundException
	{
		FileReader read = new FileReader("ClueFiles/Peopleconfig.txt");
		Scanner scan = new Scanner(read);
		String line;
		String[] split;
		boolean first = true;
		while(scan.hasNextLine())
		{
			line = scan.nextLine();
			split = line.split(",");
			if (first) {
				Player p = new HumanPlayer(split[0], split[1].replaceAll("\\s",""), Integer.parseInt(split[2].replaceAll("\\s","")), Integer.parseInt(split[3].replaceAll("\\s","")));
				players.add(p);
				first = false;
			}
			else
				players.add(new ComputerPlayer(split[0], split[1].replaceAll("\\s",""), Integer.parseInt(split[2].replaceAll("\\s","")), Integer.parseInt(split[3].replaceAll("\\s",""))));
		}
		scan.close();
	}
	
	private void loadCards() throws FileNotFoundException
	{
		FileReader read = new FileReader("ClueFiles/rooms.txt");
		Scanner scan = new Scanner(read);
		String line;
		String[] split;
		
		while(scan.hasNextLine())
		{
			line = scan.nextLine();
			split = line.split(",");
			Card c;
			if(!split[1].equals("Closet")){
				c = new Card(split[1], CardType.ROOM);
				deck.add(c);
			}
		}
		
		read = new FileReader("ClueFiles/Peopleconfig.txt");
		scan = new Scanner(read);
		
		while(scan.hasNextLine())
		{
			line = scan.nextLine();
			split = line.split(",");
			Card c = new Card(split[0], CardType.PERSON);
			deck.add(c);
		}
		
		read = new FileReader("ClueFiles/WeaponsConfig.txt");
		scan = new Scanner(read);
		
		while(scan.hasNextLine())
		{
			line = scan.nextLine();
			split = line.split(",");
			Card c = new Card(split[0], CardType.WEAPON);
			deck.add(c);
		}
		scan.close();
	}
	
	// Action Functions
	private void dealCards()
	{
		boolean firstRoom = false;
		boolean firstPerson = false;
		boolean firstWeapon = false;
		boolean[] seen = new boolean[deck.size()];
		
		for(int i = 0; i < deck.size(); i++)
		{
			seen[i] = false;
		}
		
		for(int i = 0; i < deck.size(); i++)
		{
			switch(deck.get(i).getType())
			{
			case PERSON: 
				if(!firstPerson) 
					{
					firstPerson = true; 
					seen[i] = true;
					solution.person = deck.get(i);
					}
				break;
			case WEAPON: 
				if(!firstWeapon) 
				{
					firstWeapon = true;
					seen[i] = true;
					solution.weapon = deck.get(i);
				}
				break;
			case ROOM: 
				if(!firstRoom) 
					{
					firstRoom = true; 
					seen[i] = true;
					solution.room = deck.get(i);
					}
				break;
			default: break;
			}
		}
		
		int playerSize = players.size();
		int currentLocation = 0;
		for(int i = 0; i < deck.size(); i++)
		{
			if(!seen[i]) 
			{
				players.get(currentLocation).addCardToHand(deck.get(i));
				currentLocation = (currentLocation + 1) % playerSize;
			}
		}
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
		return players;
	}
	
	
	public ArrayList<Card> getDeck() {
		// TODO Auto-generated method stub
		return deck;
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
