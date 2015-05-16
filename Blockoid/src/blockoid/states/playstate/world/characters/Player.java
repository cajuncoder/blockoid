package blockoid.states.playstate.world.characters;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import blockoid.Assets;
import blockoid.Audio;
import blockoid.Game;
import blockoid.graphics.SpriteSheet;
import blockoid.states.GameState;
import blockoid.states.playstate.PlayState;
import blockoid.states.playstate.world.items.*;
import blockoid.states.playstate.world.tiles.Dirt;
import blockoid.states.playstate.world.tiles.Empty;
import blockoid.states.playstate.world.tiles.Tile;
import blockoid.states.playstate.world.tiles.Water;
import blockoid.states.playstate.world.World;

public class Player {

	String name;
	public SpriteSheet sprite;
	public Inventory inventory;
	public Inventory toolbelt;
	public int dx = 0;
	public int dy = 0;
	public double x = 0;
	public double y = 0;
	public double oldX = 0;
	public double oldY = 0;
	public int oldXTile = 0;
	public int oldYTile = 0;
	public boolean oldi = false;
	public boolean inventoryOpen = false;
	public double xVel = 0;
	public double yVel = 0;
	public int jumpVel = 0;
	public int jumpCounter = 0;
	private boolean standingOnGround = false;
	public boolean inWater = false;
	private int timeOnGround = 0;
	private int timeInAir = 0;
	public int height = 16;
	public int width = 16;
	private int frame;
	private int frameCounter;
	private int oldFrameCounter;
	public int hitpool;
	public int hitpoints;
	public int[] animation;
	public int[] walkRight = {0, 1};
	public int[] walkLeft = {3, 4};
	public int[] jumpRight = {2};
	public int[] jumpLeft = {5};
	Audio jump = Assets.getAudio("jump");
	public Item rightHandItem = null;
	public int toolbeltIndex = 0;
	int lightLevel = 0;
	
	public Player() {
		inventory = new Inventory("Inventory",8,4);
		toolbelt = new ToolBelt("Tool Belt", 9,1);
		inventory.addItem(new PickAxe());
		inventory.addItem(new DirtBlock(16));
		inventory.addItem(new DirtBlock());
		inventory.addItem(new DirtBlock(9));
		inventory.addItem(new DirtBlock());
		inventory.addItem(new DirtBlock());
		inventory.addItem(new DirtBlock(8));
		inventory.addItem(new DirtBlock());
		name = "Ogg";
		hitpool = 3;
		hitpoints = hitpool;
		sprite = Assets.getSpriteSheet("characters/character", width, height);
		animation = walkRight;
		frame = 0;
	}
	
	public void update(Game game, World world) {
		if(oldYTile-1 > 0) {
			lightLevel = (int) Math.ceil(world.tiles[oldXTile][oldYTile-1].lightLevel);
		}
		
		//Tool Belt
		toolbelt.moveTo(game.width/2 - toolbelt.sizeX/2, game.height - toolbelt.sizeY-2);
		if(game.mouseWheel.mouseWheelDown) {
			toolbeltIndex-=1;
			if(toolbeltIndex < 0) toolbeltIndex = toolbelt.slots.length-1;
		}
		if(game.mouseWheel.mouseWheelUp) {
			toolbeltIndex+=1;
			if(toolbeltIndex > toolbelt.slots.length-1) toolbeltIndex = 0;
		}
		for (int i = 1; i < 10; i++) {
			if (game.keyboard.num[i]) toolbeltIndex = i-1;
		}
		rightHandItem = toolbelt.slots[toolbeltIndex][0].item;
		
		PlayState ps = (PlayState)game.gameState;
		if(ps.gui.selectedInventory==null && rightHandItem==null) {
			if(ps.gui.grabbedItem==null) {
				emptyPrimary(world);
				emptySecondary(world);
			}
		}
		if(inventoryOpen == false && rightHandItem!=null) {
			if(ps.gui.grabbedItem==null) {
				rightHandItem.processPrimary(world);
				rightHandItem.processSecondary(world);
			}
		}

		
		//Inventory
		if(ps.gui.inventories.size() > 1) {
			inventoryOpen = true;
		}else{inventoryOpen = false;}
		if(game.keyboard.i && oldi == false) {
			if(inventoryOpen==true) {
				ps.gui.removeInventory(inventory);
			}else{
				inventory.moveTo(game.width/2 - 4*12, game.height/2 - 2*12);
				ps.gui.addInventory(inventory);
			}
		}
		
		if(standingOnGround) {
			xVel = 0;
		}else{
			if(xVel > 0) xVel -= 0.03;
			if(xVel < 0) xVel += 0.03;
		}

		if(yVel <= 2.5) yVel += 0.125;
		if(standingOnGround) yVel = 0;
		if(standingOnGround) {
			timeInAir = 0;
		}else{timeInAir++;}
		
		//Controls
		if(game.keyboard.right){
			if(standingOnGround || inWater) {
				xVel=1.00;
			}else{
				if(xVel<1.00) xVel+=0.1;
			}
			animation = walkRight;
			frameCounter+=1;
		}
		if(game.keyboard.left){
			if(standingOnGround || inWater) {
				xVel=-1.00;
			}else{
				if(xVel>-1.00) xVel-=0.1;
			}
			animation = walkLeft;
			frameCounter+=1;
		}
		
		if(game.keyboard.space && timeOnGround>1 || game.keyboard.space && yVel < 0 && timeInAir < 14){
			yVel = -1.9;
			//jump.play(false);
		}
		
		if(game.keyboard.space && inWater){
			//jumpVel = 4;
			yVel = -1.9;
			//jump.play(false);
		}
		
		if(!standingOnGround && animation == walkRight) {
			animation = jumpRight;
			frame = 0;
		}
		if(!standingOnGround && animation == walkLeft) {
			animation = jumpLeft;
			frame = 0;
		}

		//Frame
		if(frameCounter > 5) {
			frameCounter = 0;
			frame++;
			if(frame>=animation.length) frame = 0;
		}
		
		if(frameCounter == oldFrameCounter && standingOnGround) {
			frame = 0;
			if(animation==jumpRight) animation = walkRight;
			if(animation==jumpLeft) animation = walkLeft;
		}
		
		standingOnGround = false;
		if(inWater) {
			xVel = xVel*0.66;
			yVel = yVel*0.66;
		}
		x+=xVel;
		y+=yVel;
		
		dx = (int)x;
		dy = (int)y;
		checkCollision(world);
		
		oldX = x;
		oldY = y;
		oldFrameCounter = frameCounter;
		dx = (int)x;
		dy = (int)y;
		if(standingOnGround) {timeOnGround+=1;} else {timeOnGround=0;}
		
		oldi = game.keyboard.i;
	}
	
	public void checkCollision(World world) {
		if(world!=null) {
			
			//standingOnGround = false;
			
				int w = sprite.spriteSizeX/5;
				int h = sprite.spriteSizeY;
				int[] xOffs = {-w, w};
				//int[] yOffs = {-12, -8, 0};
				int[] yOffs = {0, -8, -12};
				//boolean[][] checks = new boolean[xOffs.length][yOffs.length];
				
				for(int yi = 0; yi < yOffs.length; yi++) {
					for(int xi = 0; xi < xOffs.length; xi++) {
					
						//checks[xi][yi] = false;
						
						int yMod = 0;
						if(yi == 0) yMod = -1;
						
						int xPos = (int)Math.round(oldX+xOffs[xi]);
						int yPos = (int)Math.round(oldY+yOffs[yi]);
						int xTile = xPos / world.TILE_SIZE;
						int yTile = yPos / world.TILE_SIZE;
						if(xTile >= world.sizeX) xTile = world.sizeX-1;
						if(yTile >= world.sizeY) yTile = world.sizeY-1;
						if(xTile < 0) xTile = 0; if(yTile < 0) yTile = 0;
						int oldxTile = xTile;
						int oldyTile = yTile;
						

						
						//if(world.tiles[oldxTile][oldyTile].wallRight && xTile > oldxTile || world.tiles[xTile][yTile].wallRight && oldxTile > xTile) {
						//	x = oldX;
						//}
						
						//if(world.tiles[oldxTile][oldyTile].wallLeft && xTile < oldxTile || world.tiles[xTile][yTile].wallLeft && oldxTile < xTile) {
						//	x = oldX;
						//}
				
						
						
						//y
						xPos = (int)Math.round(oldX+xOffs[xi]);
						yPos = (int)Math.round(y+yOffs[yi]);
						xTile = xPos / world.TILE_SIZE;
						yTile = yPos / world.TILE_SIZE;
						if(xTile >= world.sizeX) xTile = world.sizeX-1;
						if(yTile >= world.sizeY) yTile = world.sizeY-1;
						if(xTile < 0) xTile = 0; if(yTile < 0) yTile = 0;
				
						if(world.tiles[xTile][yTile].solid == true) {
							//y = oldY;
							if(yi == 0) {
								standingOnGround = true;
								y = world.tiles[xTile][yTile].y;
								yVel = 0;
							}
							if(yi > 0) {
								yVel = 0;
								y+=Math.abs(y-oldY);
							}
						}	
						
						
						//x
						xPos = (int)Math.round(x+xOffs[xi]);
						yPos = (int)Math.round(oldY+yOffs[yi]+yMod);
						xTile = xPos / world.TILE_SIZE;
						yTile = yPos / world.TILE_SIZE;
						if(xTile >= world.sizeX) xTile = world.sizeX-1;
						if(yTile >= world.sizeY) yTile = world.sizeY-1;
						if(xTile < 0) xTile = 0; if(yTile < 0) yTile = 0;
				
						if(world.tiles[xTile][yTile].solid == true) {
							
							if(yi > 0) {
								xVel=0;
								x = oldX;
							} 
							if(yi == 0) {
								if(standingOnGround && x-oldX < 0 && !world.tiles[xTile][yTile-1].solid) {
									yVel=-1.4;
									//xVel=-1.25;
									y+=yVel;
									//x+=xVel;
									standingOnGround=false;
								} else
								if(standingOnGround && x-oldX > 0 && !world.tiles[xTile][yTile-1].solid) {
									yVel=-1.4;
									//xVel=+1.25;
									y+=yVel;
									//x+=xVel;
									standingOnGround=false;
								}
								
									x = oldX;
								
							}
							
						}	
				
						//if(world.tiles[oldxTile][oldyTile].wallDown && yTile > oldyTile || world.tiles[xTile][yTile].wallDown && yTile < oldyTile) {
						//	y = oldY;
						//}
						
						//if(world.tiles[oldxTile][oldyTile].wallUp && yTile < oldyTile || world.tiles[xTile][yTile].wallUp && yTile > oldyTile) {
						//	y = oldY;
						//}
				
					} //xi
				} //yi
			
			//Check Water
			//Check Up
			int xTile = (int) ((x)/8);
			int yTile = (int) ((y-1)/8);
			oldXTile = (int) (oldX/8);
			oldYTile = (int) (oldY/8);
			if(xTile < 0) xTile = 0;
			if(yTile < 0) yTile = 0;
			if(xTile >= world.sizeX) xTile = world.sizeX-1;
			if(yTile >= world.sizeY) yTile = world.sizeY-1;
			if(oldXTile < 0) oldXTile = 0;
			if(oldYTile < 0) oldYTile = 0;

			Water wt = null;
			if(world.tiles[xTile][yTile].getClass().equals(Water.class)) wt = (Water)world.tiles[xTile][yTile];
			
			if(wt!=null&&wt.mass>60) {
				inWater = true;
			}else{inWater = false;}
		}
		
	}
	
	public Tile getTile(double x, double y, World world) {
		int xTile = (int) (x/8);
		int yTile = (int) (y/8);
		int oldXTile = (int) (oldX/8);
		int oldYTile = (int) (oldY/8);
		if(xTile < 0) xTile = 0;
		if(yTile < 0) yTile = 0;
		if(oldXTile < 0) oldXTile = 0;
		if(oldYTile < 0) oldYTile = 0;
		return null;
	}
	
	public void place(int x, int y) {
		this.x = x;
		this.y = y;
		this.oldX = x;
		this.oldY = y;
	}
	
	public void draw(Graphics2D g, int OffX, int OffY){
		//g.drawString(name, dx-OffX-32, dy-OffY-16);
		sprite.drawSprite(dx-(width/2)-OffX, dy-(height-1)-OffY, animation[frame], lightLevel, g);
		//sprite.drawSprite(dx-(width/2)-OffX+16, dy-(height-1)-OffY, animation[frame], g);
		//if(inventoryOpen) inventory.draw(g);
	}
	
	
	
	////Empty Hand////
	public void emptyPrimary(World world) {
		if(world.game.mouse.holdL) {
			int tileX = (int) ((world.game.mouseMotion.x+world.CameraOffX)/8);
			int tileY = (int) ((world.game.mouseMotion.y+world.CameraOffY)/8);
			if(tileX >= world.sizeX) tileX = world.sizeX-1;
			if(tileX < 0) tileX = 0;
			if(tileY >= world.sizeY) tileY = world.sizeY-1;
			if(tileY < 0) tileY = 0;
		
			world.tiles[tileX][tileY].hitpoints-=2;// = new Empty(tileX,tileY,false);
		}
	}
	
	public void emptySecondary(World world) {
		if(world.game.mouse.holdR) {
			int tileX = (int) ((world.game.mouseMotion.x+world.CameraOffX)/8);
			int tileY = (int) ((world.game.mouseMotion.y+world.CameraOffY)/8);
			if(tileX >= world.sizeX) tileX = world.sizeX-1;
			if(tileX < 0) tileX = 0;
			if(tileY >= world.sizeY) tileY = world.sizeY-1;
			if(tileY < 0) tileY = 0;
		
			world.bgTiles[tileX][tileY].hitpoints-=2;// = new Empty(tileX,tileY,false);
		}
	}
	
	
}
