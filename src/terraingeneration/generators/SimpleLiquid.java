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
				for(int v=0; v<1; v++){
					int px=x;
					int py=y;
					
					int trials = 0, remainingvolume = this.volume;
					int dir = 1;
					while(trials<200 && remainingvolume>0 && px>0 && py<w.getHeight()-1 && px< w.getWidth()-2 ){//k
						//System.out.println(px+" , "+py+" "+w.getTerrainAt(px,py));
						trials++;
						if(w.getTerrainAt(px,py)!=World.AIR){
							py--;
						}
						else if(w.getTerrainAt(px,py+1)==World.AIR){
							py++;
							trials=0;
						}
						else if(w.getTerrainAt(px+dir,py)==World.AIR){
							px+=dir;
						}
						else if(w.getTerrainAt(px+dir,py)!=World.AIR){
							dir=dir*-1;
							if(w.getTerrainAt(px+dir,py)!=World.AIR){
								remainingvolume = depositLiquid(w,px,py,remainingvolume);
							}
						}
						if(trials>50){
							remainingvolume = depositLiquid(w,px,py,remainingvolume);
						}
					}
				}
			}
		}
	}

	private int depositLiquid(World w, int px, int py, int level) {
		int sumValue = 0;
		while(sumValue<level && w.getTerrainAt(px,py)==World.AIR){
			int lv = getLayerValue(w,px,py);
			if(lv==-1){
				return level-sumValue;
			} else{
				sumValue+=lv;
				fillLayer(w,px,py);
				py--;
			}
		}
		return level-sumValue;
			
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
			if (w.isInRange(px-a, py+1, 0) && w.getTerrainAt(px-a,py+1)==World.AIR){
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
