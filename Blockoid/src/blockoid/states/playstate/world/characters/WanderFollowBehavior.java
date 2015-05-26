package blockoid.states.playstate.world.characters;

import blockoid.states.playstate.world.World;

public class WanderFollowBehavior implements Behavior {	
	private Being being;
	
	public WanderFollowBehavior(Being being) {
		attachBeing(being);
	}
	
	@Override
	public void attachBeing(Being being) {
		this.being = being;
	}
	
	@Override
	public void act(World world, long elapsedTime) {
		boolean playerNearby = Math.abs(world.player.x - being.x) < 25 && (world.player.y == being.y || world.player.y == being.y + 1);
		Behavior behavior = (Behavior) (playerNearby ? new FollowBehavior(being) : new WanderBehavior(being));
		behavior.act(world, elapsedTime);
	}
}
