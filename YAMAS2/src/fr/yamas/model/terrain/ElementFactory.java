package fr.yamas.model.terrain;


import fr.yamas.model.Element;
import fr.yamas.model.ElementCreator;
import fr.yamas.model.Factory;

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
				c.setSkin(2);
				return c;
			}
		});
		factory.registre(8, new ElementCreator() {
			@Override
			public Element create() {
				Case c = new Case(TypeTerrain.FORET);
				c.setTraversable(true);
				c.setCoutDeplacement(2);
				c.setSkin(3);
				return c;
			}
		});
		factory.registre(9, new ElementCreator() {
			@Override
			public Element create() {
				Case c = new Case(TypeTerrain.EAU);
				c.setCoutDeplacement(99);
				c.setTraversable(false);
				c.setSkin(1);
				return c;
			}
		});
		factory.registre(14, new ElementCreator() {
			@Override
			public Element create() {
				Case c = new Case(TypeTerrain.SOL);
				c.setCoutDeplacement(1);
				c.setTraversable(true);
				c.setSkin(6);
				return c;
			}
		});
		factory.registre(5, new ElementCreator() {
			@Override
			public Element create() {
				Case c = new Case(TypeTerrain.SPAWN);
				c.setTraversable(true);
				c.setCoutDeplacement(1);
				c.setSkin(1);
				return c;
			}
		});
		factory.registre(7, new ElementCreator() {
			@Override
			public Element create() {
				Case c = new Case(TypeTerrain.MURH);
				c.setTraversable(false);
				c.setCoutDeplacement(99);
				c.setSkin(0);
				return c;
			}
		});
		factory.registre(11, new ElementCreator() {
			@Override
			public Element create() {
				Case c = new Case(TypeTerrain.MURV);
				c.setTraversable(false);
				c.setCoutDeplacement(99);
				c.setSkin(2);
				return c;
			}
		});
		factory.registre(12, new ElementCreator() {
			@Override
			public Element create() {
				Case c = new Case(TypeTerrain.PONTH);
				c.setCoutDeplacement(1);
				c.setTraversable(true);
				c.setSkin(4);
				return c;
			}
		});
		factory.registre(13, new ElementCreator() {
			@Override
			public Element create() {
				Case c = new Case(TypeTerrain.PONTV);
				c.setTraversable(true);
				c.setCoutDeplacement(1);
				c.setSkin(5);
				return c;
			}
		});

		return factory;
	}

}
