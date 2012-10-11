package Ov2;

import java.util.ArrayList;
import java.util.List;

public class Board {
	private Checker[] board;

	public static int counter = 0;

	/**
	* Initializes a new standard board with the Checkers in starting position
	*
	* @param numberOfCheckers The number of Checkers on the board. Must be an even number!
	*/
	public Board(int numberOfCheckers) {
	if(numberOfCheckers % 2 != 0) {
	throw new IllegalArgumentException("The number of checkers on the board must be an even number!");
	}
	board = new Checker[numberOfCheckers + 1];
	for (int i = 0; i < numberOfCheckers / 2; i++) {
	board[i] = Checker.BLACK;
	board[numberOfCheckers - i] = Checker.RED;
	}
	board[numberOfCheckers / 2] = Checker.EMPTY;
	}

	/**
	* Initializes a new Board with the exact same Chckers as the Board given as argument
	* @param copyFrom
	*/
	public Board(Board copyFrom) {
	board = new Checker[copyFrom.size()];
	for (int i = 0; i < board.length; i++) {
	board[i] = copyFrom.get(i);
	}
	}

	public int size() {
	return board.length;
	}

	/**
	* Returns the Checker at the given position
	*
	* @param i The position
	* @return
	* @throws IndexOutOfBoundsException
	*/
	public Checker get(int i) throws IndexOutOfBoundsException{
	if(i < 0 || i >= size()) {
	throw new IndexOutOfBoundsException(i + "");
	}
	return board[i];
	}

	/**
	* Returns the number of positions the Checker at the given position can move. Positive is right, negative is left and zero is unable to move
	*
	* @param i The position of the Checker
	* @return The number of positions
	*/
	public int canMove(int i) {
	if(i < 0 || i > board.length - 1) {
	return 0;
	}
	if(board[i] == Checker.EMPTY) {
	return 0;
	}
	// Check if the Checker is at it's end
	if(i + board[i].getMoveDirection() > board.length - 1 || i + board[i].getMoveDirection() < 0) {
	return 0;
	}
	// Check if the Checker can move one spot
	if(board[i + board[i].getMoveDirection()] == Checker.EMPTY) {
	return board[i].getMoveDirection();
	}
	// Check if the Checker can move two spots without coming off the board
	if(i + 2 * board[i].getMoveDirection() > board.length - 1 || i + 2 * board[i].getMoveDirection() < 0) {
	return 0;
	}
	// Check if the Checker can move two spots and land on an empty spot
	if(board[i + 2 * board[i].getMoveDirection()] == Checker.EMPTY) {
	return 2 * board[i].getMoveDirection();
	}
	return 0;
	}

	public void swap(int from, int to) throws IndexOutOfBoundsException {
	if(from < 0 || from > board.length - 1) {
	throw new IndexOutOfBoundsException("from: " + from + "");
	}
	if(to < 0 || to > board.length - 1) {
	throw new IndexOutOfBoundsException("to: " + to + "");
	}
	Checker temp = board[from];
	board[from] = board[to];
	board[to] = temp;
	}

	public void printBoard() {
	for (Checker c : board) {
	System.out.print(c);
	}
	}

	@Override
	public String toString() {
	StringBuilder sb = new StringBuilder();
	for (Checker c : board) {
	sb.append(c);
	}
	return sb.toString();
	}

	public static void main(String[] args) {
	LinearCheckersPuzzleNode root = new LinearCheckersPuzzleNode(new Board(24), 0);
	LinearCheckersPuzzleNode solution = (LinearCheckersPuzzleNode) GeneralAStar.aStar(root);
	if(solution == null) {
	System.out.println("Found no solution!");
	} else {
	int moveCounter = 0;
	LinearCheckersPuzzleNode current = solution;
	List<LinearCheckersPuzzleNode> path = new ArrayList<LinearCheckersPuzzleNode>();
	while(!current.parents.isEmpty()) {
	moveCounter++;
	path.add(current);
	current = (LinearCheckersPuzzleNode) current.parents.get(0);
	}
	System.out.println(root.getBoard());
	for (int i = path.size() - 1; i >= 0; i--) {
	System.out.println(path.get(i).getBoard() + " : " + path.get(i).getFValue());
	}
	System.out.println("Number of moves: " + moveCounter);
	}
	}
}
