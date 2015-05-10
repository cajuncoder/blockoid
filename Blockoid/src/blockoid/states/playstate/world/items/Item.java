package blockoid.states.playstate.world.items;

import java.awt.Graphics2D;

import blockoid.Assets;
import blockoid.graphics.SpriteSheet;
import blockoid.states.playstate.world.World;

public abstract class Item {

	public double x;
	public double y;
	public double xVel = 0;
	public double yVel = 0;
	public int lightLevel = 0;
	public String name;
	public SpriteSheet inventorySprite;
	public boolean stackable = false;
	public int numInStack = 1;
	public int maxInStack = 16;
	public Inventory inventory = null;
	
	
	public void processPrimary(World world) {
		
	}
	
	public void processSecondary(World world) {
		
	}
	
	public void update(World world) {
		int tileX = (int) (x/8);
		int tileY = (int) (y/8);
		if(tileX >= world.sizeX) tileX = world.sizeX-1;
		if(tileX < 0) tileX = 0;
		if(tileY >= world.sizeY) tileY = world.sizeY-1;
		if(tileY < 0) tileY = 0;
		
		lightLevel = (int)Math.ceil(world.tiles[tileX][tileY].lightLevel);
		
		if(!world.tiles[tileX][tileY].solid && yVel < 2.5) {
			yVel+=0.1;
		}
		if(xVel > 0) xVel -= 0.05;
		if(xVel < 0) xVel += 0.05;
		if(xVel > 4) xVel = 4;
		if(xVel < -4) xVel = -4;
		if(yVel > 4) yVel = 4;
		if(yVel < -4) yVel = -4;
		
		y+=yVel;
		x+=xVel;
		
		if(world.tiles[tileX][tileY].solid) {
			y=world.tiles[tileX][tileY].y;
			xVel = 0;
		}
		
		//replace this later
		if(world.player!=null) {
			int distX = (int) Math.abs(world.player.x - x);
			int distY = (int) Math.abs(world.player.y - y);
			if(distX < 8 && distY < 8) {
				if(!world.player.toolbelt.inventoryFull()) {
					world.player.toolbelt.addItem(this);
					world.items.remove(this);
				}else
				if(!world.player.inventory.inventoryFull()) {
					world.player.inventory.addItem(this);
					world.items.remove(this);
				}
			}
		}
	}
	
	public Item getNewInstance() {
		return null;
	}
	
	public void draw(Graphics2D g, int dx, int dy) {
		inventorySprite.drawSprite(dx, dy, 0, g);
		
	}
	
	public void worldDraw(Graphics2D g, int xOff, int yOff) {
		int dx = (int) x-xOff-(inventorySprite.spriteSizeX/2);
		int dy = (int) y-yOff-inventorySprite.spriteSizeY;
		inventorySprite.drawSprite(dx, dy, 0, lightLevel, g);	
	}

}
