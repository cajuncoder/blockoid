package blockoid.game.being.behavior;

import blockoid.Game;
import blockoid.game.World;
import blockoid.game.being.Being;

public class WanderBehavior implements Behavior {
	private boolean headingLeft;
	private double decision;
	private long decisionTime;
	
	private Being being;
	
	public WanderBehavior(Being being) {
		attachBeing(being);
		this.decision = 0;
		this.decisionTime = 0;
	}
	
	@Override
	public void attachBeing(Being being) {
		this.being = being;
	}
	
	@Override
	public void act(World world, long elapsedTime) {
		if (decisionTime == 0 || elapsedTime > decisionTime + 2000000000) {
			decisionTime = elapsedTime;
			decision = Math.random();
		}
		
		if (decision < 0.20) {
			// change heading
			headingLeft = !headingLeft;
		} else if (decision < 0.60) {
			// walk
			if (headingLeft) 
				being.aiMoveLeft();
			else
				being.aiMoveRight();
		} else if (decision < 0.61) {
			//being.jump();
		}
	}
}
