package blockoid.game.item;

import blockoid.Assets;
import blockoid.game.tile.DesertStone;
import blockoid.game.tile.Stone;
import blockoid.game.tile.Tile;

public class DesertStoneBlock extends Block {

	public DesertStoneBlock() {
		super(1);
		name = "Sandstone Block";
		inventorySprite = Assets.getSpriteSheet("tiles/desertStone", Tile.TILE_SIZE, Tile.TILE_SIZE);
	}
	
	public Item getNewInstance() {
		Item result = new DesertStoneBlock();
		return result;
	}
	
	public Tile getNewTileInstance(int tileX, int tileY, boolean bgTile) {
		Tile tile = new DesertStone(tileX,tileY,bgTile);
		return tile;
	}
	
}
