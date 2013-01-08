package terraingeneration;
import java.awt.image.BufferedImage;
import java.util.Random;

import terraingeneration.generators.*;


public class World {
	
	public static final int AIR = 0, DIRT = 1, GRASS=2, STONE=3, COPPER=4, IRON=5, WATER=6;
	
	int seedCalls = 0;
	
	private int[][] terrain;
	private Random seed;
	
	Generator[] generators = new Generator[]{
			new CaveSystem(0.93,30,5,0.1),//shitty, but it provides seed areas for the air orepockets
			
			new OrePocket(World.AIR ,0.008, 0.00, 0.16, 20),
			new OrePocket(World.AIR ,0.008, 0.14, 0.41, 25),
			new OrePocket(World.AIR ,0.008, 0.39, 0.61, 30),
			new OrePocket(World.AIR ,0.008, 0.59, 0.90, 40),
			
			new Surface(),
			new OrePocket(World.COPPER,0.008, 0.15, 0.41, 20),
			new OrePocket(World.COPPER,0.008, 0.39, 0.80, 10),
			new OrePocket(World.STONE, 0.008, 0.00, 0.56, 20),
			new OrePocket(World.DIRT,  0.008, 0.39, 0.80, 10),
			
			new CleanScraps(World.AIR),
			//new SimpleErosion(100) // this is shit. Makes the map way worse.
			new SimpleLiquid(World.WATER, 0.1, 1.8, 30, 30)
	};
	
	public World(int width, int height, int seed){
		setTerrain(new int[width][height]);
		this.seed = new Random(seed);
		
		for(int i=0; i<width; i++){
			for(int p=0; p<height; p++){
				terrain[i][p] = World.DIRT;
			}
		}
		
		for(int i=0; i<generators.length; i++){
			generators[i].applyToWorld(this);
		}
	}

	public void setTerrain(int[][] terrain) {
		this.terrain = terrain;
	}

	public int[][] getTerrain() {
		return terrain;
	}

	public int getWidth() {
		return terrain.length;
	}
	
	public int getHeight() {
		return terrain[0].length;
	}
	
	public Random getSeed(){
		seedCalls++;
		if(seedCalls>5){
			this.seed = new Random (seed.nextLong());
		}
		return this.seed;
	}

	public void setTerrainAt(int x, int y, int value) {
		terrain[x][y]=value;
	}

	public int getTerrainAt(int x, int y) {
		return terrain[x][y];
	}

	
	
}
