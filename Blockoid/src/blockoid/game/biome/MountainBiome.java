package blockoid.game.biome;

import blockoid.game.World;
import blockoid.game.tile.Dirt;
import blockoid.game.tile.Stone;

public class MountainBiome extends Biome {

	public MountainBiome(World world, int index) {
		super(world, index);
		this.base = new Stone(0,0,false);
		this.top = new Dirt(0,0,false);
		this.roughnessVariance = 5;
		this.slopeVariance = 32;
		fill();
	}

}
