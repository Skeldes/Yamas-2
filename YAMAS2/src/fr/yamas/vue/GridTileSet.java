package fr.yamas.vue;

import fr.yamas.model.Element;
import fr.yamas.model.terrain.Case;
import fr.yamas.model.terrain.TypeTerrain;

public class GridTileSet implements TileSet {

	private Tile[] sol;
	private Tile[] obstacle;
	
	public GridTileSet() {
		sol = new Tile[7];
		obstacle = new Tile[3];
		sol[0] = new Tile(1,0);
		sol[1] = new Tile(4,0);
		sol[2] = new Tile(5,0);
		sol[3] = new Tile(7,0);
		sol[4] = new Tile(1,1);
		sol[5] = new Tile(2,1);
		sol[6] = new Tile(3,1);
		
		obstacle[0] = new Tile(6,0);
		obstacle[1] = new Tile(8,0);
		obstacle[2] = new Tile(0,1);
		
		
	}

	@Override
	public int getTileWidth() {
		return 32;
	}

	@Override
	public int getTileHeight() {
		return 32;
	}

	@Override
	public String getImageFile() {
		return "tileTerrain.png";
	}

	@Override
	public Tile getTile(Element e) {
		if(e instanceof Case) {
			Case c = (Case)e;
			if(c.getType()==TypeTerrain.MURH||c.getType()==TypeTerrain.MURV||c.getType()==TypeTerrain.EAU)
				return obstacle[c.getSkin()];
			else 
				return sol[c.getSkin()];
		}
		return sol[0];
	}

}
