package blockoid.graphics;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Button {
	
	public String string;
	public Font font; 
	int w;
	int h;
	int x;
	int y;
	public boolean clicked = false;
	public boolean selected = false;
	public boolean wasSelected = false;
	
	public Button(String string, Font font) {
		this.string = string;
		this.font = font;
		this.w = FontUtil.getWidth(string, font);
		this.h = FontUtil.getHeight(string, font);
	}
	
	public void setPos(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void update(int mx, int my, boolean mouseBtn) {
		wasSelected = selected;
		selected = false;
		if(mx > x && mx < x+w) {
			if(my > y-h && my < y) {
				selected = true;
			}
		}
		clicked = false;
		if(selected == true && mouseBtn == true) clicked = true;
	}
	
	public void draw(Graphics g, int x, int y) {
		this.x = x;
		this.y = y;
		g.setFont(font);
		g.drawString(string, x, y);
	}
	
	public void draw(Graphics2D g) {
		g.setFont(font);
		g.drawString(string, x, y);
	}
	
	public int getWidth() {
		return this.w;
	}
	
	public int getHeight() {
		return this.h;
	}
}
