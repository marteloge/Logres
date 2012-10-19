package ConstraintSatisfactionProblems;

import java.util.ArrayList;

public class KQueenPuzzle {
	
	static ArrayList<ArrayList<Integer>> board = new ArrayList<ArrayList<Integer>>();
	
		
	public static void main(String[] args) {
	
		
		long startTime = System.currentTimeMillis();
		
		
		LocalSearch LS = new LocalSearch(220);
		board = LS.simulate();
		
		long stopTime = System.currentTimeMillis();
		long total = stopTime - startTime;
		
		System.out.println("Starttime: " + startTime);
		System.out.println("Stoptime: " + stopTime);
		
		System.out.println(total);
		
		
	}
		
}
