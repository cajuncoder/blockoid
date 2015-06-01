package blockoid.game.being.behavior;

import blockoid.game.World;
import blockoid.game.being.Being;

public abstract class RunFromBeing extends Behavior {
	public RunFromBeing() {
		super();
	}
	
	@Override
	public int act(Being being, World world, long elapsedTime) {
		Being target = (Being)being.brain.getMemory(targetName()).retrieve();
		if (target.isDead())
			return failed(being);
		
		int targetDistance =  (int) Math.sqrt(Math.pow(being.x - target.x, 2) + Math.pow(being.y - target.y, 2));
		if (targetDistance <= being.sightRange) {
			being.useMaxSpeed();
			if (target.x > being.x) being.aiMoveLeft();
			if (target.x < being.x) being.aiMoveRight();
			return running(being);
		}
		
		return succeeded(being);
	}
	
	protected String targetName() {
		return "target.being";
	}
}
