package ConstraintSatisfactionProblems;

import java.util.ArrayList;

public class LocalSearch {
	
	private ArrayList<ArrayList<Integer>> board; //Selve brettet. 1(Queen)|0(Empty)
	private int k; //Dimensjonen p� brettet (K*K)
	private int antallSimuleringer = 0;
	private boolean continueSimulation = true; //settes til false om vi har funnet en optimal l�sning.
	
	
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
	 * Dette er selve simuleringen av det lokale s�ket etter en optimal l�sning.
	 * @return ArrayList<Arraylist<Integer>> som forh�pentligvis er en optimal l�sning p�
	 * problemet.
	 */
	
	public ArrayList<ArrayList<Integer>> simulate(){
				
		//Dette er en while-l�kke som begrenser at algoritmen skal kj�re i det uendelige
		//velger k*100 for at den skal f� kj�re nok, men ikke for mange ganger.
		
		while(continueSimulation && antallSimuleringer<k*100){
			
			//Velger ut en random rad
			double randomRow = Math.random()*k;
			//Evaluerer den random raden (evaluerer hver posisjon i raden)
			ArrayList<Integer> evaluatedRow = evaluateRow((int)randomRow);
			
			int bestValue = k*k; //Setter k*k bare for � ha en stoor verdi som evaluateRow ikke vil komme frem til
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
			
			//Bytter posisjon p� dronning
			while(swapping){
				// M� ha random siden flere verdier kan v�re like som kan f�re til at en l�sning kj�rer seg fast.
				double randomCol = Math.random()*k;
				if(evaluatedRow.get((int)randomCol)==bestValue){
					board.get((int)randomRow).set(currentCol, 0);
					board.get((int)randomRow).set((int)randomCol, 1);
					swapping=false;
				}
				
			}
			
			//Sjekker om byttet f�rte til en optimal l�sning
			if(haveWon()){
				continueSimulation=false;
			}

			antallSimuleringer++;
		}
		
		//Hvis vi ikke br�t simuleringen selv s� vil det si at den kj�rte for lenge uten � finne en 
		//optimal l�sning
		
		if(continueSimulation){
			System.out.println("No optimal solution was found");
			System.out.println("Simulations: " + antallSimuleringer);
		}
		
		//Hvis vi br�t l�kken selv og fant en optimal l�sning.
		else{
			System.out.println("We found a optimal solution");
			System.out.println(" ");
			for(int i=0; i<k; i++){
				System.out.println(board.get(i).toString());
			}
			System.out.println(" ");
			System.out.println("Simulations: " + antallSimuleringer);
		}
		
		
		//Returnerer brettet (ikke n�dvendigvis en optimal l�sning)
		return board;
	}
	
	
	/**
	 * Denne metoden skal ta inn en rad fra board, evaluere alle plassene i raden og 
	 * notere hvor mange dronninger det kolliderer med om man plasserer dronningen i rad i i kolonne j
	 * @param int row - Er valgt random n�r metoden kalles opp
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
	 * @param row - x-posisjon p� brettet
	 * @param col - y-posisjon p� brettet
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
	 * Sjekker om brettet er en optimal l�sning p� problemet
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
	
}
