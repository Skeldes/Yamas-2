package vue;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;


/**
 * Class qui g�re les Layer
 * 
 * les Layers sont une sorte de couche. Ont associe a chaque layer des sprites et des textures associ�s aux sprites
 * 
 * @author Thomas Feuilletin
 *
 */
public class AWTLayer implements Layer {

	private int tileWidth;
	private int tileHeight;
	private int textureWidth;
	private int textureHeight;
	
	private BufferedImage texture;
	
	private int[][] sprites;
	
	@Override
	public int getTileWidth() {
		return this.tileWidth;
	}

	@Override
	public int getTileHeight() {
		return this.tileHeight;
	}

	@Override
	public int getTextureWidth() {
		return this.textureWidth;
	}

	@Override
	public int getTextureHeight() {
		return this.textureHeight;
	}

	@Override
	public void setTileSize(int width, int height) {
		this.tileWidth = width;
		this.tileHeight = height;
	}

	@Override
	public void setTexture(String fileName) throws IOException {
		texture = ImageIO.read(this.getClass().getClassLoader().getResource(fileName));
		textureWidth = texture.getWidth() / tileWidth;
		textureHeight = texture.getHeight() / tileHeight;
	}

	@Override
	public void setSpriteCount(int count) {
		sprites = new int[count][4];
	}

	@Override
	public void setSpriteTexture(int index, int tileX, int tileY) {
		if (sprites == null) {
			throw new RuntimeException("Sprites non d�finis");
		}
		if (index < 0 || index >= sprites.length) {
			throw new IllegalArgumentException("Index sprite" + index + " invalide");
		}
		if (tileX < 0 || tileX >= textureWidth || tileY < 0 || tileY >= textureHeight) {
			throw new IllegalArgumentException("Coordonn�es tuiles " + tileX + " " + tileY + " invalides");
		}
		sprites[index][0] = tileX;
		sprites[index][1] = tileY;
	}

	@Override
	public void setSpriteLocation(int index, int screenX, int screenY) {
		if (sprites == null || index < 0 || index >= sprites.length)
			return;
		sprites[index][2] = screenX;
		sprites[index][3] = screenY;
	}

	
	/**
	 * dessine l'ensemble de sprite sur la fen�tre
	 * @param g un objet graphics
	 */
	public void draw(Graphics g) {
		if (texture == null) {
			throw new RuntimeException("Texture par charg�e");
		}
		if (sprites == null) {
			throw new RuntimeException("Sprites non d�fini");
		}
		if (tileWidth == 0 || tileHeight == 0) {
			throw new RuntimeException("Taille des tuiles non d�finie");
		}
		for (int i = 0; i < sprites.length; i++) {
			int tileX = sprites[i][0];
			int tileY = sprites[i][1];
			int screenX = sprites[i][2];
			int screenY = sprites[i][3];

			g.drawImage(texture, screenX, screenY, screenX + tileWidth, screenY + tileHeight, tileX*tileWidth, tileY*tileHeight,
					tileX*tileWidth + tileWidth, tileY*tileHeight + tileHeight, null);
		}
	}

}
