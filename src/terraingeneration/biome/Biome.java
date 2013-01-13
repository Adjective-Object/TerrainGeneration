package terraingeneration.biome;

import terraingeneration.World;
import terraingeneration.generators.Generator;

public abstract class Biome {
	
	public abstract void applyToWorld(World w, int leftBoundary, int rightBoundary);

	public abstract boolean isFound(int distanceOut);
}
