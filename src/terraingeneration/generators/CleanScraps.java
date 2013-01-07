package terraingeneration.generators;

import java.util.ArrayList;

import terraingeneration.World;

public class CleanScraps extends Generator{

	int scrapType;
	static final int threshold = 10;
	
	public CleanScraps(int scrapType){
		this.scrapType = scrapType;
	}
	
	@Override
	public void applyToWorld(World w) {
		System.out.println("Cleaning Scraps");
		
		boolean[][] okayBlocks = new boolean[w.getWidth()][w.getHeight()];
		for(int x=0; x<w.getWidth(); x++){
			for(int y=0; y<w.getHeight(); y++){
				okayBlocks[x][y]=false;
			}
		}
		for(int x=0; x<w.getWidth(); x++){
			for(int y=0; y<w.getHeight(); y++){
				if(!okayBlocks[x][y]){
					okayBlocks=checkCluster(w,scrapType,x,y,okayBlocks);
				}
			}
		}
	}

	//this does not work properly. (exploration is not exhaustive, essentially a raycast-to-hit)
	private static boolean[][] checkCluster(World w, int scrapType, int x, int y, boolean[][]okayBlocks) {
		ArrayList<int[]> currentBlocks= new ArrayList<int[]>(0);
		currentBlocks.add(new int[]{x,y});
		int blockTally=0;
		int talliger = 0;
		
		while (blockTally<threshold && currentBlocks.size()>talliger){
			int[] block = currentBlocks.get(talliger);
			if(w.getTerrainAt(block[0],block[1])!=scrapType){//if it's not the scrap "limiter"
				blockTally++;//count this block
				
				//add blocks exploring out from center
				if(block[0]>=x && block[0]<w.getWidth()-1){
					currentBlocks.add(new int[] {block[0]+1, block[1]});	}
				if(block[0]<=x && block[0]>0){
					currentBlocks.add(new int[] {block[0]-1, block[1]});	}
				if(block[1]>=y && block[1]<w.getHeight()-1){
					currentBlocks.add(new int[] {block[0],   block[1]+1});	}
				if(block[1]<=y && block[1]>0){
					currentBlocks.add(new int[] {block[0],   block[1]-1});	}
				
			}
			okayBlocks[block[0]][block[1]]=true;//say that this block has been checked out as part of a cluster
			talliger++;//remove checked block
		}
		
		if(blockTally<threshold){// if it was too small
			//System.out.println("cluster too small");
			for(int i=0; i<talliger; i++){
				w.setTerrainAt(currentBlocks.get(i)[0],currentBlocks.get(i)[1],scrapType);//replace it with air
			}
		}
		else{
			//System.out.println("cluster checks out");
		}
		
		return okayBlocks;
		
	}

}
