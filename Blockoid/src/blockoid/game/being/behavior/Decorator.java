package blockoid.game.being.behavior;

import blockoid.game.being.Being;

public abstract class Decorator extends Behavior {
	protected Behavior behavior;
	
	public Decorator(Behavior behavior) {
		this.behavior = behavior;
	}
	
	protected int failed(Being being) {
		return behavior.failed(being);
	}
	
	protected int running(Being being) {
		return behavior.running(being);
	}
	
	protected int succeeded(Being being) {
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
	
	protected void setState(Being being, int state) {
		behavior.setState(being, state);
	}
	
	protected int getState(Being being) {
		return behavior.getState(being);
	}
}
