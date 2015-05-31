package blockoid.game.being.behavior;

import blockoid.game.World;
import blockoid.game.being.Being;
import blockoid.game.being.Memory;
import blockoid.game.tile.Tile;

public abstract class PlaceTile extends Behavior {
	public PlaceTile() {
		super();
	}

	@Override
	public int act(Being being, World world, long elapsedTime) {
		world.tiles[getX(being)][getY(being)] = getTile(being);
		return succeeded(being);
	}

	protected abstract Tile getTile(Being being);
	protected abstract int getX(Being being);
	protected abstract int getY(Being being);
}
