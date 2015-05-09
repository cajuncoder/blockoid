package blockoid.graphics;

import java.io.Serializable;

public class Sprite implements Serializable {
	
	//Coordinates on SpriteSheet
	public int x;
	public int y;
		
	public Sprite(int x, int y) {
		this.x = x;
		this.y = y;
	}

}
