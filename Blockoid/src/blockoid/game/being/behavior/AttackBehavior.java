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
		setMinSeparation(15);
		setMaxSeparation(250);
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
		
		boolean targetNearby = Math.abs(target.x - being.x) < minSeparation;
		if (targetNearby) {
			target.knockBack(being, 3);
			target.hurt(2, world);
		} else {
			behaviors.get("follow").act(world, elapsedTime);
		}
	}
}
