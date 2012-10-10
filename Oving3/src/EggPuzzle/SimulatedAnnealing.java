package EggPuzzle;

public class SimulatedAnnealing {

	private double dt;
	private double t;
	
	private int iterations;
	
	private double q;
	private double p;
	private double x;
	
	
	public  Node SimulatedAnnealing(Node root, double tMax) {//må returnere noden?

		Node currentNode = root;// blir kalt for P i øvingen, men vanskelig med en p og en P
		Node bestNode = root; //antar denne er best
		iterations = 0;
		t = tMax;
		dt = 0.00001;
		
		while (currentNode.evaluatedValue() < currentNode.getTargetValue()
				&& t > 0) {
			
			//Naboen med høyest evaluering. dvs første i prioritetskøen
			Node pMax = currentNode.getChild();
			
			q = (pMax.evaluatedValue() - currentNode.evaluatedValue())/ currentNode.evaluatedValue();
			p = Math.min(1, Math.exp(-q / t));
			x = Math.random();
			
			if (x > p ) {//Dvs exploiting
				currentNode = pMax;
			} else {//dvs Exploring
				currentNode = currentNode.getRandomChild();
			}
			if (t > 0.0)//Hvordan kan vi bruke t?
				t -= dt;
			
			iterations++;
			
			//er den nye currentNode bedre enn den forrige?
			if (bestNode.evaluatedValue() < currentNode.evaluatedValue())
				bestNode = currentNode;
		}
		
		
		if (bestNode.evaluatedValue() < bestNode.getTargetValue()) {
			System.out.println("Fant ikke en optimal løsning");
		} else {
			System.out.println("Fant en optimal løsning");
		}
		System.out.println("Antall iterasjoner: " + iterations);
		return bestNode;

	}
}
