package model;

public class Case extends Element {

	private boolean traversable;
	public int coutDeplacement;
	public TypeTerrain type;

	public Case(int x, int y, TypeTerrain t) {
		super(x, y);
		switch (t) {
		case SPAWN:
			this.skin = 5;
			this.traversable = true;
			this.coutDeplacement = 1;
			break;
		case PLAINE:
			this.skin = 6;
			this.traversable = true;
			this.coutDeplacement = 1;
			break;
		case MURH:
			this.skin = 7;
			this.traversable = false;
			this.coutDeplacement = 99;
			break;
		case FORET:
			this.skin = 8;
			this.traversable = true;
			this.coutDeplacement = 2;
			break;
		case EAU:
			this.skin = 9;
			this.traversable = false;
			this.coutDeplacement = 99;
			break;
		case MURV:
			this.skin = 11;
			this.traversable = false;
			this.coutDeplacement = 99;
			break;
		case PONTH:
			this.skin = 12;
			this.traversable = true;
			this.coutDeplacement = 1;
			break;
		case PONTV:
			this.skin = 13;
			this.traversable = true;
			this.coutDeplacement = 1;
			break;
		case SOL:
			this.skin = 14;
			this.traversable = true;
			this.coutDeplacement = 1;
			break;
		}

		this.type = t;
	}
}
