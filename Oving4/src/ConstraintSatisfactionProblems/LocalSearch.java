package ConstraintSatisfactionProblems;

import java.util.ArrayList;

public class LocalSearch {
	
	private ArrayList<ArrayList<Integer>> board; //Selve brettet. 1(Queen)|0(Empty)
	private int k; //Dimensjonen på brettet (K*K)
	private int antallSimuleringer = 0;
	private boolean continueSimulation = true;
	
	
	public LocalSearch(int k){
		board = new  ArrayList<ArrayList<Integer>>();
		this.k = k;
		init();
		
		
//		System.out.println("-------------------------------");
//		System.out.println(" ");
//		System.out.println("This is the board:");
//		System.out.println(" ");
//		System.out.println(board.get(0).toString());
//		System.out.println(board.get(1).toString());
//		System.out.println(board.get(2).toString());
//		System.out.println(board.get(3).toString());
//		System.out.println(" ");
//		System.out.println("-------------------------------");
//		System.out.println(" ");
		
	}
	
	/**
	 * Lager et startbrett med K dronninger.
	 * Hver dronning blir plassert i hver sin rad i tilfeldig kolonne.
	 */
	
	public void init(){
		
		//Lager KxK brett
		for(int i=0; i<k; i++){
			ArrayList<Integer> temp = new ArrayList<>(5);
			for(int j=0; j<k; j++){
				temp.add(j,0);
			}
			board.add(temp);
			
			//Plasserer dronning i hver rad
			double random = Math.random()*k;
			boolean placeQueen = true;
			while(placeQueen){
				if(board.get(i).get((int)random)==0){
					board.get(i).set((int)random, 1);
					placeQueen=false;
				}
				
			}
		}
	}
	
	public ArrayList<ArrayList<Integer>> simulate(){
		
		
		while(continueSimulation && antallSimuleringer<k*100){
			
			double randomRow = Math.random()*k;
			ArrayList<Integer> evaluatedRow = evaluateRow((int)randomRow);
			
//			System.out.println("Random row: " + (int)randomRow);
//			System.out.println("EvaluatedRow: " + evaluatedRow.toString());
			
			int bestValue = k*k;
			int bestPos = 0;
			int currentCol = 0;
			
			for(int i=0; i<k; i++){
				if(evaluatedRow.get(i)<bestValue){
					bestValue = evaluatedRow.get(i);
				}
				if(board.get((int)randomRow).get(i)==1){
					currentCol=i;
				}
			}

			boolean swapping = true;
			
			while(swapping){
				double randomCol = Math.random()*k;
				if(evaluatedRow.get((int)randomCol)==bestValue){
					board.get((int)randomRow).set(currentCol, 0);
					board.get((int)randomRow).set((int)randomCol, 1);
					swapping=false;
				}
				
			}

			if(haveWon()){
				continueSimulation=false;
			}

			antallSimuleringer++;
		}
		
		if(continueSimulation){
			System.out.println("No optimal solution was found");
			System.out.println("Simulations: " + antallSimuleringer);
		}
		else{
			System.out.println("We found a optimal solution");
			System.out.println(" ");
//			for(int i=0; i<k; i++){
//				System.out.println(board.get(i).toString());
//			}
			System.out.println(" ");
			System.out.println("Simulations: " + antallSimuleringer);
		}
		
		return board;
	}
	
	
	/**
	 * Denne metoden skal ta inn en rad fra board, evaluere alle plassene i raden og finne den
	 * posisjoenne som kolliderer med minst queens.
	 * @param int row - Velges random når metoden kalles opp
	 * @return int col - Den kolonnen i row som kolliderer med minst queens
	 * 
	 */
	
	public ArrayList<Integer> evaluateRow(int row){
		int currentRow = row;
		int currentCol = 0;
		
		for(int i=0; i<k; i++){
			if(board.get(currentRow).get(i)==1)
				currentCol=i;
		}
		
		
		ArrayList<Integer> temp = new ArrayList<>();
		
		//i representerer "col", row we input
		for(int i=0; i<k;i++){
			int collisions = 0;
			collisions += countVertical(row, i, currentCol);
			collisions += countDiagonalDown(row, i, currentCol);
			collisions += countDiagonalUp(row, i, currentCol);
			temp.add(collisions);
		}
		
		return temp;
		
	}
	
	
	public int countVertical(int row, int col, int currentCol){
		int count=0;
		for(int i=0; i<k;i++){
			if(board.get(i).get(col)==1 && i!=row)
				count++;
		}
		return count;
		
	}
	
	public int countDiagonalDown(int row, int col, int currentCol){
		
		int count = 0;
		int startRow = row;
		int startCol = col;
		
		while((startRow-1)>=0 && (startCol-1)>=0){
			startRow--;
			startCol--;
		}
		
		while(startRow<k && startCol<k){
			if(board.get(startRow).get(startCol)==1 && startRow!=row ){
				count++;
			}
			startRow++;
			startCol++;
		}
		
		return count;
	}
	
	public int countDiagonalUp(int row, int col, int currentCol){
		int count = 0;
		int startRow = row;
		int startCol = col;
		
		while(((startRow-1)>=0) && ((startCol+1)<k)){
			startRow--;
			startCol++;
		}
		
		while(startRow<k && startRow>=0 && startCol>=0 && startCol<k){

			if(board.get(startRow).get(startCol)==1 && row!=startRow){
				count++;
			}
			startRow++;
			startCol--;
		}
		return count;
	}
	

	public boolean haveWon(){
		for(int i = 0; i<k; i++){
			ArrayList<Integer> temp = evaluateRow(i);
			for(int j = 0; j<k; j++){
				if(board.get(i).get(j)==1 && temp.get(j)!=0)
					return false;
			}
		}
		return true;
	}
	
}
