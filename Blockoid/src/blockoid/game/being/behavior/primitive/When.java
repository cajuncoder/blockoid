package blockoid.game.being.behavior.primitive;

import blockoid.game.World;
import blockoid.game.being.Being;
import blockoid.game.being.behavior.Behavior;

public class When extends Behavior {
	protected Behavior condition, then, otherwise;
	
	public When(Behavior condition, Behavior then, Behavior otherwise) {
		this.condition = condition;
		this.then = then;
		this.otherwise = otherwise;
	}
	
	@Override
	public int act(Being being, World world, long elapsedTime) {
		condition.act(being, world, elapsedTime);
		Behavior action = condition.hasSucceeded(being) ? then : otherwise;
		action.act(being, world, elapsedTime);
		if (action.isRunning(being))
			return running(being);
		if (action.hasSucceeded(being))
			return succeeded(being);
		if (action.hasFailed(being))
			return failed(being);
		return getState(being);
	}
}
