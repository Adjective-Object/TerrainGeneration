package terraingeneration.generators;

import terraingeneration.World;

public class Desert extends Generator{
	
	static final int depth = 50, wander= 3, maxwidth=200;
	
	double chance;
	int blockID;
	
	public Desert(int blockID, double chance){
		this.chance=chance;
		this.blockID=blockID;
	}
	
	@Override
	public void applyToWorld(World w) {
		int pre=-1, nex=-1;
		for (int i=0; i<w.getWidth(); i++){
			if(w.getSeed().nextDouble()<=chance || (pre!=-1 && i-pre>maxwidth) ){
				nex = i;
			}
			if(pre!=nex && pre!=-1){
				makeDesert(w,pre,nex);
			}
			pre = nex;
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
		while(y!=0 && w.getTerrainAt(x, y)!=World.AIR){
			w.setTerrainAt(x, y, blockID);
			y--;
		}
	}

}
