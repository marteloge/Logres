package Ov2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;


public class TheFractionPuzzle {

	public static double target;
	public static Set<String> closedSetStrings; //trenger denne for at man ikke lager unødvendige noder

	public static void main(String[] args) {
		// Lage en closedSetHash for å unngå duplikate noeder
		closedSetStrings = new HashSet<String>();
		
		//Hente måltilstanden
		System.out.println("Skriv inn nevner");
		Scanner scanner = new Scanner(System.in);
		double temp = scanner.nextDouble();
		target = 1 / temp;
		System.out.println("Beregner 1/" + temp + "\n");
		scanner.close();
		
		// Bruker AStar for å finne stien til målnoden
		FractionPuzzleNode solutionNode =(FractionPuzzleNode) AStar.aStar(new FractionPuzzleNode("123456789", 0));
		
		System.out.println("Stien:");
		FractionPuzzleNode current = solutionNode;
		//Må lagre foreldrene så man kan skrive dem ut i riktig rekkefølge
		List<FractionPuzzleNode> path = new ArrayList<FractionPuzzleNode>();
		while(!current.parents.isEmpty()) {
			current = (FractionPuzzleNode) current.parents.get(0);
			path.add(current);
		}
		
		//Skrive ut resultatet
		System.out.println("1234 / 56789"); //Starttilstand
		for(int i = path.size() - 1; i >= 0; i--) {
			System.out.println(path.get(i).getPermutation().substring(0,4) + " / " + path.get(i).getPermutation().substring(4));
		}
		System.out.println("Løsning: " + solutionNode.getPermutation().substring(0, 4) + " / " + solutionNode.getPermutation().substring(4));
	}
}
