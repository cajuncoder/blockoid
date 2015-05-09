package blockoid.states.playstate.world.objects;

import java.awt.Graphics;
import java.awt.Graphics2D;

import blockoid.graphics.SpriteSheet;
import blockoid.states.playstate.world.tiles.Tile;

public abstract class GameObject {

	public SpriteSheet spriteSheet;
	public int dx;
	public int dy;
	public Tile tile;
	
	public GameObject(Tile tile) {
		this.tile = tile;
		this.dx = tile.x;
		this.dy = tile.y;
	}
	
	public void update() {
		
	}
	
	public void draw(Graphics2D g, int xOff, int yOff) {
		int lightLevel = (int) Math.ceil(tile.lightLevel);
		spriteSheet.drawSprite(dx-xOff-(spriteSheet.spriteSizeX/2)+5, dy-yOff-spriteSheet.spriteSizeY, 0, lightLevel, g);
	}
	
}
