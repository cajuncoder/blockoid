package blockoid.states.playstate.world.characters;

import blockoid.Game;
import blockoid.states.playstate.world.World;

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
