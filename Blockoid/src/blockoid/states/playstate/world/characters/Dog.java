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

public class Dog extends Being {
	private Behavior behavior;
	
	public Dog(Game game) {
		super(game);
		behavior = new WanderFollowBehavior(this);
		sprite = Assets.getSpriteSheet("characters/dog20x14", 20, 14);
		height = 14;
		speed = 0.66;
		idleRight = new int[]{5};
		idleLeft = new int[]{0};
		walkRight = new int[]{6, 7, 6, 8};
		walkLeft = new int[]{1, 2, 1, 3};
		jumpRight = new int[]{9};
		jumpLeft = new int[]{4};
	}
	
	public void act(World world, long elapsedTime) {
		behavior.act(world, elapsedTime);
	}
}

