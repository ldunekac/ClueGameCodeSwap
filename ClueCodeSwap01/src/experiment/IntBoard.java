package experiment;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class IntBoard {
	private int maxRow = 4,
				maxCol = 4;
	
	private HashSet<Integer> targetsHS;
	private HashMap<Integer, Boolean> visitedHM;
	private HashMap<Integer, LinkedList<Integer>> adjHM;
	
	public IntBoard() {
		adjHM = new HashMap<Integer, LinkedList<Integer>>();
		targetsHS = new HashSet<Integer>();
		visitedHM = new HashMap<Integer, Boolean>();
	}

	private boolean isSquareValid(int row, int col, int maxRow, int maxCol) {
		if (row < 0 || col < 0 || row >= maxRow || col >= maxCol) 
			return false;			
		return true;
	}
	
	public void calcAdjacencies() {	
		int onSquare = 0;
		LinkedList<Integer> l;
		
		for (int r = 0; r < maxRow; r++) {
			for (int c = 0; c < maxCol; c++) {
				l = new LinkedList<Integer>();
				if (isSquareValid(r-1, c, maxRow, maxCol)) {
					l.add(calcIndex(r-1,c));
				}
				if (isSquareValid(r, c-1, maxRow, maxCol)) {
					l.add(calcIndex(r,c-1));
				}
				if (isSquareValid(r+1, c, maxRow, maxCol)) {
					l.add(calcIndex(r+1,c));
				}
				if (isSquareValid(r, c+1, maxRow, maxCol)) {
					l.add(calcIndex(r,c+1));
				}
				if (l.size() > 0) {
					adjHM.put(onSquare, l);										
				}
				onSquare++;
			}			
		}
	}


	public void findTargets(int place, int steps) {
		if (steps == 0) {
			targetsHS.add(place);
			return;
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
		targetsHS.clear();
		for (int i = 0; i < maxRow*maxCol; i++) {
			visitedHM.put(i, false);
		}
		visitedHM.put(place, true);
		findTargets(place, steps);
	}	

	public HashSet<Integer> getTargets() {		
		return (HashSet<Integer>) targetsHS.clone(); 
	}	
	
	public LinkedList<Integer> getAdjList(int place) {
		return (LinkedList<Integer>) adjHM.get(place).clone();
	}
	
	public int calcIndex(int row, int column) {
		return (row * 4) + column;
	}

}
