package ConstraintSatisfactionProblems;

import java.util.ArrayList;

public class LocalSearch {
	
	private ArrayList<ArrayList<Integer>> board; //Selve brettet. 1(Queen)|0(Empty)
	private int k; //Dimensjonen på brettet (K*K)
	private int antallSimuleringer = 0;
	private boolean continueSimulation = true; //settes til false om vi har funnet en optimal løsning.
	
	
	public LocalSearch(int k){
		board = new  ArrayList<ArrayList<Integer>>();
		this.k = k;
		init();
		
		//Skriver ut startbrettet
		
		System.out.println("-------------------------------");
		System.out.println(" ");
		System.out.println("This is the startboard:");
		System.out.println(" ");
		for(int i = 0; i<k; i++){
			System.out.println(board.get(i).toString());
		}
		System.out.println(" ");
		System.out.println("-------------------------------");
		System.out.println(" ");
		
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
	
	
	/**
	 * Dette er selve simuleringen av det lokale søket etter en optimal løsning.
	 * @return ArrayList<Arraylist<Integer>> som forhåpentligvis er en optimal løsning på
	 * problemet.
	 */
	
	public ArrayList<ArrayList<Integer>> simulate(){
				
		//Dette er en while-løkke som begrenser at algoritmen skal kjøre i det uendelige
		//velger k*100 for at den skal få kjøre nok, men ikke for mange ganger.
		
		while(continueSimulation && antallSimuleringer<k*100){
			
			//Velger ut en random rad
			double randomRow = Math.random()*k;
			//Evaluerer den random raden (evaluerer hver posisjon i raden)
			ArrayList<Integer> evaluatedRow = evaluateRow((int)randomRow);
			
			int bestValue = k*k; //Setter k*k bare for å ha en stoor verdi som evaluateRow ikke vil komme frem til
			int currentCol = 0;
			
			//Finner den beste verdin den fant i den evaluerte raden
			for(int i=0; i<k; i++){
				if(evaluatedRow.get(i)<bestValue){
					bestValue = evaluatedRow.get(i);
				}
				if(board.get((int)randomRow).get(i)==1){
					currentCol=i;
				}
			}

			boolean swapping = true;
			
			//Bytter posisjon på dronning
			while(swapping){
				// Må ha random siden flere verdier kan være like som kan føre til at en løsning kjører seg fast.
				double randomCol = Math.random()*k;
				if(evaluatedRow.get((int)randomCol)==bestValue){
					board.get((int)randomRow).set(currentCol, 0);
					board.get((int)randomRow).set((int)randomCol, 1);
					swapping=false;
				}
				
			}
			
			//Sjekker om byttet førte til en optimal løsning
			if(haveWon()){
				continueSimulation=false;
			}

			antallSimuleringer++;
		}
		
		//Hvis vi ikke brøt simuleringen selv så vil det si at den kjørte for lenge uten å finne en 
		//optimal løsning
		
		if(continueSimulation){
			System.out.println("No optimal solution was found");
			System.out.println("Simulations: " + antallSimuleringer);
		}
		
		//Hvis vi brøt løkken selv og fant en optimal løsning.
		else{
			System.out.println("We found a optimal solution");
			System.out.println(" ");
			for(int i=0; i<k; i++){
				System.out.println(board.get(i).toString());
			}
			System.out.println(" ");
			System.out.println("Simulations: " + antallSimuleringer);
		}
		
		
		//Returnerer brettet (ikke nødvendigvis en optimal løsning)
		return board;
	}
	
	
	/**
	 * Denne metoden skal ta inn en rad fra board, evaluere alle plassene i raden og 
	 * notere hvor mange dronninger det kolliderer med om man plasserer dronningen i rad i i kolonne j
	 * @param int row - Er valgt random når metoden kalles opp
	 * @return ArrayList<Integer> - En hel rad der alle posisjoner er evaluert i forhold til board
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
	
	/**
	 * Teller alle kollisjoner i vertikal retning i forhold til posisjon (x,y)
	 * @param row - x-posisjon på brettet
	 * @param col - y-posisjon på brettet
	 * @param currentCol - 
	 * @return
	 */
	
	
	public int countVertical(int row, int col, int currentCol){
		int count=0;
		for(int i=0; i<k;i++){
			if(board.get(i).get(col)==1 && i!=row)
				count++;
		}
		return count;
		
	}
	
	/**
	 * Teller alle kollisjoner i diagonal retning (ned) i posisjoenen (x, y)
	 * @param row
	 * @param col
	 * @param currentCol
	 * @return
	 */
	
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
	
	/**
	 * Teller alle kollisjoner i diagonal retning (opp) i posisjoenen (x, y)
	 * @param row
	 * @param col
	 * @param currentCol
	 * @return
	 */
	
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
	
	/**
	 * Sjekker om brettet er en optimal løsning på problemet
	 * @return true/false: board == optimal solution?
	 */
	
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
	
	public int multiply(int a, int b) {
		return a*b;
	}
	
}
