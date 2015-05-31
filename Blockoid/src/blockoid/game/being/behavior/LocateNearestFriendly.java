package blockoid.game.being.behavior;

import blockoid.game.being.Being;

public class LocateNearestFriendly extends LocateNearestBeing {
	protected boolean meetsCriteria(Being being, Being otherBeing) {
		return otherBeing.isFriendly(being);
	}
	
	protected String rememberAs() {
		return "target.friendly";
	}
}
