package blockoid.game.biome;

import blockoid.game.World;
import blockoid.game.object.PalmTree;
import blockoid.game.tile.Desert;
import blockoid.game.tile.DesertStone;
import blockoid.game.tile.Dirt;
import blockoid.game.tile.Stone;

public class DesertBiome extends Biome {

	public DesertBiome(World world, int index) {
		super(world, index);
		this.base = new DesertStone(0,0,false);
		this.top = new Desert(0,0,false);
		this.roughnessVariance = 1;
		this.slopeVariance = 16;
		this.smoothness = 4;
		this.treeRarity = 20;
		this.treeType = new PalmTree(null);
		fill();
	}

}
