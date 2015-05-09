package blockoid.input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import blockoid.Game;

public class MouseMotion implements MouseMotionListener {

	public int x, y;
	private Game game;
	public boolean mouseDragged = false;
	
	public MouseMotion(Game game) {
		this.game = game;
	}
	
	public void mouseDragged(MouseEvent e) {
		mouseDragged = true;
		x = e.getX()/game.scale;
		y = e.getY()/game.scale;
		e.consume();
	}

	public void mouseMoved(MouseEvent e) {
		mouseDragged = false;
		x = e.getX()/game.scale;
		y = e.getY()/game.scale;
		e.consume();
	}

}
