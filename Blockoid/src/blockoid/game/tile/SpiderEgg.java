package blockoid.game.tile;

import java.awt.Graphics;
import java.awt.Graphics2D;

import blockoid.Assets;
import blockoid.game.World;
import blockoid.game.being.Being;
import blockoid.game.being.Spider;
import blockoid.game.being.Wolf;
import blockoid.game.item.DirtBlock;
import blockoid.game.item.Item;
import blockoid.graphics.SpriteSheet;

public class SpiderEgg extends Tile {

	public SpiderEgg(int yIndex, int xIndex, boolean isBackgroundTile) {
		super(yIndex, xIndex, isBackgroundTile);
		super.sprite = Assets.getSpriteSheet("tiles/stone", Tile.TILE_SIZE, Tile.TILE_SIZE);
		super.hitpool = 3;
		super.hitpoints = hitpool;
		super.solid = true;
		super.breakSound = Assets.getAudio("breakDirt");
	}
	
	public void destroy(World world) {
		super.destroy(world);
		for (int i = 0; i < (int)(Math.random() * 10) + 1; i++) {
			Being spider = new Spider(world.game);
			spider.place(x, y);
			world.beings.add(spider);
		}
	}
	
	public void update(World world) {
		processHP(world);
	}

	public void draw(Graphics2D g, int OffX, int OffY) {
		sprite.drawSprite(x-OffX, y-OffY, 0, (int)Math.ceil(lightLevel), g);
		drawDamageOverlay(g, OffX, OffY);
	}
	
}

