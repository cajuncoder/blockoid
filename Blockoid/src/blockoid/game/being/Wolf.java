package blockoid.game.being;

import blockoid.Game;
import blockoid.game.World;
import blockoid.game.being.behavior.AttackBehavior;

public class Wolf extends Dog {

	public Wolf(Game game) {
		super(game);
		behavior = new AttackBehavior(this);
	}

	public void act(World world, long elapsedTime) {
		speed = 0.66;
		behavior.act(world, elapsedTime);
	}
}
