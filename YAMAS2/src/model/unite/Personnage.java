package model.unite;

import java.util.ArrayList;

import model.Element;
import model.terrain.Case;

/**
 * 
 * @author Thomas Feuilletin
 * @author Yann Derrien
 *
 */
public abstract class Personnage extends Element {

	private int pv;
	private int pvMax;

	// XXX l'attaque d�pend des comp�tences. est-ce justicieux de faire une variable
	// attaque ?
	private int attaque;

	private int armee;

	public boolean estSelectionne;
	public boolean aJouer;

	private ArrayList<Case> depPossible;
	private int deplacement;

	private Etats etat;

	/*
	 * XXX probl�me de port�e Certaine attaques ont une port�e diff�rente �
	 * retravailler
	 */
	private int portee;
	public ArrayList<Case> attaquePossible;


	/*
	 * Fonctions qui g�re le d�placements
	 */

	/*
	 * XXX Am�lioration requise. Fonction pour avec des petits valeurs de d mais met
	 * beaucoup trop de temps avec des grandes valeurs (>8). A am�liorer.
	 */
	

	/**
	 * Met l'unit� sur la case de coordonn�e (x,y)
	 * 
	 * @param x un entier compris entre 0 et la largeur du terrain
	 * @param y un entier comrpis entre 0 et la hauteur du terrain
	 */
	public void deplacement(int x, int y) {
		this.getPos()[0] = x;
		this.getPos()[1] = y;
		this.setCaseElement(x + y * 100);
		this.estSelectionne = false;
		this.aJouer = true;
		this.depPossible.clear();
	}

	/*
	 * 
	 * Fonctions qui g�re les capacit�s (attaques/supports)
	 */

	/**
	 * Calcule l'ensemble des cases sur les quelles le personnage peut attaquer
	 * depuis la case c avec la port�e p
	 * 
	 * @param c un entier compris entre 0 et hauteur du terrain * largeur du terrain
	 * @param p un entier positif ou nul
	 */
	public static void calPorteAttaque(int c, int p) {
		// TODO � d�finir
	}

	/*
	 * Fonctions qui g�re l'�tat du personnage
	 */

	/**
	 * Applique a la variable pv une variation de vie varVie
	 * 
	 * @param varVie un entier
	 */
	public void setVie(int varVie) {
		this.pv += varVie;
		if (this.pv <= 0)
			this.etat = Etats.MORT;
	}

	public Etats getEtat() {
		return this.etat;
	}

	public int getPv() {
		return pv;
	}

	public int getPvMax() {
		return pvMax;
	}

	public int getAttaque() {
		return attaque;
	}

	public int getArmee() {
		return armee;
	}

	public int getDeplacement() {
		return deplacement;
	}
	
	public void setEtat(Etats etat) {
		this.etat = etat;
	}

	public void setPv(int pv) {
		this.pv = pv;
	}

	public void setPvMax(int pvMax) {
		this.pvMax = pvMax;
	}

	public void setAttaque(int attaque) {
		this.attaque = attaque;
	}

	public void setArmee(int armee) {
		this.armee = armee;
	}

	public void setDeplacement(int deplacement) {
		this.deplacement = deplacement;
	}

	public ArrayList<Case> getDepPossible() {
		return depPossible;
	}

	public void setDepPossible(ArrayList<Case> depPossible) {
		this.depPossible = depPossible;
	}
	
	

}