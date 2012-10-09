package Oving2;

public class LinearCheckersPuzzleNode extends Node {
	
	//instans av brettet
	private LinearCheckersPuzzle state;

	public LinearCheckersPuzzleNode(LinearCheckersPuzzle puzzle, int pos) {
		// Super kaller Node siden konstruktør som setter: children, parents, g og h.
		super();
		this.g = pos;
		this.state = puzzle;
	}
	
	public LinearCheckersPuzzle getPuzzle() {
		return state;
	}

	//A* bruker g og h. Her kalkuleres h.
	public void calculateH() {
		boolean canMove = false;
		boolean[] status = new boolean[state.size()];
		status[state.size() / 2] = true;
		//Teller den totale distansen fra hver "checker" til målet.
		int sum = 0;
		// Teller alle svarte (B for black)
		for (int i = 0; i <= state.size() / 2; i++) {
			if(state.canMoveCheckerAtIndex(i) != 0) {
				canMove = true;
			}
			if(state.getCheckerAtIndex(i) == 'B') {
				for (int j = status.length - 1; j > status.length / 2; j--) {
					if(!status[j]) {
						status[j] = true;
						sum += j - i;
						break;
					}
				}
			}
		}
		// Teller alle røde (R for red)
		for (int i = state.size() / 2; i < state.size(); i++) {
			if(state.canMoveCheckerAtIndex(i) != 0) {
				canMove = true;
			}
			if(state.getCheckerAtIndex(i) == 'R') {
				for (int j = 0; j < status.length / 2; j++) {
					if(!status[j]) {
						status[j] = true;
						sum += i - j;
						break;
					}
				}
			}
		}
		if(canMove || sum == 0) {
			this.h = sum / 2.0;
		}
		else {
			// Hvis brettet er i en "dead lock" vil vi ssette denne noden med en veldig høy h verdi
			this.h = Double.MAX_VALUE - this.g;
		}
	}

	public void generateChildren() {
		for (int i = 0; i < state.size(); i++) {
			// Finner ut hvor mange rykk checkeren kan bevege seg
			int canMove = state.canMoveCheckerAtIndex(i);
			if(canMove != 0) {
				LinearCheckersPuzzle newBoard = new LinearCheckersPuzzle(state);
				newBoard.swap(i, i + canMove);
				children.add(new LinearCheckersPuzzleNode(newBoard, this.g + 1));
			}
		}
	}

	//Sjekker om tilstanden er en løsning
	public boolean isSolution() {
		for (int i = 0; i < state.size(); i++) {
			//Hvis noen av brikkene til venstre for hullet er svarte så er det ikke en løsning
			if(i < state.size() / 2 && state.getCheckerAtIndex(i) != 'R') {
				return false;
			}
			//Hvis det ikke er et hull på midten av brettet så er det ikke en løsning
			else if (i == state.size() / 2 && state.getCheckerAtIndex(i) != ' ') {
				return false;
			}
			//Hvis noen av brikkene til høyre for hullet er røde så er det ikke en løsning
			else if (i > state.size() / 2 && state.getCheckerAtIndex(i) != 'B') {
				return false;
			}
		}
		return true;
	}

	public String getHash() {
		return state.toString();
	}
}