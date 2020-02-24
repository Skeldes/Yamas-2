package model;

public class Element {
	public int[] pos = new int[2];
	public int skin;
	public int caseElement;

	public Element(int x, int y) {
		this.pos[0] = x;
		this.pos[1] = y;
		caseElement = x + y * 100;
	}

	public boolean posEgale(Element e) {
		return e.pos[0] == this.pos[0] && e.pos[1] == this.pos[1];
	}
}
