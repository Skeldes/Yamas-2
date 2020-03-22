package fr.yamas.game;

import fr.yamas.vue.*;

/**
 * Class principale
 * 
 * 
 * @author Thomas Feuilletin
 * @author Yann Derrien
 *
 */
public class Main {

	private GUIFacade gui;
	private GameMode currentMode;

	/**
	 * Boucle de jeu principale
	 */
	public void run() {
		int fps = 60;
		long nanoPerFrame = (long) (1000000000.0 / fps);
		long lastTime = System.nanoTime();
		while (!gui.isClosingRequested()) {
			long nowTime = System.nanoTime();
			if ((nowTime - lastTime) < nanoPerFrame) {
				continue;
			}
			lastTime = nowTime;
			synchronized (this) {
				currentMode.handleInput();
				currentMode.update();
				currentMode.render();
			}

			long elapsed = System.nanoTime() - lastTime;
			long milliSleep = (nanoPerFrame - elapsed) / 1000000;
			if (milliSleep > 0) {
				try {
					Thread.sleep(milliSleep);
				} catch (InterruptedException ex) {
					ex.printStackTrace();
				}
			}
		}
		gui.dispose();
	}

	/**
	 * Boucle de jeu pour test optimisation
	 */
	public void bench() {
		int count = 0;
		long begin = System.nanoTime();

		while (!gui.isClosingRequested()) {
			synchronized (this) {
				currentMode.handleInput();
				currentMode.update();
				currentMode.render();
			}
			count++;
		}

		long end = System.nanoTime();
		double fps = count / ((end - begin) / 1_000_000_000.0);
		System.out.println("Fps : " + fps);
		gui.dispose();
	}

	/**
	 * Fonction qui initie la façade
	 * 
	 * @param gui une façade
	 */
	public void setGUI(GUIFacade gui) {
		this.gui = gui;
	}

	/**
	 * Change le mode de jeu par le mode mode
	 * 
	 * @param mode un mode de jeu
	 */
	public synchronized void setGameMode(GameMode mode) {
		try {
			mode.setParent(this);
			mode.setGUI(gui);
			mode.init();
			this.currentMode = mode;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Main Yamas = new Main();
		Yamas.setGUI(new AWTGUIFacade());
		Yamas.setGameMode(new PlayGameMode());
		Yamas.bench();
	}

}
