package TheEggCartonPuzzle;

public class EggPuzzle{
	
	static SimulatedAnnealing SA = new SimulatedAnnealing();
	static int m = 10;
	static int n = 10;
	static int k = 3;
	static int t = 1;
	static double dt = 0.00005;
	static int targets = Math.min(m, n)*k;
	
	static int[][]board = new int[m][n];
	
	public static void init(){
		int temp = 0;
		
		while(temp<targets){
			double row = Math.random()*m;
			double col = Math.random()*n;
			
			if(board[(int)row][(int)col]==0){
				board[(int)row][(int)col]=1;
				temp++;
			}
			
			
		}
		
	}
	
	public static void main(String[] args) {
		
		init();
		System.out.println("DETTE ER BRETTET:");
		for(int i= 0; i<m; i++){
			for(int j=0; j<n; j++){
				System.out.print(board[i][j]);
			}
			System.out.println(" ");
		}
		
		EggPuzzleNode node = new EggPuzzleNode(board, targets, k);
		SA.SimulatedAnnealingAlgorithm(node, t, dt, m, n, k);
		
	}
	
	
}