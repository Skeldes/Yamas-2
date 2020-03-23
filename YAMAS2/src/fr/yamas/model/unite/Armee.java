package fr.yamas.model.unite;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import fr.yamas.model.terrain.Case;

public class Armee {

	private Map<Integer, ArrayList<Personnage>> armees = new TreeMap<Integer, ArrayList<Personnage>>();
	private ArmeeFactory af;
	
	private int nbArmee;

	private int[][][] Larmees = new int[2][4][3];
	private int[][] armeeJ1 = { { 2, 0, 0 }, { 4, 0, 1 }, { 1, 1, 0 }, { 1, 1, 1 } };
	private int[][] armeeJ2 = { { 2, 99, 99 }, { 2, 99, 98 }, { 1, 98, 99 }, { 1, 98, 98 } };

	public Armee() {
		Larmees[0] = armeeJ1;
		Larmees[1] = armeeJ2;
	}

	public void init() {
		nbArmee = 0;
		for (int i = 0; i < Larmees.length; i++) {
			ArrayList<Personnage> armee = new ArrayList<Personnage>();
			for (int j = 0; j < Larmees[i].length; j++) {
				Personnage p = (Personnage) af.create(Larmees[i][j][0]);
				int[] pos = new int[2];
				pos[0] = Larmees[i][j][1];
				pos[1] = Larmees[i][j][2];
				p.setPos(pos);
				p.setCaseElement(Larmees[i][j][1] + Larmees[i][j][2] * 100);
				p.setArmee(i + 1);
				p.setRoute(new ArrayList<Case>());
				armee.add(p);
			}
			nbArmee = getNbArmee() + 1;
			setArmee(i + 1, armee);
		}
	}

	public ArrayList<Personnage> getArmee(int code) {
		return armees.get(code);
	}

	public void setArmee(int code, ArrayList<Personnage> armee) {
		armees.put(code, armee);
	}

	public void setAf(ArmeeFactory af) {
		this.af = af;
	}

	public int getNbArmee() {
		return nbArmee;
	}

}
