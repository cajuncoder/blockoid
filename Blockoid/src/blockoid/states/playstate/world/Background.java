package blockoid.states.playstate.world;

import java.awt.Color;
import java.awt.Graphics2D;

import blockoid.Assets;
import blockoid.graphics.SpriteSheet;

public class Background {

	private World world;
	Color atmosphere = new Color(222,222,255);
	//Color base1 = new Color(169,222,193);
	Color base1 = new Color(169-16,222-16,193-16);
	Color base2 = new Color(109,201,180);
	int nOfStars = 96;
	Star[] stars = new Star[nOfStars];
	SpriteSheet bgTile = Assets.getSpriteSheet("bg/backgroundTile2", 64, 128);
	SpriteSheet tile = Assets.getSpriteSheet("bg/foregroundTile3", 128, 128);
	//SpriteSheet[] foreground;
	//SpriteSheet[] background;
	
	public Background(World world) {
		this.world = world;
		for(int i = 0; i < stars.length; i++) {
			stars[i] = new Star(world.game);
		}
		//foreground = new SpriteSheet[(((world.sizeX*8) + (1024))/tile.spriteSizeX)/3];
		//background = new SpriteSheet[(((world.sizeX*8) + (1024*2))/bgTile.spriteSizeX)/1];
		//background = new SpriteSheet[1024*2/bgTile.spriteSizeX];
		//for(int i = 0; i < foreground.length; i++) {
		//	foreground[i] = tile;
		//}
		//for(int i = 0; i < background.length; i++) {
		//	background[i] = bgTile;
		//}
	}
	
	Color atmosclr = atmosphere;
	int oldLightLevel = -1;
	Color baseclr = Color.BLACK;
	public void draw(Graphics2D g, int CameraOffX, int CameraOffY) {
		int lightLevel = world.sunlightLevel;
		//Atmosphere
		int x = 0;
		int y = 0;
		int xSize;
		int ySize;
		
		int interval = 255/7;
		//Color clr = modify(atmosphere, -(7-lightLevel)*interval, -(7-lightLevel)*interval, -(7-lightLevel)*interval);
		if(oldLightLevel!=lightLevel) atmosclr = modify(atmosphere, lightLevel);
		Color clr = atmosclr;
		for(int i = 4; i >= 0; i--) {
			
			int dy = i*(world.game.height/10);
			int dx = 0;
			ySize = world.game.height/8;
			if(i==4) ySize = ySize + world.game.height/2;
			xSize = world.game.width;
			g.setColor(clr);
			g.fillRect(dx, dy, xSize, ySize);
			//clr = clr.darker();
			clr = modify(clr, -lightLevel, -lightLevel, 0);
		}
		if(lightLevel < 3.9) {
			if(lightLevel >= 3) {g.setColor(new Color(255,255,255,55));} else {g.setColor(Color.WHITE);}
			for(int i = 0; i < stars.length; i++) {
				
				stars[i].draw(g);
				if(stars[i].x > world.game.width) System.out.println("Star out of bounds");
			}
		}
		
		int foreOffX = CameraOffX/3;
		int backOffX = 0;//CameraOffX/4;

		//for(int i = 0; i < background.length; i++) {
		int scrnW = world.game.width;
		int tileW = tile.spriteSizeX;
		int bgTileW = bgTile.spriteSizeX;
		for(int i = backOffX/bgTileW; i <= (backOffX/bgTileW)+(scrnW/bgTileW)+1; i++) {
			bgTile.drawSprite((i*bgTileW)-backOffX, world.game.height/2-48, 0, world.sunlightLevel, g);
		}
		for(int i = foreOffX/tileW; i <= (foreOffX/tileW)+(scrnW/tileW)+1; i++) {
			tile.drawSprite((i*tileW)-foreOffX, world.game.height/2-48, 0, world.sunlightLevel, g);
		}
		if(lightLevel!=oldLightLevel) baseclr = new Color(tile.sheets[world.sunlightLevel].getRGB(127, 127), true);
		g.setColor(baseclr);
		g.fillRect(0, world.game.height/2+tile.spriteSizeY-48, world.game.width, world.game.height/2);
		
		oldLightLevel = lightLevel;
	}
	
	
	private Color modify(Color color, int r, int g, int b) {
		int red = color.getRed()+r;
		int green = color.getGreen()+g;
		int blue = color.getBlue()+b;
		red = bound(red, 0, 255);
		green = bound(green, 0, 255);
		blue = bound(blue, 0, 255);
		color = new Color(red, green, blue);
		return color;
	}
	
	private Color modify(Color color, int shade) {
		int[] greenMultis = {4, 8, 18, 29, 42, 56, 80, 100, 133};
		int[] blueMultis = {4, 8, 25, 36, 45, 59, 75, 100, 133};
		int[] redMultis = {4, 8, 18, 29, 46, 72, 89, 100, 133};
	            int newRed = color.getRed()*redMultis[shade]/100;
	            int newGreen = color.getGreen()*greenMultis[shade]/100;
	            int newBlue = color.getBlue()*blueMultis[shade]/100;
	            int red = bound(newRed,0,255);
	            int green = bound(newGreen,0,255);
	            int blue = bound(newBlue,0,255);
	            int alpha = color.getAlpha();
	            Color newColor = new Color(red,green,blue,alpha);
	            return newColor;
	}
	
	private int bound(int value, int min, int max) {
		if(value > max) value = max;
		if(value < min) value = min;
		return value;
	}
}
