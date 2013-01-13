package terraingeneration.biome;

import terraingeneration.World;

public class Spawn extends Biome{
	
	static final float mineDepth = 0.5F;

	@Override
	public void applyToWorld(World w, int leftBoundary, int rightBoundary) {
		int surf=0, x=leftBoundary+(rightBoundary-leftBoundary)/2;
		//find surface
		while(w.getTerrainAt(x,surf)==World.AIR){
			surf++;
		}
		
		
		makeSpawnShop(w,x,surf);
		int center = leftBoundary+(rightBoundary-leftBoundary)/2;
		
		for(int i=center-25; i<center+25; i++){
			for(int y=surf-10; y>0; y--){
				w.setTerrainAt(i,y,World.AIR);
			}
		}
		
		for(int i=leftBoundary; i<rightBoundary; i++){
			for(int y=(int)(0.2F*w.getHeight()); y<w.getHeight()*mineDepth; y++){
				if(w.getTerrainAt(i,y)==World.AIR &&
						w.getSeed().nextDouble()<=0.1){
					w.setTerrainAt(i,y,World.TORCH);
				}
			}
		}
	}
	
	private void makeSpawnShop(World w, int x, int y){
		
		
		int O=World.AIR;
		int W=World.DIRT;//TODO wood
		int S=World.STONE;
		int D=World.DIRT;
		int I=-1;
		
		int[][] shop = {
				{O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O},
				{O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O},
				{O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O},
				{O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O},
				{O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O},
				{O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,W,W,W,W,W,W,W,W,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O},
				{O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,W,O,O,O,O,O,O,O,O,W,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O},
				{I,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,W,O,O,O,O,O,O,O,O,O,O,W,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,I},
				{I,I,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,W,O,O,O,O,O,O,O,O,O,O,O,O,W,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,I,I},
				{I,I,I,I,I,O,O,O,O,O,O,O,O,O,O,O,O,O,W,O,O,O,O,O,O,O,O,O,O,O,O,W,O,O,O,O,O,O,O,O,O,O,O,O,O,I,I,I,I,I},
				{I,I,I,I,I,I,I,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,I,I,I,I,I,I,I},
				{I,I,I,I,I,I,I,I,I,I,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,I,I,I,I,I,I,I,I,I,I},
				{I,I,I,I,I,I,I,I,I,I,I,I,O,O,O,O,O,O,W,W,W,W,W,W,W,W,W,W,W,W,W,W,O,O,O,O,O,O,I,I,I,I,I,I,I,I,I,I,I,I},
				{I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,S,S,S,S,S,S,S,S,S,S,S,S,S,S,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I},
				{I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,S,S,S,S,S,S,S,S,S,S,S,S,S,S,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I},
			};
		
		w.makeStructure(shop,
				x - shop[0].length/2,
				y - shop.length +3);
		
	}
	
	public boolean isFound (int dOut){
		return dOut==0;
	}
}
