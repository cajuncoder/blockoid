package blockoid.states.playstate.world.items;

import blockoid.Assets;
import blockoid.states.playstate.world.tiles.DesertStone;
import blockoid.states.playstate.world.tiles.Stone;
import blockoid.states.playstate.world.tiles.Tile;

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
