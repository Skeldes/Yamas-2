package model;

/**
 * 
 * @author Thomas Feuilletin
 *
 *
 *         Tank unit� de type corps � corps d�fensif. Peut de mouvement et
 *         d'attaque mais beaucoup de defence
 */
public class Tank extends Personnage {

	public Tank(int skin, int x, int y, int armee) {
		super(20, 5, 1, 3, skin, x, y, armee);
	}

}
