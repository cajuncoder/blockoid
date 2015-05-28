package blockoid.game.being;

import blockoid.Game;
import blockoid.game.World;
import blockoid.game.being.behavior.AttackBehavior;

public class Wolf extends Dog {

	public Wolf(Game game) {
		super(game);
		behavior = new AttackBehavior(this);
		maxSpeed = 1.5;
	}

	public void act(World world, long elapsedTime) {
		Being enemy = world.nearestEnemy(this);
		if (enemy == null)
			return;
		behavior.act(world, elapsedTime);
	}
}
