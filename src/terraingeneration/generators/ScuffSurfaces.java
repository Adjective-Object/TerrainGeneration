package terraingeneration.generators;

import terraingeneration.World;

public class ScuffSurfaces extends OrePocket{

	public ScuffSurfaces(int blockID, int intoID, double rarity, double lowerBound,
			double upperBound, int veinSize) {
		super(blockID, new int[] {intoID}, rarity, lowerBound, upperBound, veinSize);
	}

	public void applyToWorld(World w){
		for(int y=(int)(lowerBound*w.getHeight()); y<upperBound*w.getHeight(); y++){
			//System.out.println(y+" scuffing "+blockID+" into "+intoID);
			for(int x=0; x<w.getWidth(); x++){
				if(w.isInRange(x, y, 1) && w.getTerrainAt(x, y)==this.blockID && w.bordersBlock(this.canOverwrite[0], x, y)){
					if(w.getSeed().nextFloat()<=rarity){
						makeOreVein(w,x,y,veinSize,veinSize,blockID);
					}
				}
			}
		}
	}


}
