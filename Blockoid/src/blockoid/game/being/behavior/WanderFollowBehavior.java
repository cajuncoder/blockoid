package blockoid.game.being.behavior;

import blockoid.game.World;
import blockoid.game.being.Being;

public class WanderFollowBehavior extends Behavior {	
	protected int attractionDistance;
	protected Being target;
	
	public WanderFollowBehavior(Being being) {
		super(being);
		setAttractionDistance(100);
		behaviors.put("follow", new FollowBehavior(being));
		behaviors.put("wander", new WanderBehavior(being));
	}
	
	public void setTarget(Being being) {
		target = being;
		FollowBehavior behavior = (FollowBehavior)behaviors.get("follow");
		behavior.setTarget(target);
	}
	
	public void setAttractionDistance(int dist) {
		attractionDistance = dist;
	}
	
	@Override
	public void act(World world, long elapsedTime) {
		if (target.isDead()) {
			behaviors.get("wander").act(world, elapsedTime);
			return;
		}
		
		boolean targetNearby = Math.abs(target.x - being.x) < attractionDistance;
		behaviors.get(targetNearby ? "follow" : "wander").act(world, elapsedTime);
	}
}
