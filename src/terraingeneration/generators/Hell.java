package terraingeneration.generators;

import terraingeneration.World;

public class Hell extends Generator{
	
	double start, end;
	
	public Hell(double start, double end){
		this.start = start;
		this.end=end;
	}
	
	@Override
	public void applyToWorld(World w) {
		for(int i=0; i<w.getWidth(); i++){
			for(int y = (int) (w.getHeight()*start); y<w.getHeight()*end; y++){
				w.setTerrainAt(i,y,World.AIR);
			}
		}
	}
	//Chewing on Tin foil - Ska
	
	
	
}
