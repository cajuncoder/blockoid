package blockoid.game.tile;

import java.awt.Graphics;
import java.awt.Graphics2D;

import blockoid.Assets;
import blockoid.game.World;
import blockoid.game.item.DesertBlock;
import blockoid.game.item.DirtBlock;
import blockoid.game.item.Item;
import blockoid.graphics.SpriteSheet;

public class Desert extends Tile {

	public Desert(int yIndex, int xIndex, boolean isBackgroundTile) {
		super(yIndex, xIndex, isBackgroundTile);
		super.sprite = Assets.getSpriteSheet("tiles/desert", Tile.TILE_SIZE, Tile.TILE_SIZE);
		super.hitpool = 4;
		super.hitpoints = hitpool;
		super.solid = true;
		//super.itemDrop = new DesertBlock();
		super.breakSound = Assets.getAudio("breakDirt");
	}

	public Item getItemDrop() {
		Item item = new DesertBlock();
		return item;
	}
	
	public void update(World world) {
		if(yIndex-1 >= 0) {
			if(world.tiles[xIndex][yIndex-1].inTheSun) {
				if(!isBackgroundTile) world.tiles[xIndex][yIndex] = new DesertGrass(xIndex,yIndex,false);
				//If you uncomment the below line, GameObjects will break!
				//...unless I happen to have fixed them!
				//if(isBackgroundTile) world.bgTiles[xIndex][yIndex] = new DesertGrass(xIndex,yIndex,true);
			}
		}
		processHP(world);
	}
	
	public void draw(Graphics2D g, int OffX, int OffY) {
		sprite.drawSprite(x-OffX, y-OffY, 0, (int)Math.ceil(lightLevel), g);
		drawDamageOverlay(g, OffX, OffY);
	}
	
}
