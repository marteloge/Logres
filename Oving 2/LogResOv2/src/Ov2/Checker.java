package Ov2;

public enum Checker {
	EMPTY, RED, BLACK;

	public static int LEFT = -1;
	public static int RIGHT = 1;
	public static Checker STARTING_COLOUR = BLACK;

	public String toString() {
		if(this == EMPTY) {
			return " ";
		} else if(this == BLACK) {
			return "B";
		} else {
			return "R";
		}
	}

	public int getMoveDirection() {
		return this == RED ? LEFT : RIGHT;
	}

	public Checker getOtherColour() {
		return this == RED ? BLACK : RED;
	}
}