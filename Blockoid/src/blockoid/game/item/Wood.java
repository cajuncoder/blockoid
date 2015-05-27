package blockoid.game.item;

import blockoid.Assets;
import blockoid.graphics.SpriteSheet;

public class Wood extends Item {

	public Wood() {
		super.name = "Wood";
		stackable = true;
		maxInStack = 16;
		super.inventorySprite = Assets.getSpriteSheet("items/wood", 11, 11);
	}
	
	public Item getNewInstance() {
		Item result = new Wood();
		return result;
	}
	
}
