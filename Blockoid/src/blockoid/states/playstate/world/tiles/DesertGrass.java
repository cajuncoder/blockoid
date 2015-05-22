package blockoid.states.playstate.world.tiles;

import java.awt.Graphics;
import java.awt.Graphics2D;

import blockoid.Assets;
import blockoid.graphics.SpriteSheet;

public class DesertGrass extends Tile {

	public DesertGrass(int yIndex, int xIndex, boolean isBackgroundTile) {
		super(yIndex, xIndex, isBackgroundTile);
		super.sprite = Assets.getSpriteSheet("tiles/desertGrass", Tile.TILE_SIZE, Tile.TILE_SIZE); 
		super.hitpool = 4;
		super.hitpoints = hitpool;
		super.solid = true;
	}

	public void draw(Graphics2D g, int OffX, int OffY) {
		sprite.drawSprite(x-OffX, y-OffY, 0, (int)Math.ceil(lightLevel), g);
		drawDamageOverlay(g, OffX, OffY);
	}
	
}
