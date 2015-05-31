package blockoid.game.being.behavior;

import blockoid.game.being.Being;

public class LocateNearestEnemy extends LocateNearestBeing {
	protected boolean meetsCriteria(Being being, Being otherBeing) {
		return otherBeing.isEnemy(being);
	}
	
	protected String rememberAs() {
		return "target.enemy";
	}
}
