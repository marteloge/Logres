package TheEggCartonPuzzle;

import java.util.ArrayList;

public class Metoder {

	private static ArrayList<ArrayList<Integer>> board = new ArrayList<ArrayList<Integer>>();
	
	
	//Genererer er nytt brett (tomt)
	public static void init(int row, int col){
		
		for(int i=0; i<row; i++){
			board.add(new ArrayList<Integer>());
		}
		
		for(int i=0; i<row;i++){
			for(int j=0; j<col; j++){
				board.get(i).add(0);
			}
		}
		
	}
	
	//Ikke ferdig!!!
	public static ArrayList<Integer> UpdateBoard (int x, int y, int k, ArrayList<Integer> board){
		ArrayList<Integer> temp = board;
		
		int vannrett = 0;
		int loddrett = 0;
		int skråNed = 0;
		int skråOpp = 0;
		
		//lager hjelpemetoder for å telle i alle retninger
		
		return temp;
	}
	
	//Teller antall 0 i vanrett posisjon
	public int CountVannrett(int x, ArrayList<ArrayList<Integer>> board){
		int count = 0;
		
		for(int i=0; i<board.get(0).size(); i++){
			if(board.get(x).get(i) == 1)
				count++;
		}
		
		return count;
	}
	
	//Teller antall 0 i loddrett posisjon 
	public int CountLoddrett(int y, ArrayList<ArrayList<Integer>> board){
		int count = 0;
		
		for(int i=0; i<board.size();i++){
			if(board.get(i).get(y)==1)
				count++;
		}
		
		return count;
				
	}
	
	//Teller antall 0 i negativ diagonal (utifra punktet (row,col))
	public static int CountDiagonalDown(int row, int col, ArrayList<ArrayList<Integer>> board){
		int count = 0;
		int startRow = row;
		int startCol = col;
		
		while((startRow-1)>=0 && (startCol-1)>=0){
			startRow--;
			startCol--;
		}
		
		
		while(startRow<board.size() && startCol<board.get(0).size()){
			
			if(board.get(startRow).get(startCol)==1)
				count++;
			startRow++;
			startCol++;
		}
		
		return count;
		
	}
	
	//Teller antall 0 i positiv diagonal (utifra punktet (row,col))
	public static int CountDiagonalUp(int row, int col, ArrayList<ArrayList<Integer>> board){
		int count = 0;
		int startRow = row;
		int startCol = col;
		
		while(((startRow-1)>=0) && ((startCol+1)<board.get(0).size())){
			startRow--;
			startCol++;
		}
		
		while(startRow<board.size() && startCol>=0){
			if(board.get(startRow).get(startCol)==1)
				count++;
			startRow++;
			startCol--;
		}
		
		return count;
	}
	
	
	
	public static void main(String[] args) {
		init(4,4);
		
		board.get(3).set(0, 1);
		board.get(3).set(1, 1);
		board.get(3).set(2, 1);
		board.get(3).set(3, 1);
		board.get(0).set(0, 1);
		board.get(1).set(0, 1);
		board.get(2).set(0, 1);
		board.get(3).set(0, 1);
		board.get(0).set(3, 1);
		System.out.println(board.toString());
				
	}
	
	
	
}
