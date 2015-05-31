package blockoid.game.being.behavior;

import blockoid.game.being.Being;

public class FollowFriendly extends Follow {
	@Override
	protected String targetName() {
		return "target.friendly";
	}
}
