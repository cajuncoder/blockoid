package blockoid.game.being.behavior;

import blockoid.game.World;
import blockoid.game.being.Being;

public class Selector extends CompositeBehavior {
	public Selector(Behavior[] behaviors) {
		super(behaviors);
	}
	
	public int act(Being being, World world, long elapsedTime) {
		Behavior behavior = null;
		int i = 0;
		for (i = 0; i < behaviors.length; i++) {
			behavior = behaviors[i];
			if (!behavior.isRunning(being)) {
				if (behavior.hasSucceeded(being))
					return succeeded(being);
				if (behavior.hasFailed(being))
					continue;
			}

			behavior.act(being, world, elapsedTime);
			if (behavior.isRunning(being))
				return running(being);
			if (behavior.hasSucceeded(being))
				return succeeded(being);
			break;
		}
		
		if (i == behaviors.length-1 && behavior.hasFailed(being))
			return failed(being);
		
		return getState(being);
	}
}
