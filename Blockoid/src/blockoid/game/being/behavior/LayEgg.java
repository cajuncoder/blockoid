package blockoid.game.being.behavior;

import blockoid.game.being.Being;
import blockoid.game.tile.SpiderEgg;
import blockoid.game.tile.Tile;

public class LayEgg extends PlaceTile {
	protected Tile getTile(Being being) {
		return new SpiderEgg(getX(being), getY(being), false);
	}
	
	protected int getX(Being being) {
		return being.oldXTile;
	}
	
	protected int getY(Being being) {
		return being.oldYTile;
	}
}
