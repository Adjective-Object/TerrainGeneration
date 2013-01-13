package terraingeneration.blockattributes;

import terraingeneration.World;

public class WallMounted extends BlockAttribute{

	@Override
	public boolean stepBlock(World w, int x, int y) {
		if(isMount(w,x,y)){
			
			return false;
		}
		else{
			w.setTerrainAt(x, y, World.AIR);
			return true; //TODO dropping
		}
	}
	
	private boolean isMount(World w, int x, int y){
		for(int a=-1; a<=1 ;a++){
			int p=0;
			if(a==0){
				p=1;
			}
			//System.out.println("==");
			for (int b=0; b<=p; b++){
				//System.out.println(a+" "+b);
				if(w.isInRange(x+a,y+b,0) && (b==0 && p==0) && w.getTerrainAt(x+a,y+b)!=World.AIR){//TODO this mounts on liquids, do block trait classifications
					//System.out.println("[");
					return true;
				}
			}
		}
		return false;
	}

}
