package clueGame;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Board {
	private ArrayList<BoardCell> cells;
	private Map<Character, String> rooms;	
	private Map<Integer, LinkedList<Integer>> adjHM;
	private ArrayList<Integer> targets;
	private Map<Integer, Boolean> visitedHM;
	
	private int numRows, numColumns;
	private final String DEFAULT_LAYOUT_FILE = "ClueLayout.csv";
	private final String DEFAULT_LEGEND_FILE = "ClueLegend.txt";
	private String layoutFile;
	private String legendFile;
	private int startingCell;
	
	public Board() {
		__init__(DEFAULT_LAYOUT_FILE, DEFAULT_LEGEND_FILE);
	}

	public Board(String layoutFile, String legendFile) {
		__init__(layoutFile, legendFile);
	}
	
	private void __init__(String layout, String legendFile)
	{
		rooms = new HashMap<Character, String>();
		cells = new ArrayList<BoardCell>();
		adjHM = new HashMap<Integer, LinkedList<Integer>>();
		this.layoutFile = layout;
		this.legendFile = legendFile;
		//doorCells = new ArrayList<Integer>();
		targets = new ArrayList<Integer>();
		visitedHM = new HashMap<Integer, Boolean>();
	}
	
	public ArrayList<BoardCell> getCells() {
		return cells;
	}

	public Map<Character, String> getRooms() {
		return rooms;
	}
	
	public int getNumRows() {
		//System.out.println("rows: " + numRows);
		return numRows;
	}

	public int getNumColumns() {
		//System.out.println("cols: " + numColumns);
		return numColumns;
	}

	public void loadRoomConfig() {
		FileReader layoutFileReader = null;

		String currentPath = "";
		try {
			currentPath = new java.io.File( "." ).getCanonicalPath();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.out.println("Unable to find path.");
			System.exit(2);
		}
		
		currentPath = currentPath + File.separatorChar+ "ClueFiles" + File.separatorChar;
		try {
			layoutFileReader = new FileReader(currentPath + legendFile);
		} catch (FileNotFoundException e) {
			System.out.println("Unable to load file : " + currentPath + legendFile);
			System.exit(1); // Unrecoverable error
		}
		Scanner in = new Scanner(layoutFileReader);
		
		String[] lineInput = null;
		String line = null;		
		
		while (in.hasNext()) {
			line = in.nextLine();
			lineInput = line.split(",");			
			if (lineInput.length != 2)
				throw new BadConfigFormatException("Room legend is invalid");
			rooms.put(lineInput[0].charAt(0), lineInput[1].trim());
		}		
	}

	public void loadBoardConfig() {
		FileReader layoutFileReader = null;
		
		int rowSize = -1; 
		String currentPath = "";
		try {
			currentPath = new java.io.File( "." ).getCanonicalPath();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.out.println("Unable to find path.");
			System.exit(2);
		}
		
		currentPath = currentPath + File.separatorChar+ "ClueFiles" + File.separatorChar;

		try {
			layoutFileReader = new FileReader(currentPath+layoutFile);
		} catch (FileNotFoundException e) {
			System.out.println("Unable to load file : " + currentPath+layoutFile);
			System.exit(1); // Unrecoverable error
		}
		Scanner in = new Scanner(layoutFileReader);
		
		String[] lineInput = null;
		String line = null;
		int row = 0,
			column = 0;
		
		while (in.hasNext()) {
			line = in.nextLine().trim();
			if (rowSize == -1) {
				rowSize = line.split(",").length;
				numColumns = rowSize;
			} else {
				if (rowSize != line.split(",").length)
					throw new BadConfigFormatException("Row and Column Error");
			}
			lineInput = line.split(",");
			for (String i : lineInput) {
				i = i.trim();
				if (i.length() == 1) {
					if (rooms.containsKey(i.charAt(0))) {
						if (i.charAt(0) == 'W')
							cells.add(new WalkwayCell(row, column));
						else
							cells.add(new RoomCell(row, column, i.charAt(0)));
					} else
						throw new BadConfigFormatException("Room is not in legend file");
				} else if (i.length() == 2) {
					if (rooms.containsKey(i.charAt(0))) {
							cells.add(new RoomCell(row, column, i.charAt(0), parseDoorDirection(i.substring(1, 2).charAt(0))));
							//doorCells.add(calcIndex(row, column)
					} else {
						throw new BadConfigFormatException("Room is not in legend file");
					}
				} else {
					throw new BadConfigFormatException("Invalid data! Too many characters!");
				}
				column++;
			}
			row++;
			column = 0;
		}		
		numRows = row;
		//System.out.println("Board size = " + numRows+","+numColumns);
	}
	
	public RoomCell.DoorDirection parseDoorDirection(char c) {
		switch (c) {
		case 'N':	return RoomCell.DoorDirection.NONE;
		case 'U': 	return RoomCell.DoorDirection.UP;
		case 'D': 	return RoomCell.DoorDirection.DOWN;
		case 'L': 	return RoomCell.DoorDirection.LEFT;
		case 'R': 	return RoomCell.DoorDirection.RIGHT;
		default: 	throw new BadConfigFormatException("Door Direction Invalid");
		}
	}
	
	public void loadConfigFiles() {
		loadRoomConfig();
		loadBoardConfig();
	}
	
	public int calcIndex(int row, int column) {
		return (row * numColumns) + column;
	}

	public RoomCell getRoomCellAt(int row, int column) {		
		return (RoomCell) cells.get(calcIndex(row, column));
	}
	
	public BoardCell getCellAt(int index) {
		return cells.get(index);
	}
	
	public BoardCell getCellAt(int row, int column) {
		return cells.get(calcIndex(row, column));
	}
	
	private boolean isSquareValid(int row, int col) {
		if (row < 0 || col < 0 || row >= numRows || col >= numColumns) 
			return false;			
		return true;
	}
	
	// DO not modify! That means you Luke.
	public void calcAdjacencies() {
		int onSquare = 0;
		LinkedList<Integer> l;
		
		for (int r = 0; r < numRows; r++) {
			for (int c = 0; c < numColumns; c++) {
				l = new LinkedList<Integer>();
				

				if (getCellAt(r, c).isRoom()) {
					// Don't look in closet or inside a room
					if (getRoomCellAt(r,c).getInitial() == 'X' || !getRoomCellAt(r,c).isDoorway()) {
						adjHM.put(onSquare, new LinkedList<Integer>());
						onSquare++;
						continue;
					}										
				}

				
				// Walkways & Doors
				if (isSquareValid(r-1, c) && ((getCellAt(r-1,c).isWalkway() 
						|| getRoomCellAt(r-1,c).getDoorDirection() == RoomCell.DoorDirection.DOWN))) 
				{				
					l.add(calcIndex(r-1,c));
				}
				if (isSquareValid(r, c-1) && ((getCellAt(r,c-1).isWalkway() 
						|| getRoomCellAt(r,c-1).getDoorDirection() == RoomCell.DoorDirection.RIGHT))) 
				{					
					l.add(calcIndex(r,c-1));
				}
				if (isSquareValid(r+1, c) && ((getCellAt(r+1,c).isWalkway() 
						|| getRoomCellAt(r+1,c).getDoorDirection() == RoomCell.DoorDirection.UP))) 
				{					
					l.add(calcIndex(r+1,c));
				}
				if (isSquareValid(r, c+1) && ((getCellAt(r,c+1).isWalkway() 
						|| getRoomCellAt(r,c+1).getDoorDirection() == RoomCell.DoorDirection.LEFT))) 
				{					
					l.add(calcIndex(r,c+1));
				}
				if (l.size() > 0) {					
					adjHM.put(onSquare, l);
					//System.out.println(onSquare + " " + l);
				}
				onSquare++;
				
			}			
		}
	}
	
	public void findTargets(int place, int steps) {
		if (steps == 0) {
			BoardCell b = getCellAt(place);
			if (b.isDoorway() || b.isWalkway()) {
				targets.add(place);
			}
			return;
		}
	
		if (startingCell != place) {
			BoardCell b = getCellAt(place);
			if (b.isDoorway()) 
				targets.add(place);
		}
		LinkedList<Integer > l = adjHM.get(place);
		for (Integer i : l) {
			if (visitedHM.get(i) == false) {
				visitedHM.put(i, true);
				findTargets(i,steps-1);
				visitedHM.put(i, false);
			}
		}
	}	
	
	public void startTargets(int place, int steps) {		
		targets.clear();
		for (int i = 0; i < numRows*numColumns; i++) {
			visitedHM.put(i, false);
		}
		visitedHM.put(place, true);
		findTargets(place, steps);
	}	
	
	public void calcTargets(int row, int column, int steps) {
		startingCell = calcIndex(row, column);
		startTargets(calcIndex(row, column), steps);
	}
	
	public Set<BoardCell> getTargets() {
		Set<BoardCell> s = new HashSet<BoardCell>();
		for (Integer i : targets) {
			s.add(cells.get(i));		
		}
	
		return s;
	}

	public LinkedList<Integer> getAdjList(int place) {
		return (LinkedList<Integer>) adjHM.get(place);
	}
}
