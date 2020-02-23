package model;

import java.util.ArrayList;

/**
 * 
 * @author Thomas Feuilletin
 * @author Yann Derrien
 *
 */
public class Personnage extends Element {

	private int pv;
	private int pvMax;

	// XXX l'attaque dépend des compétences. est-ce justicieux de faire une variable
	// attaque ?
	private int attaque;

	private int armee;

	public boolean estSelectionne;
	public boolean aJouer;

	public ArrayList<Case> depPossible;
	private int deplacement;

	/*
	 * XXX problème de portée Certaine attaques ont une portée différente à
	 * retravailler
	 */
	private int portee;
	public ArrayList<Case> attaquePossible;

	/**
	 * Constructeur. Crée un Personnage avec pv points de vie, attaque attaques,
	 * portee portée, deplacement points de deplacements. Ont lui associe le skin
	 * skin, la position(x,y) ainsi que l'armée du joueur armee
	 * 
	 * @param pv          un entier positif
	 * @param attaque     un entier positif ou nul
	 * @param portee      un entier positif
	 * @param deplacement un entier positif
	 * @param skin        un entier positif ou nul
	 * @param x           un entier compris entre 0 et la largeur du terrain
	 * @param y           un entier compris entre 0 et la hauteur du terrain
	 * @param armee       un entier compris entre 1 et le nombre d'armée
	 */
	public Personnage(int pv, int attaque, int portee, int deplacement, int skin, int x, int y, int armee) {
		super(x, y);
		this.skin = skin;
		this.pv = pv;
		this.pvMax = pv;
		this.attaque = attaque;
		this.portee = portee;
		this.deplacement = deplacement;
		this.depPossible = new ArrayList<Case>();
		this.estSelectionne = false;
		this.armee = armee;
		this.aJouer = false;
	}

	/*
	 * Fonctions qui gére le déplacements
	 */

	/*
	 * XXX Amélioration requise. Fonction pour avec des petits valeurs de d mais met
	 * beaucoup trop de temps avec des grandes valeurs (>8). A améliorer.
	 */

	/**
	 * Calcul l'ensemble des déplacement possible du personnage depuis la case c
	 * avec les points de deplacement d
	 * 
	 * @param c un entier compris entre 0 et largeur terrain * hauteur terrain
	 * @param d un entier positif ou nul
	 */
	private void calDeplacement(int c, int d) {
		boolean casePossible = true;
		int armeeEnemie = armee == 1 ? 2 : 1;
		// On vérifie qu'il n'y ai pas d'ennemie sur la case c
		if (armeeEnemie == 1) {
			for (int i = 0; i < game.PlayGameMode.armeeJoueurUn.size(); i++) {
				if (c == game.PlayGameMode.armeeJoueurUn.get(i).caseElement)
					casePossible = false;
			}
		} else if (armeeEnemie == 2) {
			for (int i = 0; i < game.PlayGameMode.armeeJoueurDeux.size(); i++) {
				if (c == game.PlayGameMode.armeeJoueurDeux.get(i).caseElement)
					casePossible = false;
			}
		}

		if (d >= 0 && c >= 0 && c < 400 && casePossible) {// Si l'unité à encore des points d, que la case existe dans
															// le plateau et si il n'y a pas d'ennemie sur la case
			depPossible.add(game.PlayGameMode.battleField.get(c));
			if ((c + 1) % 20 != 0 && c + 1 < 400)
				calDeplacement(c + 1, d - game.PlayGameMode.battleField.get(c + 1).coutDeplacement);
			if ((c - 1) % 20 != 19 && c - 1 >= 0)
				calDeplacement(c - 1, d - game.PlayGameMode.battleField.get(c - 1).coutDeplacement);
			if (c - 20 >= 0)
				calDeplacement(c - 20, d - game.PlayGameMode.battleField.get(c - 20).coutDeplacement);
			if (c + 20 < 400)
				calDeplacement(c + 20, d - game.PlayGameMode.battleField.get(c + 20).coutDeplacement);
		}
	}

	/*
	 * Après l'appel de la fonction calDeplacement(), la liste de de deplacement
	 * possible est remplis de doublon. Ont créer donc une fonction pour nettoyer
	 * cette liste.
	 */
	/**
	 * Enlève les doublons de la liste de de deplacement possible
	 */
	private void clearDep() {
		for (int i = 0; i < depPossible.size(); i++) {
			Case c = depPossible.get(i);
			int j = i + 1;
			while (j < depPossible.size()) {
				if (depPossible.get(j).pos[0] == c.pos[0] && depPossible.get(j).pos[1] == c.pos[1]) {
					depPossible.remove(j);
				} else
					j++;
			}
			if (armee == 1) {
				for (int h = 0; h < game.PlayGameMode.armeeJoueurUn.size(); h++) {
					if ((c.pos[0] != this.pos[0] || c.pos[1] != this.pos[1])
							&& (c.pos[0] == game.PlayGameMode.armeeJoueurUn.get(h).pos[0]
									&& c.pos[1] == game.PlayGameMode.armeeJoueurUn.get(h).pos[1])) {
						depPossible.remove(i--);
					}
				}
			} else {
				for (int h = 0; h < game.PlayGameMode.armeeJoueurDeux.size(); h++) {
					if ((c.pos[0] != this.pos[0] || c.pos[1] != this.pos[1])
							&& (c.pos[0] == game.PlayGameMode.armeeJoueurDeux.get(h).pos[0]
									&& c.pos[1] == game.PlayGameMode.armeeJoueurDeux.get(h).pos[1])) {
						depPossible.remove(i--);
					}
				}
			}
		}
	}

	/**
	 * Calcul les deplacement possible du personnges dans une liste sans doublon
	 */
	public void calculDeplacement() {
		calDeplacement(this.caseElement, this.deplacement);
		clearDep();
	}

	/**
	 * Met l'unité sur la case de coordonnée (x,y)
	 * 
	 * @param x un entier compris entre 0 et la largeur du terrain
	 * @param y un entier comrpis entre 0 et la hauteur du terrain
	 */
	public void deplacement(int x, int y) {
		this.pos[0] = x;
		this.pos[1] = y;
		this.caseElement = x + y * 20;
		this.estSelectionne = false;
		this.aJouer = true;
		this.depPossible.clear();
	}

	/*
	 * 
	 * Fonctions qui gére les capacités (attaques/supports)
	 */

	/**
	 * Calcule l'ensemble des cases sur les quelles le personnage peut attaquer
	 * depuis la case c avec la portée p
	 * 
	 * @param c un entier compris entre 0 et hauteur du terrain * largeur du terrain
	 * @param p un entier positif ou nul
	 */
	public static void calPorteAttaque(int c, int p) {
		// TODO à définir
	}

	/*
	 * Fonctions qui gére l'état du personnage
	 */

	/**
	 * Applique a la variable pv une variation de vie varVie
	 * 
	 * @param varVie un entier
	 */
	public void setVie(int varVie) {
		this.pv += varVie;
	}

}