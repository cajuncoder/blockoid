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
		storeObject(being, "currentBehaviorIndex", -1);
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
	
	protected Behavior currentBehavior(Being being) {
		if (currentBehaviorIndex(being) == -1)
			return nextBehavior(being);
		return behaviors[currentBehaviorIndex(being)];
	}
	
	protected boolean hasNextBehavior(Being being) {
		return currentBehaviorIndex(being) + 1 < behaviors.length;
	}
	
	protected Behavior nextBehavior(Being being) {
		storeObject(being, "currentBehaviorIndex", !hasNextBehavior(being) ? 0 : currentBehaviorIndex(being)+1);
		return behaviors[currentBehaviorIndex(being)];
	}
	
	protected int currentBehaviorIndex(Being being) {
		Object index = retrieveObject(being, "currentBehaviorIndex");
		return index == null ? -1 : (Integer)index;
	}
}
