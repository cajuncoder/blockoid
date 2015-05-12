package blockoid.states.playstate.world.biomes;

import java.util.Random;

import blockoid.states.playstate.world.World;
import blockoid.states.playstate.world.objects.OakTree;
import blockoid.states.playstate.world.tiles.Dirt;
import blockoid.states.playstate.world.tiles.Empty;
import blockoid.states.playstate.world.tiles.Grass;
import blockoid.states.playstate.world.tiles.Stone;
import blockoid.states.playstate.world.tiles.Tile;

public abstract class Biome {

	public static int BIOME_SIZE = 32;
	public World world;
	public int index;
	public int startX;
	public int endX;
	public int slope;
	public int slopeVariance = 16;
	public int roughness;
	public int roughnessVariance = 5;
	public int treeRarity = 10;
	public Tile base;
	public Tile top;
	
	public Biome(World world, int index) {
		this.index = index;
		this.world = world;
		startX = index*BIOME_SIZE;
		endX = (index*BIOME_SIZE) + BIOME_SIZE;
		if(endX >= world.sizeX) endX = world.sizeX-1;
		if(startX < 0) startX = 0;
	}
	
	public void fill() {
		int stackHeight = world.sizeY/2;
		if(startX > 0) {
			startX+=1;
			stackHeight = world.getSurface(startX-1);
		}
		slope = slopeVariance;
		roughness = roughnessVariance;

		Random r = new Random();
		int previousLow = stackHeight;
		System.out.println("!!!!! Filling Biome !!!!");
		System.out.println(base);
		System.out.println(top);
		for(int x = startX; x <= endX; x++) {
			
			//Slopes
			if(stackHeight + slope > stackHeight) stackHeight-= r.nextInt(roughness);
			if(stackHeight + slope < stackHeight) stackHeight+= r.nextInt(roughness);
			if(slope >= 0 && stackHeight <= previousLow+slope){
				previousLow = stackHeight;
				slope = r.nextInt(slopeVariance*2)-slopeVariance;
				if(slope==0) slope = r.nextInt(2)-1;
				roughness = r.nextInt(roughnessVariance)+1;
			}
			if(slope < 0 && stackHeight >= previousLow+slope){
				previousLow = stackHeight;
				slope = r.nextInt(slopeVariance*2)-slopeVariance;
				if(slope==0) slope = r.nextInt(2)-1;
				roughness = r.nextInt(roughnessVariance)+1;
			}
			if(stackHeight + slope > world.sizeY-64) {
				slope = +slopeVariance;
				previousLow = stackHeight;
			}
			if(stackHeight + slope < 64) {
				slope = -slopeVariance;
				previousLow = stackHeight;
			}
			
			for(int y = 0; y < world.sizeY; y++) {
				
				if(y > stackHeight && y < stackHeight+11) {
					world.tiles[x][y] = top.newInstance(x, y, false);
					world.bgTiles[x][y] = top.newInstance(x, y, true);
				}
				if(y > stackHeight && y >= stackHeight+11) {
					world.tiles[x][y] = base.newInstance(x, y, false);
					world.bgTiles[x][y] = base.newInstance(x, y, true);
				}
				if(y == stackHeight) {
					world.tiles[x][y] = top.newInstance(x, y, false);
					world.bgTiles[x][y] = top.newInstance(x, y, true);
					int plantTree = r.nextInt(treeRarity);
					if(plantTree == 0) world.objects.add(new OakTree(world.bgTiles[x][y]));
				}
				if(y < stackHeight) {
					world.tiles[x][y] = new Empty(x, y, false);
					world.bgTiles[x][y] = new Empty(x, y, true);
				}
			}
		}
	}

}
