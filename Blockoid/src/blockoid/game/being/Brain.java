package blockoid.game.being;

import java.util.HashMap;

public class Brain {
	HashMap<String, Memory> memories;
	
	public Brain() {
		memories = new HashMap<String, Memory>();
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
}
