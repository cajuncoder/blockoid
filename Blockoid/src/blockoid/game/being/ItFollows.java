package blockoid.game.being;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import blockoid.Assets;
import blockoid.Game;
import blockoid.audio.Audio;
import blockoid.game.World;
import blockoid.game.being.behavior.Behavior;
import blockoid.game.being.behavior.FollowBehavior;
import blockoid.game.item.*;
import blockoid.game.tile.Dirt;
import blockoid.game.tile.Empty;
import blockoid.game.tile.Tile;
import blockoid.game.tile.Water;
import blockoid.graphics.SpriteSheet;
import blockoid.state.GameState;
import blockoid.state.State;

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

