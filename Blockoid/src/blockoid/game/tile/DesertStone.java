package blockoid.game.tile;

import java.awt.Graphics;
import java.awt.Graphics2D;

import blockoid.Assets;
import blockoid.game.World;
import blockoid.game.item.DesertBlock;
import blockoid.game.item.DesertStoneBlock;
import blockoid.game.item.Item;
import blockoid.graphics.SpriteSheet;

public class DesertStone extends Tile {

	public DesertStone(int yIndex, int xIndex, boolean isBackgroundTile) {
		super(yIndex, xIndex, isBackgroundTile);
		super.sprite = Assets.getSpriteSheet("tiles/desertStone", Tile.TILE_SIZE, Tile.TILE_SIZE);
		super.hitpool = 8;
		super.hitpoints = hitpool;
		super.solid = true;
		//super.itemDrop = new DesertStoneBlock();
		super.breakSound = Assets.getAudio("breakDirt");
	}
	
	public Item getItemDrop() {
		Item item = new DesertStoneBlock();
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
