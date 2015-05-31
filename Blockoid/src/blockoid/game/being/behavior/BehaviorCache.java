package blockoid.game.being.behavior;

import java.util.HashMap;

public class BehaviorCache {
	protected HashMap<String, Object> cache;
	
	public BehaviorCache() {
		cache = new HashMap<String, Object>();
	}
	
	public void set(String key, Object val) {
		cache.put(key, val);
	}
	
	public Object get(String key) {
		return cache.get(key);
	}
}
