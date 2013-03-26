package clueGame;

public class Card {
	public String name;
	public CardType cardType;
	
	public Card(String name, CardType type)
	{
		this.name = name;
		cardType = type;
	}
	
	public Card(String name)
	{
		this.name = name;
		cardType = null;
	}
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	public CardType getType() {
		// TODO Auto-generated method stub
		return cardType;
	}

	// TESTING PURPOSES
	public boolean isA(String string) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
