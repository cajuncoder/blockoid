package blockoid.states.playstate.world.objects;

import java.awt.Graphics;
import java.awt.Graphics2D;

import blockoid.graphics.SpriteSheet;
import blockoid.states.playstate.world.World;
import blockoid.states.playstate.world.items.Item;
import blockoid.states.playstate.world.tiles.Empty;
import blockoid.states.playstate.world.tiles.Tile;

public abstract class GameObject {

	public SpriteSheet spriteSheet;
	public int dx;
	public int dy;
	public Tile tile;
	public int tileX;
	public int tileY;
	public int lightLevel;
	public Item dropItem;
	
	public GameObject(Tile tile) {
		if(tile!=null) {
		this.tile = tile;
		this.dx = tile.x;
		this.dy = tile.y;
		this.tileX = tile.xIndex;
		this.tileY = tile.yIndex;
		}
	}
	
	public void update(World world) {
		this.tile = world.bgTiles[tileX][tileY];
		if(tile.solid) {
			lightLevel = (int) Math.ceil(tile.lightLevel);
		}else{
			remove(world);
		}
	}
	
	public void draw(Graphics2D g, int xOff, int yOff) {
		spriteSheet.drawSprite(dx-xOff-(spriteSheet.spriteSizeX/2)+5, dy-yOff-spriteSheet.spriteSizeY, 0, lightLevel, g);
	}
	
	public void remove(World world) {
		if(dropItem!=null) {
			Item item = dropItem.getNewInstance();
			item.x = dx;
			item.y = dy;
			world.addItem(item, (int)item.x, (int)item.y-this.spriteSheet.spriteSizeY);
		}
		world.removeObject(this);
	}
	
	public GameObject getNewInstance(Tile tile) {
		
		return null;
	}
	
}
