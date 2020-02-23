package model;
public class Case extends Element {
	
	private boolean traversable;
	public int coutDeplacement;
	
	
	public Case(int x, int y,int skin, boolean trav, int ctDeplacement) {
		super(x,y,skin);
		this.traversable = trav;
		this.coutDeplacement = ctDeplacement;
	}
}
