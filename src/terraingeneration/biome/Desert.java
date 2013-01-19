package terraingeneration.biome;

import terraingeneration.World;

public class Desert extends Biome{
	
	static final int depth = 50, wander= 3, maxwidth=200;
	static final float pyramidChance=1.0F;
	static final int wallThickness=5, sandHearth=3;
	
	double chance;
	
	public Desert(){}
	
	public void applyToWorld(World w, int left, int right){
		makeDesert(w,left,right-1);
		if(w.getSeed().nextFloat()<=pyramidChance){
			makePyramid(w,left+(right-left)/2, 20+w.getSeed().nextInt(10), World.WOOD);
		}
	}
	
	private void makeDesert(World w, int left, int right){
		int startDepth=0, border = left+(right-left)/2;
		while(w.getTerrainAt(left,startDepth)==World.AIR){
			startDepth++;
		}
		int CDL=startDepth;
		for(int i=left; i<border; i++){
			int p=-1;
			if(CDL<startDepth+depth){
				p=1;
			}
			if(w.getSeed().nextBoolean()){
				CDL+=p*w.getSeed().nextInt(wander);
			}
			fillColumn(w, i, CDL);
		}
		
		while(w.getTerrainAt(right,startDepth)==World.AIR){
			startDepth++;
		}
		int CDR=startDepth;
		for(int i=right; i>=border; i--){
			int p=-1;
			if(CDR<startDepth+depth){
				p=1;
			}
			if(w.getSeed().nextBoolean()){
				CDR+=p*w.getSeed().nextInt(wander);
			}
			fillColumn(w, i, CDR);
		}
	}
	
	private void fillColumn(World w, int x, int y){
		int ty = 0;
		boolean hitsurface=false;
		while(ty<y || w.getTerrainAt(x, ty)==World.AIR){
			if(w.getTerrainAt(x, ty) != World.AIR){
				hitsurface=true;
			}
			if(hitsurface){
				w.setTerrainAt(x, ty, World.SAND);
			}
			ty++;
		}
	}
	
	private void makePyramid(World w, int center, int width, int pyramidBlock){
		//finding tip of pyramid 
		int y=0;
		while (w.getTerrainAt(center,y)==World.AIR){
			y++;
		}
		y-=width;
		if(y<0){y=0;}
		
		for (int i=0; i<width; i++){
			int block = pyramidBlock;
			if (i>=Desert.wallThickness){
				block=World.AIR;
			}
			w.setTerrainAt(center,y, block);
			for (int left=1; left<width-i; left++){
				w.setTerrainAt(center-left, y+left, block);
			}
			for (int right=1; right<width-i; right++){
				w.setTerrainAt(center+right, y+right, block);
			}
			y++;
		}
		
		for(int x=center-width-sandHearth; x<center+width+sandHearth; x++){
			int ty = y;
			while(w.getTerrainAt(x, ty)==World.AIR){
				w.setTerrainAt(x, ty, World.SAND);
				ty++;
			}
		}
		
		 
	}
	
	@Override
	public boolean isFound(int distanceOut) {
		return distanceOut>200;
	}

}
