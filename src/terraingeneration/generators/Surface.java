package terraingeneration.generators;

import terraingeneration.World;

public class Surface extends Generator{
	
	static final int stoneTransition = 10;
	static final int drunkwalk = 10;
	static final float dirtLayer = 0.4F;
	
	
	public Surface(){}
	
	@Override
	
	
	public void applyToWorld(World w) {
		int[] surfacepoints = new int[ w.getWidth()/10+1];
		surfacepoints[0]=(int) (w.getHeight()*0.2);
		
		for(int i=1; i<w.getWidth()/10+1; i++){
			surfacepoints[i]=surfacepoints[i-1]+w.getSeed().nextInt(drunkwalk)-w.getSeed().nextInt(drunkwalk);
			System.out.print(surfacepoints[i]+" ");
		}
		
		for(int x=0; x<w.getTerrain().length; x++){
			int surfLevel = (int) (surfacepoints[x/10] + (surfacepoints[x/10+1]-surfacepoints[x/10]) * (x%10/10.0F) );
			for(int y=0; y<w.getTerrain()[0].length; y++){
				if(y>surfLevel && w.getTerrainAt(x,y)!=World.AIR){
					if(w.getSeed().nextFloat() >= (y-surfLevel-w.getHeight()*dirtLayer)*1.0/stoneTransition ){
						w.setTerrainAt(x,y,World.DIRT);
					}
					else{
						w.setTerrainAt(x,y,World.STONE);
					}
				}
				else{
					w.setTerrainAt(x,y,World.AIR);
				}
			}
		}
	}

}
