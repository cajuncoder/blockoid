package blockoid.game.being.behavior;

import blockoid.game.World;
import blockoid.game.being.Being;

public class AttackBehavior extends Behavior {
	protected Being target;
	private int minSeparation, maxSeparation;
	
	public AttackBehavior(Being being) {
		super(being);
		FollowBehavior follow = new FollowBehavior(being);
		behaviors.put("follow", follow);
		WanderBehavior wander = new WanderBehavior(being);
		behaviors.put("wander", wander);
		setMinSeparation(16);
		setMaxSeparation(128);
	}
	
	// dear lord these are verbose and repetitive :(
	public void setMinSeparation(int min) {
		minSeparation = min;
		FollowBehavior behavior = (FollowBehavior)behaviors.get("follow");
		behavior.setMinSeparation(min);
	}
	
	public void setMaxSeparation(int max) {
		maxSeparation = max;
		FollowBehavior behavior = (FollowBehavior)behaviors.get("follow");
		behavior.setMaxSeparation(max);
	}
	
	public void setTarget(Being being) {
		target = being;
		FollowBehavior behavior = (FollowBehavior)behaviors.get("follow");
		behavior.setTarget(being);
	}
	
	@Override
	public void act(World world, long elapsedTime) {
		if (target.isDead())
			return;
		
		int targetDistance = (int) (Math.abs(target.x - being.x)+Math.abs(target.y - being.y));
		if (targetDistance < minSeparation) {
			target.knockBack(being, 3);
			target.hurt(2, world);
		} else if (targetDistance < maxSeparation){
			behaviors.get("follow").act(world, elapsedTime);
		} else {
			behaviors.get("wander").act(world, elapsedTime);
		}
	}
}
