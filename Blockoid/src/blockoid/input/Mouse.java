package blockoid.input;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.SwingUtilities;


//add with addMouseListener(new Mouse());
public class Mouse extends MouseAdapter {
	public boolean clickL = false;
	public boolean clickR = false;
	public boolean holdL = false;
	public boolean holdR = false;
	public int x;
	public int y;
	
	public void clear() {
		clickL = false;
		clickR = false;
	}
	
	public void mousePressed(MouseEvent e){
		
		if (SwingUtilities.isLeftMouseButton(e)) {
			clickL = true;
			holdL = true;
		}
		if (SwingUtilities.isRightMouseButton(e)) {
			clickR = true;
			holdR = true;
		}
		x = e.getX();
		y = e.getY();
	}
	public void mouseReleased(MouseEvent e){
		clickL = false;
		holdL = false;
		clickR = false;
		holdR = false;
		x = e.getX();
		y = e.getY();
	}
}
