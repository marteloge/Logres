package Ov2;

public class LinearCheckersPuzzleNode extends Node {

private Board state;

public LinearCheckersPuzzleNode(Board board, int lvl) {
super();
this.g = lvl;
this.state = board;
}

public Board getBoard() {
return state;
}

@Override
public void calculateH() {
boolean canMove = false;
boolean[] status = new boolean[state.size()];
status[state.size() / 2] = true;
// Count the total distance from every checker to goal state
int sum = 0;
// Count black
for (int i = 0; i <= state.size() / 2; i++) {
if(state.canMove(i) != 0) {
canMove = true;
}
if(state.get(i) == Checker.BLACK) {
for (int j = status.length - 1; j > status.length / 2; j--) {
if(!status[j]) {
status[j] = true;
sum += j - i;
break;
}
}
}
}
// Count red
for (int i = state.size() / 2; i < state.size(); i++) {
if(state.canMove(i) != 0) {
canMove = true;
}
if(state.get(i) == Checker.RED) {
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
// Since a checker can hop one or two "hops", be optimistically and believe that every checker can actually make two optimal hops
this.h = sum / 2.0;
} else {
// If the board is in a "dead lock" we make this node have a very high h value
this.h = Double.MAX_VALUE - this.g;
}
// System.out.println("Generated h = " + this.h + " for board = " + state);
}

@Override
public void generateChildren() {
// Board.counter++;
// System.out.println("Chose: " + this.state + " with f = " + getFValue());
for (int i = 0; i < state.size(); i++) {
// Find how many spots the Checker can move
int canMove = state.canMove(i);
if(canMove != 0) {
Board newBoard = new Board(state);
newBoard.swap(i, i + canMove);
children.add(new LinearCheckersPuzzleNode(newBoard, this.g + 1));
// System.out.println("Generated children: " + newBoard);
}
}
}

@Override
public boolean isSolution() {
for (int i = 0; i < state.size(); i++) {
if(i < state.size() / 2 && state.get(i) != Checker.RED) {
return false;
} else if (i == state.size() / 2 && state.get(i) != Checker.EMPTY) {
return false;
} else if (i > state.size() / 2 && state.get(i) != Checker.BLACK) {
return false;
}
}
return true;
}

@Override
public String getHash() {
return state.toString();
}

}
