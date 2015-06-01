package blockoid.game.being.behavior;

import blockoid.game.World;
import blockoid.game.being.Being;

public class AttackedByFriendly extends AttackedByBeing {
	public AttackedByFriendly() {
		super();
	}
	
	protected String targetName() {
		return "target.friendly";
	}
}
