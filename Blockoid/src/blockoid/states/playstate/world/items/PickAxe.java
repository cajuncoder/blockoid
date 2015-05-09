package blockoid.states.playstate.world.items;

import blockoid.graphics.SpriteSheet;
import blockoid.states.playstate.world.Assets;

public class PickAxe extends Item {

	public PickAxe() {
		super.name = "PickAxe";
		super.inventorySprite = Assets.get("inventoryPickaxe");
	}
	
	public Item getNewInstance() {
		Item result = new PickAxe();
		return result;
	}
	
}
