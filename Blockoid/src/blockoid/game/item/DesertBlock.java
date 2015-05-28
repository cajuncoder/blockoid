package blockoid.game.item;

import blockoid.Assets;
import blockoid.game.tile.Desert;
import blockoid.game.tile.Stone;
import blockoid.game.tile.Tile;

public class DesertBlock extends Block {

	public DesertBlock() {
		super(1);
		name = "Sand Block";
		inventorySprite = Assets.getSpriteSheet("tiles/desert", Tile.TILE_SIZE, Tile.TILE_SIZE);
	}
	
	public Item getNewInstance() {
		Item result = new DesertBlock();
		return result;
	}
	
	public Tile getNewTileInstance(int tileX, int tileY, boolean bgTile) {
		Tile tile = new Desert(tileX,tileY,bgTile);
		return tile;
	}
	
}
