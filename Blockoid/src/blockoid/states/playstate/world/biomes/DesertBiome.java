package blockoid.states.playstate.world.biomes;

import blockoid.states.playstate.world.World;
import blockoid.states.playstate.world.tiles.Desert;
import blockoid.states.playstate.world.tiles.Dirt;
import blockoid.states.playstate.world.tiles.Stone;

public class DesertBiome extends Biome {

	public DesertBiome(World world, int index) {
		super(world, index);
		this.base = new Stone(0,0,false);
		this.top = new Desert(0,0,false);
		this.roughnessVariance = 2;
		this.slopeVariance = 6;
		this.treeRarity = 100;
		fill();
	}

}
