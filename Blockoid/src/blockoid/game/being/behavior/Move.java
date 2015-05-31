package blockoid.game.being.behavior;

import blockoid.game.World;
import blockoid.game.being.Being;

public abstract class Move extends Behavior {
	public Move() {
		super();
	}

	@Override
	public int act(Being being, World world, long elapsedTime) {
		int targetX = (Integer)being.brain.getMemory("target.x").retrieve();
		//int targetY = (Integer)being.brain.getMemory("target.y").retrieve();
		
		if (Math.abs(being.x - targetX) < 1)
			return succeeded(being);
		
		setSpeed(being);
		if (being.x < targetX) being.aiMoveRight();
		if (being.x > targetX) being.aiMoveLeft();
		return running(being);
	}
	
	protected abstract void setSpeed(Being being);
}
