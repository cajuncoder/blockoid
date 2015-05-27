package blockoid.game.tile;

import java.awt.Graphics;
import java.awt.Graphics2D;

import blockoid.Assets;
import blockoid.game.World;
import blockoid.game.item.DirtBlock;
import blockoid.game.item.Item;
import blockoid.game.item.StoneBlock;
import blockoid.graphics.SpriteSheet;

public class Stone extends Tile {

	public Stone(int yIndex, int xIndex, boolean isBackgroundTile) {
		super(yIndex, xIndex, isBackgroundTile);
		super.sprite = Assets.getSpriteSheet("tiles/stone", Tile.TILE_SIZE, Tile.TILE_SIZE);
		super.hitpool = 8;
		super.hitpoints = hitpool;
		super.solid = true;
		//super.itemDrop = new StoneBlock();
		super.breakSound = Assets.getAudio("breakDirt");
	}

	public Item getItemDrop() {
		Item item = new StoneBlock();
		return item;
	}
	
	public void update(World world) {
		processHP(world);
	}
	
	public void draw(Graphics2D g, int OffX, int OffY) {
		sprite.drawSprite(x-OffX, y-OffY, 0, (int)Math.ceil(lightLevel), g);
		drawDamageOverlay(g, OffX, OffY);
	}
	
}
