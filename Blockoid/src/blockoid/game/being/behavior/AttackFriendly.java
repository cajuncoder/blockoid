package blockoid.game.being.behavior;

import blockoid.game.World;
import blockoid.game.being.Being;

public class AttackFriendly extends AttackBeing {
	public AttackFriendly() {
		super();
	}
	
	protected String targetName() {
		return "target.friendly";
	}
}
