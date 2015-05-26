package blockoid.states.playstate.world.characters;

import blockoid.Game;
import blockoid.states.playstate.world.World;

public class WanderBehavior implements Behavior {
	private boolean headingLeft;
	private double decision;
	private long counter;
	
	private Being being;
	
	public WanderBehavior(Being being) {
		attachBeing(being);
		this.decision = 0;
		this.counter = 0;
	}
	
	@Override
	public void attachBeing(Being being) {
		this.being = being;
	}
	
	@Override
	public void act(World world, long elapsedTime) {
		if (counter % 100 == 0)
			decision = Math.random();
		
		if (decision < 0.5) {
			// change heading
			headingLeft = !headingLeft;
		} else if (decision < 0.95) {
			// walk
			if (headingLeft) 
				being.moveLeft();
			else
				being.moveRight();
		} else if (decision < 1.0) {
			being.jump();
		}
			
		counter++;
	}
}
