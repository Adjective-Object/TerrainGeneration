package terraingeneration.blockattributes;

import terraingeneration.World;

public class TransformOnContact extends BlockAttribute{
	
	int into,contact;
	
	public TransformOnContact(int into, int contact){
		this.into=into;
		this.contact=contact;
	}
	
	@Override
	public boolean stepBlock(World w, int x, int y) {
		if (w.bordersBlock(contact, x, y)){
			w.setTerrainAt(x, y, into);
		}
		return false;//does not ever need to change others
	}

}
