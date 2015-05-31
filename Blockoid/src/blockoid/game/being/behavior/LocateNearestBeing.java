package blockoid.game.being.behavior;

import java.util.ArrayList;

import blockoid.game.World;
import blockoid.game.being.Being;
import blockoid.game.being.Memory;

public class LocateNearestBeing extends Behavior {
	public LocateNearestBeing() {
		super();
	}
	
	public int act(Being being, World world, long elapsedTime) {
		ArrayList<Being> beings = (ArrayList<Being>) being.brain.getMemory("beings").retrieve();
		Being closestBeing = null;
		int closestDist = Integer.MAX_VALUE;
		for (Being b : beings) {
			if (!meetsCriteria(being, b)) continue;
			int dist = (int) Math.sqrt(Math.pow(being.x - b.x, 2) + Math.pow(being.y - b.y, 2));
			if (dist < closestDist) {
				closestDist = dist;
				closestBeing = b;
			}
		}
		
		if (closestBeing == null)
			return failed(being);
		
		being.brain.addMemory(rememberAs(), new Memory<Being>(closestBeing));
		return succeeded(being);
	}
	
	protected boolean meetsCriteria(Being being, Being otherBeing) {
		return true;
	}
	
	protected String rememberAs() {
		return "target.being";
	}
}
