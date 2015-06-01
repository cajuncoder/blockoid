package blockoid.game.being.behavior;

import java.util.HashMap;

import blockoid.Game;
import blockoid.game.World;
import blockoid.game.being.Being;

public abstract class Behavior {
	public static final int FAILED    = 0;
	public static final int RUNNING   = 1;
	public static final int SUCCEEDED = 2;
	public static final int STATELESS = 3;
	
	public int failed(Being being) {
		setState(being, Behavior.FAILED);
		return Behavior.FAILED;
	}
	
	public int running(Being being) {
		setState(being, Behavior.RUNNING);
		return Behavior.RUNNING;
	}
	
	public int succeeded(Being being) {
		setState(being, Behavior.SUCCEEDED);
		return Behavior.SUCCEEDED;
	}
	
	public int reset(Being being) {
		setState(being, Behavior.STATELESS);
		return Behavior.STATELESS;
	}
	
	public boolean hasFailed(Being being) {
		return getState(being) == Behavior.FAILED;
	}
	
	public boolean isRunning(Being being) {
		return getState(being) == Behavior.RUNNING;
	}
	
	public boolean hasSucceeded(Being being) {
		return getState(being) == Behavior.SUCCEEDED;
	}
	
	public void setState(Being being, int state) {
		System.out.println(this.getClass().getName() + ": " + (state == Behavior.FAILED ? "Failed" : (state == Behavior.SUCCEEDED ? "Succeeded" : (state == Behavior.RUNNING ? "Running" : "Stateless"))));
		storeObject(being, "state", state);
	}
	
	public int getState(Being being) {
		Object state = retrieveObject(being, "state");
		return state == null ? Behavior.STATELESS : (Integer)state;
	}
	
	protected void storeObject(Being being, String name, Object obj) {
		being.bcache.set(this.hashCode() + "." + name, obj);
	}
	
	protected Object retrieveObject(Being being, String name) {
		return being.bcache.get(this.hashCode() + "." + name);
	}
	
	abstract public int act(Being being, World world, long elapsedTime);
}
