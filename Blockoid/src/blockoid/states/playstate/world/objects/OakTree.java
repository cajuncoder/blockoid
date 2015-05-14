package blockoid.states.playstate.world.objects;

import java.awt.Graphics;
import java.awt.Graphics2D;

import blockoid.Assets;
import blockoid.graphics.SpriteSheet;
import blockoid.states.playstate.world.items.Wood;
import blockoid.states.playstate.world.objects.GameObject;
import blockoid.states.playstate.world.tiles.Tile;

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
