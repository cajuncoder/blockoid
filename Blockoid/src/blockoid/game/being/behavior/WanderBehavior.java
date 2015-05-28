package blockoid.game.being.behavior;

import blockoid.Game;
import blockoid.game.World;
import blockoid.game.being.Being;

public class WanderBehavior extends Behavior {
	private boolean headingLeft;
	private double decision;
	private long decisionTime;
	
	public WanderBehavior(Being being) {
		super(being);
		this.headingLeft = false;
		this.decision = 0;
		this.decisionTime = 0;
	}
	
	@Override
	public void act(World world, long elapsedTime) {
		being.useMinSpeed();
		
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
