package terraingeneration.generators;

import terraingeneration.World;

public class ShallowCave extends Cave{

	
	static final int descent = 10, xwander=40;
	
		public void populateCave(World w, CaveNode node){
			
			int wid = node.width;
			if (w.getSeed().nextFloat()<=0.2){
				wid--;
		}
		
		if(node.width>1){
			CaveNode c = new CaveNode(
					w,
					node,
					(int)(node.x+w.getSeed().nextFloat()*xwander-w.getSeed().nextFloat()*xwander),
					(int)(node.y+(0.5+w.getSeed().nextFloat()/2)*descent),
					wid
					);
			node.children.add(c);
		}
		
		
		for(int i=0; i<node.children.size(); i++){
			populateCave(w,node.children.get(i));
		}

	}
}