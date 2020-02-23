package model;

import model.Personnage;

/**
 * 
 * @author Thomas Feuilletin
 * @author Yann Derrien
 * 
 *         Archer : Unit� a distance. Il n'a pas beaucoup de point de vie,
 *         inflige des d�gats moyen mais a longue distance. il se d�place
 *         rapidement
 *
 */
public class Archer extends Personnage {

	public Archer(int x, int y, int armee, int skin) {
		super(10, 10, 10, 3, skin, x, y, armee);
	}

	/**
	 * Attaque de base sur la cible p inflige des d�gats moyens
	 * @param p un personnage
	 */
	public void tir(Personnage p) {
		// TODO � d�finir
	}

	/**
	 * Attaque � longue port�e sur la cible p, d�gat faible
	 * @param p un personnage
	 */
	public void tirLongPortee(Personnage p) {
		// TODO � d�finir
	}
	
	/**
	 * Attaque � courte port� sur la cible p, d�gat puissance
	 * @param p un personnage
	 */
	public void tirPrecis(Personnage p) {
		//TODO � d�finir
	}
}
