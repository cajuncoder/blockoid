package blockoid.game.object;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import blockoid.Assets;
import blockoid.audio.Audio;
import blockoid.game.Player;
import blockoid.game.World;
import blockoid.game.item.Item;
import blockoid.game.tile.Empty;
import blockoid.game.tile.Stone;
import blockoid.game.tile.Tile;
import blockoid.graphics.SpriteSheet;

public abstract class GameObject {

	public SpriteSheet spriteSheet;
	public int dx;
	public int dOffX;
	public int dy;
	public int dOffY;
	public Tile tile;
	public int tileX;
	public int tileY;
	public int hitpoints;
	public int hitpool;
	public int lightLevel;
	public Item dropItem;
	public Audio hit = Assets.getAudio("hitWood");
	public boolean selected = false;
	
	public GameObject(Tile tile) {
		if(tile!=null) {
		this.tile = tile;
		this.tile.object = this;
		this.dx = tile.x;
		this.dy = tile.y;
		this.tileX = tile.xIndex;
		this.tileY = tile.yIndex;
		this.hitpool = 16;
		this.hitpoints = hitpool;
		}
	}
	
	int healCounter = 0;
	
	public void update(World world) {
		if(tile.solid) {
			lightLevel = (int) Math.ceil(tile.lightLevel);
		}else{
			destroy(world);
			return;
		}
		healCounter++;
		if(hitpoints < hitpool && healCounter >= 60) {
			hitpoints++;
			healCounter = 0;
		}
		if(hitpoints <= 0) {
			destroy(world);
			return;
		}
		
		getSelected(world);
	}
	
	public void damage(World world, int value) {
		this.hitpoints -= value;
		healCounter = 0;
		hit.play(false);
	}
	
	public void destroy(World world) {
		this.tile.object = null;
		if(dropItem!=null) {
			Item item = dropItem.getNewInstance();
			item.x = dx;
			item.y = dy;
			world.addItem(item, (int)item.x, (int)item.y-this.spriteSheet.spriteSizeY);
		}
		//world.removeObject(this);
	}
	
	public void getSelected(World world) {
		selected = false;
		Player player = world.player;
		if(player!=null) {
			int mx = world.game.mouseMotion.x;// + world.CameraOffX;
			int my = world.game.mouseMotion.y;// + world.CameraOffY;
			//System.out.println(mx);
			//System.out.println(dx+dOffX);
			if(mx > (dx+dOffX) && mx < (dx+dOffX+spriteSheet.spriteSizeX)) {
				if(my > (dy+dOffY) && my < (dy+dOffY+spriteSheet.spriteSizeY)) {
					int xi = mx-(dx+dOffX);
					int yi = my-(dy+dOffY);
					Color color = new Color(spriteSheet.sheet.getRGB(xi, yi), true);
					if(color.getAlpha() > 0) {
						player.selectedObject = this;
						//selected = true;
					}
					//System.out.println("Something is selected!");
				}
			}
		}
	}
	
	public void draw(Graphics2D g, int xOff, int yOff) {
		dOffX = -xOff-(spriteSheet.spriteSizeX/2)+5;
		dOffY = -yOff-spriteSheet.spriteSizeY;
		int lightOff = 0; if(selected) lightOff = 1;
		spriteSheet.drawSprite(dx+dOffX, dy+dOffY, 0, lightLevel+lightOff, g);
	}
	
	public GameObject getNewInstance(Tile tile) {
		
		return null;
	}
	
}
