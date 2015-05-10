package blockoid.states.playstate.world.objects;

import java.awt.Graphics;
import java.awt.Graphics2D;

import blockoid.Assets;
import blockoid.graphics.SpriteSheet;
import blockoid.states.playstate.world.objects.GameObject;
import blockoid.states.playstate.world.tiles.Tile;

public class OakTree extends GameObject {

	public OakTree(Tile tile) {
		super(tile);
		spriteSheet = Assets.getSpriteSheet("objects/oakTree", 60, 83);
	}
	
	//public void draw(Graphics2D g, int xOff, int yOff) {
	//	spriteSheet.drawSprite(dx-xOff-(spriteSheet.spriteSizeX/2)+5, dy-yOff-spriteSheet.spriteSizeY, 0, g);
	//}
}
