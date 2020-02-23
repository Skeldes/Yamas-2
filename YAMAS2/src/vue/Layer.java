package vue;

import java.io.IOException;

/**
 * Interface pour la gestion de Layers
 * 
 * @author Thomas Feuilletin
 *
 */
public interface Layer {

	/**
	 * @return la largeur en pixel d'une tuille
	 */
	public int getTileWidth();

	/**
	 * @return la hauteur en pixel d'une tuille
	 */
	public int getTileHeight();

	/**
	 * @return la largeur en pixel d'une texture
	 */
	public int getTextureWidth();

	/**
	 * @return la hauteur en pixel d'une texture
	 */
	public int getTextureHeight();

	/**
	 * change la hauteur d'une tuille a height et la largeur a width
	 * 
	 * @param width  un entier
	 * @param height un entier
	 */
	public void setTileSize(int width, int height);

	/**
	 * Recupère les textures du fichier fileName
	 * 
	 * @param fileName une chaine de caractère
	 * @throws IOException
	 */
	public void setTexture(String fileName) throws IOException;

	/**
	 * change le nombre de spritepar le nombre count
	 * 
	 * @param count un entier
	 */
	public void setSpriteCount(int count);

	/**
	 * change la texture du sprite index par la tuille de postion x -> tileX y ->
	 * tileY
	 * 
	 * @param index un entier
	 * @param tileX un entier
	 * @param tileY un entier
	 */
	public void setSpriteTexture(int index, int tileX, int tileY);

	/**
	 * change la position sur l'ecran du sprite d'index : index en la postion x :
	 * screenX y :screenY
	 * 
	 * @param index   un entier
	 * @param screenX un entier
	 * @param screenY un entier
	 */
	public void setSpriteLocation(int index, int screenX, int screenY);
}
