package terraingeneration.generators;

import java.util.ArrayList;

import terraingeneration.World;

public class SimpleLiquid extends Generator{
	
	int resolution, liquidID, volume;
	double startDepth, endDepth;
	
	public SimpleLiquid(int liquidID, double startDepth, double endDepth, int resolution, int volume){
		this.startDepth=startDepth;
		this.endDepth=endDepth;
		this.resolution=resolution;
		this.liquidID=liquidID;
		this.volume = volume;
	}
	
	@Override
	public void applyToWorld(World w) {
		for(int y=(int) (w.getHeight()*startDepth); y<w.getHeight()*endDepth; y+= resolution){
			for(int x=0; x<w.getWidth()-1; x+=resolution){
				for(int v=0; v<3; v++){
					int px=x;
					int py=y;
					
					while(py<w.getHeight()-1 && w.getTerrainAt(px,py)!=World.AIR){
						py++;
					}
					int trials = 0;
					while(trials<30 && px>0 && py<w.getHeight()-1 && px< w.getWidth()-2 ){//k
						trials++;
						if(w.getTerrainAt(px,py+1)==World.AIR){
							py++;
							trials=0;
						}
						else if(w.getTerrainAt(px+1,py)==World.AIR){
							px++;
						}
						else if(w.getTerrainAt(px-1,py)==World.AIR){
							px--;
						}
						else{
							break;
						}
					}
					if (px>0 && py<w.getHeight()-1 && px< w.getWidth()-2 ){
						depositLiquid(w,px,py);
					}
				}
			}
		}
	}

	private void depositLiquid(World w, int px, int py) {
		int sumValue = 0;
		while(sumValue<this.volume && w.getTerrainAt(px,py)==World.AIR){
			int lv = getLayerValue(w,px,py);
			if(lv==-1){
				break;
			} else{
				sumValue+=lv;
				fillLayer(w,px,py);
				py--;
			}
		}
			
	}

	private void fillLayer(World w, int px, int py) {
		int a=1, b=1;
		w.setTerrainAt(px, py, this.liquidID);
		while(px-a>=0 && w.getTerrainAt(px-a,py)==World.AIR){
			w.setTerrainAt(px-a, py, this.liquidID);
			a++;
		}
		while(px+b<w.getWidth()-1 && w.getTerrainAt(px+b,py)==World.AIR){
			w.setTerrainAt(px+b, py, this.liquidID);
			b++;
		}
	}
	
	private int getLayerValue(World w, int px, int py) {
		int a=1, b=1, value=1;
		while(px-a>=0 && w.getTerrainAt(px-a,py)==World.AIR){
			value++;
			a+=1;
			if (w.getTerrainAt(px-a,py+1)==World.AIR){
				return-1;
			}
		}
		while(px+b<w.getWidth()-1 && w.getTerrainAt(px+b,py)==World.AIR){
			value++;
			b+=1;
			if (w.getTerrainAt(px+b,py+1)==World.AIR){
				return-1;
			}
		}
		return value;
	}

}
