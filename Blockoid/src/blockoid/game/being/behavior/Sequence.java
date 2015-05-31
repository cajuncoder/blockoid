package blockoid.game.being.behavior;

import blockoid.game.World;
import blockoid.game.being.Being;

public class Sequence extends ComplexBehavior {
	public Sequence(Behavior[] behaviors) {
		super(behaviors);
	}

	public int act(Being being, World world, long elapsedTime) {
		Behavior behavior = currentBehavior(being);
		if (!behavior.isRunning(being)) {
			if (behavior.hasFailed(being))
				return failed(being);
			if (behavior.hasSucceeded(being)) {
				if (!hasNextBehavior(being))
					return succeeded(being);
				behavior = nextBehavior(being);
			}
		}

		behavior.act(being, world, elapsedTime);
		if (behavior.isRunning(being))
			return running(being);
		if (behavior.hasFailed(being))
			return failed(being);
		return getState(being);
	}
}
