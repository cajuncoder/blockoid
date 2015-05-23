package blockoid.states.playstate.world.biomes;

import java.util.Random;

import blockoid.states.playstate.world.World;
import blockoid.states.playstate.world.objects.GameObject;
import blockoid.states.playstate.world.objects.OakTree;
import blockoid.states.playstate.world.tiles.Dirt;
import blockoid.states.playstate.world.tiles.Empty;
import blockoid.states.playstate.world.tiles.Grass;
import blockoid.states.playstate.world.tiles.Stone;
import blockoid.states.playstate.world.tiles.Tile;

public abstract class Biome {

	public static int BIOME_SIZE = 128;
	public World world;
	public int index;
	public int startX;
	public int endX;
	public int previousLow;
	public int stackHeight;
	public int slope;
	public int slopeVariance = 16;
	public int smoothness = 1;
	public int roughness;
	public int roughnessVariance = 3;
	public int treeRarity = 10;
	public GameObject treeType = new OakTree(null);
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
		stackHeight = world.sizeY/2;
		if(startX > 0) {
			startX+=1;
			stackHeight = world.getSurface(startX-1);
		}
		Random r = new Random();
		
		slope = r.nextInt(slopeVariance) - r.nextInt(slopeVariance);
		if(slope==0) slope = 1;
		roughness = r.nextInt(roughnessVariance)+2;

		previousLow = stackHeight;
		
		for(int x = startX; x <= endX; x++) {
			
			//Slopes
			if(r.nextInt(smoothness) == 0) {
				if(slope>0) stackHeight+= r.nextInt(roughness);
				if(slope<0) stackHeight-= r.nextInt(roughness);
			}
			System.out.println("Slope!!!: " + slope + " Roughness: " + roughness);
			System.out.println("PreviousLow: " + previousLow + " StackHeight: " + stackHeight);
			if(slope > 0 && stackHeight >= previousLow+slope){
				previousLow = stackHeight;
				slope = r.nextInt(slopeVariance) - r.nextInt(slopeVariance);
				if(slope==0) slope = 1;
				roughness = r.nextInt(roughnessVariance)+2;
			}
			if(slope < 0 && stackHeight <= previousLow+slope){
				previousLow = stackHeight;
				slope = r.nextInt(slopeVariance) - r.nextInt(slopeVariance);
				if(slope==0) slope = -1;
				roughness = r.nextInt(roughnessVariance)+2;
			}
			if(stackHeight + slope > world.sizeY-64) {
				slope = -slopeVariance;
				previousLow = stackHeight;
			}
			if(stackHeight + slope < 64) {
				slope = +slopeVariance;
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
					if(plantTree == 0) world.objects.add(treeType.getNewInstance(world.bgTiles[x][y]));//(new OakTree(world.bgTiles[x][y]));
				}
				if(y < stackHeight) {
					world.tiles[x][y] = new Empty(x, y, false);
					world.bgTiles[x][y] = new Empty(x, y, true);
				}
			}
		}
	}

}
