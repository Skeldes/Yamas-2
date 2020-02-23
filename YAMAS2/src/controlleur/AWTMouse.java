package controlleur;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;


/**
 * Class gérant le controlle de la souris 
 * @author Thomas
 *
 */
public class AWTMouse implements MouseMotionListener, MouseListener, Mouse {

	private boolean[] buttons;
	private int x;
	private int y;
	
	
	/**
	 * Constructeur
	 */
	public AWTMouse() {
		buttons = new boolean[4];
	}
	
	@Override
	public boolean isButtonPressed(int button) {
		if(button>= buttons.length) {
			return false;
		}
		return buttons[button];
	}

	@Override
	public int getX() {
		return x;
	}

	@Override
	public int getY() {
		return y;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getButton() <= 3) {
			buttons[e.getButton()] = true;
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(e.getButton()<=3) {
			buttons[e.getButton()] = false;
		}
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		x = e.getX();
		y = e.getY();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		x = e.getX();
		y = e.getY();
	}
	
	
	/*
	 * Fonction non utilisée
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

}
