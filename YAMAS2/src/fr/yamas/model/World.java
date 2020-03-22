package fr.yamas.model;

import fr.yamas.model.terrain.Carte;
import fr.yamas.model.unite.Armee;

public class World {
	private Carte carte;
	private Armee armees;
	
	
	public Armee getArmees() {
		return armees;
	}
	
	public void setArmees(Armee armees) {
		this.armees = armees;
	}

	public Carte getCarte() {
		return carte;
	}

	public void setCarte(Carte carte) {
		this.carte = carte;
	}

}
