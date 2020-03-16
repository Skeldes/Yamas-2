package model.terrain;


import model.Element;
import model.ElementCreator;
import model.Factory;

public class ElementFactory extends Factory {

	public ElementFactory getDefault() {
		ElementFactory factory = new ElementFactory();
		factory.registre(0, new ElementCreator() {
			@Override
			public Element create() {
				return new Case(TypeTerrain.INCONNUE);
			}
		});
		factory.registre(6, new ElementCreator() {
			@Override
			public Element create() {
				Case c = new Case(TypeTerrain.PLAINE);
				c.setTraversable(true);
				c.setCoutDeplacement(1);
				c.setSkin(6);
				return c;
			}
		});
		factory.registre(8, new ElementCreator() {
			@Override
			public Element create() {
				Case c = new Case(TypeTerrain.FORET);
				c.setTraversable(true);
				c.setCoutDeplacement(2);
				c.setSkin(8);
				return c;
			}
		});
		factory.registre(9, new ElementCreator() {
			@Override
			public Element create() {
				Case c = new Case(TypeTerrain.EAU);
				c.setCoutDeplacement(99);
				c.setTraversable(false);
				c.setSkin(9);
				return c;
			}
		});
		factory.registre(14, new ElementCreator() {
			@Override
			public Element create() {
				Case c = new Case(TypeTerrain.SOL);
				c.setCoutDeplacement(1);
				c.setTraversable(true);
				c.setSkin(14);
				return c;
			}
		});
		factory.registre(5, new ElementCreator() {
			@Override
			public Element create() {
				Case c = new Case(TypeTerrain.SPAWN);
				c.setTraversable(true);
				c.setCoutDeplacement(1);
				c.setSkin(5);
				return c;
			}
		});
		factory.registre(7, new ElementCreator() {
			@Override
			public Element create() {
				Case c = new Case(TypeTerrain.MURH);
				c.setTraversable(false);
				c.setCoutDeplacement(99);
				c.setSkin(7);
				return c;
			}
		});
		factory.registre(11, new ElementCreator() {
			@Override
			public Element create() {
				Case c = new Case(TypeTerrain.MURV);
				c.setTraversable(false);
				c.setCoutDeplacement(99);
				c.setSkin(11);
				return c;
			}
		});
		factory.registre(12, new ElementCreator() {
			@Override
			public Element create() {
				Case c = new Case(TypeTerrain.PONTH);
				c.setCoutDeplacement(1);
				c.setTraversable(true);
				c.setSkin(12);
				return c;
			}
		});
		factory.registre(13, new ElementCreator() {
			@Override
			public Element create() {
				Case c = new Case(TypeTerrain.PONTV);
				c.setTraversable(true);
				c.setCoutDeplacement(1);
				c.setSkin(13);
				return c;
			}
		});

		return factory;
	}

}
