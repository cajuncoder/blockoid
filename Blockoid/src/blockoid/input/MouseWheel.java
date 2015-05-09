package blockoid.input;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class MouseWheel implements MouseWheelListener {

	public boolean mouseWheelUp = false;
	public boolean mouseWheelDown = false;
	
	public void clear() {
		mouseWheelUp = false;
		mouseWheelDown = false;
	}
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		int rot = e.getWheelRotation();
		if(rot > 0) mouseWheelUp = true;
		if(rot < 0) mouseWheelDown = true;
	}

}
