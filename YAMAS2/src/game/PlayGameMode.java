package game;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import controlleur.Keyboard;
import controlleur.Mouse;
import model.*;
import vue.Layer;

/**
 * 
 * @author Thomas Feuilletin
 * @author Yann Derrien
 * @author Marie Ozon
 *
 */
public class PlayGameMode extends GameMode {

	// variables du terrain
	final int levelHeight = 100;
	final int levelWidth = 100;
	final Carte carte = new Carte(1);
	public static List<Case> battleField = new ArrayList<Case>();
	private Layer levelLayer;

	// Variable des armées
	private static Personnage[] armeeJ1 = { new Guerrier(3, 1, 1, 13), new Guerrier(3, 3, 1, 13),
			new Archer(4, 2, 1, 11), new Archer(5, 1, 1, 11) };
	private static Personnage[] armeeJ2 = { new Guerrier(98, 98, 2, 17), new Guerrier(98, 96, 2, 17),
			new Archer(96, 98, 2, 16), new Archer(96, 96, 2, 16) };
	public static List<Personnage> armeeJoueurUn = new ArrayList<Personnage>();
	public static List<Personnage> armeeJoueurDeux = new ArrayList<Personnage>();
	private Layer charsLayer;

	private boolean uniteSelect;
	private int indiceUnite;
	private int indiceUniteSelect;

	private Layer layerInfoBg;
	private Layer layerInfoInf;

	// Variable d'info
	private int currentPlayer;
	private Layer infoLayer;

	// Controlleur
	private Mouse mouse;
	private Keyboard keyboard;

	// Champs de vision
	final int visionWidth = 20;
	final int visionHeight = 20;
	private int angleHautX;
	private int angleHautY;
	private int angleBasX;
	private int angleBasY;

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
		for (int i = 0; i < carte.carteC1.length; i++) {
			for (int j = 0; j < carte.carteC1[i].length; j++) {
				switch (carte.carteC1[i][j]) {
				case 6:
					battleField.add(new Case(j, i, TypeTerrain.PLAINE));
					break;
				case 13:
					battleField.add(new Case(j, i, TypeTerrain.SOL));
					break;
				default:
					battleField.add(new Case(j, i, TypeTerrain.PLAINE));
					break;
				}
			}
		}
		for (int i = 0; i < carte.carteC2.length; i++) {
			for (int j = 0; j < carte.carteC2[i].length; j++) {
				switch (carte.carteC2[i][j]) {
				case 5:
					battleField.remove(i * carte.carteC1.length + j);
					battleField.add(i * carte.carteC1.length + j, new Case(j, i, TypeTerrain.SPAWN));
					break;
				case 7:
					battleField.remove(i * carte.carteC1.length + j);
					battleField.add(i * carte.carteC1.length + j, new Case(j, i, TypeTerrain.MURH));
					break;
				case 8:
					battleField.remove(i * carte.carteC1.length + j);
					battleField.add(i * carte.carteC1.length + j, new Case(j, i, TypeTerrain.FORET));
					break;
				case 9:
					battleField.remove(i * carte.carteC1.length + j);
					battleField.add(i * carte.carteC1.length + j, new Case(j, i, TypeTerrain.EAU));
					break;
				case 11:
					battleField.remove(i * carte.carteC1.length + j);
					battleField.add(i * carte.carteC1.length + j, new Case(j, i, TypeTerrain.MURV));
					break;
				case 12:
					battleField.remove(i * carte.carteC1.length + j);
					battleField.add(i * carte.carteC1.length + j, new Case(j, i, TypeTerrain.PONTH));
					break;
				case 13:
					battleField.remove(i * carte.carteC1.length + j);
					battleField.add(i * carte.carteC1.length + j, new Case(j, i, TypeTerrain.PONTV));
					break;
				case 14:
					battleField.remove(i * carte.carteC1.length + j);
					battleField.add(i * carte.carteC1.length + j, new Case(j, i, TypeTerrain.SOL));
					break;
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
		levelLayer.setSpriteCount(visionWidth * visionHeight);
		levelLayer.setTileSize(32, 32);
		angleHautX = 0;
		angleHautY = 0;
		angleBasX = 20;
		angleBasY = 20;

		try {
			levelLayer.setTexture("tileTerrain.png");
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < angleBasY; i++) {
			for (int j = 0; j < angleBasX; j++) {
				Case current = battleField.get(i * levelHeight + j);

				levelLayer.setSpriteTexture(i * visionWidth + j, current.skin % 10 - 1, current.skin / 10);
				levelLayer.setSpriteLocation(i * visionWidth + j, current.pos[0] * levelLayer.getTileWidth(),
						current.pos[1] * levelLayer.getTileHeight());
			}
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
		gui.createWindow(levelLayer.getTileWidth() * visionWidth, levelLayer.getTileHeight() * visionHeight,
				"Yamas 2 - le retour !");
		mouse = gui.getMouse();
		keyboard = gui.getKeyboard();
	}

	/*
	 * Fonctions principale !
	 */

	int selectedTileX, selectedTileY;

	/*
	 * TODO ajouter la possibilité de pouvoir annulé son déplacement et de pouvoir
	 * attaqué
	 */
	@Override
	public void handleInput() {
		selectedTileX = mouse.getX() / 32 + angleHautX;
		selectedTileY = mouse.getY() / 32 + angleHautY;
		
		List<Personnage> currentArmy;
		currentArmy = currentPlayer == 1 ? armeeJoueurUn : armeeJoueurDeux;

		if (selectedTileX >= 0 && selectedTileY >= 0 && selectedTileX < 100 && selectedTileY < 100) {

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

		if (keyboard.isKeyPressed(KeyEvent.VK_ESCAPE)) {
			gui.setClosingRequested();
		}
		if (keyboard.isKeyPressed(KeyEvent.VK_DOWN)) {
			if (angleBasY + 1 <= levelHeight) {
				angleHautY++;
				angleBasY++;
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		if (keyboard.isKeyPressed(KeyEvent.VK_UP)) {
			if (angleHautY - 1 >= 0) {
				angleHautY--;
				angleBasY--;
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		if (keyboard.isKeyPressed(KeyEvent.VK_RIGHT)) {
			if (angleBasX + 1 <= levelWidth) {
				angleHautX++;
				angleBasX++;
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		if (keyboard.isKeyPressed(KeyEvent.VK_LEFT)) {
			if (angleHautX - 1 >= 0) {
				angleHautX--;
				angleBasX--;
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
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
				for (int i = 0; i < armeeJoueurDeux.size(); i++) {
					if (armeeJoueurDeux.get(i).getEtat() == Etats.VIE)
						countVie++;
				}
				if (countVie == 0) {
					gui.isClosingRequested();
				}
				if (countJouer == 0) {

					currentPlayer = 2;
					for (int i = 0; i < armeeJoueurUn.size(); i++) {
						armeeJoueurUn.get(i).aJouer = false;
					}
					boolean[] aBouger = {false,false};//0 angle haut  1 angleBas
					Personnage p = armeeJoueurDeux.get(0);
					if (p.pos[0] - 10 < 0) {
						angleHautX = 0;
						angleBasX = 20;
						aBouger[0] = true;
					} else {
						angleHautX = p.pos[0] - 10;
					}
					if (p.pos[1] - 10 < 0) {
						angleHautY = 0;
						angleBasY = 20;
						aBouger[1]=true;
					} else {
						angleHautY = p.pos[1] - 10;
					}
					if (p.pos[0] + 10 > 100) {
						angleBasX = 100;
						angleHautX = 80;
					} else if (p.pos[0] + 10 > 100 && !aBouger[0]) {
						angleBasX = p.pos[0] + 10;
					}
					if (p.pos[1] + 10 > 100) {
						angleBasY = 100;
						angleHautY = 80;
					} else if (p.pos[1] + 10 > 100 && !aBouger[1]){
						angleBasY = p.pos[1] + 10;
					}
				}
			} else if (currentPlayer == 2) {
				int countJouer = 0;
				int countVie = 0;
				for (int i = 0; i < armeeJoueurDeux.size(); i++) {
					if (!armeeJoueurDeux.get(i).aJouer)
						countJouer++;
				}
				for (int i = 0; i < armeeJoueurUn.size(); i++) {
					if (armeeJoueurUn.get(i).getEtat() == Etats.VIE)
						countVie++;
				}
				if (countVie == 0) {
					gui.isClosingRequested();
				}
				if (countJouer == 0) {
					currentPlayer = 1;
					for (int i = 0; i < armeeJoueurDeux.size(); i++) {
						armeeJoueurDeux.get(i).aJouer = false;
					}
					
					boolean[] aBouger = {false,false};//0 angle haut  1 angleBas
					Personnage p = armeeJoueurUn.get(0);
					if (p.pos[0] - 10 < 0) {
						angleHautX = 0;
						angleBasX = 20;
						aBouger[0] = true;
					} else {
						angleHautX = p.pos[0] - 10;
					}
					if (p.pos[1] - 10 < 0) {
						angleHautY = 0;
						angleBasY = 20;
						aBouger[1]=true;
					} else {
						angleHautY = p.pos[1] - 10;
					}
					if (p.pos[0] + 10 > 100) {
						angleBasX = 100;
						angleHautX = 80;
					} else if (p.pos[0] + 10 > 100 && !aBouger[0]) {
						angleBasX = p.pos[0] + 10;
					}
					if (p.pos[1] + 10 > 100) {
						angleBasY = 100;
						angleHautY = 80;
					} else if (p.pos[1] + 10 > 100 && !aBouger[1]){
						angleBasY = p.pos[1] + 10;
					}
				}
			}
		}
		if ((now - lastUpdate2) >= 1000000000 / 12) {
			lastUpdate2 = now;
			uniteSelect = false;
			indiceUniteSelect = -1;

			updateTerrain();

			updateArmee();

			if (uniteSelect) {
				updateInfo();
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
		Case c = battleField.get(selectedTileX + selectedTileY * 100);

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
				return;
			}
			for (int i = 0; i < currentArmy.size(); i++) {
				Personnage p = currentArmy.get(i);
				if (c.posEgale(p) && !p.aJouer) {
					uniteSel.estSelectionne = false;
					p.estSelectionne = true;
					changementUniteSelect(i);
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

	/**
	 * Fonction qui met à jour le layer levelLayer
	 */
	private void updateTerrain() {
		int index = 0;
		int screenX = 0;
		int screenY = 0;
		for (int i = angleHautY; i < angleBasY; i++) {
			for (int j = angleHautX; j < angleBasX; j++) {

				levelLayer.setSpriteTexture(index, battleField.get(j + i * levelWidth).skin % 10 - 1,
						battleField.get(j + i * levelWidth).skin / 10);

				levelLayer.setSpriteLocation(index++, screenX * levelLayer.getTileWidth(),
						screenY * levelLayer.getTileHeight());
				screenX++;
			}
			screenX = 0;
			screenY++;
		}
	}

	/**
	 * 
	 * @param indiceUniteSelect
	 */
	private void updateArmee() {
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
			if (x - angleHautX >= 0 && x - angleHautX < 20 && y - angleHautY >= 0 && y - angleHautY < 20) {
				charsLayer.setSpriteLocation(i, (x - angleHautX) * charsLayer.getTileWidth(),
						(y - angleHautY) * charsLayer.getTileHeight());
				charsLayer.setSpriteTexture(i, skin % 10 - 1, skin / 10);
			} else
				charsLayer.setSpriteTexture(i, 0, 0);
		}
	}

	private void updateInfo() {
		if (currentPlayer == 1) {
			Personnage unite = armeeJoueurUn.get(indiceUniteSelect);
			unite.calculDeplacement();
			infoLayer.setSpriteCount(unite.depPossible.size());
			for (int i = 0; i < unite.depPossible.size(); i++) {
				Case caseCourrante = unite.depPossible.get(i);
				if (caseCourrante.pos[0] - angleHautX >= 0 && caseCourrante.pos[0] - angleHautX < 20
						&& caseCourrante.pos[1] - angleHautY >= 0 && caseCourrante.pos[1] - angleHautY < 20) {
					infoLayer.setSpriteTexture(i, 3, 0);
					infoLayer.setSpriteLocation(i, (caseCourrante.pos[0] - angleHautX) * infoLayer.getTileWidth(),
							(caseCourrante.pos[1] - angleHautY) * infoLayer.getTileHeight());
				}
			}
		} else {
			Personnage unite = armeeJoueurDeux.get(indiceUniteSelect);
			unite.calculDeplacement();
			infoLayer.setSpriteCount(unite.depPossible.size());
			for (int i = 0; i < unite.depPossible.size(); i++) {
				Case caseCourrante = unite.depPossible.get(i);
				if (caseCourrante.pos[0] - angleHautX >= 0 && caseCourrante.pos[0] - angleHautX < 20
						&& caseCourrante.pos[1] - angleHautY >= 0 && caseCourrante.pos[1] - angleHautY < 20) {
					infoLayer.setSpriteTexture(i, 3, 0);
					infoLayer.setSpriteLocation(i, (caseCourrante.pos[0] - angleHautX) * infoLayer.getTileWidth(),
							(caseCourrante.pos[1] - angleHautY) * infoLayer.getTileHeight());
				}
			}
		}
	}

}
