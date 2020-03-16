package model;

import java.util.ArrayList;

import model.terrain.Case;
import model.unite.Personnage;

public class Updateur {

	private World w;
	private Personnage pCourant;

	private ArrayList<Case> calDeplacement(int c, int d, int armee) {
		boolean casePossible = true;
		int armeeEnemie = armee == 1 ? 2 : 1;
		ArrayList<Case> depPossible = new ArrayList<Case>();
		// On vérifie qu'il n'y ai pas d'ennemie sur la case c
		if (armeeEnemie == 1) {
			for (int i = 0; i < w.getArmees().getArmee(1).size(); i++) {
				if (c == w.getArmees().getArmee(1).get(i).getCaseElement())
					casePossible = false;
			}
		} else if (armeeEnemie == 2) {
			for (int i = 0; i < w.getArmees().getArmee(2).size(); i++) {
				if (c == w.getArmees().getArmee(2).get(i).getCaseElement())
					casePossible = false;
			}
		}
		if (d >= 0 && c >= 0 && c < 10000 && casePossible) {// Si l'unité à encore des points d, que la case existe dans
															// le plateau et si il n'y a pas d'ennemie sur la case
			depPossible.add(w.getCarte().getCarte().get(c));
			if ((c + 1) % 100 != 0 && c + 1 < 10000)
				depPossible.addAll(
						calDeplacement(c + 1, d - w.getCarte().getCarte().get(c + 1).getCoutDeplacement(), armee));
			if ((c - 1) % 100 != 99 && c - 1 >= 0)
				depPossible.addAll(
						calDeplacement(c - 1, d - w.getCarte().getCarte().get(c - 1).getCoutDeplacement(), armee));
			if (c - 100 >= 0)
				depPossible.addAll(
						calDeplacement(c - 100, d - w.getCarte().getCarte().get(c - 100).getCoutDeplacement(), armee));
			if (c + 100 < 10000)
				depPossible.addAll(
						calDeplacement(c + 100, d - w.getCarte().getCarte().get(c + 100).getCoutDeplacement(), armee));
		}
		return depPossible;
	}

	private ArrayList<Case> clearDep(ArrayList<Case> depPossible, Case oC, int armee) {
		for (int i = 0; i < depPossible.size(); i++) {
			Case c = depPossible.get(i);
			int j = i + 1;
			while (j < depPossible.size()) {
				if (depPossible.get(j).getPos()[0] == c.getPos()[0]
						&& depPossible.get(j).getPos()[1] == c.getPos()[1]) {
					depPossible.remove(j);
				} else
					j++;
			}
			if (armee == 1) {
				for (int h = 0; h < game.PlayGameMode.world.getArmees().getArmee(1).size(); h++) {
					if ((c.getPos()[0] != oC.getPos()[0] || c.getPos()[1] != oC.getPos()[1])
							&& (c.getPos()[0] == w.getArmees().getArmee(1).get(h).getPos()[0]
									&& c.getPos()[1] == w.getArmees().getArmee(1).get(h).getPos()[1])) {
						depPossible.remove(i--);
					}
				}
			} else {
				for (int h = 0; h < game.PlayGameMode.world.getArmees().getArmee(2).size(); h++) {
					if ((c.getPos()[0] != oC.getPos()[0] || c.getPos()[1] != oC.getPos()[1])
							&& (c.getPos()[0] == w.getArmees().getArmee(2).get(h).getPos()[0]
									&& c.getPos()[1] == w.getArmees().getArmee(2).get(h).getPos()[1])) {
						depPossible.remove(i--);
					}
				}
			}
		}
		return depPossible;
	}

	public void calculDeplacement() {
		pCourant.setDepPossible(clearDep(calDeplacement(pCourant.getCaseElement(), pCourant.getDeplacement(), pCourant.getArmee()),
				w.getCarte().getCarte().get(pCourant.getCaseElement()), pCourant.getArmee()));
	}

	public void setW(World w) {
		this.w = w;
	}

	public Personnage getpCourant() {
		return pCourant;
	}

	public void setpCourant(Personnage pCourant) {
		this.pCourant = pCourant;
	}

}
