package blockoid.game.being.behavior;

import java.util.ArrayList;

import blockoid.game.World;
import blockoid.game.being.behavior.Behavior;
import blockoid.game.being.Being;
import blockoid.game.being.Memory;
import blockoid.game.item.Item;
import blockoid.game.tile.Tile;

public class Perceive extends Behavior {
	public int act(Being being, World world, long elapsedTime) {
		// TODO(griffy) if can't see, return Behavior.FAILED;
		// Look for objects, beings, and tiles?
		ArrayList<Item> items = new ArrayList<Item>();
		for (Item item : world.items) {
			if (Math.abs(being.x - item.x) <= being.sightRange &&
				Math.abs(being.y - item.y) <= being.sightRange)
				items.add(item);
		}
		being.brain.addMemory("items", new Memory<ArrayList<Item>>(items));
		
		ArrayList<Being> beings = new ArrayList<Being>();
		for (Being b : world.allBeings()) {
			if (b.hashCode() == being.hashCode()) continue;
			if (Math.abs(being.x - b.x) <= being.sightRange &&
				Math.abs(being.y - b.y) <= being.sightRange)
				beings.add(b);
		}
		being.brain.addMemory("beings", new Memory<ArrayList<Being>>(beings));

		return succeeded(being);
	}
}
