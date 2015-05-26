package blockoid.states.playstate.world.characters;

import blockoid.states.playstate.world.World;

public class WanderFollowBehavior implements Behavior {	
	private static final int ATTRACTION_DISTANCE = 50;
	private Being being;
	private Behavior[] behaviors;
	
	public WanderFollowBehavior(Being being) {
		attachBeing(being);
		behaviors = new Behavior[]{
			new FollowBehavior(being), 
			new WanderBehavior(being)
		};
	}
	
	@Override
	public void attachBeing(Being being) {
		this.being = being;
	}
	
	@Override
	public void act(World world, long elapsedTime) {
		boolean playerNearby = Math.abs(world.player.x - being.x) < ATTRACTION_DISTANCE;
		behaviors[playerNearby ? 0 : 1].act(world, elapsedTime);
	}
}
