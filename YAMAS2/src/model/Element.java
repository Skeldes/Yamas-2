package model;

public abstract class Element  {
	private int[] pos = new int[2];
	private int skin;
	private int caseElement;


	public boolean posEgale(Element e) {
		return e.getPos()[0] == this.getPos()[0] && e.getPos()[1] == this.getPos()[1];
	}


	public int getSkin() {
		return skin;
	}

	public void setSkin(int skin) {
		this.skin = skin;
	}

	public int[] getPos() {
		return pos;
	}

	public void setPos(int[] pos) {
		this.pos = pos;
	}


	public int getCaseElement() {
		return caseElement;
	}


	public void setCaseElement(int caseElement) {
		this.caseElement = caseElement;
	}
}
