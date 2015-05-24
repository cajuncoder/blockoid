package blockoid.states.playstate.world.tiles;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.lang.reflect.Constructor;

import blockoid.Assets;
import blockoid.Audio;
import blockoid.graphics.SpriteSheet;
import blockoid.states.playstate.world.World;
import blockoid.states.playstate.world.items.Item;
import blockoid.states.playstate.world.objects.GameObject;

public abstract class Tile {

	public static int TILE_SIZE = 8;
	public int x;
	public int y;
	public int xIndex;
	public int yIndex;
	public SpriteSheet sprite;
	//public SpriteSheet lightMask;
	public SpriteSheet damageOverlay;
	public double lightLevel = 0;
	public double density = 0.9;
	public boolean inTheSun = false;
	public boolean solid;
	public boolean isBackgroundTile = false;
	public int hitpool = 0;
	public int hitpoints = 0;
	public Item itemDrop = null;
	public Audio breakSound = null;
	public GameObject object = null;
	
	public Tile(int xIndex, int yIndex, boolean isBackgroundTile) {
		this.isBackgroundTile = isBackgroundTile;
		this.xIndex = xIndex;
		this.yIndex = yIndex;
		this.x = xIndex*TILE_SIZE;
		this.y = yIndex*TILE_SIZE;
		//this.lightMask = Assets.getSpriteSheet("tiles/lightMask", TILE_SIZE, TILE_SIZE);
		this.damageOverlay = Assets.getSpriteSheet("tiles/damageOverlay", TILE_SIZE, TILE_SIZE);
	}
	
	public void update(World world) {
		processHP(world);
	}
	
	int healCounter = 0;
	public void processHP(World world) {
		if(hitpool > 0) {
			healCounter++;
			if(hitpoints <= 0) {
				if(itemDrop!=null)world.addItem(itemDrop.getNewInstance(), x+4, y);
				Tile replacement = new Empty(xIndex,yIndex,isBackgroundTile);
				replacement.lightLevel=this.lightLevel;
				if(!isBackgroundTile) {
					world.tiles[xIndex][yIndex] = replacement;
					if(breakSound!=null) breakSound.play(false);
				}else{
					replacement.isBackgroundTile = true;
					world.bgTiles[xIndex][yIndex] = replacement;
					if(breakSound!=null) breakSound.play(false);
				}
			}
			if(hitpoints>0 && hitpoints < hitpool  && healCounter >= 60) {
				hitpoints++;
				healCounter=0;
			}
		}
	}
	
	public void damage(int value) {
		healCounter=0;
		hitpoints-=value;
	}
	
	public void getLight(World world) {
		if(yIndex >= world.renderStartY && yIndex <= world.renderEndY) {
			if(xIndex >= world.renderStartX && xIndex <= world.renderEndX) {
	
		if(world.tiles[xIndex][yIndex].getClass().equals(Empty.class) &&
				world.bgTiles[xIndex][yIndex].getClass().equals(Empty.class)) {
			this.inTheSun = true;
		}
		if(inTheSun && world.sunlightLevel > lightLevel) lightLevel = world.sunlightLevel;
		if(lightLevel >= density && density > 0) {
		//lightLevel = 0;
		Tile right = null;
		Tile left = null;
		Tile up = null;
		Tile down = null;
		if(xIndex+1 < world.sizeX-1) right = world.tiles[xIndex+1][yIndex];
		if(xIndex-1 > 0) left = world.tiles[xIndex-1][yIndex];
		if(yIndex-1 > 0) up = world.tiles[xIndex][yIndex-1];
		if(yIndex+1 < world.sizeY-1) down = world.tiles[xIndex][yIndex+1];

		//left
		//if(left!=null && left.xIndex > renderStartX+4) {
		if(left!=null) {
			if(lightLevel-left.density > left.lightLevel) {
				 left.lightLevel = lightLevel-left.density;
				 left.getLight(world);
				//if(left.inTheSun && world.bgTiles[xIndex][yIndex].getClass().equals(Empty.class)) {
				//	this.inTheSun = true;
				//	lightLevel = left.lightLevel;
				//}
			}
		}
		//right
		if(right!=null) {
			if(lightLevel-right.density  > right.lightLevel) {
				 right.lightLevel = lightLevel-right.density;
				 right.getLight(world);
				//if(right.inTheSun && world.bgTiles[xIndex][yIndex].getClass().equals(Empty.class)) {
				//	this.inTheSun = true;
				//	lightLevel = right.lightLevel;
				//}
			}
		}
		//up
		if(up!=null) {
			if(lightLevel-up.density  > up.lightLevel) {
				 up.lightLevel = lightLevel-up.density;
				 up.getLight(world);
				//if(up.inTheSun && world.bgTiles[xIndex][yIndex].getClass().equals(Empty.class)) {
				//	this.inTheSun = true;
				//	lightLevel = up.lightLevel;
				//}
			}
		}
		//down
		if(down!=null) {
			//if(this.inTheSun) {
			//	down.lightLevel = lightLevel;
			//	if(down.getClass().equals(Empty.class)) down.inTheSun=true;
			//	down.getLight(world);
			//}else
			if(lightLevel-down.density  > down.lightLevel) {
				 down.lightLevel = lightLevel-down.density;
				 //if(this.inTheSun) down.lightLevel = lightLevel;
				 down.getLight(world);
			}
		}
			}
		}
		}
		if(lightLevel < 0) lightLevel = 0;
		//if(inTheSun && world.sunlightLevel > lightLevel) lightLevel = world.sunlightLevel;
	}
	
	
	public Tile newInstance(int xIndex, int yIndex, boolean isBackgroundTile) {

			Object instance = null;
			Class<?> clazz = this.getClass();
			try {
				Constructor<?> constructor = clazz.getConstructor(
						int.class, int.class, boolean.class);
				instance = constructor.newInstance(xIndex, yIndex, isBackgroundTile);
			} catch (Exception e) {
				e.printStackTrace();
			}
		return (Tile) instance;
	}
	
	public void draw(Graphics2D g, int OffX, int OffY) {
		
	}
	
	public void drawDamageOverlay(Graphics2D g, int OffX, int OffY) {
		if(hitpool>0 && hitpoints < hitpool) {
			if(hitpoints<0) hitpoints=0;
			int multiple = 4;
			if(hitpoints>0) {
				multiple=hitpool/hitpoints;
			}
			int frame = 4/multiple;
			damageOverlay.drawSprite(x-OffX, y-OffY, -frame+4, (int)Math.ceil(lightLevel), g);
		}
	}
	
	//public void drawLightMask(Graphics2D g, int OffX, int OffY) {
	//	lightMask.drawSprite(x-OffX, y-OffY, (int)Math.ceil(lightLevel), g);
	//}
}
