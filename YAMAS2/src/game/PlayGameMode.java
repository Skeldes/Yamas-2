package game;

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import controlleur.Mouse;
import model.*;
import vue.Layer;

/**
 * 
 * @author Thomas Feuilletin
 * @author Yann Derrien guest Marie Ozon
 *
 */
public class PlayGameMode extends GameMode {

	// variables du terrain
	final int levelHeight = 20;
	final int levelWidth = 20;
	final int[][] level = { { 2, 2, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3 },
			{ 2, 2, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3 },
			{ 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3 },
			{ 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3 },
			{ 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3 },
			{ 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4, 4, 4, 4, 4 },
			{ 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3 },
			{ 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3 },
			{ 3, 3, 3, 3, 3, 3, 3, 3, 5, 5, 5, 5, 3, 3, 3, 3, 3, 3, 3, 3 },
			{ 3, 3, 3, 3, 3, 3, 3, 3, 5, 5, 5, 5, 3, 3, 3, 3, 3, 3, 3, 3 },
			{ 3, 3, 3, 3, 3, 3, 3, 3, 5, 5, 5, 5, 3, 3, 3, 3, 3, 3, 3, 3 },
			{ 3, 3, 3, 3, 3, 3, 3, 3, 5, 5, 5, 5, 3, 3, 3, 3, 3, 3, 3, 3 },
			{ 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3 },
			{ 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3 },
			{ 4, 4, 4, 4, 4, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3 },
			{ 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3 },
			{ 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3 },
			{ 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3 },
			{ 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 2, 2 },
			{ 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 2, 2 }, };
	public static List<Case> battleField = new ArrayList<Case>();
	private Layer levelLayer;

	// Variable des armées
	private static Personnage[] armeeJ1 = { new Guerrier(0, 0, 1, 13), new Guerrier(1, 0, 1, 13),
			new Archer(0, 1, 1, 11), new Archer(1, 1, 1, 11) };
	private static Personnage[] armeeJ2 = { new Guerrier(19, 19, 2, 17), new Guerrier(18, 19, 2, 17),
			new Archer(19, 18, 2, 16), new Archer(18, 18, 2, 16) };
	public static List<Personnage> armeeJoueurUn = new ArrayList<Personnage>();
	public static List<Personnage> armeeJoueurDeux = new ArrayList<Personnage>();
	private Layer charsLayer;

	private boolean uniteSelect;
	private int indiceUnite;

	private Layer layerInfoBg;
	private Layer layerInfoInf;

	// Variable d'info
	private int currentPlayer;
	private Layer infoLayer;

	// Controlleur
	private Mouse mouse;

	/*
	 * Fonctions d'initialisation des donnée de jeu
	 */
	/**
	 * Fonction qui crée les armées des deux joueurs
	 */
	private void createArmy() {
		for (int i = 0; i < armeeJ1.length; i++) {
			armeeJoueurUn.add(armeeJ1[i]);
			armeeJoueurDeux.add(armeeJ2[i]);
		}
	}

	/**
	 * Fonction qui crée le terrain
	 */
	private void createBattleField() {
		for (int i = 0; i < level.length; i++) {
			for (int j = 0; j < level[i].length; j++) {
				switch (level[i][j]) {
				case 2:
					battleField.add(new Case(j, i, TypeTerrain.SPAWN));
					break;
				case 3:
					battleField.add(new Case(j, i, TypeTerrain.PLAINE));
					break;
				case 4:
					battleField.add(new Case(j, i, TypeTerrain.MUR));
					break;
				case 5:
					battleField.add(new Case(j, i, TypeTerrain.FORET));
					break;
				default:
					battleField.add(new Case(j, i, TypeTerrain.PLAINE));
				}
			}
		}
	}

	/**
	 * Fonction qui crée et met a zero le layer info
	 */
	public void initInfo() {
		infoLayer = gui.createLayer();
		infoLayer.setTileSize(32, 32);
		infoLayer.setSpriteCount(0);
		try {
			infoLayer.setTexture("tuille.png");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Fonction qui init le terrain est a chaque element lui associe son skin
	 * 
	 */
	public void initTerrain() {
		createBattleField();
		levelLayer = gui.createLayer();
		levelLayer.setSpriteCount(battleField.size());
		levelLayer.setTileSize(32, 32);
		try {
			levelLayer.setTexture("tuille.png");
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < battleField.size(); i++) {
			Case current = battleField.get(i);
			levelLayer.setSpriteTexture(i, current.skin % 10 - 1, current.skin / 10);
			levelLayer.setSpriteLocation(i, current.pos[0] * levelLayer.getTileWidth(),
					current.pos[1] * levelLayer.getTileHeight());
		}
	}

	/**
	 * Fonction qui Init les armée puis a chaque unités des deux armées leur associe
	 * leur skin
	 * 
	 */
	public void initArmee() {
		createArmy();
		uniteSelect = false;
		indiceUnite = -1;
		charsLayer = gui.createLayer();
		charsLayer.setSpriteCount(armeeJoueurUn.size() + armeeJoueurDeux.size());
		charsLayer.setTileSize(32, 32);
		try {
			charsLayer.setTexture("tuille.png");
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < armeeJoueurUn.size() + armeeJoueurDeux.size(); i++) {
			if (i < armeeJoueurUn.size()) {
				Personnage unite = armeeJoueurUn.get(i);
				charsLayer.setSpriteTexture(i, unite.skin % 10 - 1, unite.skin / 10);
				charsLayer.setSpriteLocation(i, unite.pos[0] * charsLayer.getTileWidth(),
						unite.pos[1] * charsLayer.getTileHeight());
			} else {
				Personnage unite = armeeJoueurDeux.get(i % armeeJoueurUn.size());
				charsLayer.setSpriteTexture(i, unite.skin % 10 - 1, unite.skin / 10);
				charsLayer.setSpriteLocation(i, unite.pos[0] * charsLayer.getTileWidth(),
						unite.pos[1] * charsLayer.getTileHeight());
			}
		}
	}

	@Override
	public void init() {
		initTerrain();
		initArmee();
		initInfo();
		currentPlayer = 1;
		gui.createWindow(levelLayer.getTileWidth() * levelWidth, levelLayer.getTileHeight() * levelHeight,
				"Yamas 2 - le retour !");
		mouse = gui.getMouse();
	}

	/*
	 * Fonctions principale !
	 */

	int selectedTileX, selectedTileY;

	/*
	 * TODO ajouter la possibilité de pouvoir bouger sur la case actuel, pouvoir
	 * annulé son déplacement et de pouvoir attaqué
	 */
	@Override
	public void handleInput() {
		selectedTileX = mouse.getX() / 32;
		selectedTileY = mouse.getY() / 32;

		List<Personnage> currentArmy;
		currentArmy = currentPlayer == 1 ? armeeJoueurUn : armeeJoueurDeux;

		if (selectedTileX >= 0 && selectedTileY >= 0 && selectedTileX < levelWidth && selectedTileY < levelHeight) {

			if (mouse.isButtonPressed(MouseEvent.BUTTON1)) {
				gestionDep(currentArmy);
			}

			else if (mouse.isButtonPressed(MouseEvent.BUTTON3)) {
				if (uniteSelect) {
					currentArmy.get(indiceUnite).estSelectionne = false;
					deselectUnite();
				}
			}
		}
	}

	long lastUpdate;
	long lastUpdate2;

	@Override
	public void update() {
		long now = System.nanoTime();
		if ((now - lastUpdate) >= 1000000000 / 4) {
			lastUpdate = now;
			if (currentPlayer == 1) {
				int countJouer = 0;
				int countVie = 0;
				for (int i = 0; i < armeeJoueurUn.size(); i++) {
					if (!armeeJoueurUn.get(i).aJouer)
						countJouer++;
				}
				for(int i = 0; i<armeeJoueurDeux.size(); i++) {
					if(armeeJoueurDeux.get(i).getEtat() == Etats.VIE)
						countVie ++;
				}
				if(countVie == 0) {
					gui.isClosingRequested();
				}
				if (countJouer == 0) {
					currentPlayer = 2;
					for (int i = 0; i < armeeJoueurUn.size(); i++) {
						armeeJoueurUn.get(i).aJouer = false;
					}
				}
			} else if (currentPlayer == 2) {
				int countJouer = 0;
				int countVie = 0;
				for (int i = 0; i < armeeJoueurDeux.size(); i++) {
					if (!armeeJoueurDeux.get(i).aJouer)
						countJouer++;
				}
				for(int i = 0; i<armeeJoueurUn.size(); i++) {
					if(armeeJoueurUn.get(i).getEtat() == Etats.VIE)
						countVie++;
				}
				if(countVie == 0) {
					gui.isClosingRequested();
				}
				if (countJouer == 0) {
					currentPlayer = 1;
					for (int i = 0; i < armeeJoueurDeux.size(); i++) {
						armeeJoueurDeux.get(i).aJouer = false;
					}
				}
			}
		}
		if ((now - lastUpdate2) >= 1000000000 / 12) {
			lastUpdate2 = now;
			boolean uniteSelect = false;
			int indiceUniteSelect = -1;
			for (int i = 0; i < battleField.size(); i++) {
				levelLayer.setSpriteLocation(i, battleField.get(i).pos[0] * levelLayer.getTileWidth(),
						battleField.get(i).pos[1] * levelLayer.getTileHeight());
				levelLayer.setSpriteTexture(i, battleField.get(i).skin % 10 - 1, battleField.get(i).skin / 10);
			}
			for (int i = 0; i < armeeJoueurUn.size() + armeeJoueurDeux.size(); i++) {
				int x, y, skin;
				if (i < armeeJoueurUn.size()) {
					x = armeeJoueurUn.get(i).pos[0];
					y = armeeJoueurUn.get(i).pos[1];
					skin = armeeJoueurUn.get(i).skin;
					if (armeeJoueurUn.get(i).estSelectionne && !armeeJoueurUn.get(i).aJouer) {
						uniteSelect = true;
						indiceUniteSelect = i;
					}
				} else {
					x = armeeJoueurDeux.get(i % armeeJoueurUn.size()).pos[0];
					y = armeeJoueurDeux.get(i % armeeJoueurUn.size()).pos[1];
					skin = armeeJoueurDeux.get(i % armeeJoueurUn.size()).skin;
					if (armeeJoueurDeux.get(i % armeeJoueurUn.size()).estSelectionne
							&& !armeeJoueurDeux.get(i % armeeJoueurUn.size()).aJouer) {
						uniteSelect = true;
						indiceUniteSelect = i % armeeJoueurUn.size();
					}
				}
				charsLayer.setSpriteLocation(i, x * charsLayer.getTileWidth(), y * charsLayer.getTileHeight());
				charsLayer.setSpriteTexture(i, skin % 10 - 1, skin / 10);
			}
			if (uniteSelect) {
				if (currentPlayer == 1) {
					Personnage unite = armeeJoueurUn.get(indiceUniteSelect);
					unite.calculDeplacement();
					infoLayer.setSpriteCount(unite.depPossible.size());
					for (int i = 0; i < unite.depPossible.size(); i++) {
						Case caseCourrante = unite.depPossible.get(i);
						infoLayer.setSpriteTexture(i, 3, 0);
						infoLayer.setSpriteLocation(i, caseCourrante.pos[0] * infoLayer.getTileWidth(),
								caseCourrante.pos[1] * infoLayer.getTileHeight());
					}
				} else {
					Personnage unite = armeeJoueurDeux.get(indiceUniteSelect);
					unite.calculDeplacement();
					infoLayer.setSpriteCount(unite.depPossible.size());
					for (int i = 0; i < unite.depPossible.size(); i++) {
						Case caseCourrante = unite.depPossible.get(i);
						infoLayer.setSpriteTexture(i, 3, 0);
						infoLayer.setSpriteLocation(i, caseCourrante.pos[0] * infoLayer.getTileWidth(),
								caseCourrante.pos[1] * infoLayer.getTileHeight());
					}
				}
			} else {
				infoLayer.setSpriteCount(0);
			}
		}
	}

	@Override
	public void render() {
		if (!gui.beginPaint()) {
			return;
		}
		try {
			gui.drawLayer(levelLayer);
			gui.drawLayer(charsLayer);
			gui.drawLayer(infoLayer);
		} finally {
			gui.endPaint();
		}
	}

	/*
	 * Fonctions secondaire
	 */

	private void gestionDep(List<Personnage> currentArmy) {
		Case c = battleField.get(selectedTileX + selectedTileY * 20);

		if (!uniteSelect) {
			for (int i = 0; i < currentArmy.size(); i++) {
				if (c.posEgale(currentArmy.get(i)) && !currentArmy.get(i).aJouer) {
					currentArmy.get(i).estSelectionne = true;
					selectUnite(i);
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					return;
				}
			}
		} else {
			Personnage uniteSel = currentArmy.get(indiceUnite);
			if (uniteSel.posEgale(c)) {
				uniteSel.aJouer = true;
				uniteSel.estSelectionne = false;
				deselectUnite();
				System.out.println("j'ai jouer !");
				return;
			}
			for (int i = 0; i < currentArmy.size(); i++) {
				Personnage p = currentArmy.get(i);
				if (c.posEgale(p) && !p.aJouer) {
					uniteSel.estSelectionne = false;
					p.estSelectionne = true;
					changementUniteSelect(i);
					System.out.println("changement fait !");
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					return;
				}
			}
			for (int i = 0; i < uniteSel.depPossible.size(); i++) {
				Case cc = uniteSel.depPossible.get(i);
				if (c.posEgale(cc)) {
					uniteSel.deplacement(selectedTileX, selectedTileY);
					deselectUnite();
					return;
				}
			}
		}
	}

	private void changementUniteSelect(int indice) {
		indiceUnite = indice;
	}

	private void selectUnite(int indice) {
		uniteSelect = true;
		indiceUnite = indice;
	}

	private void deselectUnite() {
		indiceUnite = -1;
		uniteSelect = false;
	}

}
