package blockoid.game.being.behavior.primitive;

import java.util.ArrayList;

import blockoid.game.being.Being;
import blockoid.game.being.behavior.Behavior;

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
	
	public int succeeded(Being being) {
		reset(being);
		return super.succeeded(being);
	}
	
	public int failed(Being being) {
		reset(being);
		return super.failed(being);
	}
}
