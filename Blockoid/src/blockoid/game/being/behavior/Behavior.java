package blockoid.game.being.behavior;

import blockoid.Game;
import blockoid.game.World;
import blockoid.game.being.Being;

public interface Behavior {
	public void attachBeing(Being being);
	public void act(World world, long elapsedTime);
}
