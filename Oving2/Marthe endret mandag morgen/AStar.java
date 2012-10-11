package Ov2;

import java.util.*;

public class AStar {

	public static Node aStar(Node root) {
		
		// Nodene som er oppdaget, men ikke ekspandert enda, som er prioritetskø
		PriorityQueue<Node> openSet = new PriorityQueue<Node>(100, new Comparator<Node>(){
			// Definere hvordan nodene i openSet skal sammenlignes
			public int compare(Node n1, Node n2) {
				if(n1.getFValue() < n2.getFValue()) {
					return -1;
				} else if(n1.getFValue() > n2.getFValue()) {
					return 1;
				} else {
					return 0;
				}
			}
		});
		
		// Lagre nodene som er feridg ekspandert
		// Velger hashMap for å finne den raskt. her trengs ingen prioritet
		Map<String, Node> closedSet = new HashMap<String, Node>();
		
		// Må legge til rootNode til openSet
		openSet.add(root);
		closedSet.put(root.getHash(), root);
		Node n;
		
		// Så lenge det finnes ikke-ekspanderte noder i openSet:
		while(!openSet.isEmpty()) {// Så skal man ta ut den noden med laves f-verdi
			n = openSet.poll();
			
			// Først sjekker man om noden inneholder løsningstilstanden
			if(n.isSolution()) {
				System.out.println("Generert " + closedSet.size() + " noder.");
				return n;
				}
			
			// hvis ikke, ekspander noden
			n.generateChildren();
			
			// Går igjennom alle banra
			for (Node child : n.children) {
				//Lagrer node n som dens forelder
				child.parents.add(n);

				// Det kan hende denne noden har vært laget/oppdagt før:
				if(!closedSet.containsKey(child.getHash())) {
					closedSet.put(child.getHash(), child);
					// Lager h-verdien og legger chiild til openSet
					child.calculateH();
					openSet.add(child);
					}
				else {//hvis den finnes fra før, så legg bare til en forelder til
					closedSet.get(child.getHash()).addParent(n);
				}
			}
		}
		
		// Hvis ingen løsning funnet: 
		return null;
	}
}