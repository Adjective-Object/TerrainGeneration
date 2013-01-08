package terraingeneration.generators;

import terraingeneration.World;

public class Grasser extends Generator{
	
	int grass, resolution;
	double startdepth, enddepth;
	
	static final int trialcap= 15;
	
	public Grasser(int grass, double startdepth, double enddepth, int resolution){
		this.grass=grass;
		this.resolution=resolution;
		this.startdepth=startdepth;
		this.enddepth=enddepth;
	}
	
	@Override
	public void applyToWorld(World w) {
		System.out.println("Grassing Terrain");
		//surface layer falling
		for(int i=0; i<w.getWidth();i++){
			for(int y=0; y<w.getHeight();y++){
				if(w.getTerrainAt(i,y)==World.DIRT){
					w.setTerrainAt(i, y, grass);
					break;
				}
				else if(w.getTerrainAt(i,y)!=World.AIR){
					break;
				}
			}
		}
		
		//dropping out random seed points
		for(int i=0; i<w.getWidth();i+=resolution){
			for(int y=(int) (w.getHeight()*startdepth); y<w.getHeight()*enddepth;y+=resolution){
				int trials = 0;
				int x=i;
				while (w.isInRange(x, y, 1) &&
						(w.getTerrainAt(x, y)!=World.DIRT || !w.bordersBlock(World.AIR, x, y)) &&
						trials<trialcap){
					y+=w.getSeed().nextInt(1)-w.getSeed().nextInt(1);
					x+=w.getSeed().nextInt(1)-w.getSeed().nextInt(1);
					trials++;
				} if(trials<trialcap){
					w.setTerrainAt(x, y, grass);
				}
			}
		}
	}

}
