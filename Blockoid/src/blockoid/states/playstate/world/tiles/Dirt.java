package blockoid.states.playstate.world.tiles;

import java.awt.Graphics;
import java.awt.Graphics2D;

import blockoid.Assets;
import blockoid.graphics.SpriteSheet;
import blockoid.states.playstate.world.World;
import blockoid.states.playstate.world.items.DirtBlock;

public class Dirt extends Tile {

	public Dirt(int yIndex, int xIndex, boolean isBackgroundTile) {
		super(yIndex, xIndex, isBackgroundTile);
		super.sprite = Assets.getSpriteSheet("tiles/dirt", Tile.TILE_SIZE, Tile.TILE_SIZE);
		super.hitpool = 4;
		super.hitpoints = hitpool;
		super.solid = true;
		super.itemDrop = new DirtBlock();
		super.breakSound = Assets.getAudio("breakDirt");
	}

	public void update(World world) {
		if(yIndex-1 >= 0 && !isBackgroundTile) {
			if(world.tiles[xIndex][yIndex-1].inTheSun) {
				world.tiles[xIndex][yIndex] = new Grass(xIndex,yIndex,false);
			}
		}
		processHP(world);
	}
	
	public void draw(Graphics2D g, int OffX, int OffY) {
		sprite.drawSprite(x-OffX, y-OffY, 0, (int)Math.ceil(lightLevel), g);
		drawDamageOverlay(g, OffX, OffY);
	}
	
}
