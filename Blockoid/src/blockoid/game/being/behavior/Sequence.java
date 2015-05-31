package blockoid.game.being.behavior;

import blockoid.game.World;
import blockoid.game.being.Being;

public class Sequence extends CompositeBehavior {
	public Sequence(Behavior[] behaviors) {
		super(behaviors);
	}

//	public int act(Being being, World world, long elapsedTime) {
//		Behavior behavior = currentBehavior(being);
//		if (!behavior.isRunning(being)) {
//			if (behavior.hasFailed(being))
//				return failed(being);
//			if (behavior.hasSucceeded(being)) {
//				if (!hasNextBehavior(being))
//					return succeeded(being);
//				behavior = nextBehavior(being);
//			}
//		}
//
//		behavior.act(being, world, elapsedTime);
//		if (behavior.isRunning(being))
//			return running(being);
//		if (behavior.hasFailed(being))
//			return failed(being);
//		return getState(being);
//	}
	
	public int act(Being being, World world, long elapsedTime) {
		Behavior behavior = null;
		int i = 0;
		for (i = 0; i < behaviors.length; i++) {
			behavior = behaviors[i];
			if (!behavior.isRunning(being)) {
				if (behavior.hasFailed(being))
					return failed(being);
				if (behavior.hasSucceeded(being))
					continue;
			}
	
			behavior.act(being, world, elapsedTime);
			if (behavior.isRunning(being))
				return running(being);
			if (behavior.hasFailed(being))
				return failed(being);
			break;
		}
		
		if (i == behaviors.length-1 && behavior.hasSucceeded(being))
			return succeeded(being);
		
		return getState(being);
	}
}
