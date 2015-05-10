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

public class Player extends Character {	
	public Player() {
		super();
		
		inventory.addItem(new PickAxe());
		inventory.addItem(new DirtBlock(16));
		inventory.addItem(new DirtBlock());
		inventory.addItem(new DirtBlock(9));
		inventory.addItem(new DirtBlock());
		inventory.addItem(new DirtBlock());
		inventory.addItem(new DirtBlock(8));
		inventory.addItem(new DirtBlock());
	}
	
	public void move(Game game, World world) {
		//Controls
		if(game.keyboard.right){
			moveRight();
		}
		if(game.keyboard.left){
			moveLeft();
		}
	
		if(game.keyboard.space && timeOnGround>1 || game.keyboard.space && yVel < 0 && timeInAir < 14){
			jump();
		}
	}	

}
