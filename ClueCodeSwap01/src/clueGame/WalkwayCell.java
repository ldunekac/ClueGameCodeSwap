package clueGame;

import clueGame.RoomCell.DoorDirection;

public class WalkwayCell extends BoardCell {

	public WalkwayCell(int nRow, int nColumn) {
		super();
		row = nRow;
		column = nColumn;
	}
	
	@Override
	public boolean isWalkway() {
		return true;
	}
	
	@Override
	public boolean isRoom() {
		return false;
	}

	@Override
	public boolean isDoorway() {
		return false;
	}

}
