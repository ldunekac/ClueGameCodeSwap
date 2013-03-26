package clueGame;

import java.util.Random;
import java.util.Set;

public class ComputerPlayer extends Player {
	private char lastRoomVisited;
	
	public ComputerPlayer(){
		super("name", "Color", 0,0);
		init();
	}
	public ComputerPlayer(String name, String color, int locx, int locy)
	{
		super(name, color, locx, locy);
		init();
	}
	
	private void init()
	{
		lastRoomVisited = 'z';
	}
	
	public BoardCell pickLocation(Set<BoardCell> targets)
	{
		// Check if a room in site that is not last visited
		for(BoardCell b : targets)
		{
			if(b.isRoom() && ((RoomCell)b).getInitial() != lastRoomVisited)
			{
				return b;
			}
		}
		
		Random r = new Random();
		int choice = r.nextInt(targets.size());
		
		for(BoardCell b : targets)
		{
			if(choice == 0) return b;
			choice--;
		}
		// will never be hit
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

	public void setLastVisited(char location) {
		lastRoomVisited = location;
		
	}
	@Override
	public Solution makeAccusation(String person, String weapon, String room) {
		// TODO Auto-generated method stub
		return null;
	}

}
