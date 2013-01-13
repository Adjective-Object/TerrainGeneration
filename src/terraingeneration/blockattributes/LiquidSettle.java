package terraingeneration.blockattributes;

import terraingeneration.World;

public class LiquidSettle extends BlockAttribute{

	@Override
	public boolean stepBlock(World w, int x, int y) {//failure of a liquid system.
		if(w.isInRange(x, y+1, 0) && w.getTerrainAt(x, y+1)==World.AIR){
			w.setTerrainAt(x, y+1, w.getTerrainAt(x,y));
			w.setTerrainAt(x, y, World.AIR);
			return true;
		}
		
		else{
			int p=1;
			if(w.getSeed().nextBoolean()){
				p=-1;
			}
			for (int i=0; i<2; i++){
				if(w.isInRange(x+p, y, 0) && w.getTerrainAt(x+p, y)==World.AIR){
					w.setTerrainAt(x+p, y, w.getTerrainAt(x,y));
					w.setTerrainAt(x, y, World.AIR);
					return true;	
				}
				p=-p;
			}
			return false;
		}
	}

}
