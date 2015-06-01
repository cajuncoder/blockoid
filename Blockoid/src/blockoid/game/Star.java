package blockoid.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;

import blockoid.Game;

public class Star {
	int x;
	int y;
	int dx;
	int dy;
	private Game game;
	
	public Star(Game game) {
		this.game = game;
		Random r = new Random();
		x = r.nextInt(100);
		y = r.nextInt(66);
	}
	
	public void draw(Graphics2D g) {
		dx = game.width*x/100;
		dy = game.height*y/100;
		//g.setColor(Color.white);
		g.fillRect(dx,dy,1,1);
	}
}
