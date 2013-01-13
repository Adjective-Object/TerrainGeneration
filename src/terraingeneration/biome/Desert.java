package terraingeneration.biome;

import terraingeneration.World;

public class Desert extends Biome{
	
	static final int depth = 50, wander= 3, maxwidth=200;
	
	double chance;
	
	public Desert(){}
	
	public void applyToWorld(World w, int leftBoundary, int rightBoundary){
		makeDesert(w,leftBoundary,rightBoundary-1);
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
		int ty = y;
		boolean hitsurface=false;
		while(ty<y){
			if(w.getTerrainAt(x, y) != World.AIR){
				hitsurface=true;
			}
			if(hitsurface){
				w.setTerrainAt(x, y, World.SAND);
			}
			ty++;
		}
	}
	
	@Override
	public boolean isFound(int distanceOut) {
		return distanceOut>800;
	}

}
