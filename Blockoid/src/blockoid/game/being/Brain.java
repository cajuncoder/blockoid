package blockoid.game.being;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import blockoid.game.World;
import blockoid.game.being.behavior.Rest;

public class Brain {
	private long STM_TIME = 10 * World.SECOND;

	HashMap<String, Memory> memories;
	HashMap<String, Long> times;
	
	public Brain() {
		memories = new HashMap<String, Memory>();
		times = new HashMap<String, Long>();
	}
	
	// TODO(griffy) Ideally, this should behave somewhat like
	// a real brain in that it would have short term and long term
	// memory requiring rehearsal of memories to keep them around,
	// with natural decay of memories happening between rehearsals...
	public void addMemory(String name, Memory mem) {
		memories.put(name, mem);
	}
	
	public Memory getMemory(String name) {
		return memories.get(name);
	}
	
	public void update(World world, long elapsedTime) {
		// Expire memories that are moving out of STM
		ArrayList<String> fadedMemories = new ArrayList<String>();
		for (String name : times.keySet()) {
			if (elapsedTime > times.get(name) + STM_TIME) {
				fadedMemories.add(name);
			}
		}
		
		for (String name : fadedMemories) {
			memories.remove(name);
			times.remove(name);
		}
		
		// Record the time of all new memories
		for (String name : memories.keySet()) {
			if (!times.containsKey(name))
				times.put(name, elapsedTime);
		}
	}
}
