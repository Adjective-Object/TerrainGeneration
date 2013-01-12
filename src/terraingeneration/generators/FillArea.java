package terraingeneration.generators;

import terraingeneration.World;

public class FillArea extends Generator{

	int id, overid;
	double startheight, endheight;
	
	public FillArea(int blockID, int overwriteID, double startheight, double endheight) {
		this.id=blockID;
		this.overid=overwriteID;
		this.startheight=startheight;
		this.endheight=endheight;
		
	}
	
	@Override
	public void applyToWorld(World w) {
		for (int i=0; i<w.getWidth(); i++){
			for (int y=(int) (w.getHeight()*startheight); y<w.getHeight()*endheight; y++){
				if(w.getTerrainAt(i, y)==overid){
					w.setTerrainAt(i, y, id);
				}
			}
		}
	}

}
