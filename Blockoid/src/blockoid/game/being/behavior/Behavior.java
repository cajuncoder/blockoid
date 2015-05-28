package blockoid.game.being.behavior;

import java.util.HashMap;

import blockoid.Game;
import blockoid.game.World;
import blockoid.game.being.Being;

abstract public class Behavior {
	protected Being being;
	protected HashMap<String, Behavior> behaviors = new HashMap<String, Behavior>();
	
	public Behavior(Being being) {
		attachBeing(being);
	}
	
	public void attachBeing(Being being) {
		this.being = being;
	}
	
	abstract public void act(World world, long elapsedTime);
}
