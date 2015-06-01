package blockoid.game.being.behavior.primitive;

import blockoid.game.World;
import blockoid.game.being.Being;
import blockoid.game.being.behavior.Behavior;

public class Loop extends Repeat {
	public Loop(Behavior behavior) {
		super(behavior, Integer.MAX_VALUE);
	}
}
