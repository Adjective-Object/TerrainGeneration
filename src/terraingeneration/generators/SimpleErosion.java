package terraingeneration.generators;

import terraingeneration.World;

public class SimpleErosion extends Generator{

	int particles = 1;
	private int foldCap = 10;
	private int dumpSearchWidth = 10;
	
	private int x,y,dirtLoad,trials;
	private double ymom,xmom;
	
	public SimpleErosion(int particles){
		this.particles = particles;
	}
	
	public void applyToWorld(World w){
		for(int i=0; i<particles;i++){
			System.out.println("Erosion "+i+"/"+particles);
			this.y=0;
			this.x = (int) ((w.getWidth())/(float)particles+1)*(i+1);
			this.dirtLoad=0;
			this.trials=0;
			this.xmom=0;
			this.ymom=0;
			while (y<w.getHeight()-2 && trials<foldCap){
				trials++;
				int oldy=y;

				//limit x bounds
				if(x<1){x=1;}
				if(x>w.getWidth()-2){x=w.getWidth()-2;}
				
				stepParticle(w);
				
				//trials
				if(y>oldy){
					trials=0;
				}
			}
			dumpDirt(w);
		}
	}
	
	private void dumpDirt(World w) {
		int yup = 0;
		while (dirtLoad>0){
			for(int i=0; i<dumpSearchWidth; i++){
				if(x+i<w.getWidth() && w.getTerrainAt(x+i,y-yup)==World.AIR){
					w.setTerrainAt(x+i,y-yup,World.DIRT);
					dirtLoad-=1;
				}
				if(x+i>0 && w.getTerrainAt(x-i,y-yup)==World.AIR){
					w.setTerrainAt(x-i,y-yup,World.DIRT);
					dirtLoad-=1;
				}
			}
			yup++;
		}
	}

	private int slipAcross(World w, double chance){
		if(w.getSeed().nextDouble()<=chance){
			w.setTerrainAt(x,y+1,World.AIR);
			return dirtLoad+1;
		}
		return dirtLoad;
	}
	
	private void stepParticle(World w){
		if(w.getTerrainAt(x,y+1)==World.AIR){
			y++;
			ymom++;
		}
		else{
			//Shift to side while sliding down
			if(w.getTerrainAt(x,y+1)==World.DIRT){
				dirtLoad = slipAcross(w,(xmom+ymom)/10);
			}
			if (w.getTerrainAt(x+1,y+1)==World.AIR){//right
				y++;x++;
				ymom=ymom/2;xmom+=ymom;
			}
			else if (w.getTerrainAt(x-1,y+1)==World.AIR){//left
				y++;x--;
				ymom=ymom/2;xmom-=ymom;
			}
			//slide to left or right w/ momentum
			else{//path down blocked{
				if (w.getTerrainAt(x+1,y)==World.AIR){//right
					x++;
					xmom+=ymom;
				}
				else if (w.getTerrainAt(x-1,y)==World.AIR){//left
					x--;
					xmom-=ymom;
				}
				else{//straight down, turn
					if(xmom!=0){
						xmom+=ymom*(xmom/Math.abs(xmom));
					} else if (w.getSeed().nextBoolean()){
						xmom+=ymom;
					} else{
						xmom-=ymom;
					}
				}
				ymom=0;
			}
		}
	}
	
}
