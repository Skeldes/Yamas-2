package model;

public class Case extends Element {

	private boolean traversable;
	public int coutDeplacement;
	public TypeTerrain type;

	public Case(int x, int y, TypeTerrain t) {
		super(x, y);
		switch (t) {
		case PLAINE:
			this.skin = 6;
			this.traversable = true;
			this.coutDeplacement = 1;
			break;
		case SPAWN:
			this.skin = 5;
			this.traversable = true;
			this.coutDeplacement = 1;
			break;
		case MUR:
			this.skin = 7;
			this.traversable = false;
			this.coutDeplacement = 99;
			break;
		case FORET:
			this.skin = 8;
			this.traversable = true;
			this.coutDeplacement = 2;
			break;
		}
		this.type = t;
	}
}
