package terraingeneration.blockattributes;

import terraingeneration.World;

public class EmitsLight extends BlockAttribute{
	
	int lightradius;
	
	public EmitsLight(int radius){
		this.lightradius=radius;
	}

	@Override
	public boolean stepBlock(World w, int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
