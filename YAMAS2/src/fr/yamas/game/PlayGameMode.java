package fr.yamas.game;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import fr.yamas.controlleur.*;
import fr.yamas.model.*;
import fr.yamas.model.terrain.*;
import fr.yamas.model.unite.*;
import fr.yamas.vue.Camera;
import fr.yamas.vue.Layer;

/**
 * 
 * @author Thomas Feuilletin
 * @author Yann Derrien
 * @author Marie Ozon
 * 
 *
 */
public class PlayGameMode extends GameMode {

	// variables du terrain
	public static World world;

	// Variable des armées

	// Variable d'info
	private int currentPlayer;

	// Controlleur
	private Mouse mouse;
	private Keyboard keyboard;

	private Camera camera;
	private Updateur updateur;

	/*
	 * Fonctions d'initialisation des donnée de jeu
	 */

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
		Thread t = new Thread() {
			public void run() {
				updateur.calAllDep1();
			}
		};
		camera = new Camera(this, world);
		currentPlayer = 1;
		getGui().createWindow(32 * camera.getWidth(), 32 * camera.getHeight(),
				"Yamas 2 - le retour !");
		mouse = getGui().getMouse();
		keyboard = getGui().getKeyboard();
		t.run();
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
		selectedTileX = mouse.getX() / 32 + camera.getCorner(0);
		selectedTileY = mouse.getY() / 32 + camera.getCorner(1);

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
			getGui().setClosingRequested();
		}
		if (keyboard.isKeyPressed(KeyEvent.VK_DOWN)) {
			camera.move(0, 1);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		if (keyboard.isKeyPressed(KeyEvent.VK_UP)) {
			camera.move(0, -1);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		if (keyboard.isKeyPressed(KeyEvent.VK_RIGHT)) {
			camera.move(1, 0);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		if (keyboard.isKeyPressed(KeyEvent.VK_LEFT)) {
			camera.move(-1, 0);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	long lastUpdate;
	long lastUpdate2;

	@Override
	public void update() {
		long now = System.nanoTime();
		if ((now - lastUpdate) >= 1_000_000_000 / 4) {
			lastUpdate = now;
			if (currentPlayer == 1) {
				int countJouer = 0;
				int countVie = 0;
				for (int i = 0; i < world.getArmees().getArmee(1).size(); i++) {
					if (!world.getArmees().getArmee(1).get(i).isaJouer())
						countJouer++;
				}
				for (int i = 0; i < world.getArmees().getArmee(2).size(); i++) {
					if (world.getArmees().getArmee(2).get(i).getEtat() == Etats.VIE)
						countVie++;
				}
				if (countVie == 0) {
					getGui().isClosingRequested();
				}
				if (countJouer == 0) {

					currentPlayer = 2;
					for (int i = 0; i < world.getArmees().getArmee(1).size(); i++) {
						world.getArmees().getArmee(1).get(i).setaJouer(false);
					}
					updateur.calAllDep2();
					camera.centerCamera(
							world.getCarte().getCarte().get(world.getArmees().getArmee(2).get(0).getCaseElement()));
				}
			} else if (currentPlayer == 2) {
				int countJouer = 0;
				int countVie = 0;
				for (int i = 0; i < world.getArmees().getArmee(2).size(); i++) {
					if (!world.getArmees().getArmee(2).get(i).isaJouer())
						countJouer++;
				}
				for (int i = 0; i < world.getArmees().getArmee(1).size(); i++) {
					if (world.getArmees().getArmee(1).get(i).getEtat() == Etats.VIE)
						countVie++;
				}
				if (countVie == 0) {
					getGui().isClosingRequested();
				}
				if (countJouer == 0) {
					currentPlayer = 1;
					for (int i = 0; i < world.getArmees().getArmee(2).size(); i++) {
						world.getArmees().getArmee(2).get(i).setaJouer(false);
					}
					updateur.calAllDep1();
					camera.centerCamera(
							world.getCarte().getCarte().get(world.getArmees().getArmee(1).get(0).getCaseElement()));
				}
			}
		}
		if ((now - lastUpdate2) >= 1000000000 / 12) {
			lastUpdate2 = now;
			camera.update();
		}
	}

	@Override
	public void render() {
		if (!getGui().beginPaint()) {
			return;
		}
		try {
			camera.draw();
		} finally {
			getGui().endPaint();
		}
	}

	/*
	 * Fonctions secondaire
	 */

	private void gestionDep(List<Personnage> currentArmy) {
		Case c = world.getCarte().getCarte().get(selectedTileX + selectedTileY * 100);
		if (updateur.getpCourant() == null) {
			for (int i = 0; i < currentArmy.size(); i++) {
				if (c.posEgale(currentArmy.get(i)) && !currentArmy.get(i).isaJouer()) {
					currentArmy.get(i).setSelectionne(true);
					updateur.setpCourant(currentArmy.get(i));
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
				uniteSel.setaJouer(true);
				uniteSel.setSelectionne(false);
				updateur.calAllDep();
				return;
			}
			for (int i = 0; i < currentArmy.size(); i++) {
				Personnage p = currentArmy.get(i);
				if (c.posEgale(p) && !p.isaJouer()) {
					uniteSel.setSelectionne(false);
					p.setSelectionne(true);
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
					uniteSel.setSelectionne(false);
					updateur.calAllDep();
					return;
				}
			}
		}
	}

}
