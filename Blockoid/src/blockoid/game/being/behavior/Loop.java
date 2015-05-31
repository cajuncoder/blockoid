package blockoid.game.being.behavior;

import blockoid.game.World;
import blockoid.game.being.Being;

public class Loop extends Repeat {
	public Loop(Behavior behavior) {
		super(behavior, Integer.MAX_VALUE);
	}
}
