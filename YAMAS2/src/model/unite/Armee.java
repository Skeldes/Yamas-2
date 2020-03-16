package model.unite;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class Armee {

	private Map<Integer, ArrayList<Personnage>> armees = new TreeMap<Integer, ArrayList<Personnage>>();
	private ArmeeFactory af;

	private int[][][] Larmees = new int[2][4][3];
	private int[][] armeeJ1 = { { 2, 0, 0 }, { 2, 0, 1 }, { 1, 1, 0 }, { 1, 1, 1 } };
	private int[][] armeeJ2 = { { 2, 19, 19 }, { 2, 19, 18 }, { 1, 18, 19 }, { 1, 18, 18 } };

	public Armee() {
		Larmees[0] = armeeJ1;
		Larmees[1] = armeeJ2;
	}

	public void init() {
		for (int i = 0; i < Larmees.length; i++) {
			ArrayList<Personnage> armee = new ArrayList<Personnage>();
			for (int j = 0; j < Larmees[i].length; j++) {
				Personnage p = (Personnage) af.create(Larmees[i][j][0]);
				int[] pos = new int[2];
				pos[0] = Larmees[i][j][1];
				pos[1] = Larmees[i][j][2];
				p.setPos(pos);
				System.out.println("x =" + j + " y =" + i + " case = " + (j + i * 100));
				p.setCaseElement(Larmees[i][j][1] + Larmees[i][j][2] * 100);
				p.setArmee(i + 1);
				p.setSkin(p.getSkin() + i * 10);
				armee.add(p);
			}
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

}
