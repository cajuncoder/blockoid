package blockoid.game.item;

import blockoid.Assets;
import blockoid.graphics.SpriteSheet;

public class PickAxe extends Item {

	public PickAxe() {
		super.name = "PickAxe";
		super.inventorySprite = Assets.getSpriteSheet("items/inventoryPickaxe", 11, 11);
	}
	
	public Item getNewInstance() {
		Item result = new PickAxe();
		return result;
	}
	
}
