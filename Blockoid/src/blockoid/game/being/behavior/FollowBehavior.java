package blockoid.game.being.behavior;

import blockoid.game.World;
import blockoid.game.being.Being;

public class FollowBehavior implements Behavior {
	private static final int MIN_SEPARATION = 10;
	private static final int MAX_SEPARATION = 150;
	
	private double oldDistance = 0;
	private double oldX = 0;
	
	private Being being;
	
	public FollowBehavior(Being being) {
		attachBeing(being);
	}
	
	@Override
	public void attachBeing(Being being) {
		this.being = being;
		this.oldX = being.x;
	}
	
	@Override
	public void act(World world, long elapsedTime) {
		if(world.player!=null) {
			being.speed = world.player.speed;
		
			double playerX = world.player.x;
		
			double distance = Math.abs(playerX - being.x);
			if (distance > MIN_SEPARATION) {
				if (playerX > being.x) being.aiMoveRight();
				if (playerX < being.x) being.aiMoveLeft();
			//boolean gapWidening = Math.abs(distance - oldDistance) > 1;
			//if (being.x == oldX && (gapWidening || distance > MAX_SEPARATION)) being.jump();
			}
			oldDistance = distance;
			oldX = being.x;
		}
	}
}
