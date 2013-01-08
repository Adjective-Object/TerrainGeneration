package terraingeneration.blockattributes;

import terraingeneration.World;

public class PropogatesAlongSurface extends BlockAttribute{
	
	int pA, pI;
	double chance;
	
	public PropogatesAlongSurface(int propogatesInto, int propogatesAlong,  double propogationChance){
		this.pA=propogatesAlong;
		this.pI=propogatesInto;
		this.chance=propogationChance;
	}

	@Override
	public boolean stepBlock(World w, int xo, int yo) {
		for (int x=-1; x<=1; x++){
			for(int y=-1; y<1; y++){
					if(w.isInRange(x+xo, y+yo, 1) &&
							w.getTerrainAt(x+xo, y+yo)==pA &&
							w.getSeed().nextDouble()<=chance &&
							w.bordersBlock(World.AIR, x+xo,y+yo)){
						w.setTerrainAt(x+xo, y+yo,pI);
						return true;
					}
			}
		}
		return false;
	}
	
	
}
