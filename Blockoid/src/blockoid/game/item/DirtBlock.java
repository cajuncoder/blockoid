package blockoid.game.item;

import java.awt.Color;
import java.awt.Graphics2D;

import blockoid.Assets;
import blockoid.game.World;
import blockoid.game.tile.Dirt;
import blockoid.game.tile.Tile;
import blockoid.graphics.SpriteSheet;

public class DirtBlock extends Item {

	public DirtBlock() {
		this(1);
	}
	
	public DirtBlock(int numInStack) {
		super();
		stackable = true;
		name = "Dirt Block";
		inventorySprite = Assets.getSpriteSheet("tiles/dirt", Tile.TILE_SIZE, Tile.TILE_SIZE);
		this.numInStack = numInStack;
	}
	
	public Item getNewInstance() {
		Item result = new DirtBlock();
		return result;
	}
	
	public void processPrimary(World world) {
		if(world.game.mouse.holdL) {
			int tileX = (int) ((world.game.mouseMotion.x+world.CameraOffX)/8);
			int tileY = (int) ((world.game.mouseMotion.y+world.CameraOffY)/8);
			if(tileX >= world.sizeX) tileX = world.sizeX-1;
			if(tileX < 0) tileX = 0;
			if(tileY >= world.sizeY) tileY = world.sizeY-1;
			if(tileY < 0) tileY = 0;
		
			if(!world.tiles[tileX][tileY].solid) {
				world.tiles[tileX][tileY] = new Dirt(tileX,tileY,false);
				if(numInStack > 1) {
					numInStack-=1;
				}else{
					inventory.removeItem(this);
				}

			}
		}
	}
	
	public void processSecondary(World world) {
		if(world.game.mouse.holdR) {
			int tileX = (int) ((world.game.mouseMotion.x+world.CameraOffX)/8);
			int tileY = (int) ((world.game.mouseMotion.y+world.CameraOffY)/8);
			if(tileX >= world.sizeX) tileX = world.sizeX-1;
			if(tileX < 0) tileX = 0;
			if(tileY >= world.sizeY) tileY = world.sizeY-1;
			if(tileY < 0) tileY = 0;
		
			if(!world.bgTiles[tileX][tileY].solid) {
				world.bgTiles[tileX][tileY] = new Dirt(tileX,tileY,true);
				if(numInStack > 1) {
					numInStack-=1;
				}else{
					inventory.removeItem(this);
				}

			}
		}
	}
	
	public void draw(Graphics2D g, int dx, int dy) {
		g.setColor(Color.black);
		dx+=2;
		dy+=2;
		inventorySprite.drawSprite(dx, dy, 0, g);
		g.drawRect(dx, dy, 7, 7);
		if(numInStack > 1) {
			g.setColor(Color.WHITE);
			g.drawString(Integer.toString(numInStack), dx-1, dy+6);
		}
	}
	
	public void worldDraw(Graphics2D g, int xOff, int yOff) {
		int dx = (int) x-1-xOff-(inventorySprite.spriteSizeX/2);
		int dy = (int) y-1-yOff-inventorySprite.spriteSizeY;
		g.setColor(Color.black);

		inventorySprite.drawSprite(dx, dy, 0, lightLevel, g);
		g.drawRect(dx, dy, 7, 7);
	}
	
	
}
