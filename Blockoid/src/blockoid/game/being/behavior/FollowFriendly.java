package blockoid.game.being.behavior;

import blockoid.game.being.Being;

public class FollowFriendly extends FollowBeing {
	@Override
	protected String targetName() {
		return "target.friendly";
	}
}
