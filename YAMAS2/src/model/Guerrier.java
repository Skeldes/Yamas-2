package model;

import model.Personnage;

/**
 * 
 * @author Thomas Feuilletin
 * @author Yann Derrien
 *
 *         Guerrier : Unit� au corps � corps. Il � des points de vie moyen mais
 *         une bonne attaque. il se d�place a vitesse moyenne
 *
 */
public class Guerrier extends Personnage {

	public Guerrier(int x, int y, int armee, int skin) {
		super(10, 10, 10, 6, skin, x, y, armee);
	}

	/**
	 * Attaque rapide sur la cible p mais qui inflige peu de d�gats
	 * @param p un personnage
	 */
	public void coupRapide(Personnage p) {
		// TODO � d�finir
	}
	
	/**
	 * Attaque puissante sur la cible p qui inflige beaucoup de d�gats
	 * @param p un personnage
	 */
	public void coupPuissant(Personnage p){
		//TODO � d�finir
	}
}
