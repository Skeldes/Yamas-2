package controlleur;


/**
 * Interface dédier a la gestion de la souris
 * @author Thomas Feuilletin
 *
 */
public interface Mouse {
	
	/**
	 * @param button un entier
	 * @return si le boutons numero button est pressé
	 */
	public boolean isButtonPressed(int button);
	/**
	 * 
	 * @return la position sur l'axe X de la souris
	 */
	public int getX();
	
	/**
	 * 
	 * @return la position sur l'axe Y de la souris
	 */
	public int getY();
}
