package vue;

import controlleur.Keyboard;
import controlleur.Mouse;

/**
 * Interface pour la gestion des facades
 * @author Thomas Feuilletin 
 *
 */
public interface GUIFacade {
	
	/**
	 * Cr�e une f�netre avec un canvas de largeur width de hauteur height et de titre title
	 * @param width un entier
	 * @param height un entier
	 * @param title une chaine de caract�re
	 */
	public void createWindow(int width, int height, String title);
	
	/**
	 * @return si il y a une requete pour ferm� la fenetre
	 */
	public boolean isClosingRequested();
	
	/**
	 * ferme l'application
	 */
	public void setClosingRequested();
	
	/**
	 * Ferme la fenetre
	 */
	public void dispose();
	
	/**
	 * @return si on a commencer � dessiner dans la fen�tre
	 */
	public boolean beginPaint();
	
	/**
	 * met fin au dessin
	 */
	public void endPaint();
	
	/**
	 * Cr�er un layer
	 * @return un Layer
	 */
	public Layer createLayer();
	
	/**
	 * Dessine le layer layer
	 * @param layer un Layer
	 */
	public void drawLayer(Layer layer);
	
	public void drawString(String text, int x, int y, int width, int height);
	
	/**
	 * @return un controlleur de la souris
	 */
	public Mouse getMouse();
	
	/**
	 * @return un controlleur du clavier
	 */
	public Keyboard getKeyboard();
}
