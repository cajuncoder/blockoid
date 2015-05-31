package blockoid.game.being.behavior;

import blockoid.game.World;
import blockoid.game.being.Being;

public class Attack extends Behavior {
	public Attack() {
		super();
	}
	
	@Override
	public int act(Being being, World world, long elapsedTime) {
		Being target = (Being)being.brain.getMemory("target.enemy").retrieve();
		if (target.isDead())
			return failed(being);
		
		int targetDistance =  (int) Math.sqrt(Math.pow(being.x - target.x, 2) + Math.pow(being.y - target.y, 2));
		if (targetDistance <= being.attackRange) {
			target.knockBack(being, 3);
			target.hurt(2, world);
			return succeeded(being);
		}
		
		if (targetDistance < being.sightRange) {
			if (target.x > being.x) being.aiMoveRight();
			if (target.x < being.x) being.aiMoveLeft();
			return running(being);
		}
		
		return failed(being);
	}
}
