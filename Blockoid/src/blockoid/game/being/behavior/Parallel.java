package blockoid.game.being.behavior;

import blockoid.game.World;
import blockoid.game.being.Being;

public class Parallel extends CompositeBehavior {
	public Parallel(Behavior[] behaviors) {
		super(behaviors);
	}
	
	protected int failed(Being being) {
		setState(being, Behavior.FAILED);
		return Behavior.FAILED;
	}
	
	protected int running(Being being) {
		setState(being, Behavior.RUNNING);
		return Behavior.RUNNING;
	}
	
	protected int succeeded(Being being) {
		setState(being, Behavior.SUCCEEDED);
		return Behavior.SUCCEEDED;
	}
	
	public int act(Being being, World world, long elapsedTime) {
		boolean failed = false;
		boolean succeeded = true;
		for (int i = 0; i < behaviors.length; i++) {
			Behavior behavior = behaviors[i];
			behavior.act(being, world, elapsedTime);
			if (behavior.hasFailed(being))
				failed = true;
			succeeded = succeeded && behavior.hasSucceeded(being);
		}
		
		if (failed)
			return failed(being);
		if (succeeded)
			return succeeded(being);
		return running(being);
	}
}
