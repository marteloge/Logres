package Ov2;

import java.util.*;

public abstract class Node {
	
	public int g; //kostnad fra root til denne noden. ikke noen forskjell på stegene, så hver "kant" vil ha kostnad 1
	public double h; //heuristisk, estimert kostnat fra denne noden til målNoden
	public List<Node> children; //En node kan ha flere barn
	public List<Node> parents; //samt flere foreldre
	public Node cheapestParent; // Men det er en av foreldrene som vil være best, dvs ha minst kostnad
	
	public Node() {
		children = new ArrayList<Node>();
		parents = new ArrayList<Node>();
		g = 0;
		h = 0;
	}

	public void addParent(Node parent) {
		if(this.cheapestParent == null) {
			return;
		}
		parents.add(parent);
		if(parent.g < this.cheapestParent.g) {
			this.cheapestParent = parent;
			this.updateChildren();
		}
	}

	public void updateChildren() {
		for (Node child : children) {
			if(child.cheapestParent.g > this.g) {
				child.cheapestParent = this;
				child.updateChildren();
			}
		}
	}
	
	public double getFValue() { //totalt estimert kostnad
		return g + h;
	}

	abstract public void calculateH();

	abstract public void generateChildren();

	abstract public boolean isSolution();

	abstract public String getHash();

}


