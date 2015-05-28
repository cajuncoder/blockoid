package blockoid.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.ImageIcon;

import blockoid.Assets;
import blockoid.Game;
import blockoid.game.being.Being;
import blockoid.game.being.ItFollows;
import blockoid.game.biome.Biome;
import blockoid.game.biome.DesertBiome;
import blockoid.game.biome.ForestBiome;
import blockoid.game.biome.MountainBiome;
import blockoid.game.biome.PlainsBiome;
import blockoid.game.item.*;
import blockoid.game.object.GameObject;
import blockoid.game.object.OakTree;
import blockoid.game.object.PalmTree;
import blockoid.game.tile.Desert;
import blockoid.game.tile.DesertGrass;
import blockoid.game.tile.Dirt;
import blockoid.game.tile.Empty;
import blockoid.game.tile.Grass;
import blockoid.game.tile.Stone;
import blockoid.game.tile.Tile;
import blockoid.graphics.SpriteSheet;

//TO DO:
// - Water: Improve Flow
// - Player water physics
// - Map generation parameters
// - Stone and tile variety --- maybe
// - Background
// - Basic NPCs
// - Draw and Update Objects by Tile (?) [Render Distance not large enough however]
/////Alternatively: Have objects composed of, and contained in multiple tiles with a parent.
// - Pickaxe; make it swing-able! Separate player body and hand sprites.
// - Basic Sound Effects

//MOSTLY DONE!
// - Inventories and Chests; Items

//DONE!
// - Dirt and grass to become one; dirt becomes grass when exposed to sunlight
// - Player abilities (break blocks / objects)
// - Tiles drop items; use-able items and item drops from destroyed blocks
// - Improve Lighting
// - Audio Assets


public class World {
	
	public int CameraOffX = 0;
	public int CameraOffY = 0;
	public Game game;
	public int sizeX = 512*16;
	public int sizeY = 512/2;
	public static int TILE_SIZE = 8;
	public Tile[][] bgTiles = new Tile[sizeX][sizeY];
	public Tile[][] tiles = new Tile[sizeX][sizeY];
	public int renderStartX;
	public int renderEndX;
	public int renderStartY;
	public int renderEndY;
	
	public int sunlightLevel = 7;
	public int minute = 0;
	public int hour = 11;
	public int[] lightLevels = {2,2,3,4,5,6,7,7,7,7,7,7,7,7,7,7,7,7,7,6,5,4,3,2};
	public Biome[] biomes;
	
	//public Image tilebg = new ImageIcon("res/gfx/tiles/tilebg.png").getImage();
	public SpriteSheet tilebg = Assets.getSpriteSheet("tiles/tilebg", 10, 10);
	public BufferedImage worldbg = Assets.getImage("bg/forebackground");
	public BufferedImage worldbg2 = Assets.getImage("bg/backbackground");
	//public CopyOnWriteArrayList<GameObject> objects = new CopyOnWriteArrayList<GameObject>();
	public CopyOnWriteArrayList<Item> items = new CopyOnWriteArrayList<Item>();
	
	Background background;
	public Player player;
	//public ArrayList<Object> liquidTiles = new ArrayList<Object>();
	public CopyOnWriteArrayList<Being> beings = new CopyOnWriteArrayList<Being>();
	
	public World(Game game) {
		this.game = game;
		player = new Player(game);
		background = new Background(this);

		int nOfBiomes = sizeX/Biome.BIOME_SIZE;
		
		biomes = new Biome[nOfBiomes];
		for(int i = 0; i < nOfBiomes; i++) {
			Random r = new Random();
			int biomeType = r.nextInt(4);
			if(biomeType == 0) biomes[i] = new PlainsBiome(this, i);
			if(biomeType == 1) biomes[i] = new DesertBiome(this, i);
			if(biomeType == 2) biomes[i] = new MountainBiome(this, i);
			if(biomeType == 3) biomes[i] = new ForestBiome(this, i);
			if(biomeType == 4) System.out.println("BIOME NULL");
		}
		
		////////// PLAYER --- TEMPORARY //////////
		player.place(((sizeX/2)*8)+4, getSurface(sizeX/2)*8);
		
		//creatures.add(new ItFollows());
		//creatures.get(0).place(((sizeX/2)*8)+4+5, getSurface(sizeX/2)*8+5);
	}
	
	private int spawnNewPlayerCounter = 600;
	public void update(long elapsedTime) {
		
		if(player==null) {
			spawnNewPlayerCounter -= 1;
			if(spawnNewPlayerCounter <= 0) {
			player = new Player(game);
			player.place(((sizeX/2)*8)+4, getSurface(sizeX/2)*8);
			}
		}else{spawnNewPlayerCounter = 600;}
		
		for(int y = renderStartY; y < sizeY && y <= renderEndY; y++) {
			for(int x = renderStartX; x < sizeX && x <= renderEndX; x++) {
				tiles[x][y].lightLevel = 0;
				bgTiles[x][y].lightLevel = 0;
				tiles[x][y].inTheSun=false;
				bgTiles[x][y].inTheSun=false;
			}
		}
		
		if(player!=null) player.selectedObject=null;
		
		//Updates
		for(int y = renderStartY; y < sizeY && y <= renderEndY; y++) {
			for(int x = renderStartX; x < sizeX && x <= renderEndX; x++) {
				tiles[x][y].update(this);
				bgTiles[x][y].update(this);
				tiles[x][y].getLight(this);
				
				
			}
		}
		
		for(int y = renderStartY; y < sizeY && y <= renderEndY; y++) {
			for(int x = renderStartX; x < sizeX && x <= renderEndX; x++) {
				bgTiles[x][y].lightLevel = tiles[x][y].lightLevel;
				
				if(tiles[x][y].object!=null) tiles[x][y].object.update(this);
				if(bgTiles[x][y].object!=null) bgTiles[x][y].object.update(this);
			}
		}
		
		for(Item i: items) {
			i.update(this);
		}
		
		for (Being being: beings) {
			being.update(this, elapsedTime);
		}
		
		//for(GameObject o: objects) {
		//	o.update(this);
		//}
		
		if(player!=null) player.update(this, elapsedTime);
		
		if(player!=null) {
			if(player.selectedObject!=null) player.selectedObject.selected=true;
			CameraOffX = Math.round(player.dx - game.width/2);
			CameraOffY = Math.round(player.dy - game.height/2 - (game.height/8));
		}
		
		if(CameraOffX < 0) CameraOffX = 0;
		if(CameraOffX > (sizeX*TILE_SIZE) - game.width) CameraOffX = (sizeX*TILE_SIZE) - game.width;
		if(CameraOffY < 0) CameraOffY = 0;
		if(CameraOffY > (sizeY*TILE_SIZE) - game.height) CameraOffY = (sizeY*TILE_SIZE) - game.height;
		minute++;
		if(minute > 30*5) {
			sunlightLevel = lightLevels[hour];
			minute = 0;
			hour++;
			if(hour > 23) hour = 0;
		}
	}
	
	public void draw(Graphics2D g) {
		int startXPadding = -4;
		int endXPadding = 4;
		int startYPadding = -4;
		int endYPadding = 12;
		renderStartX = CameraOffX/8 + startXPadding;
		renderStartY = CameraOffY/8 + startYPadding;
		renderEndX = (CameraOffX/8)+(game.width/8)+endXPadding;
		renderEndY = (CameraOffY/8)+(game.height/8)+endYPadding;
		int subRenderStartX = renderStartX-startXPadding;
		int subRenderStartY = renderStartY-startYPadding;
		int subRenderEndX = renderEndX-endXPadding+1;
		int subRenderEndY = renderEndY-endYPadding+1;
		
		if(renderStartX < 0) renderStartX = 0;
		if(renderStartY < 0) renderStartY = 0;
		if(renderEndX >= sizeX) renderEndX = sizeX;
		if(renderEndY >= sizeY) renderEndY = sizeY;
		
		if(background!=null) background.draw(g, CameraOffX, CameraOffY);
		
		//Outlines
		for(int y = subRenderStartY; y < sizeY && y <= subRenderEndY; y++) {
			for(int x = subRenderStartX; x < sizeX && x <= subRenderEndX; x++) {
				if(bgTiles[x][y].solid && !bgTiles[x][y].getClass().equals(Empty.class) && !tiles[x][y].solid) {
					//g.drawImage(tilebg, tiles[x][y].x-1-CameraOffX, tiles[x][y].y-1-CameraOffY, null);
					tilebg.drawSprite(tiles[x][y].x-1-CameraOffX, tiles[x][y].y-1-CameraOffY, 0, (int)Math.ceil(tiles[x][y].lightLevel), g);
				}
			}
		}
		
		//Draw Background Tiles
		g.setColor(new Color(1,1,1,75));
		for(int y = renderStartY; y < sizeY && y <= renderEndY; y++) {
			for(int x = renderStartX; x < sizeX && x <= renderEndX; x++) {
				//bgTiles[x][y].update(this);
				if(x >= subRenderStartX && y >= subRenderStartY && x <= subRenderEndX && y <= subRenderEndY) {
				if(!bgTiles[x][y].getClass().equals(Empty.class) && !tiles[x][y].solid){
					bgTiles[x][y].draw(g, CameraOffX, CameraOffY);
					g.fillRect(tiles[x][y].x-CameraOffX, tiles[x][y].y-CameraOffY, 8, 8);
				}
				}
				if(bgTiles[x][y].object!=null) bgTiles[x][y].object.draw(g, CameraOffX, CameraOffY);
			}
		}
		
		// Game Objects //
		//for(GameObject o : objects) {
		//	o.draw(g, CameraOffX, CameraOffY);
		//}
		
		// Foreground //
		//Outlines
		for(int y = subRenderStartY; y < sizeY && y <= subRenderEndY; y++) {
			for(int x = subRenderStartX; x < sizeX && x <= subRenderEndX; x++) {
				if(tiles[x][y].solid) {
					//g.drawImage(tilebg, tiles[x][y].x-1-CameraOffX, tiles[x][y].y-1-CameraOffY, null);
					tilebg.drawSprite(tiles[x][y].x-1-CameraOffX, tiles[x][y].y-1-CameraOffY, 0, (int)Math.ceil(tiles[x][y].lightLevel), g);
				}
			}
		}
		
		//Player In Water
		if(player != null && player.inWater) player.draw(g, CameraOffX, CameraOffY);
		
		//Draw Foreground Tiles
		for(int y = renderStartY; y < sizeY && y <= renderEndY; y++) {
			for(int x = renderStartX; x < sizeX && x <= renderEndX; x++) {
				if(x >= subRenderStartX && y >= subRenderStartY && x <= subRenderEndX && y <= subRenderEndY) {
					tiles[x][y].draw(g, CameraOffX, CameraOffY);
				}
				if(tiles[x][y].object!=null) tiles[x][y].object.draw(g, CameraOffX, CameraOffY);
			}
		}
		
		//Player && Items
		if(player!=null && !player.inWater) player.draw(g, CameraOffX, CameraOffY);
		for(Item i: items) {
			i.worldDraw(g, CameraOffX, CameraOffY);
		}
		
		for (Being being: beings) {
			being.draw(g, CameraOffX, CameraOffY);
		}
	}
	
	
	
	public int getSurface(int x) {
		for(int y = sizeY-1; y > 0; y--) {
			if(tiles[x][y] != null && !tiles[x][y].solid && !bgTiles[x][y].solid) {
				return tiles[x][y].yIndex+1;
			}
		}
		return 0;
	}
	
	public synchronized void addItem(Item item, int x, int y) {
		item.x = x;
		item.y = y;
		items.add(item);
	}
	
	//public synchronized void addObject(GameObject object, Tile tile) {
		
	//}
	
	//public synchronized void removeObject(GameObject object) {
		//if(player!=null && player.selectedObject.equals(object)) player.selectedObject = null;
	//	if(object.tile.object!=null) object.tile.object=null;
	//	objects.remove(object);
	//}
	
	public Being nearestBeing(Being being, boolean filterFriendly, boolean filterEnemy) {
		double nearest = Integer.MAX_VALUE;
		Being nearestBeing = null;
		
		ArrayList<Being> beings = (ArrayList<Being>) this.beings.clone();
		beings.add(player);
		
		for (Being b : beings) {
			if (b.hashCode() == being.hashCode()) continue;
			if (filterFriendly && b.isFriendly(being)) continue;
			if (filterEnemy && b.isEnemy(being)) continue;
			
			if (Math.abs(b.x - being.x) < nearest) {
				nearest = Math.abs(b.x - being.x);
				nearestBeing = b;
			}
		}
		
		return nearestBeing;
	}
	
	public Being nearestEnemy(Being being) {
		return nearestBeing(being, true, false);
	}
	
	public Being nearestFriendly(Being being) {
		return nearestBeing(being, false, true);
	}
}
