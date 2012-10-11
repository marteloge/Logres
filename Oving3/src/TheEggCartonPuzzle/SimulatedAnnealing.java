package TheEggCartonPuzzle;

public class SimulatedAnnealing {

	private double q;
	private double p;
	private double x;
	private EggPuzzleNode currentNode;
	private EggPuzzleNode bestNode;
	private int iterations=0;
	private int targetValue;
	
	
	public EggPuzzleNode SimulatedAnnealingAlgorithm(EggPuzzleNode root, double t, double dt, int m, int n, int k) {
		currentNode = root;
		bestNode = root;
		targetValue = Math.min(m,n)*k;
		
		while (currentNode.getEvaluatedValue() < targetValue && t > 0) {
			EggPuzzleNode pMax = currentNode.getChild();
			
			q = (pMax.getEvaluatedValue() - currentNode.getEvaluatedValue()/ currentNode.getEvaluatedValue());
			p = Math.min(1, Math.exp(-q / t));
			x = Math.random();
			
			if (x > p ) {//Dvs exploiting
				currentNode = pMax;
			} else {//dvs Exploring
				currentNode = currentNode.getRandomChild();
			}
			if (t > 0.0)
				t -= dt;
			
			iterations++;
			
			//er den nye currentNode bedre enn den forrige?
			if (bestNode.getEvaluatedValue() < currentNode.getEvaluatedValue())
				bestNode = currentNode;
		}
		
		
		//Vi antar at det finnes en optimal løsning, men ikke sikkert vi kommer frem til den før tiden går ut!!
		if (bestNode.getEvaluatedValue() < targetValue) {
			System.out.println("Fant ikke en optimal losning");
		} else {
			System.out.println("Fant en optimal losning!!!!!!!!----------------------------------------------->");
			for(int i=0; i<m; i++){
				for(int j=0; j<n; j++){
					System.out.print(bestNode.getBoard()[i][j]);
				}
				System.out.println(" ");
			}
		}
		System.out.println("Antall iterasjoner: " + iterations);
		return bestNode;

	}
}
