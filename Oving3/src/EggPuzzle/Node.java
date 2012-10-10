package Ov3Tillegg;

import java.util.*;

public abstract class Node {

	protected PriorityQueue<Node> children;
	
	
	public Node(){
		
	}
	
	public int evaluatedValue() {//Lages av den objektive funksjonen?
		// TODO Auto-generated method stub
		return 0;
	}

	public abstract int getTargetValue();//Målets verdi - idealverdien
	//return Math.min(M, N) * K;

	public Node getChild() {
		// Gi barnet med høyest prioritet - det beste barnet?
		return null;
	}

	public Node getRandomChild() {
		//for lister :  return children.get((int) (Math.random() * lChildren.size()));
	}

}
