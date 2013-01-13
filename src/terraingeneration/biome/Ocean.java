package terraingeneration.biome;

import terraingeneration.World;

public class Ocean extends Biome{
	
	static final int oceanDepth = 50, wander=3, underLevel=5;
	
	public void applyToWorld(World w, int left, int right){
		int startDepth=0;
		while(w.getTerrainAt(left,startDepth)==World.AIR){
			startDepth++;
		}
		
		int start = left, end=right, i=1;
		if(left<w.getWidth()/2){
			start=right;
			end=left;
			i=-1;
		}
		
		int depth=startDepth;
		for(int x=start; x!=end; x+=i){
			int p=-1;
			if(startDepth+this.oceanDepth>depth){
				p=1;
			}
			if(w.getSeed().nextBoolean()){
				depth+=p*w.getSeed().nextInt(wander);
			}
			//drawline
			fillColumn(w,x,depth, startDepth);
		}
	}
	
	private void fillColumn(World w, int x, int by, int depth){
		int counter = 0;
		int y=by;
		while(y>=0){
			counter++;
			if(counter<10 && y>depth-3){
				w.setTerrainAt(x, y, World.SAND);
			}
			else if(y>depth+3){
				w.setTerrainAt(x, y, World.WATER);
			}
			else{
				w.setTerrainAt(x, y, World.AIR);
			}
			y--;
		}
		y = by+1;
		counter=0;
		while(w.getTerrainAt(x,y)==World.AIR && counter<underLevel){
			w.setTerrainAt(x,y,World.DIRT);
			counter++;
			y++;
		}
	}
	
	@Override
	public boolean isFound(int distanceOut) {
		return true;
	}
	
}
