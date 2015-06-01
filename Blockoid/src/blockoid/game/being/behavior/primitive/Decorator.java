package blockoid.game.being.behavior.primitive;

import blockoid.game.being.Being;
import blockoid.game.being.behavior.Behavior;

public abstract class Decorator extends Behavior {
	protected Behavior behavior;
	
	public Decorator(Behavior behavior) {
		this.behavior = behavior;
	}
	
	public int failed(Being being) {
		return behavior.failed(being);
	}
	
	public int running(Being being) {
		return behavior.running(being);
	}
	
	public int succeeded(Being being) {
		return behavior.succeeded(being);
	}
	
	public int reset(Being being) {
		return behavior.reset(being);
	}
	
	public boolean hasFailed(Being being) {
		return behavior.hasFailed(being);
	}
	
	public boolean isRunning(Being being) {
		return behavior.isRunning(being);
	}
	
	public boolean hasSucceeded(Being being) {
		return behavior.hasSucceeded(being);
	}
	
	public void setState(Being being, int state) {
		behavior.setState(being, state);
	}
	
	public int getState(Being being) {
		return behavior.getState(being);
	}
}
