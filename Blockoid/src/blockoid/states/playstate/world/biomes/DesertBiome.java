package blockoid.states.playstate.world.biomes;

import blockoid.states.playstate.world.World;
import blockoid.states.playstate.world.objects.PalmTree;
import blockoid.states.playstate.world.tiles.Desert;
import blockoid.states.playstate.world.tiles.DesertStone;
import blockoid.states.playstate.world.tiles.Dirt;
import blockoid.states.playstate.world.tiles.Stone;

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
