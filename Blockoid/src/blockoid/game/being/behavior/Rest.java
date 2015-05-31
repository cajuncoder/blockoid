package blockoid.game.being.behavior;

import blockoid.game.World;
import blockoid.game.being.Being;
import blockoid.game.being.Memory;

public class Rest extends Behavior {
	long time;
	
	public static final long MILLISECOND = 1000000;
	public static final long SECOND = 1000*MILLISECOND;
	public static final long MINUTE = 60*SECOND;
	public static final long HOUR   = 60*MINUTE;
	
	public Rest(long time) {
		super();
		this.time = time;
	}

	@Override
	public int act(Being being, World world, long elapsedTime) {
		if (isRunning(being)) {
			long startTime = (Long)being.brain.getMemory("rest.start").retrieve();
			if (elapsedTime >= startTime + time)
				return succeeded(being);
		} else {
			being.brain.addMemory("rest.start", new Memory<Long>(elapsedTime));
		}
		return running(being);
	}
}
