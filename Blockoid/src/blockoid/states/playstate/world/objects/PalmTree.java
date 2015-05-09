package blockoid.states.playstate.world.objects;

import java.awt.Graphics;
import java.awt.Graphics2D;

import blockoid.graphics.SpriteSheet;
import blockoid.states.playstate.world.objects.GameObject;
import blockoid.states.playstate.world.tiles.Tile;

public class PalmTree extends GameObject {

	public PalmTree(Tile tile) {
		super(tile);
		spriteSheet = new SpriteSheet("palmtree3.png", 51, 54);
	}
	
	//public void draw(Graphics2D g, int xOff, int yOff) {
	//	int lightLevel = (int) Math.ceil(tile.lightLevel);
	//	spriteSheet.drawSprite(dx-xOff-(spriteSheet.spriteSizeX/2)+5, dy-yOff-spriteSheet.spriteSizeY, 0, lightLevel, g);
	//}
}
