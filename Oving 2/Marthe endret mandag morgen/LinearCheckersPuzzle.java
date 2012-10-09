package Ov2;

import java.util.*;

public class LinearCheckersPuzzle {
	
	private char[] puzzle;

	
	public LinearCheckersPuzzle(int checkerCount) {
		if (checkerCount % 2 != 0) {// Må være et partall
			throw new IllegalArgumentException(
					"Antallet Checkers må være partall!");
		}
		puzzle = new char[checkerCount + 1];// husk den tomme ruten
		// Legger ut brikkene
		for (int i = 0; i < checkerCount / 2; i++) {
			puzzle[i] = 'B';
			puzzle[checkerCount - i] = 'R';
		}
		// Midten skal være tom
		puzzle[checkerCount / 2] = ' ';
	}

	//Må ha en konstruktør til, der vi tar inn en puzzle
	public LinearCheckersPuzzle(LinearCheckersPuzzle puzzle) {
		this.puzzle = new char[puzzle.size()];
		for (int i = 0; i < this.puzzle.length; i++) {
			this.puzzle[i] = puzzle.getCheckerAtIndex(i);
		}
	}

	
	private int getMoveDirection(char c) {
		return c == 'R' ? -1 : 1;
	}
	
	public char getCheckerAtIndex(int i)  throws IndexOutOfBoundsException{
		if (!(i < 0 || i >= size())) {
			return puzzle[i];
		}
		throw new IndexOutOfBoundsException(i + " er en ugyldig index");
	}

	
	public int canMoveCheckerAtIndex(int i) {
		//Hvis index er utenfor listen eller gir den tomme ruten, så er det ingen flytt
		if (i < 0 || i > puzzle.length - 1) {
			return 0;
		}
		if (puzzle[i] == ' ') {
			return 0;
		}
		
		int moveDirection = getMoveDirection(puzzle[i]);
		
		// Må sjekke om checker'n er på enden, da kan den ikke flytte seg i den retningen
		if (i + moveDirection > puzzle.length - 1
				|| i + moveDirection < 0) {
			return 0;
		}
		
		// Sjekker om checker'n kan flytte seg ett felt
		if (puzzle[i + moveDirection] == ' ') {
			return moveDirection;
		}
		// Sjekker om checker'n kan flytte seg to felter uten å gå utenfor lista
		if (i + 2 * moveDirection > puzzle.length - 1
				|| i + 2 * moveDirection < 0) {
			return 0;
		}
		// Sjekker om checker'n kan flytte seg to felter o lande på den tomme ruta
		if (puzzle[i + 2 * moveDirection] == ' ') {
			return 2 * moveDirection;
		}
		
		//ellers så kan vi heller ikke flytte
		return 0;
	}

	//Flytte checker'n
	public void swapChecker(int from, int to) {
		if (from < 0 || from > puzzle.length - 1) {
			return;
		}
		if (to < 0 || to > puzzle.length - 1) {
			return;
		}
		char temp = puzzle[from];
		puzzle[from] = puzzle[to];
		puzzle[to] = temp;
	}

	public int size() {
		return puzzle.length;
	}
	
	//Må kunne skrive ut puzzle'n på en ordentlig måte
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (char c : puzzle) {
			sb.append(c);
		}
		return sb.toString();
	}

	
	public static void main(String[] args) {
		//Henter størrelsen på puzzle'n
		System.out.println("Skriv inn antall checkers i spillet:");
		Scanner scanner = new Scanner(System.in);
		int checkerCount = scanner.nextInt();
		scanner.close();
		
		//Lager rotnoden og løser puzzle'n ved hjelp av AStar
		LinearCheckersPuzzleNode root = new LinearCheckersPuzzleNode(new LinearCheckersPuzzle(
				checkerCount), 0);
		LinearCheckersPuzzleNode solution = (LinearCheckersPuzzleNode) AStar
				.aStar(root);
		
		if (solution == null) {
			System.out.println("Fant ingen løsning");
		}
		else {
			//GJør som i FractionPuzzle, finner stien fra løsningsnoden opp til rotnoden
			int moveCounter = 0;
			LinearCheckersPuzzleNode current = solution;
			ArrayList<LinearCheckersPuzzleNode> path = new ArrayList<LinearCheckersPuzzleNode>();
			
			while (!current.parents.isEmpty()) {
				moveCounter++;
				path.add(current);
				current = (LinearCheckersPuzzleNode) current.parents.get(0);
			}
			
			System.out.println(root.getPuzzle());
			for (int i = path.size() - 1; i >= 0; i--) {
				System.out.println(path.get(i).getPuzzle());
			}
			System.out.println("Antall steg: " + moveCounter);
		}
	}
}