package blockoid.states.playstate.world.characters;

import blockoid.Game;
import blockoid.states.playstate.world.World;

public interface Behavior {
	public void attachBeing(Being being);
	public void act(World world, long elapsedTime);
}
