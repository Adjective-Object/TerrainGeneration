package terraingeneration.biome;

import terraingeneration.World;
import terraingeneration.generators.CaveSystem;
import terraingeneration.generators.CleanScraps;
import terraingeneration.generators.FillArea;
import terraingeneration.generators.Generator;
import terraingeneration.generators.Grasser;
import terraingeneration.generators.HellFrame;
import terraingeneration.generators.OrePocket;
import terraingeneration.generators.ScuffSurfaces;
import terraingeneration.generators.ShallowCave;
import terraingeneration.generators.SimpleLiquid;
import terraingeneration.generators.Surface;

public class BasicTerrainGen extends Biome{
	
	Generator[] generators;

	
	public BasicTerrainGen(Generator[] generators){
		this.generators = generators;
	}
	
	public void applyToWorld(World w, int left, int right){
		for (int i=0; i<generators.length; i++){
			generators[i].applyToWorld(w);
		}
	}

	@Override
	public boolean isFound(int distanceOut) {
		return true;
	}
	
}
