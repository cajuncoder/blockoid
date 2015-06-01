package blockoid.game.being.behavior;

import blockoid.game.World;
import blockoid.game.being.Being;

public class RunFromFriendly extends RunFromBeing {
	public RunFromFriendly() {
		super();
	}

	protected String targetName() {
		return "target.friendly";
	}
}
