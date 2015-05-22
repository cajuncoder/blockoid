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

public class ItFollows extends Creature {
	private static final int MAX_SEPARATION = 25;
	
	private double oldDistance = 0;
	
	public ItFollows() {
		super();
		//Assets.getAudio("itfollows").play(true);
	}
	
	public void act(Game game, World world) {
		double playerX = world.player.x;
		double playerY = world.player.y;
		
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

