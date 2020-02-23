package model;

/**
 * 
 * @author Thomas Feuilletin
 *
 *
 *         Tank unité de type corps à corps défensif. Peut de mouvement et
 *         d'attaque mais beaucoup de defence
 */
public class Tank extends Personnage {

	public Tank(int skin, int x, int y, int armee) {
		super(20, 5, 1, 3, skin, x, y, armee);
	}

}
