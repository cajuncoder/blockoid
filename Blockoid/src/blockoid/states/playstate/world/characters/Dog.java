package blockoid.states.playstate.world.characters;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

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

public class Dog extends Creature {
	private static final int MAX_SEPARATION = 25;
	
	private double oldDistance = 0;
	private boolean headingLeft;
	private double decision;
	private long counter;
	
	public Dog() {
		super();
		decision = 0;
		counter = 0;
	}
	
	public void act(Game game, World world) {
		double playerX = world.player.x;
		double playerY = world.player.y;
		
		boolean playerNearby = Math.abs(playerX - x) < 25 && (playerY == y || playerY == y + 1);
//		if (playerNearby) {
//			follow(world.player);
//		} else {
			if (counter % 100 == 0)
				decision = Math.random();
			
			if (decision < 0.5) {
				// change heading
				headingLeft = !headingLeft;
			} else if (decision < 0.95) {
				// walk
				if (headingLeft) 
					moveLeft();
				else
					moveRight();
			} else if (decision < 1.0) {
				jump();
			}
//		}
			
		counter++;
	}
	
	public void follow(Player player) {
		double playerX = player.x;
		double playerY = player.y;
		double distance = Math.abs(playerX - x);
		if (distance > MAX_SEPARATION) {
			if (playerX > x) moveRight();
			if (playerX < x) moveLeft();
			boolean gapWidening = Math.abs(distance - oldDistance) > 0;
			if (gapWidening) jump();
		}
		oldDistance = distance;
	}
}

