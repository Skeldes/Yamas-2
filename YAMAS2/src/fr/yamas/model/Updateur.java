package fr.yamas.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

import fr.yamas.model.terrain.Case;
import fr.yamas.model.unite.Personnage;

public class Updateur {

	private World w;
	private Personnage pCourant;

	private void calDeplacement(int c, int d, int armee) {
		boolean casePossible = true;
		int armeeEnemie = armee == 1 ? 2 : 1;
		HashMap<Integer, Case> dep = pCourant.getDepPossible();

		ArrayList<Case> carte = w.getCarte().getCarte();
		// On vérifie qu'il n'y ai pas d'ennemie sur la case c

		for (Personnage p : w.getArmees().getArmee(armeeEnemie)) {
			if (c == p.getCaseElement())
				casePossible = false;
		}

		if (d >= 0 && c >= 0 && c < 10000 && casePossible) {// Si l'unité à encore des points d, que la case existe
															// dans
			// le plateau et si il n'y a pas d'ennemie sur la case
			if (!dep.containsKey(c))
				dep.put(c, carte.get(c));
			if ((c + 1) % 100 != 0 && c + 1 < 10000)
				calDeplacement(c + 1, d - carte.get(c + 1).getCoutDeplacement(), armee);
			if ((c - 1) % 100 != 99 && c - 1 >= 0)
				calDeplacement(c - 1, d - carte.get(c - 1).getCoutDeplacement(), armee);
			if (c - 100 >= 0)
				calDeplacement(c - 100, d - carte.get(c - 100).getCoutDeplacement(), armee);
			if (c + 100 < 10000)
				calDeplacement(c + 100, d - carte.get(c + 100).getCoutDeplacement(), armee);
		}
	}

	private void clearDep() {
		for (Personnage p : w.getArmees().getArmee(pCourant.getArmee())) {
			if (pCourant.getDepPossible().containsKey(p.getCaseElement()) && pCourant != p) {
				pCourant.getDepPossible().remove(p.getCaseElement());
			}
		}
	}

	private void calculDeplacement() {
		pCourant.getDepPossible().clear();
		calDeplacement(pCourant.getCaseElement(), pCourant.getDeplacement(), pCourant.getArmee());
		clearDep();

	}

	public void calAllDep() {
		ArrayList<Personnage> armee = w.getArmees().getArmee(pCourant.getArmee());
		for (Personnage p : armee) {
			if (!p.isaJouer()) {
				pCourant = p;
				calculDeplacement();
			}
		}
		pCourant = null;
	}

	public void calAllDep1() {
		long begin = System.nanoTime();
		ArrayList<Personnage> armee = w.getArmees().getArmee(1);
		for (Personnage p : armee) {
			System.out.println(p.getPos()[0] + " " + p.getPos()[1]);
			pCourant = p;
			calculDeplacement();
		}
		pCourant = null;
		long end = System.nanoTime();
		double time = (end - begin) / 1_000_000_000.0;
		System.out.println(time);
	}

	public void calAllDep2() {
		long begin = System.nanoTime();
		ArrayList<Personnage> armee = w.getArmees().getArmee(2);
		for (Personnage p : armee) {
			pCourant = p;
			calculDeplacement();
		}
		pCourant = null;
		long end = System.nanoTime();
		double time = (end - begin) / 1_000_000_000.0;
		System.out.println(time);
	}

	/**
	 * Ta gueule c'est magique
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public ArrayList<Case> CalculChemin(int x, int y) {
		HashMap<Integer, Case> g = pCourant.getDepPossible();
		for (Personnage p : w.getArmees().getArmee(pCourant.getArmee())) {
			g.put(p.getCaseElement(), w.getCarte().getCarte().get(p.getCaseElement()));
		}
		for(Case c : g.values()) {
			c.setCout(Integer.MAX_VALUE);
			c.setHeuristique(Integer.MAX_VALUE);
			c.setPrevious(null);
		}
		ArrayList<Case> ClosedList = new ArrayList<Case>();
		PriorityQueue<Case> OpenList = new PriorityQueue<Case>();
		Case depart = g.get(pCourant.getCaseElement());
		depart.setCout(0);
		depart.setHeuristique(0);
		OpenList.add(depart);
		while (!OpenList.isEmpty()) {
			Case next = OpenList.poll();
			if (next.getPos()[0] == x && next.getPos()[1] == y) {
				ArrayList<Case> res = new ArrayList<Case>();
				Case current = next;
				do {
					res.add(0, current);
					current = current.getPrevious();
				} while (current != null);
				return res;
			}
			for (Integer i : g.keySet()) {
				Case c = g.get(i);
				if (c.getPos()[0] == next.getPos()[0] && c.getPos()[1] + 1 == next.getPos()[1]
						|| c.getPos()[0] == next.getPos()[0] && c.getPos()[1] - 1 == next.getPos()[1]
						|| c.getPos()[0] + 1 == next.getPos()[0] && c.getPos()[1] == next.getPos()[1]
						|| c.getPos()[0] - 1 == next.getPos()[0] && c.getPos()[1] == next.getPos()[1]) {
					int newCost = next.getCout() + c.getCoutDeplacement();
					if (!ClosedList.contains(c) && c.getCout() > newCost) {
						c.setCout(newCost);
						c.setHeuristique(newCost + Math.abs(c.getPos()[0] - x) + Math.abs(c.getPos()[1] - y));
						c.setPrevious(next);
						OpenList.add(c);
					}
				}

			}
			ClosedList.add(next);
		}
		throw new IllegalStateException("No route found ! ");
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
