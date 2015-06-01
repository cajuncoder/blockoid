package blockoid.game.being.behavior;

import blockoid.game.World;
import blockoid.game.being.Being;

public class AttackedByBeing extends Behavior {
	public AttackedByBeing() {
		super();
	}
	
	@Override
	public int act(Being being, World world, long elapsedTime) {
		Being target = (Being)being.brain.getMemory(targetName()).retrieve();
		if (target.isDead())
			return failed(being);
		
		if (being.attackedBy(target))
			return succeeded(being);
		
		return failed(being);
	}
	
	protected String targetName() {
		return "target.being";
	}
}
