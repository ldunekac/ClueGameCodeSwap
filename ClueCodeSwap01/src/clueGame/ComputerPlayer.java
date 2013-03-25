package clueGame;

import java.util.Set;

public class ComputerPlayer extends Player {
	private char lastRoomVisited;
	
	public ComputerPlayer(){}
	public ComputerPlayer(String name, String color, int locx, int locy)
	{
		super(name, color, locx, locy);
	}
	
	public BoardCell pickLocation(Set<BoardCell> targets)
	{
		// TODO
		return null;
	}
	
	public void createSuggestion()
	{
		
	}
	
	public void updateSeen(Card seen)
	{
		
	}

	@Override
	public boolean isComputer() {
		return true;
	}

	@Override
	public boolean isHuman() {
		return false;
	}

	public void setLastVisited(String string) {
		// TODO Auto-generated method stub
		
	}
}
