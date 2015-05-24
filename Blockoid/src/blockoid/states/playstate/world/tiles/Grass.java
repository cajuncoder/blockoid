package blockoid.states.playstate.world.tiles;

import java.awt.Graphics;
import java.awt.Graphics2D;

import blockoid.Assets;
import blockoid.graphics.SpriteSheet;
import blockoid.states.playstate.world.World;
import blockoid.states.playstate.world.items.DirtBlock;
import blockoid.states.playstate.world.items.Item;

public class Grass extends Tile {

	public Grass(int yIndex, int xIndex, boolean isBackgroundTile) {
		super(yIndex, xIndex, isBackgroundTile);
		super.sprite = Assets.getSpriteSheet("tiles/grass", Tile.TILE_SIZE, Tile.TILE_SIZE);
		super.hitpool = 4;
		super.hitpoints = hitpool;
		super.solid = true;
		//super.itemDrop = new DirtBlock();
		super.breakSound = Assets.getAudio("breakDirt");
	}
	
	public Item getItemDrop() {
		Item item = new DirtBlock();
		return item;
	}
	
	public void update(World world) {
		if(yIndex-1 >= 0) {
			if(!world.tiles[xIndex][yIndex-1].getClass().equals(Empty.class) && !isBackgroundTile) {
				world.tiles[xIndex][yIndex] = new Dirt(xIndex,yIndex,false);
			}
			if(!world.bgTiles[xIndex][yIndex-1].getClass().equals(Empty.class) && isBackgroundTile) {
				world.bgTiles[xIndex][yIndex] = new Dirt(xIndex,yIndex,true);
			}
		}
		processHP(world);
	}

	public void draw(Graphics2D g, int OffX, int OffY) {
		sprite.drawSprite(x-OffX, y-OffY, 0, (int)Math.ceil(lightLevel), g);
		drawDamageOverlay(g, OffX, OffY);
	}
	
}

