package game;

import vue.GUIFacade;

public abstract class GameMode {
	
	protected Main parent;
	protected GUIFacade gui;
	
	/*
	 *Setteur 
	 */
	public void setGameMode(GameMode mode) {
		parent.setGameMode(mode);
	}
	
	public void setParent(Main main) {
		this.parent = main;
	}
	
	public void setGUI(GUIFacade gui) {
		this.gui = gui;
	}
	
	/**
	 * Fonction qui initialise les donn�es du mode
	 */
	public abstract void init();
	
	/**
	 * Fonction qui g�re les interaction de l'utilisateur
	 */
	public abstract void handleInput();
	
	/**
	 * Fonction qui met a jour les �tats du mode
	 */
	public abstract void update();
	
	/**
	 * Fonction qui s'occupe du rendu du mode 
	 */
	public abstract void render();
	

}
