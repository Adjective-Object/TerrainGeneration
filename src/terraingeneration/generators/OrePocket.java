package terraingeneration.generators;

import terraingeneration.World;

public class OrePocket extends Generator{
	
	int blockID, veinSize;
	double rarity, upperBound, lowerBound;
	
	public OrePocket(int blockID, double rarity, double lowerBound, double upperBound, int veinSize){
		this.blockID=blockID;
		this.lowerBound=lowerBound;
		this.upperBound=upperBound;
		this.veinSize=veinSize;
		this.rarity=rarity;
	}
	
	public void applyToWorld(World w){
		int range = (int) ((upperBound-lowerBound)*w.getHeight());
		System.out.println(range);
		for(int x=0; x<w.getWidth(); x++){
			for(int y=0; y<w.getHeight(); y++){
				if(y<upperBound*w.getHeight() && y>lowerBound*w.getHeight()){
					if(w.getSeed().nextFloat()<=
						rarity * Math.sqrt(1 - Math.pow( (2.0/range)*(y-(lowerBound*w.getHeight())-range/2),2) )
						){
						makeOreVein(w,x,y,(int)(veinSize),veinSize,blockID);
					}
				}
			}
		}
	}
	
	public static void makeOreVein(World w, int x, int y, int veinSize, int failcap, int blockID){
		int timetofail = 0;
		int xo = 0, yo = 0;
		for(int i=0; i<veinSize; i++){
			while(w.getTerrainAt(x+xo,y+yo)==blockID && timetofail<failcap){
				if(w.getSeed().nextFloat()<0.5){
					xo+=w.getSeed().nextInt(2)-w.getSeed().nextInt(2);
				}
				else{
					yo+=w.getSeed().nextInt(2)-w.getSeed().nextInt(2);
				}
				
				if(x+xo<=0 || x+xo>=w.getWidth() || y+yo<=0 || y+yo>=w.getHeight() || Math.abs(xo)>=veinSize/2|| Math.abs(yo)>=veinSize/2){
					xo=0;
					yo=0;
				}
				timetofail++;
			}
			timetofail=0;
			if(w.getTerrainAt(x+xo, y+yo)!=World.AIR){
				w.setTerrainAt(x+xo,y+yo,blockID);
			}	
		}
	}
	
}
