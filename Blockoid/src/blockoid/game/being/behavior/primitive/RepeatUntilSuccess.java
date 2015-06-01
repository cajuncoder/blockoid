package blockoid.game.being.behavior.primitive;

import blockoid.game.being.Being;
import blockoid.game.being.behavior.Behavior;

public class RepeatUntilSuccess extends RepeatUntil {
	public RepeatUntilSuccess(Behavior behavior) {
		super(behavior);
	}

	@Override
	protected boolean conditionMet(Being being) {
		return behavior.hasSucceeded(being);
	}
}
