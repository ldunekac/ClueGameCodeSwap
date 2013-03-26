package clueGame;

public class Solution {
	public Card person;
	public Card weapon;
	public Card room;
	
	public boolean hasCard(String guess) {
		if(person.getName().equals(guess) || weapon.getName().equals(guess) || room.getName().equals(guess))
			return true;
		return false;
	}
}
