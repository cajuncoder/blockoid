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
			int dist = (int) Math.sqrt(Math.pow(being.x - item.x, 2) + Math.pow(being.y - item.y, 2));
			if (dist <= being.sightRange)
				items.add(item);
		}
		being.brain.addMemory("items", new Memory<ArrayList<Item>>(items));
		
		ArrayList<Being> beings = new ArrayList<Being>();
		for (Being b : world.allBeings()) {
			if (b.hashCode() == being.hashCode()) continue;
			int dist = (int) Math.sqrt(Math.pow(being.x - b.x, 2) + Math.pow(being.y - b.y, 2));
			if (dist <= being.sightRange)
				beings.add(b);
		}
		System.out.println(beings);
		being.brain.addMemory("beings", new Memory<ArrayList<Being>>(beings));

		return succeeded(being);
	}
}
