package blockoid.game.being.behavior;

import blockoid.game.being.Being;

public class FollowEnemy extends FollowBeing {
	@Override
	protected String targetName() {
		return "target.enemy";
	}
}
