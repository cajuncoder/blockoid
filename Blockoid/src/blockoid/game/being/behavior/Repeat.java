package blockoid.game.being.behavior;

import blockoid.game.World;
import blockoid.game.being.Being;

public class Repeat extends Decorator {
	int count;
	
	public Repeat(Behavior behavior, int count) {
		super(behavior);
		this.count = count;
	}

	@Override
	public int act(Being being, World world, long elapsedTime) {
		if (hasFailed(being) || hasSucceeded(being)) {
			if (iteration(being) == count)
				return getState(being);
			reset(being);
		}
		storeObject(being, "iteration", iteration(being) + 1);
		return behavior.act(being, world, elapsedTime);
	}
	
	protected int iteration(Being being) {
		Object iteration = retrieveObject(being, "iteration");
		return iteration == null ? 0 : (Integer)iteration;
	}
}
