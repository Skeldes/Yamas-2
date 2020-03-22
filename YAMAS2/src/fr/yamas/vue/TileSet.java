package fr.yamas.vue;

import fr.yamas.model.Element;

public interface TileSet {

	public int getTileWidth();
	public int getTileHeight();
	public String getImageFile();
	public Tile getTile(Element e);
}
