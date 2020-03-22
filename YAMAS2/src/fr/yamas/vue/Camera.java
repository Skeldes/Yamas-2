package fr.yamas.vue;

import java.io.IOException;
import java.util.ArrayList;

import fr.yamas.game.GameMode;
import fr.yamas.model.World;
import fr.yamas.model.terrain.Case;
import fr.yamas.model.unite.Personnage;

public class Camera {
	private int width;
	private int height;

	private int[] corner = { 0, 0 };
	private Layer levelLayer;
	private Layer infoLayer;
	private Layer charsLayer;
	
	private TileSet armeeTileSet;
	private TileSet gridTileSet;

	private GUIFacade gui;
	private GameMode gm;
	private World w;

	public Camera(GameMode gm, World w) {
		this.gm = gm;
		this.gui = gm.getGui();
		this.w = w;
		width = 20;
		height = 20;
		armeeTileSet = new ArmeeTileSet();
		gridTileSet = new GridTileSet();
		initLayer();
	}

	public void centerCamera(Case c) {
		int worldWidth = w.getCarte().getcWidth();
		int worldHeight = w.getCarte().getcHeight();
		int x = c.getPos()[1];
		int y = c.getPos()[0];
		if ((x >= (width / 2) && x < worldWidth - (width / 2))
				&& (y >= (height / 2) && y < worldHeight - (height / 2))) {
			corner[0] = (x - (width / 2));
			corner[1] = (y - (width / 2));
		} else {
			if (x < (width / 2)) {
				corner[0] = 0;
			} else if (x >= worldWidth - (width / 2)) {
				corner[0] = worldWidth - width;
			} else {
				corner[0] = x - width / 2;
			}

			if (y < (height / 2)) {
				corner[1] = 0;
			} else if (y >= worldHeight - (height / 2)) {
				corner[1] = worldWidth - height;
			} else {
				corner[1] = y - height / 2;
			}
		}
		update();
	}

	private void initLayer() {
		levelLayer = gui.createLayer();
		levelLayer.setSpriteCount(width * height);
		levelLayer.setTileSize(gridTileSet.getTileWidth(), gridTileSet.getTileHeight());
		try {
			levelLayer.setTexture(gridTileSet.getImageFile());
		} catch (IOException e) {
			e.printStackTrace();
		}

		charsLayer = gui.createLayer();
		charsLayer.setTileSize(armeeTileSet.getTileWidth(), armeeTileSet.getTileHeight());
		try {
			charsLayer.setTexture(armeeTileSet.getImageFile());
		} catch (Exception e) {
			e.printStackTrace();
		}

		infoLayer = gui.createLayer();
		infoLayer.setTileSize(32, 32);
		try {
			infoLayer.setTexture("tileTerrain.png");
		} catch (Exception e) {
			e.printStackTrace();
		}
		update();
	}

	public void update() {
		int index = 0;
		for (int i = corner[1]; i < corner[1] + height; i++) {
			for (int j = corner[0]; j < corner[0] + width; j++) {
				Case c = w.getCarte().getCarte().get(i * 100 + j);
				levelLayer.setSpriteTexture(index, gridTileSet.getTile(c).getX(), gridTileSet.getTile(c).getY());
				levelLayer.setSpriteLocation(index, (j-corner[0])*gridTileSet.getTileWidth(), (i-corner[1]) * gridTileSet.getTileHeight());
				index++;
			}
		}
		int count = 0;
		for (int i = 0; i < w.getArmees().getNbArmee(); i++) {
			ArrayList<Personnage> armee = w.getArmees().getArmee(i + 1);
			for (Personnage p : armee) {
				if (p.getPos()[0] >= corner[0] && p.getPos()[0] < corner[0] + width && p.getPos()[1] >= corner[1]
						&& p.getPos()[1] < corner[1] + height)
					count++;
			}
		}
		charsLayer.setSpriteCount(count);
		Personnage pSel = null;
		index = 0;
		for (int i = 0; i < w.getArmees().getNbArmee(); i++) {
			ArrayList<Personnage> armee = w.getArmees().getArmee(i + 1);
			for (Personnage p : armee) {
				if (p.getPos()[0] >= corner[0] && p.getPos()[0] < corner[0] + width && p.getPos()[1] >= corner[1]
						&& p.getPos()[1] < corner[1] + height) {
					charsLayer.setSpriteLocation(index, (p.getPos()[0]-corner[0]) * armeeTileSet.getTileWidth(), (p.getPos()[1]-corner[1]) * armeeTileSet.getTileHeight());
					charsLayer.setSpriteTexture(index, armeeTileSet.getTile(p).getX(), armeeTileSet.getTile(p).getY());
					index++;
				}
				if(p.isSelectionne())
					pSel = p;
			}
		}
		if (pSel != null) {
			count = 0;
			for (Case c : pSel.getDepPossible()) {
				if (c.getPos()[0] >= corner[0] && c.getPos()[0] < corner[0] + width && c.getPos()[1] >= corner[1]
						&& c.getPos()[1] < corner[1] + height)
					count++;
			}
			infoLayer.setSpriteCount(count);
			index = 0;
			for (Case c : pSel.getDepPossible()) {
				if (c.getPos()[0] >= corner[0] && c.getPos()[0] < corner[0] + width && c.getPos()[1] >= corner[1]
						&& c.getPos()[1] < corner[1] + height) {
					infoLayer.setSpriteLocation(index, (c.getPos()[0]-corner[0]) * 32, (c.getPos()[1]-corner[1]) * 32);
					infoLayer.setSpriteTexture(index++, 3, 0);
				}
			}
		} else {
			infoLayer.setSpriteCount(0);
		}
	}

	public void move(int x, int y) {
		if (corner[0] + x < w.getCarte().getcWidth() - width && corner[0] + x >= 0)
			corner[0] += x;
		if (corner[1] + y < w.getCarte().getcHeight() - height && corner[1] + y >= 0)
			corner[1] += y;
	}

	public void draw() {
		gui.drawLayer(levelLayer);
		gui.drawLayer(charsLayer);
		gui.drawLayer(infoLayer);
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getCorner(int index) {
		if (index < corner.length)
			return corner[index];
		throw new IllegalArgumentException("indice invalide");
	}
}
