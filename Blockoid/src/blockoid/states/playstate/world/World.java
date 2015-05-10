package blockoid.states.playstate.world;

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
import blockoid.graphics.SpriteSheet;
import blockoid.states.playstate.world.characters.Player;
import blockoid.states.playstate.world.items.*;
import blockoid.states.playstate.world.objects.GameObject;
import blockoid.states.playstate.world.tiles.Desert;
import blockoid.states.playstate.world.tiles.DesertGrass;
import blockoid.states.playstate.world.tiles.Dirt;
import blockoid.states.playstate.world.tiles.Empty;
import blockoid.states.playstate.world.tiles.Grass;
import blockoid.states.playstate.world.objects.OakTree;
import blockoid.states.playstate.world.tiles.Tile;
import blockoid.states.playstate.world.objects.PalmTree;

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
	public int sizeX = 512;
	public int sizeY = 512;
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
	
	//public Image tilebg = new ImageIcon("res/gfx/tiles/tilebg.png").getImage();
	public SpriteSheet tilebg = Assets.getSpriteSheet("tiles/tilebg", 10, 10);
	public BufferedImage worldbg = Assets.getImage("bg/forebackground");
	public BufferedImage worldbg2 = Assets.getImage("bg/backbackground");
	public Star[] stars = new Star[64];
	public CopyOnWriteArrayList<GameObject> objects = new CopyOnWriteArrayList<GameObject>();
	public CopyOnWriteArrayList<Item> items = new CopyOnWriteArrayList<Item>();
	
	public Player player = new Player();
	//public ArrayList<Object> liquidTiles = new ArrayList<Object>();
	
	public World(Game game) {
		this.game = game;
		for(int i = 0; i < 64; i++) {
			stars[i] = new Star(game);
		}
		
		int stackHeight = sizeY/2;
		int slope = 7;
		int roughness = 5;
		Random r = new Random();
		int previousLow = stackHeight;
		
		for(int x = 0; x < sizeX; x++) {
			
			//Slopes
			if(stackHeight + slope > stackHeight) stackHeight-= r.nextInt(roughness);
			if(stackHeight + slope < stackHeight) stackHeight+= r.nextInt(roughness);
			if(slope >= 0 && stackHeight <= previousLow+slope){
				previousLow = stackHeight;
				slope = r.nextInt(32)-16;
				if(slope==0) slope = 1;
				roughness = r.nextInt(1)+1;
			}
			if(slope < 0 && stackHeight >= previousLow+slope){
				previousLow = stackHeight;
				slope = r.nextInt(24)-12;
				if(slope==0) slope=1;
				roughness = r.nextInt(4)+1;
			}
			if(stackHeight + slope > sizeY-64) {
				slope = +12;
				previousLow = stackHeight;
			}
			if(stackHeight + slope < 64) {
				slope = -12;
				previousLow = stackHeight;
			}
			
			for(int y = 0; y < sizeY; y++) {
				
				if(y > stackHeight) {
					tiles[x][y] = new Dirt(x, y, false);
					bgTiles[x][y] = new Dirt(x, y, true);
				}
				if(y == stackHeight) {
					tiles[x][y] = new Grass(x, y, false);
					bgTiles[x][y] = new Dirt(x, y, true);
					int plantTree = r.nextInt(10);
					if(plantTree == 0) objects.add(new OakTree(bgTiles[x][y]));
				}
				if(y < stackHeight) {
					tiles[x][y] = new Empty(x, y, false);
					bgTiles[x][y] = new Empty(x, y, true);
				}
			}
		}
		//for(int y = 0; y < sizeY; y++) {
			//tiles[0][y] = new Desert(0, y, false);
			//tiles[sizeX-1][y] = new Desert(sizeX-1, y, false);
		//}
		
		////////// PLAYER --- TEMPORARY //////////
		player.place(((sizeX/2)*8)+4, getSurface(sizeX/2)*8);
	}
	
	public void update() {
		
		for(int y = renderStartY; y < sizeY && y < renderEndY; y++) {
			for(int x = renderStartX; x < sizeX && x < renderEndX; x++) {
				tiles[x][y].lightLevel = 0;
				bgTiles[x][y].lightLevel = 0;
				tiles[x][y].inTheSun=false;
				bgTiles[x][y].inTheSun=false;
			}
		}
		
		//Updates
		for(int y = renderStartY; y < sizeY && y < renderEndY; y++) {
			for(int x = renderStartX; x < sizeX && x < renderEndX; x++) {
				tiles[x][y].update(this);
				bgTiles[x][y].update(this);
				tiles[x][y].getLight(this);
			}
		}
		
		for(int y = renderStartY; y < sizeY && y < renderEndY; y++) {
			for(int x = renderStartX; x < sizeX && x < renderEndX; x++) {
				bgTiles[x][y].lightLevel = tiles[x][y].lightLevel;
			}
		}
		
		player.update(game, this);
		for(Item i: items) {
			i.update(this);
		}
		
		CameraOffX = player.dx - game.width/2;
		CameraOffY = player.dy - game.height/2 - (game.height/8);
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
		
		renderStartX = CameraOffX/8 -3;
		renderStartY = CameraOffY/8 -3;
		renderEndX = (CameraOffX/8)+(game.width/8)+4;
		renderEndY = (CameraOffY/8)+(game.height/8)+4;
		
		if(renderStartX < 0) renderStartX = 0;
		if(renderStartY < 0) renderStartY = 0;
		if(renderEndX >= sizeX) renderEndX = sizeX;
		if(renderEndY >= sizeY) renderEndY = sizeY;
		
		//int lightRenderStartX = renderStartX -15;
		//int lightRenderStartY = renderStartY -15;
		//int lightRenderEndX = renderEndX +15;
		//int lightRenderEndY = renderEndY +15;
		
		//if(lightRenderStartX < 0) lightRenderStartX = 0;
		//if(lightRenderStartY < 0) lightRenderStartY = 0;
		//if(lightRenderEndX >= sizeX) lightRenderEndX = sizeX;
		//if(lightRenderEndY >= sizeY) lightRenderEndY = sizeY;
		
		// Background //
		
		if(sunlightLevel < 5) {
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, game.width, game.height);
			for(int i = 0; i < 64; i++) {
				stars[i].draw(g);
			}
		}
		int lightLevel = (-256+128) + ((sunlightLevel+1)*48)-1;
		if(lightLevel < 0) lightLevel = 0;
		if(hour > 12 && sunlightLevel < 7) {
			g.setColor(new Color(255,255-32,255-16,lightLevel));
		}else if(hour <= 12 && sunlightLevel < 7) {
			g.setColor(new Color(255,255,255-32,lightLevel));
		}else{
			g.setColor(new Color(255-64,255-32,255,lightLevel));
		}
		g.fillRect(0, 0, game.width, game.height);

		//g.drawImage(worldbg2, 0, game.height/3, null);
		//g.drawImage(worldbg, 0, game.height/3, null);
		
		//Outlines
		for(int y = renderStartY; y < sizeY && y < renderEndY; y++) {
			for(int x = renderStartX; x < sizeX && x < renderEndX; x++) {
				if(bgTiles[x][y].solid && !bgTiles[x][y].getClass().equals(Empty.class) && !tiles[x][y].solid) {
					//g.drawImage(tilebg, tiles[x][y].x-1-CameraOffX, tiles[x][y].y-1-CameraOffY, null);
					tilebg.drawSprite(tiles[x][y].x-1-CameraOffX, tiles[x][y].y-1-CameraOffY, 0, (int)Math.ceil(tiles[x][y].lightLevel), g);
				}
			}
		}
		
		//Draw Background Tiles
		g.setColor(new Color(1,1,1,80));
		for(int y = renderStartY; y < sizeY && y < renderEndY; y++) {
			for(int x = renderStartX; x < sizeX && x < renderEndX; x++) {
				//bgTiles[x][y].update(this);
				if(!bgTiles[x][y].getClass().equals(Empty.class) && !tiles[x][y].solid){
					bgTiles[x][y].draw(g, CameraOffX, CameraOffY);
					g.fillRect(tiles[x][y].x-CameraOffX, tiles[x][y].y-CameraOffY, 8, 8);
				}
			}
		}
		
		// Game Objects //
		for(GameObject o : objects) {
			o.draw(g, CameraOffX, CameraOffY);
		}
		
		// Foreground //
		//Outlines
		for(int y = renderStartY; y < sizeY && y < renderEndY; y++) {
			for(int x = renderStartX; x < sizeX && x < renderEndX; x++) {
				if(tiles[x][y].solid) {
					//g.drawImage(tilebg, tiles[x][y].x-1-CameraOffX, tiles[x][y].y-1-CameraOffY, null);
					tilebg.drawSprite(tiles[x][y].x-1-CameraOffX, tiles[x][y].y-1-CameraOffY, 0, (int)Math.ceil(tiles[x][y].lightLevel), g);
				}
			}
		}
		
		//Player In Water
		if(player.inWater) player.draw(g, CameraOffX, CameraOffY);
		
		//Draw Foreground Tiles
		for(int y = renderStartY; y < sizeY && y < renderEndY; y++) {
			for(int x = renderStartX; x < sizeX && x < renderEndX; x++) {
				tiles[x][y].draw(g, CameraOffX, CameraOffY);
			}
		}
		
		//Player && Items
		if(!player.inWater) player.draw(g, CameraOffX, CameraOffY);
		for(Item i: items) {
			i.worldDraw(g, CameraOffX, CameraOffY);
		}
		
	}
	
	
	
	public int getSurface(int x) {
		for(int y = sizeY-1; y > 0; y--) {
			if(!tiles[x][y].solid && !bgTiles[x][y].solid) {
				return tiles[x][y].yIndex;
			}
		}
		return 0;
	}
	
	public void addItem(Item item, int x, int y) {
		item.x = x;
		item.y = y;
		items.add(item);
	}
}
