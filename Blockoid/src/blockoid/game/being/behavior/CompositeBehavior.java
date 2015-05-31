package blockoid.game.being.behavior;

import java.util.ArrayList;

import blockoid.game.being.Being;

public abstract class CompositeBehavior extends Behavior {
	protected Behavior[] behaviors;
	
	public CompositeBehavior(Behavior[] behaviors) {
		this.behaviors = behaviors;
	}
	
	public int reset(Being being) {
		for (Behavior behavior : behaviors) {
			behavior.reset(being);
		}
		return super.reset(being);
	}
	
	protected int succeeded(Being being) {
		reset(being);
		return super.succeeded(being);
	}
	
	protected int failed(Being being) {
		reset(being);
		return super.failed(being);
	}
}
