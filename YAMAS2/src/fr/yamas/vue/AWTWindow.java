package fr.yamas.vue;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;

import fr.yamas.controlleur.AWTKeyboard;
import fr.yamas.controlleur.AWTMouse;


/**
 * 
 * @author Thomas Feuilletin
 *
 */
public class AWTWindow extends Frame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final int WindowWidth = 640;
	private final int WindowHeight = 640;
	
	private Canvas canvas;
	private int canvasWidth = 640;
	private int canvasHeight = 640;
	
	private boolean closingRequested;
	
	private BufferStrategy bs;
	
	private AWTMouse mouse;
	private AWTKeyboard keyboard;

	/**
	 * Initie une fenetre de la taille WindowWidth, WindoWHeight et de titre title
	 * @param title une chaine de caractère
	 */
	public void init(String title) {
		setTitle(title);
		setSize(WindowWidth, WindowHeight);
		setResizable(false);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we) {
				closingRequested = true;
			}
			
		});
		closingRequested = false;
	}

	
	/**
	 * Crée un canva de largeur width et de hauteur height
	 * @param width un entier
	 * @param height un entier
	 */
	public void createCanvas(int width, int height) {
		canvas = new Canvas();
		canvasWidth = width;
		canvasHeight = height;
		canvas.setPreferredSize(new Dimension(width, height));
		canvas.setMaximumSize(new Dimension(width, height));
		canvas.setMinimumSize(new Dimension(width, height));
		add(canvas);
		
		mouse = new AWTMouse();
		keyboard = new AWTKeyboard();
		canvas.addMouseListener(mouse);
		canvas.addMouseMotionListener(mouse);
		canvas.addKeyListener(keyboard);
		pack();
	}
	
	/**
	 * Crée deux buffer dans le canvas
	 * @return un object graphics
	 */
	public Graphics createGraphics() {
		bs = canvas.getBufferStrategy();
		if( bs == null) {
			canvas.createBufferStrategy(2);
			return null;
		}
		return bs.getDrawGraphics();
	}
	
	/**
	 * @return si une requete a était faite pour fermé la fenêtre
	 */
	public boolean isClosingRequested() {
		return closingRequested;
	}
	
	public void setClosingRequested(boolean closingRequested) {
		this.closingRequested = closingRequested;
	}


	/**
	 * Interchange les deux buffers
	 */
	public void switchBuffers() {
		if(bs != null) {
			bs.show();
		}
	}

	
	/*
	 * Getteur et setteur
	 */
	
	/**
	 * @return la largeur du canvas
	 */
	public int getCanvasWidth() {
		return canvasWidth;
	}

	/**
	 * @return la hauteur du canvas
	 */
	public int getCanvasHeight() {
		return canvasHeight;
	}
	
	/**
	 * @return le controlleur de la souris associé a la fenêtre
	 */
	public AWTMouse getMouse() {
		return mouse;
	}
	
	/**
	 * @return le controlleur du clavier associé à la fenêtre
	 */
	public AWTKeyboard getKeyboard() {
		return keyboard;
	}
	
	public void setClosingRequested() {
		this.closingRequested = true;
	}

}
