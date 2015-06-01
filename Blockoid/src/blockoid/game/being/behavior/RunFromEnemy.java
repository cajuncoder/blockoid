package blockoid.game.being.behavior;

import blockoid.game.World;
import blockoid.game.being.Being;

public class RunFromEnemy extends RunFromBeing {
	public RunFromEnemy() {
		super();
	}

	protected String targetName() {
		return "target.enemy";
	}
}
