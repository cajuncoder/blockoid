package blockoid.states.playstate.world.biomes;

import blockoid.states.playstate.world.World;
import blockoid.states.playstate.world.tiles.Dirt;
import blockoid.states.playstate.world.tiles.Stone;

public class ForestBiome extends Biome {

	public ForestBiome(World world, int index) {
		super(world, index);
		this.base = new Stone(0,0,false);
		this.top = new Dirt(0,0,false);
		this.roughnessVariance = 2;
		this.slopeVariance = 6;
		this.smoothness = 2;
		this.treeRarity = 3;
		fill();
	}

}
