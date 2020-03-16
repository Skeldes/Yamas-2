package model.unite;

import model.Element;
import model.ElementCreator;
import model.Factory;

public class ArmeeFactory extends Factory {

	@Override
	public ArmeeFactory getDefault() {
		ArmeeFactory af = new ArmeeFactory();
		af.registre(0, new ElementCreator() {
			@Override
			public Element create() {
				return null;
			}
		});
		af.registre(1, new ElementCreator() {
			@Override
			public Element create() {
				Archer p = new Archer();
				p.setAttaque(10);
				p.setPv(50);
				p.setPvMax(50);
				p.setDeplacement(5);
				p.setSkin(3);
				p.setEtat(Etats.VIE);
				return p;
			}
		});
		af.registre(2, new ElementCreator() {
			@Override
			public Element create() {
				Guerrier p = new Guerrier();
				p.setAttaque(10);
				p.setPv(50);
				p.setPvMax(50);
				p.setDeplacement(5);
				p.setSkin(4);
				p.setEtat(Etats.VIE);
				return p;
			}
		});
		af.registre(3, new ElementCreator() {
			@Override
			public Element create() {
				Heal p = new Heal();
				p.setAttaque(10);
				p.setPv(50);
				p.setPvMax(50);
				p.setDeplacement(5);
				p.setSkin(2);
				p.setEtat(Etats.VIE);
				return p;
			}
		});
		af.registre(4, new ElementCreator() {
			@Override
			public Element create() {
				Mage p = new Mage();
				p.setAttaque(10);
				p.setPv(50);
				p.setPvMax(50);
				p.setDeplacement(5);
				p.setSkin(6);
				p.setEtat(Etats.VIE);
				return p;
			}
		});
		af.registre(5, new ElementCreator() {
			@Override
			public Element create() {
				Tank p =new Tank();
				p.setAttaque(10);
				p.setPv(50);
				p.setPvMax(50);
				p.setDeplacement(5);
				p.setSkin(5);
				p.setEtat(Etats.VIE);
				return p;
			}
		});
		
		
		return af;
	}

}
