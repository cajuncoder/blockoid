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
import blockoid.game.being.behavior.FollowBeing;
import blockoid.game.being.behavior.FollowEnemy;
import blockoid.game.being.behavior.LocateNearestEnemy;
import blockoid.game.being.behavior.Perceive;
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

public class ItFollows extends Being {
	public ItFollows(Game game) {
		super(game);
		sightRange = 1000;
		behavior = new Loop(new Sequence(new Behavior[]{
			new Perceive(),
			new LocateNearestEnemy(),
			new FollowEnemy()
		}));
	}
	
	public void act(World world, long elapsedTime) {
		behavior.act(this, world, elapsedTime);
	}
}

