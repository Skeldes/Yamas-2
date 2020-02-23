package vue;

import java.awt.*;

import controlleur.Mouse;

/**
 * Class qui g�re l'interface
 * 
 * @author Thomas Feuilletin
 *
 */
public class AWTGUIFacade implements GUIFacade {

	public AWTWindow window;
	public Graphics g;

	@Override
	public void createWindow(int width, int height, String title) {
		window = new AWTWindow();
		window.init(title);
		window.createCanvas(width, height);
		window.setLocationRelativeTo(null);
		window.setVisible(true);
	}

	@Override
	public boolean isClosingRequested() {
		return window.isClosingRequested();
	}

	@Override
	public void dispose() {
		window.dispose();
	}

	@Override
	public boolean beginPaint() {
		if (g != null) {
			g.dispose();
		}
		g = window.createGraphics();
		if (g == null) {
			return false;
		}
		return true;
	}

	@Override
	public void endPaint() {
		if (g == null) {
			return;
		}
		g.dispose();
		g = null;
		window.switchBuffers();

	}

	@Override
	public Layer createLayer() {
		return new AWTLayer();
	}

	@Override
	public void drawLayer(Layer layer) {
		if (g == null) {
			return;
		}
		if (layer == null) {
			throw new IllegalArgumentException("Pas de layer");
		}
		if (!(layer instanceof AWTLayer)) {
			throw new IllegalArgumentException("Type de layer invalide");
		}
		AWTLayer awtLayer = (AWTLayer) layer;
		awtLayer.draw(g);
	}

	@Override
	public Mouse getMouse() {
		if (window == null)
			throw new RuntimeException("Il faut d'abord cr�er une fen�tre");
		return window.getMouse();
	}

	@Override
	public void drawString(String text, int x, int y, int width, int height) {
		g.setFont(new Font("Arial", Font.PLAIN,44));
		g.setColor(Color.black);
		FontMetrics fm = g.getFontMetrics();
		int textWidth = fm.stringWidth(text);
		int textHeight = fm.getHeight();
		
		g.drawString(text, x + (width - textWidth)/2, y + (height - textHeight)/2);
	}

}
