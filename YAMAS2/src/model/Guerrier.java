package model;

import model.Personnage;

/**
 * 
 * @author Thomas Feuilletin
 * @author Yann Derrien
 *
 *         Guerrier : Unité au corps à corps. Il à des points de vie moyen mais
 *         une bonne attaque. il se déplace a vitesse moyenne
 *
 */
public class Guerrier extends Personnage {

	public Guerrier(int x, int y, int armee, int skin) {
		super(10, 10, 10, 6, skin, x, y, armee);
	}

	/**
	 * Attaque rapide sur la cible p mais qui inflige peu de dégats
	 * @param p un personnage
	 */
	public void coupRapide(Personnage p) {
		// TODO à définir
	}
	
	/**
	 * Attaque puissante sur la cible p qui inflige beaucoup de dégats
	 * @param p un personnage
	 */
	public void coupPuissant(Personnage p){
		//TODO à définir
	}
}
