package terraingeneration.biome;

import terraingeneration.World;


public class NothingSpecial extends Biome {
		
	public void applyToWorld(World w, int leftBoundary, int rightBoundary){
		
	}
	
	@Override
	public boolean isFound(int distanceOut) {
		return distanceOut>0;
	}
}
