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
	SpriteSheet[] foreground;
	SpriteSheet[] background;
	
	public Background(World world) {
		this.world = world;
		for(int i = 0; i < stars.length; i++) {
			stars[i] = new Star(world.game);
		}
		foreground = new SpriteSheet[(((world.sizeX*8) + (1024))/tile.spriteSizeX)/3];
		//background = new SpriteSheet[(((world.sizeX*8) + (1024*2))/bgTile.spriteSizeX)/1];
		background = new SpriteSheet[1024*2/bgTile.spriteSizeX];
		for(int i = 0; i < foreground.length; i++) {
			foreground[i] = tile;
		}
		for(int i = 0; i < background.length; i++) {
			background[i] = bgTile;
		}
	}
	
	public void draw(Graphics2D g, int CameraOffX, int CameraOffY) {
		int lightLevel = world.sunlightLevel;
		//Atmosphere
		int x = 0;
		int y = 0;
		int xSize;
		int ySize;
		//g.setColor(atmosphere);
		//g.fillRect(x, y, xSize, ySize);
		
		int interval = 255/7;
		//Color clr = modify(atmosphere, -(7-lightLevel)*interval, -(7-lightLevel)*interval, -(7-lightLevel)*interval);
		Color clr = modify(atmosphere, lightLevel);
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
		
		//Background
		//x = 0;
		//y = (world.game.height/2)-1;
		//xSize = world.game.width;
		//ySize = world.game.height/2;
		//g.setColor(modify(base1,-16,-16,-16));
		//g.fillRect(x, y, xSize, ySize);
		
		//Foreground
		//x = 0;
		//y = (world.game.height/2)+1;
		//xSize = world.game.width;
		//ySize = world.game.height/2;
		//g.setColor(base1);
		//g.fillRect(x, y, xSize, ySize);
		
		//g.setColor(new Color(0,0,0,bound((7-lightLevel)*interval,0,255)));
		//g.fillRect(0, 0, world.game.width, world.game.height);
		int foreOffX = CameraOffX/3;
		int backOffX = 0;//CameraOffX/4;

		for(int i = 0; i < background.length; i++) {
			background[i].drawSprite((i*background[i].spriteSizeX)-backOffX, world.game.height/2-48, 0, world.sunlightLevel, g);
		}
		for(int i = 0; i < foreground.length; i++) {
			foreground[i].drawSprite((i*foreground[i].spriteSizeX)-foreOffX, world.game.height/2-48, 0, world.sunlightLevel, g);
		}
		Color color = new Color(foreground[0].sheets[world.sunlightLevel].getRGB(127, 127), true);
		g.setColor(color);
		g.fillRect(0, world.game.height/2+tile.spriteSizeY-48, world.game.width, world.game.height/2);
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
