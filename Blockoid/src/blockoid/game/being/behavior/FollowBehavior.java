package blockoid.game.being.behavior;

import blockoid.game.World;
import blockoid.game.being.Being;

public class FollowBehavior extends Behavior {
	private int minSeparation, maxSeparation;
	private double oldDistance = 0;
	private double oldX = 0;
	protected Being target;
	
	public FollowBehavior(Being being) {
		super(being);
		setMinSeparation(10);
		setMaxSeparation(150);
	}
	
	public void setMinSeparation(int min) {
		minSeparation = min;
	}
	
	public void setMaxSeparation(int max) {
		maxSeparation = max;
	}
	
	public void setTarget(Being being) {
		target = being;
	}
	
	@Override
	public void attachBeing(Being being) {
		super.attachBeing(being);
		this.oldX = being.x;
	}
	
	@Override
	public void act(World world, long elapsedTime) {
		if (target.isDead())
			return;

		being.useMaxSpeed();
		
		double targetX = target.x;
	
		double distance = Math.abs(targetX - being.x);
		if (distance > minSeparation) {
			if (targetX > being.x) being.aiMoveRight();
			if (targetX < being.x) being.aiMoveLeft();
		//boolean gapWidening = Math.abs(distance - oldDistance) > 1;
		//if (being.x == oldX && (gapWidening || distance > MAX_SEPARATION)) being.jump();
		}
		oldDistance = distance;
		oldX = being.x;
	}
}
