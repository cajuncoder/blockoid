package blockoid.states.playstate.world.tiles;

import java.awt.Graphics;
import java.awt.Graphics2D;

import blockoid.Assets;
import blockoid.graphics.SpriteSheet;
import blockoid.states.playstate.world.World;
import blockoid.states.playstate.world.items.DirtBlock;

public class Stone extends Tile {

	public Stone(int yIndex, int xIndex, boolean isBackgroundTile) {
		super(yIndex, xIndex, isBackgroundTile);
		super.sprite = Assets.getSpriteSheet("tiles/stone", Tile.TILE_SIZE, Tile.TILE_SIZE);
		super.hitpool = 64;
		super.hitpoints = hitpool;
		super.solid = true;
		//super.itemDrop = new DirtBlock();
		super.breakSound = Assets.getAudio("breakDirt");
	}

	public void update(World world) {
		processHP(world);
	}
	
	public void draw(Graphics2D g, int OffX, int OffY) {
		sprite.drawSprite(x-OffX, y-OffY, 0, (int)Math.ceil(lightLevel), g);
		drawDamageOverlay(g, OffX, OffY);
	}
	
}
