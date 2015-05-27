package blockoid.game.item;

import blockoid.Assets;
import blockoid.game.tile.Stone;
import blockoid.game.tile.Tile;

public class StoneBlock extends Block {

	public StoneBlock() {
		super(1);
		name = "Stone Block";
		inventorySprite = Assets.getSpriteSheet("tiles/stone", Tile.TILE_SIZE, Tile.TILE_SIZE);
	}
	
	public Item getNewInstance() {
		Item result = new StoneBlock();
		return result;
	}
	
	public Tile getNewTileInstance(int tileX, int tileY, boolean bgTile) {
		Tile tile = new Stone(tileX,tileY,bgTile);
		return tile;
	}
	
}
