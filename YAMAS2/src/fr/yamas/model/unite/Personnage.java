package fr.yamas.model.unite;

import java.util.ArrayList;
import java.util.HashMap;

import fr.yamas.model.Element;
import fr.yamas.model.terrain.Case;

/**
 * 
 * @author Thomas Feuilletin
 * @author Yann Derrien
 *
 */
public abstract class Personnage extends Element {

	private int pv;
	private int pvMax;

	private int attaque;

	private int armee;

	private boolean Selectionne;
	private boolean aJouer;

	private HashMap<Integer, Case> depPossible;
	private ArrayList<Case> route;
	private int deplacement;

	private Etats etat;

	private final int speed = 2;

	private boolean inMove;

	private int posInter = 0;
	private Direction direction = Direction.NONE;

	private int portee;
	public ArrayList<Case> attaquePossible;

	/*
	 * Fonctions qui gére le déplacements
	 */

	public void nextStep() {
		route.remove(0);
		if (!route.isEmpty()) {
			if (route.get(0).getPos()[0] == this.getPos()[0] && route.get(0).getPos()[1] + 1 == this.getPos()[1])
				this.direction = Direction.NORTH;
			else if (route.get(0).getPos()[0] == this.getPos()[0] && route.get(0).getPos()[1] - 1 == this.getPos()[1])
				this.direction = Direction.SOUTH;
			else if (route.get(0).getPos()[0] + 1 == this.getPos()[0] && route.get(0).getPos()[1] == this.getPos()[1])
				this.direction = Direction.WEST;
			else if (route.get(0).getPos()[0] - 1 == this.getPos()[0] && route.get(0).getPos()[1] == this.getPos()[1])
				this.direction = Direction.EAST;
		} else {
			inMove = false;
			direction = Direction.NONE;
		}
	}

	/**
	 * Met l'unité sur la case de coordonnée (x,y)
	 * 
	 */
	public void deplacement() {
		inMove = true;
		route.remove(0);
		if (route.get(0).getPos()[0] == this.getPos()[0] && route.get(0).getPos()[1] + 1 == this.getPos()[1])
			this.direction = Direction.NORTH;
		else if (route.get(0).getPos()[0] == this.getPos()[0] && route.get(0).getPos()[1] - 1 == this.getPos()[1])
			this.direction = Direction.SOUTH;
		else if (route.get(0).getPos()[0] + 1 == this.getPos()[0] && route.get(0).getPos()[1] == this.getPos()[1])
			this.direction = Direction.WEST;
		else if (route.get(0).getPos()[0] - 1 == this.getPos()[0] && route.get(0).getPos()[1] == this.getPos()[1])
			this.direction = Direction.EAST;

		this.Selectionne = false;
		this.setaJouer(true);
	}

	/*
	 * 
	 * Fonctions qui gére les capacités (attaques/supports)
	 */

	/**
	 * Calcule l'ensemble des cases sur les quelles le personnage peut attaquer
	 * depuis la case c avec la portée p
	 * 
	 * @param c un entier compris entre 0 et hauteur du terrain * largeur du terrain
	 * @param p un entier positif ou nul
	 */
	public static void calPorteAttaque(int c, int p) {
		// TODO à définir
	}

	/*
	 * Fonctions qui gére l'état du personnage
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

	public HashMap<Integer, Case> getDepPossible() {
		return depPossible;
	}

	public void setDepPossible(HashMap<Integer, Case> depPossible) {
		this.depPossible = depPossible;
	}

	public boolean isSelectionne() {
		return Selectionne;
	}

	public void setSelectionne(boolean selectionne) {
		Selectionne = selectionne;
	}

	public boolean isaJouer() {
		return aJouer;
	}

	public void setaJouer(boolean aJouer) {
		this.aJouer = aJouer;
	}

	public ArrayList<Case> getRoute() {
		return route;
	}

	public void addRoute(int indice, Case c) {
		this.route.add(indice, c);
	}

	public void setRoute(ArrayList<Case> route) {
		this.route = route;
	}

	public int getPosInter() {
		return posInter;
	}

	public void setPosInter(int posInter) {
		this.posInter = posInter;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public int getSpeed() {
		return speed;
	}

	public boolean isInMove() {
		return inMove;
	}

	public void setInMove(boolean inMove) {
		this.inMove = inMove;
	}

}