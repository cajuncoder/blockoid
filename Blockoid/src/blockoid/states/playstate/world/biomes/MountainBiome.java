package blockoid.states.playstate.world.biomes;

import blockoid.states.playstate.world.World;
import blockoid.states.playstate.world.tiles.Dirt;
import blockoid.states.playstate.world.tiles.Stone;

public class MountainBiome extends Biome {

	public MountainBiome(World world, int index) {
		super(world, index);
		this.base = new Stone(0,0,false);
		this.top = new Dirt(0,0,false);
		this.roughnessVariance = 9;
		this.slopeVariance = 32;
		fill();
	}

}
