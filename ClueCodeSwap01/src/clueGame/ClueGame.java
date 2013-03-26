package clueGame;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class ClueGame {
	private ArrayList<Player> players;
	private ArrayList<Card> deck;
	private Solution solution;
	private Board board;
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
		board = new Board();
		board.loadConfigFiles();
		board.calcAdjacencies();
		dealCards();
		// Assign Mr Cuddles and thehacker
		for(Player p : players)
		{
			if(p.name.equals("Mr. Cuddles"))
				MrCuddles = p;
			else if (p.name.equals("The Hacker"))
				theHacker = p;
		}
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
				if(!firstWeapon && deck.get(i).getName().equals("SQL Injection")) 
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
				if(deck.get(i).name.equals("SQL Injection"))
				{
					for(Player p : players)
					{
						if (p.getName().equals("Mr. Cuddles"))
							p.addCardToHand(deck.get(i));
					}
				}
				else {
				players.get(currentLocation).addCardToHand(deck.get(i));
				currentLocation = (currentLocation + 1) % playerSize;
				}
			}
		}
	}
	
	public void selectAnswer()
	{
		
	}
	
	public String handleSuggestion(String person, String room, String weapon, Player accusingPerson)
	{
		Random r = new Random();
		int current = r.nextInt(players.size());
		
		for(int i = 0; i < players.size(); i++)
		{
			if(!(players.get(current) == accusingPerson))
			{
				if(players.get(current).containsCard(person))
					return person;
				if(players.get(current).containsCard(room))
					return room;
				if(players.get(current).containsCard(weapon))
					return weapon;
			}
			current = (current + 1) % players.size();
		}
		return "";
	}
	
	
	public boolean checkAccusation(Solution guessSolution)
	{
		if(guessSolution.person.name.equals(solution.person.name) &&
				guessSolution.weapon.name.equals(solution.weapon.name) && 
				guessSolution.room.name.equals(solution.room.name))
			return true;
		return false;
	}

	// TESTING ONLY
	public ArrayList<Player> getPlayers() {
		return players;
	}
	
	
	public ArrayList<Card> getDeck() {
		return deck;
	}

	public Solution getSolution() {
		return solution;
	}

	public Card getCard(String s) {
		for(Card c: deck)
		{
			if(c.getName().equals(s))
				return c;
		}
		return null;
	}

	public Board getBoard() {
		// TODO Auto-generated method stub
		return board;
	}

	public void setSolution(String person, String weapon, String room) {
		solution.person = new Card(person);
		solution.weapon = new Card(weapon);
		solution.room = new Card(room);
	}

	public void stackDeck(int stackNumber) {
		// TODO Auto-generated method stub
		
	}

}
