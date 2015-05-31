package blockoid.game.being.behavior;

import blockoid.Game;
import blockoid.game.World;
import blockoid.game.being.Being;
import blockoid.game.being.Memory;

public class Wander extends Move {
	public Wander() {
		super();
	}

	@Override
	public int act(Being being, World world, long elapsedTime) {
		Memory targetX = being.brain.getMemory("target.x");
		if (!isRunning(being) || targetX == null) {
			int delta = (int)(Math.random() * (being.wanderRange + 1));
			if (Math.random() < 0.5) delta = -delta;
			int x = (int) (being.x + delta);
			being.brain.addMemory("target.x", new Memory<Integer>(x));
		}

		return super.act(being, world, elapsedTime);
	}

	@Override
	protected void setSpeed(Being being) {
		being.useMinSpeed();
	}
}
