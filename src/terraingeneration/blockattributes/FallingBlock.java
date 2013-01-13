package terraingeneration.blockattributes;

import terraingeneration.World;

public class FallingBlock extends BlockAttribute{
	
	@Override
	public boolean stepBlock(World w, int x, int y) {
		if(y!=w.getHeight()-1 && w.getTerrainAt(x, y+1)==World.AIR){
			w.setTerrainAt(x,y+1,w.getTerrainAt(x, y));
			w.setTerrainAt(x, y, World.AIR);
			return true;
		}
		return false;
	}

}
