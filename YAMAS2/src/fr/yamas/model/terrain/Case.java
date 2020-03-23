package fr.yamas.model.terrain;

import fr.yamas.model.Element;

public class Case extends Element implements Comparable<Case> {

	private boolean traversable;
	private int coutDeplacement;
	private TypeTerrain type;
	private int heuristique;
	private int cout;
	private Case previous;

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
		case INCONNUE:
			this.setSkin(0);
			break;
		default:
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

	public TypeTerrain getType() {
		return type;
	}

	public int getHeuristique() {
		return heuristique;
	}

	public void setHeuristique(int heuristique) {
		this.heuristique = heuristique;
	}

	public int getCout() {
		return cout;
	}

	public void setCout(int cout) {
		this.cout = cout;
	}

	@Override
	public int compareTo(Case o) {
		if(this.heuristique > o.getHeuristique())
			return 1;
		else if (this.heuristique == o.heuristique)
			return 0;
		else
			return -1;
	}

	public Case getPrevious() {
		return previous;
	}

	public void setPrevious(Case previous) {
		this.previous = previous;
	}

}
