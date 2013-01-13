package terraingeneration;
import java.util.Random;

import terraingeneration.blockattributes.*;
import terraingeneration.generators.*;


public class World {
	
	public static final int AIR = 0,
			DIRT = 1,
			GRASS=2,
			STONE=3,
			COPPER=4,
			IRON=5,
			WATER=6,
			LAVA=7,
			ASH=8,
			HELLROCK=9,
			MITHRIL=10,
			OBSIDIAN=11,
			SAND=12;
	
	public static final int[]
	        mess = {DIRT, STONE},
	        liquids = {LAVA, WATER};
	
	int seedCalls = 0;
	
	private int[][] terrain;
	private Random seed;
	
	static final Generator[] generators = new Generator[]{
			//basic background fill
			new Surface(),
			
			//surface features
			new Desert(World.SAND, 0.004),
			
			//creates the caves
			new CaveSystem(World.AIR,0.93,30,5,0.3,0.9),//default is kinda shit.
			new CaveSystem(World.AIR,0.93,30,8,0.1,0.11, new ShallowCave()),

			new OrePocket(World.AIR , mess, 0.008, 0.59, 0.9, 20),
			new OrePocket(World.AIR , mess,0.008, 0.3, 0.6, 10),
			
			new ScuffSurfaces(World.AIR, World.DIRT,0.04, 0.0, 1.0, 20),
			new ScuffSurfaces(World.AIR, World.STONE,0.04, 0.0, 1.0, 20),
			
			//places minerals
			new OrePocket(World.STONE, mess, 0.008, 0.00, 0.56, 20),
			new OrePocket(World.DIRT, mess, 0.008, 0.46, 0.90, 20),
			new OrePocket(World.COPPER, mess, 0.008, 0.15, 0.41, 20),
			new OrePocket(World.IRON, mess, 0.006, 0.39, 0.80, 10),
			new OrePocket(World.MITHRIL, mess, 0.001, 0.59, 0.90, 50),
			
			//lays liquids
			new SimpleLiquid(World.LAVA, 0.75, 1.0, 20, 15),
			new SimpleLiquid(World.WATER, 0.1, 1.0, 28, 20),
			
			//making hell
			new HellFrame(0.82, 1.00),
			new CaveSystem(World.ASH,0.93,30,15,0.87,1.0, new ShallowCave()),
						
			new ScuffSurfaces(World.ASH, World.AIR ,0.3, 0.82, 1.0, 5),
			
			new OrePocket(World.HELLROCK, new int[] {World.ASH}, 0.008, 0.85, 1, 50),
			new FillArea(World.LAVA, World.AIR, 0.92, 1.0),
			new SimpleLiquid(World.LAVA, 0.9, 1.0, 20, 30),
			
			new ScuffSurfaces(World.ASH, World.LAVA ,0.1, 0.82, 1.0, 20),

			new CleanScraps(World.ASH),
			new CleanScraps(World.AIR),
			
			//placing grass
			new Grasser(World.GRASS, 0.2, 0.4, 4)
	};
	
	static final BlockAttribute[][] attributes = new BlockAttribute[][]{
		new BlockAttribute[0],//air
		new BlockAttribute[0],//dirt
		new BlockAttribute[] {new PropogatesAlongSurface(World.GRASS, World.DIRT, 0.4)},//grass
		new BlockAttribute[0],//stone
		new BlockAttribute[0],//copper
		new BlockAttribute[0],//iron
		new BlockAttribute[] {new LiquidSettle()},//water
		new BlockAttribute[] {new TransformOnContact(World.OBSIDIAN,World.WATER)},//lava
		new BlockAttribute[0],//ash
		new BlockAttribute[0],//hellrock
		new BlockAttribute[0],//mihril
		new BlockAttribute[0],//obsidian
		new BlockAttribute[] {new FallingBlock()},//sand
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
				if(isInRange(x+a,y+b,0) && this.getTerrainAt(x+a,y+b)==blockValue){
					return true;
				}
			}
		}
		return false;
	}
	
	
}
