package terraingeneration.blockattributes;

import terraingeneration.World;

public abstract class BlockAttribute{

	//returns true if it should update adjacent blocks.
	public abstract boolean stepBlock(World w, int x, int y);
	
}
