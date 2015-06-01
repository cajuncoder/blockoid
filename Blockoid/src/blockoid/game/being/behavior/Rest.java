package blockoid.game.being.behavior;

import blockoid.game.World;
import blockoid.game.being.Being;
import blockoid.game.being.Memory;

public class Rest extends Behavior {
	long time;
	
	public Rest(long time) {
		super();
		this.time = time;
	}

	@Override
	public int act(Being being, World world, long elapsedTime) {
		Memory mem = being.brain.getMemory("rest.start");
		if (isRunning(being)) {
			if (mem == null)
				return succeeded(being);
			long startTime = (Long)mem.retrieve();
			if (elapsedTime >= startTime + time)
				return succeeded(being);
		} else {
			being.brain.addMemory("rest.start", new Memory<Long>(elapsedTime));
		}
		return running(being);
	}
}
