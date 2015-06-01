package blockoid.game.being.behavior;

import blockoid.game.World;
import blockoid.game.being.Being;

public class AttackBeing extends Behavior {
	public AttackBeing() {
		super();
	}
	
	@Override
	public int act(Being being, World world, long elapsedTime) {
		Being target = (Being)being.brain.getMemory(targetName()).retrieve();
		if (target.isDead())
			return failed(being);
		
		int targetDistance =  (int) Math.sqrt(Math.pow(being.x - target.x, 2) + Math.pow(being.y - target.y, 2));
		if (targetDistance <= being.attackRange) {
			world.fight(being, target);
			return succeeded(being);
		}
		
		if (targetDistance < being.sightRange) {
			if (target.x > being.x) being.aiMoveRight();
			if (target.x < being.x) being.aiMoveLeft();
			return running(being);
		}
		
		return failed(being);
	}
	
	protected String targetName() {
		return "target.being";
	}
}
