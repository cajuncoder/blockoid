package blockoid.game.being.behavior;

import blockoid.game.World;
import blockoid.game.being.Being;

public class AttackBehavior implements Behavior {
	private static final int MIN_SEPARATION = 15;
	private static final int MAX_SEPARATION = 250;
	
	private double oldDistance = 0;
	private double oldX = 0;
	
	private Being being;
	
	public AttackBehavior(Being being) {
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
			//being.speed = world.player.speed;
		
			double playerX = world.player.x;
			double playerY = world.player.y;
		
			double distance = Math.abs(playerX - being.x) + Math.abs(playerY - being.y);
			if (distance > MIN_SEPARATION && distance < MAX_SEPARATION) {
			if (playerX > being.x) being.aiMoveRight();
			if (playerX < being.x) being.aiMoveLeft();
			//boolean gapWidening = Math.abs(distance - oldDistance) > 1;
			//if (being.x == oldX && (gapWidening || distance > MAX_SEPARATION)) being.jump();
			}
			
			if(distance <= MIN_SEPARATION) {
				world.player.knockBack(this.being, 3);
				world.player.hurt(2,world);
			}
			oldDistance = distance;
			oldX = being.x;
		}
	}
}
