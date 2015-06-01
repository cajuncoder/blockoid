package blockoid.game.being;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import blockoid.Assets;
import blockoid.Game;
import blockoid.audio.Audio;
import blockoid.game.World;
import blockoid.game.being.behavior.Behavior;
import blockoid.game.being.behavior.Rest;
import blockoid.game.being.behavior.Wander;
import blockoid.game.being.behavior.primitive.Loop;
import blockoid.game.being.behavior.primitive.Sequence;
import blockoid.game.item.*;
import blockoid.game.tile.Dirt;
import blockoid.game.tile.Empty;
import blockoid.game.tile.Tile;
import blockoid.game.tile.Water;
import blockoid.graphics.SpriteSheet;
import blockoid.state.GameState;
import blockoid.state.State;

public class Dog extends Being {
	public Dog(Game game) {
		super(game);
		behavior = new Loop(new Sequence(new Behavior[]{
			new Wander(),
			new Rest(World.SECOND*2)
		}));
		sprite = Assets.getSpriteSheet("characters/dog20x14", 20, 14);
		height = 14;
		idleRight = new int[]{5};
		idleLeft = new int[]{0};
		walkRight = new int[]{6, 7, 6, 8};
		walkLeft = new int[]{1, 2, 1, 3};
		jumpRight = new int[]{9};
		jumpLeft = new int[]{4};
		minSpeed = 0.66;
		maxSpeed = 1.0;
		//maxSpeed = minSpeed;
		maxDamage = 5;
	}
	
	public void act(World world, long elapsedTime) {
		behavior.act(this, world, elapsedTime);
	}
}

