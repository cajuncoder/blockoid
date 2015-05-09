package blockoid.states.playstate;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import blockoid.Game;
import blockoid.states.GameState;
import blockoid.states.playstate.world.tiles.Dirt;
import blockoid.states.playstate.world.tiles.Empty;
import blockoid.states.playstate.world.tiles.Tile;
import blockoid.states.playstate.world.tiles.Water;
import blockoid.states.playstate.world.Assets;
import blockoid.states.playstate.world.World;

public class PlayState extends GameState {

	//MOUSE OVER STRING
	private String mOverStr = "";
	
	public Assets assets = new Assets();
	public World world;
	private Font font = new Font("Console", Font.PLAIN, 9);
	public GUI gui;
	
	public PlayState(Game game) {
		super(game);
		
		//Loading screen
		Graphics g = game.bufferGraphics;
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, game.width, game.height);
		g.setColor(Color.WHITE);
		g.drawString("Loading...", 0, 12);
		game.draw();
		
		world = new World(game);
		gui = new GUI(this);

	}

	public void update() {
		world.update();
		gui.update();
		//if(player==null) {
			//if(game.keyboard.left) world.CameraOffX-=1;
			//if(game.keyboard.right) world.CameraOffX+=1;
			//if(game.keyboard.up) world.CameraOffY-=1;
			//if(game.keyboard.down) world.CameraOffY+=1;
		//}else{
		//	player.update(game, world);
		//	world.CameraOffX = player.dx - game.WIDTH/2;
		//	world.CameraOffY = player.dy - game.HEIGHT/2 - (game.HEIGHT/8);
		//}
		
		//Paint Water!!!!!
		int brushX = (game.mouseMotion.x+world.CameraOffX)/8;
		if(brushX < 0) brushX = 0; if(brushX>=world.sizeX) brushX = world.sizeX-1;
		int brushY = (game.mouseMotion.y+world.CameraOffY)/8;
		if(brushY < 0) brushY = 0; if(brushY>=world.sizeY) brushY = world.sizeY-1;
		Tile brushTile = world.tiles[brushX][brushY];
		Water waterTile = null;
		
		int waterMass = 0;
		if(brushTile.getClass().equals(Water.class)) waterTile = (Water)brushTile;
		if(waterTile!=null) waterMass = waterTile.mass;
		
		mOverStr = world.tiles[brushX][brushY].getClass().getSimpleName() + ":" + waterMass;
		//if(game.mouse.holdL) world.tiles[brushX][brushY] = new Water(brushX, brushY);
		//if(game.mouse.holdR) world.tiles[brushX][brushY] = new Empty(brushX, brushY);
	}
	
	public void draw(Graphics2D g) {
		g.setFont(font);
		world.draw(g);
		gui.draw(g);
		//if(player!=null) player.draw(g, world.CameraOffX, world.CameraOffY);
		//g.drawString(mOverStr, 0, 14);
	}
}
