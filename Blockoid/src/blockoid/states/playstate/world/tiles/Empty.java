package blockoid.states.playstate.world.tiles;

import java.awt.Graphics;
import java.awt.Graphics2D;

import blockoid.graphics.Sprite;

public class Empty extends Tile {

	public Empty(int yIndex, int xIndex, boolean isBackgroundTile) {
		super(yIndex, xIndex, isBackgroundTile);
		sprite = null;
		solid = false;
		lightLevel = 7;
		density = 0.25;
	}

	public void draw(Graphics2D g, int OffX, int OffY) {
		//sprite.drawSprite(x-OffX, y-OffY, 0, g);
	}
	
}

