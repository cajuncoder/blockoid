package blockoid.game.being.behavior.primitive;

import blockoid.game.World;
import blockoid.game.being.Being;
import blockoid.game.being.behavior.Behavior;

public abstract class RepeatUntil extends Decorator {
	public RepeatUntil(Behavior behavior) {
		super(behavior);
	}

	@Override
	public int act(Being being, World world, long elapsedTime) {
		if (conditionMet(being)) {
			return getState(being);
		}
		
		if (hasFailed(being) || hasSucceeded(being)) {
			reset(being);
		}
		
		behavior.act(being, world, elapsedTime);
		return running(being);
	}
	
	protected abstract boolean conditionMet(Being being);
}
