package blockoid.game.being.behavior;

import blockoid.game.World;
import blockoid.game.being.Being;

public class AttackEnemy extends AttackBeing {
	public AttackEnemy() {
		super();
	}
	
	protected String targetName() {
		return "target.enemy";
	}
}
