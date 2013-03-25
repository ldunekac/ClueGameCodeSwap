package clueGame;

public class HumanPlayer extends Player {
	private static final String DEFAULT_NAME = "Mr. Cuddles";

	public HumanPlayer()
	{
		super(DEFAULT_NAME, "None", 0,0);
	}
	
	public HumanPlayer(String name, String color, int locx, int locy)
	{
		super(name, color, locx, locy);
	}

	@Override
	public boolean isComputer() {
		return false;
	}

	@Override
	public boolean isHuman() {
		return true;
	}
}
