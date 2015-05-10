package blockoid.states.playstate.world;

import java.awt.Color;
import java.awt.Graphics2D;

public class Background {

	private World world;
	Color atmosphere = new Color(222,222,255);
	//Color base1 = new Color(169,222,193);
	Color base1 = new Color(169-16,222-16,193-16);
	Color base2 = new Color(109,201,180);
	
	public Background(World world) {
		this.world = world;
	}
	
	public void draw(Graphics2D g) {
		int lightLevel = world.lightLevels[world.hour];
		//Atmosphere
		int x = 0;
		int y = 0;
		int xSize;
		int ySize;
		//g.setColor(atmosphere);
		//g.fillRect(x, y, xSize, ySize);
		int interval = 255/7;
		
		Color clr = modify(atmosphere, -(7-lightLevel)*interval/2, -(7-lightLevel)*interval/2, -(7-lightLevel)*interval/2);
		for(int i = 4; i >= 0; i--) {
			int dy = i*(world.game.height/10);
			int dx = 0;
			ySize = world.game.height/9;
			xSize = world.game.width;
			g.setColor(clr);
			g.fillRect(dx, dy, xSize, ySize);
			//clr = clr.darker();
			clr = modify(clr, -lightLevel, -lightLevel, 0);
		}
		
		//Background
		x = 0;
		y = (world.game.height/2)-1;
		xSize = world.game.width;
		ySize = world.game.height/2;
		g.setColor(modify(base1,-16,-16,-16));
		g.fillRect(x, y, xSize, ySize);
		
		//Foreground
		x = 0;
		y = (world.game.height/2)+1;
		xSize = world.game.width;
		ySize = world.game.height/2;
		g.setColor(base1);
		g.fillRect(x, y, xSize, ySize);
		
		g.setColor(new Color(0,0,0,bound((7-lightLevel)*interval,0,255)));
		g.fillRect(0, 0, world.game.width, world.game.height);
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
	
	private int bound(int value, int min, int max) {
		if(value > max) value = max;
		if(value < min) value = min;
		return value;
	}
}
