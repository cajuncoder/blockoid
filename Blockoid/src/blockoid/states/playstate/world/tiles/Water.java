package blockoid.states.playstate.world.tiles;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import blockoid.graphics.Sprite;
import blockoid.states.playstate.world.World;

public class Water extends Tile {

	public int mass = 80;
	public int oldMass = 0;
	
	public Water(int yIndex, int xIndex) {
		super(yIndex, xIndex, false);
		super.hitpool = 1;
		super.hitpoints = hitpool;
		sprite = null;
		solid = false;
	}
	
	public Water(int yIndex, int xIndex, int mass) {
		super(yIndex, xIndex, false);
		sprite = null;
		solid = false;
		this.mass = mass;
	}

	public void update(World world) {
		//getLight(world);
		
		Tile right = world.tiles[xIndex+1][yIndex];
		Tile left = world.tiles[xIndex-1][yIndex];
		Tile down = world.tiles[xIndex][yIndex+1];

		//Flow Into Nearby Water Tiles
		if(down.getClass().equals(Water.class)) {
			Water t = (Water)down;
			if(t.mass < 80 && mass > 0) {
				mass-=1;
				t.mass+=1;
				//return;
			}
			if(t.mass < mass && t.mass < 78 && mass > 10) {
				mass-=2;
				t.mass+=2;
				//return;
			}
		}
		if(right.getClass().equals(Water.class)) {
			Water t = (Water)right;
			if(t.mass < mass && mass > 0) {
				mass-=1;
				t.mass+=1;
				//return;
			}
			if(t.mass < mass && t.mass < 78 && mass > 10) {
				mass-=2;
				t.mass+=2;
				//return;
			}
		}
		if(left.getClass().equals(Water.class)) {
			Water t = (Water)left;
			if(t.mass < mass && mass > 0) {
				mass-=1;
				t.mass+=1;
				//return;
			}
			if(t.mass < mass && t.mass < 78 && mass > 2) {
				mass-=2;
				t.mass+=2;
				//return;
			}
			if(t.mass < mass && t.mass < 70 && mass > 10) {
				mass-=10;
				t.mass+=10;
				//return;
			}
		}
		
		//Create Nearby Water Tiles
		if(down.getClass().equals(Empty.class) && mass > 0) {
			world.tiles[down.xIndex][down.yIndex] = new Water(down.xIndex,down.yIndex,mass);
			mass=0;
			world.tiles[xIndex][yIndex] = new Empty(xIndex, yIndex, false);
			if(mass<=0) world.tiles[xIndex][yIndex] = new Empty(xIndex, yIndex, false);
			return;
		}
		if(right.getClass().equals(Empty.class) && mass > 0) {
			mass-=1;
			world.tiles[right.xIndex][right.yIndex] = new Water(right.xIndex,right.yIndex,1);
		}
		if(left.getClass().equals(Empty.class) && mass > 0) {
			mass-=1;
			world.tiles[left.xIndex][left.yIndex] = new Water(left.xIndex,left.yIndex,1);
		}
		
		if(mass<=0) world.tiles[xIndex][yIndex] = new Empty(xIndex, yIndex, false);
		//if(!right.getClass().equals(Water.class) && !left.getClass().equals(Water.class) && mass <2) {
		//	world.tiles[xIndex][yIndex] = new Empty(xIndex, yIndex);
		//	return;
		//}

	}
	
	
	public void getLight(World world, int renderStartX, int renderEndX, int renderStartY, int renderEndY) {
		lightLevel = 7;
	}
	
	public void draw(Graphics2D g, int OffX, int OffY) {
		//sprite.drawSprite(x-OffX, y-OffY, 0, g);
		g.setColor(new Color(100,155,255,200));
		int xtra=0;
		//if(mass < 8) xtra = 1;
		int h = mass/10;
		if(h == 7 && mass == 79) h = 8;
		g.fillRect(x-OffX, y-OffY-(h-8), 8, h);
	}
	
}

