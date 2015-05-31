package blockoid.game.being;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import blockoid.Assets;
import blockoid.Game;
import blockoid.audio.Audio;
import blockoid.game.Inventory;
import blockoid.game.Player;
import blockoid.game.World;
import blockoid.game.being.behavior.Behavior;
import blockoid.game.being.behavior.BehaviorCache;
import blockoid.game.being.behavior.Rest;
import blockoid.game.item.*;
import blockoid.game.tile.Dirt;
import blockoid.game.tile.Empty;
import blockoid.game.tile.Tile;
import blockoid.game.tile.Water;
import blockoid.graphics.SpriteSheet;
import blockoid.state.GameState;
import blockoid.state.State;

abstract public class Being {
	protected static int inventoryWidth = 8;
	protected static int inventoryHeight = 4;
	//protected static int toolbeltWidth = 12;
	//protected static int toolbeltHeight = 1;
	public Game game;
	String name;
	public SpriteSheet sprite;
	public Inventory inventory;
	public int dx = 0;
	public int dy = 0;
	public double x = 0;
	public double y = 0;
	public double oldX = 0;
	public double oldY = 0;
	public int oldXTile = 0;
	public int oldYTile = 0;
	public boolean oldi = false;
	public double xVel = 0;
	public double yVel = 0;
	public int jumpVel = 0;
	public int jumpCounter = 0;
	protected boolean standingOnGround = false;
	public boolean inWater = false;
	public boolean knockedBack = false;
	protected int timeOnGround = 0;
	protected int timeInAir = 0;
	public int height = 16;
	public int width = 16;
	protected int frame;
	protected int frameCounter;
	private int oldFrameCounter;
	public int hitpool;
	public int hitpoints;
	public double speed = 1;
	protected double minSpeed = 1;
	protected double maxSpeed = 1;
	public int[] animation;
	public int[] idleRight = {0};
	public int[] idleLeft = {3};
	public int[] walkRight = {0, 1};
	public int[] walkLeft = {3, 4};
	public int[] jumpRight = {2};
	public int[] jumpLeft = {5};
	Audio jump = Assets.getAudio("jump");
	Audio hurt = Assets.getAudio("genericHurt");
	public Item rightHandItem = null;
	int lightLevel = 0;
	public Behavior behavior;
	public Brain brain;
	public BehaviorCache bcache;
	public int sightRange = 100;
	public int attackRange = 2;
	public int followDistance = 20;
	public int wanderRange = 100;
	
	public Being(Game game) {
		inventory = new Inventory("Inventory",inventoryWidth,inventoryHeight);
		
		name = "Ogg";
		hitpool = 16;
		hitpoints = hitpool;
		sprite = Assets.getSpriteSheet("characters/character", width, height);
		animation = walkRight;
		frame = 0;
		this.game = game;
		behavior = new Rest(Rest.SECOND);
		brain = new Brain();
		bcache = new BehaviorCache();
		attackRange = sprite.spriteSizeX/2;
	}
	
	public abstract void act(World world, long elapsedTime);
	

	private int showHealthbarTimer = 120;
	private int oldHitpoints = 0;
	private int hurtTimer = 30;
	public void update(World world, long elapsedTime) {
		hurtTimer--;
		if(oldYTile-1 > 0) {
			lightLevel = (int) Math.ceil(world.tiles[oldXTile][oldYTile-1].lightLevel);
		}
		
		if(standingOnGround) {
			if(xVel >= 0.2) xVel -= 0.2;
			if(xVel <= -0.2) xVel += 0.2;
			if(xVel <= 0.2 && xVel >= -0.2) {
				xVel=0;
				knockedBack = false;
			}
		}else{
			if(xVel >= 0.03) xVel -= 0.03;
			if(xVel <= -0.03) xVel += 0.03;
			if(xVel <= 0.03 && xVel >= -0.03) {
				xVel=0;
				knockedBack = false;
			}
		}
		
		if(yVel <= 2.5) yVel += 0.125;
	    if(standingOnGround) yVel = 0;
		if(standingOnGround) {
			timeInAir = 0;
		}else{timeInAir++;}
		
		act(world, elapsedTime);
		
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
			if(animation==walkRight) animation = idleRight;
			if(animation==walkLeft) animation = idleLeft;
			if(animation==jumpRight) animation = idleRight;
			if(animation==jumpLeft) animation = idleLeft;
		}
		
		standingOnGround = false;
		if(inWater) {
			xVel = xVel*0.66;
			yVel = yVel*0.66;
		}
		
		if(x+xVel < world.sizeX*8 && x+xVel > 0) x+=xVel;
		if(y+yVel < world.sizeY*8 && y+yVel > 0) y+=yVel;
		
		//dx = (int)Math.round(x);
		//dy = (int)Math.round(y);
		checkCollision(world);
		showHealthbarTimer-=1;
		if(hitpoints!=oldHitpoints) showHealthbarTimer = 120;
		
		oldX = x;
		oldY = y;
		oldFrameCounter = frameCounter;
		dx = (int)Math.round(x);
		dy = (int)Math.round(y);
		if(standingOnGround) {timeOnGround+=1;} else {timeOnGround=0;}
		
		oldi = game.keyboard.i;
		oldHitpoints = hitpoints;
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
							if(yi == 0 && !standingOnGround) {
								if (timeInAir > 72) {
									int deduction = ((timeInAir - 72) / 10) + 1;
									//hitpoints -= deduction;
									//hitpoints = Math.max(0, hitpoints);
									hurt(deduction, world);
								}
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
								//jump over stuff
								if(standingOnGround && x-oldX < 0 && !world.tiles[xTile][yTile-1].solid && !world.tiles[xTile][yTile-2].solid) {
									yVel=-1.4;
									//xVel=-1.25;
									y+=yVel;
									//x+=xVel;
									standingOnGround=false;
									timeInAir = 0;
								} else
								if(standingOnGround && x-oldX > 0 && !world.tiles[xTile][yTile-1].solid && !world.tiles[xTile][yTile-2].solid) {
									yVel=-1.4;
									//xVel=+1.25;
									y+=yVel;
									//x+=xVel;
									standingOnGround=false;
									timeInAir = 0;
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
	
	public void hurt(int amount, World world) {
		if(hurtTimer<=0) {
			hurtTimer = 30;
			hitpoints-=amount;
			if(hitpoints<0) hitpoints = 0;
		
			if(x >= world.CameraOffX && x <= world.CameraOffX+world.game.width) {
				if(y >= world.CameraOffY && y <= world.CameraOffY+world.game.width) {
					hurt.play(false);
				}
			}
		
			//Death
			if(hitpoints <= 0 && hitpool > 0) {
				if(this.equals(world.player)) {
					world.player = null;
				}else{
					world.beings.remove(this);
				}
			}
		}
	}
	
	public void knockBack(Being being, double amount) {
		if(knockedBack==false) {
			timeInAir = 0;
			standingOnGround = false;
			knockedBack = true;
			if(amount > 7) amount = 7;
			double xDiff = this.x - being.x;
			double yDiff = this.y - being.y;
			//if(xDiff==0 && yDiff == 0) {yDiff = -1;}
			double totalDiff = Math.abs(xDiff) + Math.abs(yDiff);
			if(totalDiff==0) totalDiff = 2;
			if(yDiff > -totalDiff/2 && yDiff < totalDiff/2) yDiff -= totalDiff/2;
			this.xVel = (xDiff/totalDiff)*amount;
			this.yVel = (yDiff/totalDiff)*amount;
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
		int x = dx-(sprite.spriteSizeX/2)-OffX;
		int y = dy-(sprite.spriteSizeY-1)-OffY;
		//g.drawString(name, dx-OffX-32, dy-OffY-16);
		sprite.drawSprite(x, y, animation[frame], lightLevel, g);
		//sprite.drawSprite(dx-(width/2)-OffX+16, dy-(height-1)-OffY, animation[frame], g);
		//if(inventoryOpen) inventory.draw(g);

		if(showHealthbarTimer > 0) {
			int barX = x;
			int barY = y-height/2;
			int barWidth = width;
			int barHeight = 3;
			g.setColor(Color.DARK_GRAY);
			g.drawRect(barX, barY, barWidth, barHeight);
			g.setColor(Color.RED);
			g.fillRect(barX+1, barY+1, barWidth-1, barHeight-1);
			g.setColor(Color.GREEN);
			g.fillRect(barX+1, barY+1, (int)((barWidth-1) * (hitpoints * 1.0 / hitpool)), barHeight-1);
		}
	}
	
	public void moveLeft() {
		if(standingOnGround || inWater) {
			if(!knockedBack) xVel=-speed;
		}else{
			if(xVel>-speed) xVel-=0.1;
		}
		animation = walkLeft;
		frameCounter+=1;
	}
	
	public void moveRight() {
		if(standingOnGround || inWater) {
			if(!knockedBack) xVel=speed;
		}else{
			if(xVel<speed) xVel+=0.1;
		}
		animation = walkRight;
		frameCounter+=1;
	}
	
	//double oldOldAiX; //don't question it! :P
	double oldAiX;
	public void aiMoveRight() {
		moveRight();
		if(x == oldAiX && standingOnGround) jumpSetAmount(3);
		oldAiX = x;
	}
	
	public void aiMoveLeft() {
		moveLeft();
		if(x == oldAiX && standingOnGround) jumpSetAmount(3);
		oldAiX = x;
	}
	
	public void jump() {
		if(timeOnGround>1 || yVel < 0 && timeInAir < 14){
			yVel = -2.0;
			//jump.play(false);
			if(animation == idleRight || animation == walkRight) {
				animation = jumpRight;
				frame = 0;
			}
			if(animation == idleLeft || animation == walkLeft) {
				animation = jumpLeft;
				frame = 0;
			}
		}
		
		if (inWater){
			//jumpVel = 4;
			yVel = -2.0;
			//jump.play(false);
		}
	}
	
	public void jumpSetAmount(double amount) {
		if(timeOnGround>1 || yVel < 0 && timeInAir < 14){
			yVel = -amount;
			//jump.play(false);
			if(animation == idleRight || animation == walkRight) {
				animation = jumpRight;
				frame = 0;
			}
			if(animation == idleLeft || animation == walkLeft) {
				animation = jumpLeft;
				frame = 0;
			}
		}
		
		if (inWater){
			//jumpVel = 4;
			yVel = -2.0;
			//jump.play(false);
		}
	}
	
	public int distanceFrom(Being being) {
		return (int) Math.sqrt(Math.pow(being.x - this.x, 2) + Math.pow(being.y - this.y, 2));
	}
	
	public boolean isEnemy(Being being) {
		return true; // TODO(griffy) FREE-FOR-ALL!
		//return (this instanceof Player && !(being instanceof Player)) ||
		//	   (!(this instanceof Player) && being instanceof Player);
	}
	
	public boolean isFriendly(Being being) {
		return !isEnemy(being);
	}
	
	public boolean isDead() {
		return hitpoints == 0;
	}
	
	public boolean isAlive() {
		return !isDead();
	}
	
	public void useMinSpeed() {
		speed = minSpeed;
	}
	
	public void useMaxSpeed() {
		speed = maxSpeed;
	}
	
	public Being getNewInstance() {
		return null;
	}
}

