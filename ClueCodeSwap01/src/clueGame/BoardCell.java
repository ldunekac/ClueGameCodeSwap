package clueGame;

public abstract class BoardCell {
 
	protected int row, column;
	
	abstract public boolean isWalkway();

	abstract public boolean isRoom();
	
	abstract public boolean isDoorway();
	
	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}
}
