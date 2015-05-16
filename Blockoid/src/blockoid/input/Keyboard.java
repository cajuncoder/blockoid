package blockoid.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.Serializable;


public class Keyboard implements KeyListener, Serializable {
	public boolean[] keys = new boolean[256]; //keys pressed
	public String keyTyped = "";
	public int keyCodeTyped = 0;
	private String lastKeyTyped = "";
	private int lastKeyCodeTyped = 0;
	public boolean up, down, left, right, space, shift, i, r, g, q, tab;
	public boolean[] num = new boolean[10];
	
	//----------------------Keyboard.UPDATE-METHOD----------------------//
	public void update() {
		//note: these key indexes correspond to key IDs containing booleans
		up = keys[KeyEvent.VK_UP] || keys[KeyEvent.VK_W];
		down = keys[KeyEvent.VK_DOWN] || keys[KeyEvent.VK_S];
		left = keys[KeyEvent.VK_LEFT] || keys[KeyEvent.VK_A];
		right = keys[KeyEvent.VK_RIGHT] || keys[KeyEvent.VK_D];
		shift = keys[KeyEvent.VK_SHIFT];
		space = keys[KeyEvent.VK_SPACE];
		i = keys[KeyEvent.VK_I];
		r = keys[KeyEvent.VK_R];
		g = keys[KeyEvent.VK_G];
		q = keys[KeyEvent.VK_Q];
		tab = keys[KeyEvent.VK_TAB];
		num[0] = keys[KeyEvent.VK_0];
		num[1] = keys[KeyEvent.VK_1];
		num[2] = keys[KeyEvent.VK_2];
		num[3] = keys[KeyEvent.VK_3];
		num[4] = keys[KeyEvent.VK_4];
		num[5] = keys[KeyEvent.VK_5];
		num[6] = keys[KeyEvent.VK_6];
		num[7] = keys[KeyEvent.VK_7];
		num[8] = keys[KeyEvent.VK_8];
		num[9] = keys[KeyEvent.VK_9];
		
		if(keyTyped.equals(lastKeyTyped)) keyTyped = "";
		lastKeyTyped = keyTyped;
		if(keyCodeTyped == lastKeyCodeTyped) keyCodeTyped = 0;
		lastKeyCodeTyped = keyCodeTyped;
		//print key ID for fun, which will be same as index
		//for (int i = 0; i < keys.length; i++) {
		//	if (keys[i]) {
		//		System.out.println("KEY: " + i);
		//	}
		//}
		
	}
	
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() < keys.length) keys[e.getKeyCode()] = true;
		//System.out.println(e.getKeyCode());
	}

	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() < keys.length) keys[e.getKeyCode()] = false;

	}

	public void keyTyped(KeyEvent e) {
		keyTyped = Character.toString(e.getKeyChar());
		//System.out.println(keyTyped);
		keyCodeTyped = e.getKeyChar();
	}
}
