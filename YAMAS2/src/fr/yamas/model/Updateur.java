package fr.yamas.model;

import java.util.ArrayList;

import fr.yamas.model.terrain.Case;
import fr.yamas.model.unite.Personnage;

public class Updateur {

	private World w;
	private Personnage pCourant;

	private void calDeplacement(int c, int d, int armee) {
		boolean casePossible = true;
		int armeeEnemie = armee == 1 ? 2 : 1;
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
			if (d >= 0 && c >= 0 && c < 10000 && casePossible) {// Si l'unité à encore des points d, que la case existe dans												// le plateau et si il n'y a pas d'ennemie sur la case
				pCourant.getDepPossible().add(w.getCarte().getCarte().get(c));
				if ((c + 1) % 100 != 0 && c + 1 < 10000)
							calDeplacement(c + 1, d - w.getCarte().getCarte().get(c + 1).getCoutDeplacement(), armee);
				if ((c - 1) % 100 != 99 && c - 1 >= 0)
							calDeplacement(c - 1, d - w.getCarte().getCarte().get(c - 1).getCoutDeplacement(), armee);
				if (c - 100 >= 0)
							calDeplacement(c - 100, d - w.getCarte().getCarte().get(c - 100).getCoutDeplacement(), armee);
				if (c + 100 < 10000)
							calDeplacement(c + 100, d - w.getCarte().getCarte().get(c + 100).getCoutDeplacement(), armee);
				
			}
	}

	private void clearDep2() {
		ArrayList<Case> dep = new ArrayList<Case>();
		for(int i = 0; i < pCourant.getDepPossible().size(); i++) {
			int count = 0;
			for(int j = 0; j<dep.size();j++ ) {
				if(pCourant.getDepPossible().get(i).getCaseElement()==dep.get(j).getCaseElement())
					count++;
			}
			if(count == 0) {
				dep.add(pCourant.getDepPossible().get(i));
			}
		}
		pCourant.getDepPossible().clear();
		pCourant.getDepPossible().addAll(dep);
	}
	
	private void clearDep() {
		Case oC = w.getCarte().getCarte().get(pCourant.getCaseElement());
		int armee = pCourant.getArmee();
		for (int i = 0; i < pCourant.getDepPossible().size(); i++) {
			//System.out.println(i);
			Case c = pCourant.getDepPossible().get(i);
			if (armee == 1) {
				for (int h = 0; h < fr.yamas.game.PlayGameMode.world.getArmees().getArmee(1).size(); h++) {
					if ((c.getPos()[0] != oC.getPos()[0] || c.getPos()[1] != oC.getPos()[1])
							&& (c.getPos()[0] == w.getArmees().getArmee(1).get(h).getPos()[0]
									&& c.getPos()[1] == w.getArmees().getArmee(1).get(h).getPos()[1])) {
						pCourant.getDepPossible().remove(i--);
					}
				}
			} else {
				for (int h = 0; h < fr.yamas.game.PlayGameMode.world.getArmees().getArmee(2).size(); h++) {
					if ((c.getPos()[0] != oC.getPos()[0] || c.getPos()[1] != oC.getPos()[1])
							&& (c.getPos()[0] == w.getArmees().getArmee(2).get(h).getPos()[0]
									&& c.getPos()[1] == w.getArmees().getArmee(2).get(h).getPos()[1])) {
						pCourant.getDepPossible().remove(i--);
					}
				}
			}
		}
	}

	private void calculDeplacement() {
		calDeplacement(pCourant.getCaseElement(), pCourant.getDeplacement(), pCourant.getArmee());
		clearDep2();
		clearDep();
		
	}
	
	public void calAllDep() {
		ArrayList<Personnage> armee = w.getArmees().getArmee(pCourant.getArmee());
		for(Personnage p : armee) {
			if(!p.isaJouer()) {
				pCourant = p;
				calculDeplacement();
			}
		}
		pCourant = null;
	}
	
	public void calAllDep1() {
		long begin = System.nanoTime();
		ArrayList<Personnage> armee = w.getArmees().getArmee(1);
		for(Personnage p : armee) {
			pCourant = p;
			calculDeplacement();
		}
		pCourant = null;
		long end = System.nanoTime();
		double time = (end - begin)/1_000_000_000.0;
		System.out.println(time);
	}
	public void calAllDep2() {
		long begin = System.nanoTime();
		ArrayList<Personnage> armee = w.getArmees().getArmee(2);
		for(Personnage p : armee) {
			pCourant = p;
			calculDeplacement();
		}
		pCourant = null;
		long end = System.nanoTime();
		double time = (end - begin)/1_000_000_000.0;
		System.out.println(time);
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
