package clueGame;

public class HumanPlayer extends Player {
	private static final String DEFAULT_NAME = "Mr. Cuddles";

	public HumanPlayer()
	{
		super(DEFAULT_NAME);
	}
	
	public HumanPlayer(String name)
	{
		super(name);
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
