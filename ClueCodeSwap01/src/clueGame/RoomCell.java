package clueGame;

public class RoomCell extends BoardCell {
	public enum DoorDirection { UP, DOWN, LEFT, RIGHT, NONE };
	private DoorDirection doorDirection;
	private char roomType;
	
	public RoomCell(int nRow, int nColumn, char nRoomType) {
		super();
		row = nRow;
		column = nColumn;
		roomType = nRoomType;
		doorDirection = DoorDirection.NONE;
	}

	public RoomCell(int nRow, int nColumn, char nRoomType, DoorDirection nDoorDirection) {
		super();
		row = nRow;
		column = nColumn;
		roomType = nRoomType;
		doorDirection = nDoorDirection;		
	}
	
	
	public RoomCell() {
		super();
		row = -1;
		column = -1;
		roomType = ' ';
		doorDirection = DoorDirection.NONE;
	}
	
	@Override
	public boolean isRoom() {
		return true;
	}
	
	@Override
	public boolean isWalkway() {
		return false;
	}
	
	@Override
	public boolean isDoorway() {
		if (doorDirection != DoorDirection.NONE)
			return true;
		return false;
	}
	
	public DoorDirection getDoorDirection() {
		return this.doorDirection;
	}
	
	public char getInitial() {
		return roomType;
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}

}
