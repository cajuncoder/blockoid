package blockoid.game.being.behavior.primitive;

import blockoid.game.World;
import blockoid.game.being.Being;
import blockoid.game.being.behavior.Behavior;

public class Case extends Behavior {
	protected Behavior[][] cases;
	protected Behavior defaultCase;
	
	public Case(Behavior[][] behaviors, Behavior defaultCase) {
		this.cases = behaviors;
		this.defaultCase = defaultCase;
	}
	
	@Override
	public int act(Being being, World world, long elapsedTime) {
		Behavior action = null;
		for (int i = 0; i < cases.length; i++) {
			Behavior[] behaviors = cases[i];
			Behavior caseCondition = behaviors[0];
			Behavior caseBehavior = behaviors[1];
			
			caseCondition.act(being, world, elapsedTime);
			if (caseCondition.hasSucceeded(being)) {
				action = caseBehavior;
				break;
			}
		}
		
		if (action == null)
			action = defaultCase;
		
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
