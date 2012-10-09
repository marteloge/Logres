package Ov2;

public class FractionPuzzleNode extends Node {
	
	// hver node inneholder en permutasjon av tallene - en tilstand
	// bruker også dennne stringen som en hash, da den ikke bør være laget mer enn en gang
	private String permutation; 
	private int pos;

	public FractionPuzzleNode(String permutation, int pos) {
		super();
		this.pos = pos;
		this.permutation = permutation;
	}

	public void calculateH() {
		 // Finne en egnet h
		this.h = 1;
	}
	
	//Lager mulige barn mhp pos som den posisjoenen alle andre tall skal 
	public void generateChildren() {
		// Sdet er bare 9 tall, dvs man kommer bare til pos = 8
		if(pos == 8) {//den siste posisjonen vil ikke ha noe utbytta av å få endret seg
			//pos 0 har 8, pos 1 har 7... pos 6 har 2, pos 7 har 1 og siste pos (8) vil altså ikke ha noen flyy som ikke hatr vært laga før
			return;
		}
		
		//For hver posisjon, unntatt pos, så bytter man tallet på posisjon(i) med tallet på pos  
		for (int i = 0; i < 9; i++) {
			if(i == pos) {
				continue;
			}
			char[] perm = this.permutation.toCharArray();
			char temp = perm[this.pos];
			perm[this.pos] = perm[i];
			perm[i] = temp;
			String s = new String(perm);
			
			// Må sjekke om vi kanskje har laga dene permutasjonen før, hvis ikke så lager vi en ny node
			if(!TheFractionPuzzle.closedSetStrings.contains(s)) {//siden stringen Permutastion er hashverdien, så trenger man ikke å lage en node først
				FractionPuzzleNode child = new FractionPuzzleNode(s, pos + 1);
				child.g = this.g + 1;
				this.children.add(child);
				TheFractionPuzzle.closedSetStrings.add(s);
				}
			}
		}

	public boolean isSolution() {
		//sjekker om tilstanden er løsningen
		return Math.abs((Double.parseDouble(this.permutation.substring(0, 4)) / Double.parseDouble(this.permutation.substring(4)))) == Math.abs(TheFractionPuzzle.target);
	}

	public String getPermutation() {
		return permutation;
	}

	public String getHash() {
		return getPermutation();
	}
}


