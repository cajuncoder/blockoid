package blockoid.game.being.behavior;

import blockoid.game.World;
import blockoid.game.being.Being;

public class AttackedByEnemy extends AttackedByBeing {
	public AttackedByEnemy() {
		super();
	}
	
	protected String targetName() {
		return "target.enemy";
	}
}
