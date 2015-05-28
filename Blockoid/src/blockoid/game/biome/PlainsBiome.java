package blockoid.game.biome;

import blockoid.game.World;
import blockoid.game.tile.Dirt;
import blockoid.game.tile.Stone;

public class PlainsBiome extends Biome {

	public PlainsBiome(World world, int index) {
		super(world, index);
		this.base = new Stone(0,0,false);
		this.top = new Dirt(0,0,false);
		this.roughnessVariance = 2;
		this.slopeVariance = 6;
		this.smoothness = 3;
		this.treeRarity = 15;
		fill();
	}

}
