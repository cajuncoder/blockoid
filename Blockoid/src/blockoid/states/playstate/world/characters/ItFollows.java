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

public class ItFollows extends Being {
	private Behavior behavior;
	
	public ItFollows(Game game) {
		super(game);
		behavior = new FollowBehavior(this);
	}
	
	public void act(World world, long elapsedTime) {
		behavior.act(world, elapsedTime);
	}
}

