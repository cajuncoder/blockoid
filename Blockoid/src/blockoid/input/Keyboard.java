package blockoid.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;
import java.util.concurrent.CopyOnWriteArrayList;


public class Keyboard implements KeyListener, Serializable {
	public boolean[] keys = new boolean[256]; //keys pressed
	private Set<Integer> keyCodesTyped = new HashSet<Integer>();
	private CopyOnWriteArrayList<Character> charBuffer = new CopyOnWriteArrayList<Character>();
	public boolean up, down, left, right, space, shift, i, r, g, q, tab, esc;
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
		esc = keys[KeyEvent.VK_ESCAPE];
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
	
		//print key ID for fun, which will be same as index
		//for (int i = 0; i < keys.length; i++) {
		//	if (keys[i]) {
		//		System.out.println("KEY: " + i);
		//	}
		//}
		
	}
	
	public void clear() {
		keyCodesTyped.clear();
		charBuffer.clear();
	}
	
	public boolean isKeyTyped(int key) {
		return keyCodesTyped.contains(key);
	}
	
	public CopyOnWriteArrayList<Character> getCharacterBuffer() {
		return charBuffer;
	}
	
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() < keys.length) keys[e.getKeyCode()] = true;
		//System.out.println(e.getKeyCode());
	}

	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() < keys.length) keys[e.getKeyCode()] = false;

	}

	public void keyTyped(KeyEvent e) {
		char ch = e.getKeyChar();
		int code = (int)ch;
		if (code >= 32 && code <= 126) {
			ch = ("" + ch).toUpperCase().charAt(0);
			code = (int)ch;
		}
		keyCodesTyped.add(code);
		charBuffer.add(e.getKeyChar());
	}
}
