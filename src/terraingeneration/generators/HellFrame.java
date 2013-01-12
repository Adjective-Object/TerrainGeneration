package terraingeneration.generators;

import terraingeneration.World;

public class HellFrame extends Generator{
	
	double start, end;
	
	static final int wanderrange = 5;
	static final int ashrange = 20;
	static final int bottomborder = 5;
	static final int openAir = 60;

	public HellFrame(double start, double end){
		
		this.start = start;
		this.end=end;
	}
	
	@Override
	public void applyToWorld(World w) {
		//build framework
		for(int i=0; i<w.getWidth(); i++){
			for(int y = (int) (w.getHeight()*start)-w.getSeed().nextInt(wanderrange); y<w.getHeight()*end; y++){
				//ash and air and lava
				if(y<=w.getHeight()*start+w.getSeed().nextInt(ashrange)||
						y>=w.getHeight()*end-w.getSeed().nextInt(ashrange)-bottomborder||
						y>=w.getHeight()*end-bottomborder
						){
					w.setTerrainAt(i,y,World.ASH);
				} else if (y>=w.getHeight()*start+openAir){
					//w.setTerrainAt(i,y,World.LAVA);
					w.setTerrainAt(i,y,World.AIR);
				} else{
					w.setTerrainAt(i,y,World.AIR);
				}
			}
		}		
	}
	//Chewing on Tin foil - Ska
	
	
	
}
