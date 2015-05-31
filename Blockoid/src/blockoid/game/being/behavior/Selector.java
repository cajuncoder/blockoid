package blockoid.game.being.behavior;

import blockoid.game.World;
import blockoid.game.being.Being;

public class Selector extends ComplexBehavior {
	public Selector(Behavior[] behaviors) {
		super(behaviors);
	}
	
	public int act(Being being, World world, long elapsedTime) {
		Behavior behavior = currentBehavior(being);
		if (!behavior.isRunning(being)) {
			if (behavior.hasSucceeded(being))
				return succeeded(being);
			if (behavior.hasFailed(being)) {
				if (!hasNextBehavior(being))
					return failed(being);
				behavior = nextBehavior(being);
			}
		}

		behavior.act(being, world, elapsedTime);
		if (behavior.isRunning(being))
			return running(being);
		if (behavior.hasSucceeded(being))
			return succeeded(being);
		return getState(being);
	}
}
