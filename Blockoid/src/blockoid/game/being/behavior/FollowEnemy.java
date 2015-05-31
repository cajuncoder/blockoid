package blockoid.game.being.behavior;

import blockoid.game.being.Being;

public class FollowEnemy extends Follow {
	@Override
	protected String targetName() {
		return "target.enemy";
	}
}
