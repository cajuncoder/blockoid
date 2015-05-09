package blockoid.states.playstate.world;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;

import blockoid.Game;

public class Star {
	int x;
	int y;
	Game game;
	
	public Star(Game game) {
		this.game = game;
		Random r = new Random();
		x = r.nextInt(game.width);
		y = r.nextInt(game.height);
	}
	
	public void draw(Graphics2D g) {
		g.setColor(Color.white);
		g.fillRect(x,y,1,1);
	}
}
