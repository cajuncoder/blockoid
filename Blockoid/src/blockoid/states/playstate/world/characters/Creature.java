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

public class Creature extends Character {
	
	public Creature() {
		super();
	}
	
	public void act(Game game, World world) {
		double playerX = world.player.x;
		double playerY = world.player.y;
		
		if (x < playerX) {
			moveRight();
		} else if (x > playerX) {
			moveLeft();
		}
		
		jump();
	}
	

}

