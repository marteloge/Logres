package TheEggCartonPuzzle;

import java.util.ArrayList;

//Spesialisering av noden hvis nÃ¸dvendig
//Eggenoden ser sikker tlitt annerledes ut enn den noden ofr neste oppgave

public class EggPuzzleNode{
	private int[][]board;
	private ArrayList<EggPuzzleNode> children;
	private int targetValue;
	private int m;
	private int n;
	private int k;
	private double evaluatedValue=0;
	
	public EggPuzzleNode(int[][]board, int targets, int k){
		this.board = board;
		this.targetValue = targets;
		children = new ArrayList<EggPuzzleNode>();
		this.m = board.length;
		this.n = board[0].length;
		this.k = k;
		evaluatedValue = this.evaluatedValue(targets);
		
	}
	
	public double getEvaluatedValue(){
		return evaluatedValue;
	}
	
	public ArrayList<EggPuzzleNode> getChildren(){
		return children;
	}
	
	public int[][] getBoard(){
		return this.board;
	}
		
	public void GenerateChilden(){
				
		int[]dir={-1,0,1};
		
		for(int i=0; i<m;i++){
			for(int j=0; j<n;j++){
				if(board[i][j]==1){
					boolean move = true;
					int count = 0;
					
					while(move){
						
						double changeRow = Math.random()*2.9;
						double changeCol = Math.random()*2.9;
						count++;
						
						if ((i+dir[(int)changeRow])>=0 && (i+dir[(int)changeRow])<m && 
							(j+dir[(int)changeCol])>=0 && (j+dir[(int)changeCol])<n){
														
							if(board[i+dir[(int)changeRow]][j+dir[(int)changeCol]]==0){
								int[][]temp = new int[m][n];		
								for(int a=0; a<m;a++){
									for(int b=0; b<n; b++){
										temp[a][b] = board[a][b];
									}
								}
								temp[i][j]=0;
								temp[i+dir[(int)changeRow]][j+dir[(int)changeCol]]=1;
								children.add(new EggPuzzleNode(temp, targetValue, this.k));		
								move=false;
							}
						}
						else if(count>10){
							children.add(new EggPuzzleNode(getBoard(), targetValue, this.k));
							move=false;
						}
						
					}
				}
			}
		}
	}
	
	
	public double evaluatedValue(int targetValue) {
		//Her skal den objektive funksjonen være! (sum egg/constraints+1)
		//Går gjennom alle egg for å finne ut hvem av dem som bryter regel!
		//Constraints er summen av alle eggene som bryter en regel.
		
		
		double constraints=0;
		
		for(int i=0; i<m; i++){
			for(int j=0; j<n;j++){
				if(board[i][j]==1){
					
					if(CountDiagonalDown(i, j) > k)
						constraints++;
					else if(CountDiagonalUp(i, j) > k)
						constraints++;
					else if(CountLoddrett(i, j) > k)
						constraints++;
					else if(CountVannrett(i, j) > k)
						constraints++;
				}
			}
		}
		return (targetValue+1)/(constraints+1);
	}

	public EggPuzzleNode getChild() {
		GenerateChilden();
		EggPuzzleNode best = null;
		double score = 0;
		for(EggPuzzleNode child: children){
			if(child.evaluatedValue(targetValue)>score){
				best = child;
				score = child.evaluatedValue(targetValue);
			}
		}
		return best;
	}

	public EggPuzzleNode getRandomChild() {
		double child = Math.random()*targetValue;
		return children.get((int)child);
	}
	
	public int CountLoddrett(int row, int col){
		int count=0;
		for(int i=0; i<board.length;i++){
			if(board[i][col]==1)
				count++;
		}
		return count;
	}
	
	public int CountVannrett(int row, int col){
		int count =0;
		for(int i=0; i<board[0].length;i++){
			if(board[row][i]==1)
				count++;
		}
		return count;
	}
	
	public int CountDiagonalDown(int row, int col){
		
		int count = 0;
		int startRow = row;
		int startCol = col;
		
		while((startRow-1)>=0 && (startCol-1)>=0){
			startRow--;
			startCol--;
		}
		
		while(startRow<board.length && startCol<board[0].length){
			if(board[startRow][startCol]==1)
				count++;
			startRow++;
			startCol++;
		}
		return count;
	}
	
	public int CountDiagonalUp(int row, int col){
		int count = 0;
		int startRow = row;
		int startCol = col;
		
		while(((startRow-1)>=0) && ((startCol+1)<board[0].length)){
			startRow--;
			startCol++;
		}
		
		while(startRow<board.length && startCol>=0){
			if(board[startRow][startCol]==1)
				count++;
			startRow++;
			startCol--;
		}
		return count;
	}


}
