package blockoid.states.playstate.world.items;

import blockoid.Assets;
import blockoid.states.playstate.world.tiles.Desert;
import blockoid.states.playstate.world.tiles.Stone;
import blockoid.states.playstate.world.tiles.Tile;

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
