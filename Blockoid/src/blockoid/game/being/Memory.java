package blockoid.game.being;

public class Memory<T> {	
	T memory;
	public Memory(T mem) {
		memory = mem;
	}
	public T retrieve() {
		return memory;
	}
}
