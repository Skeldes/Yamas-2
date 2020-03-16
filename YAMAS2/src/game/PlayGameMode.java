package game;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import controlleur.Keyboard;
import controlleur.Mouse;
import model.*;
import model.terrain.Carte;
import model.terrain.Case;
import model.terrain.ElementFactory;
import model.unite.Armee;
import model.unite.ArmeeFactory;
import model.unite.Etats;
import model.unite.Personnage;
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
	public static World world;
	private Layer levelLayer;

	// Variable des armées
	private Layer charsLayer;

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

	private Updateur updateur;

	/*
	 * Fonctions d'initialisation des donnée de jeu
	 */

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
		Carte c = world.getCarte();
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
				Case current = c.getCarte().get(i * world.getCarte().getcHeight() + j);

				levelLayer.setSpriteTexture(i * visionWidth + j, current.getSkin() % 10 - 1, current.getSkin() / 10);
				levelLayer.setSpriteLocation(i * visionWidth + j, current.getPos()[0] * levelLayer.getTileWidth(),
						current.getPos()[1] * levelLayer.getTileHeight());
			}
		}
	}

	/**
	 * Fonction qui Init les armée puis a chaque unités des deux armées leur associe
	 * leur skin
	 * 
	 */
	public void initArmee() {
		Armee a = world.getArmees();
		charsLayer = gui.createLayer();
		charsLayer.setSpriteCount(a.getArmee(1).size() + a.getArmee(2).size());
		charsLayer.setTileSize(32, 32);
		try {
			charsLayer.setTexture("tileArmee.png");
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < a.getArmee(1).size() + a.getArmee(2).size(); i++) {
			if (i < a.getArmee(1).size()) {
				Personnage unite = a.getArmee(1).get(i);
				charsLayer.setSpriteTexture(i, unite.getSkin() % 10 - 1, unite.getSkin() / 10);
				charsLayer.setSpriteLocation(i, unite.getPos()[0] * charsLayer.getTileWidth(),
						unite.getPos()[1] * charsLayer.getTileHeight());
			} else {
				Personnage unite = a.getArmee(2).get(i % a.getArmee(1).size());
				charsLayer.setSpriteTexture(i, unite.getSkin() % 10 - 1, unite.getSkin() / 10);
				charsLayer.setSpriteLocation(i, unite.getPos()[0] * charsLayer.getTileWidth(),
						unite.getPos()[1] * charsLayer.getTileHeight());
			}
		}
	}

	@Override
	public void init() {
		Carte c = new Carte(1);
		Armee a = new Armee();
		ElementFactory f = new ElementFactory();
		ArmeeFactory af = new ArmeeFactory();
		c.setFactory(f.getDefault());
		a.setAf(af.getDefault());
		a.init();
		c.init();

		updateur = new Updateur();
		world = new World();
		world.setCarte(c);
		world.setArmees(a);
		updateur.setW(world);
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
		currentArmy = currentPlayer == 1 ? world.getArmees().getArmee(1) : world.getArmees().getArmee(2);

		if (selectedTileX >= 0 && selectedTileY >= 0 && selectedTileX < 100 && selectedTileY < 100) {

			if (mouse.isButtonPressed(MouseEvent.BUTTON1)) {
				gestionDep(currentArmy);
			}

			else if (mouse.isButtonPressed(MouseEvent.BUTTON3)) {
				if (updateur.getpCourant() != null) {
					updateur.setpCourant(null);
				}
			}
		}

		if (keyboard.isKeyPressed(KeyEvent.VK_ESCAPE)) {
			gui.setClosingRequested();
		}
		if (keyboard.isKeyPressed(KeyEvent.VK_DOWN)) {
			if (angleBasY + 1 <= world.getCarte().getcHeight()) {
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
			if (angleBasX + 1 <= world.getCarte().getcWidth()) {
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
				for (int i = 0; i < world.getArmees().getArmee(1).size(); i++) {
					if (!world.getArmees().getArmee(1).get(i).aJouer)
						countJouer++;
				}
				for (int i = 0; i < world.getArmees().getArmee(2).size(); i++) {
					if (world.getArmees().getArmee(2).get(i).getEtat() == Etats.VIE)
						countVie++;
				}
				if (countVie == 0) {
					gui.isClosingRequested();
				}
				if (countJouer == 0) {

					currentPlayer = 2;
					for (int i = 0; i < world.getArmees().getArmee(1).size(); i++) {
						world.getArmees().getArmee(1).get(i).aJouer = false;
					}
					boolean[] aBouger = { false, false };// 0 angle haut 1 angleBas
					Personnage p = world.getArmees().getArmee(2).get(0);
					if (p.getPos()[0] - 10 >= 0 && p.getPos()[0] + 10 < 100 && p.getPos()[1] - 10 >= 0
							&& p.getPos()[1] + 10 < 100) {
						angleHautX = p.getPos()[0] - 10;
						angleHautY = p.getPos()[1] - 10;
						angleBasX = p.getPos()[0] + 10;
						angleBasY = p.getPos()[1] + 10;
					}
					else {
						if (p.getPos()[0] - 10 < 0) {
							angleHautX = 0;
							angleBasX = 20;
							aBouger[0] = true;
						} else {
							angleHautX = p.getPos()[0] - 10;
						}
						if (p.getPos()[1] - 10 < 0) {
							angleHautY = 0;
							angleBasY = 20;
							aBouger[1] = true;
						} else {
							angleHautY = p.getPos()[1] - 10;
						}
						if (p.getPos()[0] + 10 > 100) {
							angleBasX = 100;
							angleHautX = 80;
						} else if (p.getPos()[0] + 10 > 100 && !aBouger[0]) {
							angleBasX = p.getPos()[0] + 10;
						}
						if (p.getPos()[1] + 10 > 100) {
							angleBasY = 100;
							angleHautY = 80;
						} else if (p.getPos()[1] + 10 > 100 && !aBouger[1]) {
							angleBasY = p.getPos()[1] + 10;
						}
					}
					System.out.println(angleHautX + " " + angleHautY + " : " + angleBasX + " " + angleBasY);
				}
			} else if (currentPlayer == 2) {
				int countJouer = 0;
				int countVie = 0;
				for (int i = 0; i < world.getArmees().getArmee(2).size(); i++) {
					if (!world.getArmees().getArmee(2).get(i).aJouer)
						countJouer++;
				}
				for (int i = 0; i < world.getArmees().getArmee(1).size(); i++) {
					if (world.getArmees().getArmee(1).get(i).getEtat() == Etats.VIE)
						countVie++;
				}
				if (countVie == 0) {
					gui.isClosingRequested();
				}
				if (countJouer == 0) {
					currentPlayer = 1;
					for (int i = 0; i < world.getArmees().getArmee(2).size(); i++) {
						world.getArmees().getArmee(2).get(i).aJouer = false;
					}

					boolean[] aBouger = { false, false };// 0 angle haut 1 angleBas
					Personnage p = world.getArmees().getArmee(1).get(0);
					if (p.getPos()[0] - 10 < 0) {
						angleHautX = 0;
						angleBasX = 20;
						aBouger[0] = true;
					} else {
						angleHautX = p.getPos()[0] - 10;
					}
					if (p.getPos()[1] - 10 < 0) {
						angleHautY = 0;
						angleBasY = 20;
						aBouger[1] = true;
					} else {
						angleHautY = p.getPos()[1] - 10;
					}
					if (p.getPos()[0] + 10 > 100) {
						angleBasX = 100;
						angleHautX = 80;
					} else if (p.getPos()[0] + 10 > 100 && !aBouger[0]) {
						angleBasX = p.getPos()[0] + 10;
					}
					if (p.getPos()[1] + 10 > 100) {
						angleBasY = 100;
						angleHautY = 80;
					} else if (p.getPos()[1] + 10 > 100 && !aBouger[1]) {
						angleBasY = p.getPos()[1] + 10;
					}
				}
			}
		}
		if ((now - lastUpdate2) >= 1000000000 / 12) {
			lastUpdate2 = now;

			updateTerrain();

			updateArmee();

			if (updateur.getpCourant() != null) {
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
		Case c = world.getCarte().getCarte().get(selectedTileX + selectedTileY * 100);
		if (updateur.getpCourant() == null) {
			for (int i = 0; i < currentArmy.size(); i++) {
				if (c.posEgale(currentArmy.get(i)) && !currentArmy.get(i).aJouer) {
					updateur.setpCourant(currentArmy.get(i));
					updateur.calculDeplacement();
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					return;
				}
			}
		} else {
			Personnage uniteSel = updateur.getpCourant();
			if (uniteSel.posEgale(c)) {
				uniteSel.aJouer = true;
				uniteSel.estSelectionne = false;
				System.out.println("Jai jouer");
				updateur.setpCourant(null);
				return;
			}
			for (int i = 0; i < currentArmy.size(); i++) {
				Personnage p = currentArmy.get(i);
				if (c.posEgale(p) && !p.aJouer) {
					uniteSel.estSelectionne = false;
					p.estSelectionne = true;
					updateur.setpCourant(p);
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					return;
				}
			}
			for (int i = 0; i < uniteSel.getDepPossible().size(); i++) {
				Case cc = uniteSel.getDepPossible().get(i);
				if (c.posEgale(cc)) {
					uniteSel.deplacement(selectedTileX, selectedTileY);
					updateur.setpCourant(null);
					return;
				}
			}
		}
	}

	/**
	 * Fonction qui met à jour le layer levelLayer
	 */
	private void updateTerrain() {
		int index = 0;
		int screenX = 0;
		int screenY = 0;
		int width = world.getCarte().getcWidth();
		for (int i = angleHautY; i < angleBasY; i++) {
			for (int j = angleHautX; j < angleBasX; j++) {

				levelLayer.setSpriteTexture(index, world.getCarte().getCarte().get(j + i * width).getSkin() % 10 - 1,
						world.getCarte().getCarte().get(j + i * width).getSkin() / 10);

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
		for (int i = 0; i < world.getArmees().getArmee(1).size() + world.getArmees().getArmee(2).size(); i++) {
			int x, y, skin;
			if (i < world.getArmees().getArmee(1).size()) {
				x = world.getArmees().getArmee(1).get(i).getPos()[0];
				y = world.getArmees().getArmee(1).get(i).getPos()[1];
				skin = world.getArmees().getArmee(1).get(i).getSkin();
			} else {
				x = world.getArmees().getArmee(2).get(i % world.getArmees().getArmee(1).size()).getPos()[0];
				y = world.getArmees().getArmee(2).get(i % world.getArmees().getArmee(1).size()).getPos()[1];
				skin = world.getArmees().getArmee(2).get(i % world.getArmees().getArmee(1).size()).getSkin();
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
			Personnage unite = updateur.getpCourant();
			infoLayer.setSpriteCount(unite.getDepPossible().size());
			for (int i = 0; i < unite.getDepPossible().size(); i++) {
				Case caseCourrante = unite.getDepPossible().get(i);
				if (caseCourrante.getPos()[0] - angleHautX >= 0 && caseCourrante.getPos()[0] - angleHautX < 20
						&& caseCourrante.getPos()[1] - angleHautY >= 0 && caseCourrante.getPos()[1] - angleHautY < 20) {
					infoLayer.setSpriteTexture(i, 3, 0);
					infoLayer.setSpriteLocation(i, (caseCourrante.getPos()[0] - angleHautX) * infoLayer.getTileWidth(),
							(caseCourrante.getPos()[1] - angleHautY) * infoLayer.getTileHeight());
				}
			}
		} else {
			Personnage unite = updateur.getpCourant();
			updateur.calculDeplacement();
			infoLayer.setSpriteCount(unite.getDepPossible().size());
			for (int i = 0; i < unite.getDepPossible().size(); i++) {
				Case caseCourrante = unite.getDepPossible().get(i);
				if (caseCourrante.getPos()[0] - angleHautX >= 0 && caseCourrante.getPos()[0] - angleHautX < 20
						&& caseCourrante.getPos()[1] - angleHautY >= 0 && caseCourrante.getPos()[1] - angleHautY < 20) {
					infoLayer.setSpriteTexture(i, 3, 0);
					infoLayer.setSpriteLocation(i, (caseCourrante.getPos()[0] - angleHautX) * infoLayer.getTileWidth(),
							(caseCourrante.getPos()[1] - angleHautY) * infoLayer.getTileHeight());
				}
			}
		}
	}

}
