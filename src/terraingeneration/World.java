package terraingeneration;
import java.util.Random;

import terraingeneration.blockattributes.BlockAttribute;
import terraingeneration.blockattributes.PropogatesAlongSurface;
import terraingeneration.generators.*;


public class World {
	
	public static final int AIR = 0,
			DIRT = 1,
			GRASS=2,
			STONE=3,
			COPPER=4,
			IRON=5,
			WATER=6,
			LAVA=7;
	
	int seedCalls = 0;
	
	private int[][] terrain;
	private Random seed;
	
	static final Generator[] generators = new Generator[]{
			//shitty, but it provides seed areas for the air orepockets
			new CaveSystem(0.93,30,5,0.3,0.9),

			new OrePocket(World.AIR ,0.008, 0.3, 0.41, 25),
			new OrePocket(World.AIR ,0.008, 0.39, 0.61, 30),
			new OrePocket(World.AIR ,0.008, 0.59, 0.9, 40),
			
			new Surface(),
			new OrePocket(World.COPPER,0.008, 0.15, 0.41, 20),
			new OrePocket(World.COPPER,0.008, 0.39, 0.80, 10),
			new OrePocket(World.STONE, 0.008, 0.00, 0.56, 20),
			
			new Hell(0.82, 0.98),
			
			new CleanScraps(World.AIR),
			//new SimpleErosion(100) // this is shit. Makes the map way worse.
			new SimpleLiquid(World.LAVA, 0.65, 1.0, 10, 50),
			new SimpleLiquid(World.WATER, 0.1, 1.0, 30, 30),
			new Grasser(World.GRASS, 0.2, 0.4, 8)
	};
	
	static final BlockAttribute[][] attributes = new BlockAttribute[][]{
		new BlockAttribute[0],
		new BlockAttribute[0],
		new BlockAttribute[] {new PropogatesAlongSurface(World.GRASS, World.DIRT, 0.4)},
		new BlockAttribute[0],
		new BlockAttribute[0],
		new BlockAttribute[0],
		new BlockAttribute[0],
		new BlockAttribute[0],
		new BlockAttribute[0],
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
		
		System.out.println("Stepping Terrain");
		for(int i=0; i<100; i++){
			stepAllTerrain();
		}
		
	}
	
	public void stepAllTerrain(){
		//updates all of everything
		for (int x=0; x<this.getWidth(); x++){
			for (int y=0; y<this.getHeight(); y++){
				for(int i=0; i<attributes[this.getTerrainAt(x, y)].length; i++){
					attributes[this.getTerrainAt(x, y)][i].stepBlock(this, x, y);
				}
			}
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
	
	public boolean isInRange(int x,int y,int range){
		return x-range>=0 && y-range>=0 && x+range<terrain.length && y+range<terrain[0].length;
	}
	
	
	public boolean bordersBlock(int blockValue, int x, int y){
		for(int a=-1; a<=1 ;a++){
			int p=0;
			if(a==0){
				p=1;
			}
			for (int b=-p; b<=p; b++){
				if(this.getTerrainAt(x+a,y+b)==blockValue){
					return true;
				}
			}
		}
		return false;
	}
	
	
}
