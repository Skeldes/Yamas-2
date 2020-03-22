package fr.yamas.vue;

import fr.yamas.model.Element;
import fr.yamas.model.unite.Personnage;

public class ArmeeTileSet implements TileSet {
	
	private Tile[] armeeJoueurUn;
	private Tile[] armeeJoueurDeux;
	
	public ArmeeTileSet() {
		armeeJoueurUn = new Tile[6];
		armeeJoueurDeux = new Tile[6];
		
		armeeJoueurUn[0] = new Tile(0,0);
		armeeJoueurUn[1] = new Tile(1,0);
		armeeJoueurUn[2] = new Tile(2,0);
		armeeJoueurUn[3] = new Tile(3,0);
		armeeJoueurUn[4] = new Tile(4,0);
		armeeJoueurUn[5] = new Tile(5,0);
		
		armeeJoueurDeux[0] = new Tile(0,1);
		armeeJoueurDeux[1] = new Tile(1,1);
		armeeJoueurDeux[2] = new Tile(2,1);
		armeeJoueurDeux[3] = new Tile(3,1);
		armeeJoueurDeux[4] = new Tile(4,1);
		armeeJoueurDeux[5] = new Tile(5,1);
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
		return "tileArmee.png";
	}

	@Override
	public Tile getTile(Element e) {
		if(e instanceof Personnage) {
			Personnage p = (Personnage)e;
			if(p.getArmee() == 1)
				return armeeJoueurUn[p.getSkin()];
			else if(p.getArmee()==2)
				return armeeJoueurDeux[p.getSkin()];
		}
		return armeeJoueurUn[0];
	}

}
