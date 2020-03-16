package model.terrain;

import model.Element;

public class Case extends Element {

	private boolean traversable;
	private int coutDeplacement;
	private TypeTerrain type;

	public Case(TypeTerrain t) {
		type = t;
	}
	
	public Case(int x, int y, TypeTerrain t) {
		switch (t) {
		case SPAWN:
			this.setSkin(5);
			this.setTraversable(true);
			this.setCoutDeplacement(1);
			break;
		case PLAINE:
			this.setSkin(6);
			this.setTraversable(true);
			this.setCoutDeplacement(1);
			break;
		case MURH:
			this.setSkin(7);
			this.setTraversable(false);
			this.setCoutDeplacement(99);
			break;
		case FORET:
			this.setSkin(8);
			this.setTraversable(true);
			this.setCoutDeplacement(2);
			break;
		case EAU:
			this.setSkin(9);
			this.setTraversable(false);
			this.setCoutDeplacement(99);
			break;
		case MURV:
			this.setSkin(11);
			this.setTraversable(false);
			this.setCoutDeplacement(99);
			break;
		case PONTH:
			this.setSkin(12);
			this.setTraversable(true);
			this.setCoutDeplacement(1);
			break;
		case PONTV:
			this.setSkin(13);
			this.setTraversable(true);
			this.setCoutDeplacement(1);
			break;
		case SOL:
			this.setSkin(14);
			this.setTraversable(true);
			this.setCoutDeplacement(1);
			break;
		}

		this.type = t;
	}

	public boolean isTraversable() {
		return traversable;
	}

	public void setTraversable(boolean traversable) {
		this.traversable = traversable;
	}

	public int getCoutDeplacement() {
		return coutDeplacement;
	}

	public void setCoutDeplacement(int coutDeplacement) {
		this.coutDeplacement = coutDeplacement;
	}
}
