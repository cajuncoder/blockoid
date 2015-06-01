package blockoid.game.being.behavior.primitive;

import blockoid.game.being.Being;
import blockoid.game.being.behavior.Behavior;

public class RepeatUntilFailure extends RepeatUntil {
	public RepeatUntilFailure(Behavior behavior) {
		super(behavior);
	}

	@Override
	protected boolean conditionMet(Being being) {
		return behavior.hasFailed(being);
	}
}