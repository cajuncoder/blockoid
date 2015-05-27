package blockoid.game.object;

import java.awt.Graphics;
import java.awt.Graphics2D;

import blockoid.Assets;
import blockoid.game.item.Wood;
import blockoid.game.object.GameObject;
import blockoid.game.tile.Tile;
import blockoid.graphics.SpriteSheet;

public class OakTree extends GameObject {

	public OakTree(Tile tile) {
		super(tile);
		spriteSheet = Assets.getSpriteSheet("objects/oakTree", 60, 83);
		dropItem = new Wood();
	}
	
	public GameObject getNewInstance(Tile tile) {
		GameObject o = new OakTree(tile);
		return o;
	}
}
