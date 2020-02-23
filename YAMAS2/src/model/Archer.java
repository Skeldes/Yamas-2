package model;

import model.Personnage;

/**
 * 
 * @author Thomas Feuilletin
 * @author Yann Derrien
 * 
 *         Archer : Unité a distance. Il n'a pas beaucoup de point de vie,
 *         inflige des dégats moyen mais a longue distance. il se déplace
 *         rapidement
 *
 */
public class Archer extends Personnage {

	public Archer(int x, int y, int armee, int skin) {
		super(10, 10, 10, 3, skin, x, y, armee);
	}

	/**
	 * Attaque de base sur la cible p inflige des dégats moyens
	 * @param p un personnage
	 */
	public void tir(Personnage p) {
		// TODO à définir
	}

	/**
	 * Attaque à longue portée sur la cible p, dégat faible
	 * @param p un personnage
	 */
	public void tirLongPortee(Personnage p) {
		// TODO à définir
	}
	
	/**
	 * Attaque à courte porté sur la cible p, dégat puissance
	 * @param p un personnage
	 */
	public void tirPrecis(Personnage p) {
		//TODO à définir
	}
}
