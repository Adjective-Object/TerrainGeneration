package terraingeneration.generators;

import terraingeneration.World;

public class ScuffSurfaces extends OrePocket{

	int intoID;
	
	public ScuffSurfaces(int blockID, int intoID, double rarity, double lowerBound,
			double upperBound, int veinSize) {
		super(blockID, rarity, lowerBound, upperBound, veinSize);
		this.intoID=intoID;
		// TODO Auto-generated constructor stub
	}

	public void applyToWorld(World w){
		int range = (int) ((upperBound-lowerBound)*w.getHeight());
		for(int x=0; x<w.getWidth(); x++){
			for(int y=(int)(lowerBound*w.getHeight()); y<upperBound*w.getHeight(); y++){
				if(w.isInRange(x, y, 1) && w.getTerrainAt(x, y)==this.blockID && w.bordersBlock(intoID, x, y)){
					if(w.getSeed().nextFloat()<=
						rarity * Math.sqrt(1 - Math.pow( (2.0/range)*(y-(lowerBound*w.getHeight())-range/2),2) )
						){
						makeOreVein(w,x,y,(int)(veinSize),veinSize,blockID);
					}
				}
			}
		}
	}


}
