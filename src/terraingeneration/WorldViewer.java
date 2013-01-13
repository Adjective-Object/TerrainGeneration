package terraingeneration;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;


public class WorldViewer {
	
	static final Color[] renderColors= new Color[]{
		new Color(200,255,255),	//air
		new Color(165,100,50),	//dirt
		new Color(50,190,60),	//grass
		new Color(100,100,100),	//stone
		new Color(150,65,15),	//copper
		new Color(205,175,149),	//iron
		new Color(65,105,225),	//water
		new Color(240,105,30),	//lava
		new Color(60,60,60),	//ash
		new Color(180,30,30),	//hellstone
		new Color(72,61,139),	//mithril
		new Color(10,10,10),	//obsidian
		new Color(250,250,210)	//sand
	};
	
	public static BufferedImage drawTerrain(World g) {
		BufferedImage b = new BufferedImage(g.getTerrain().length,g.getTerrain()[0].length,BufferedImage.TYPE_INT_RGB);
		for(int x=0; x<g.getTerrain().length; x++){
			for(int y=0; y<g.getTerrain()[0].length; y++){
				b.setRGB(x, y, renderColors[g.getTerrainAt(x,y)].getRGB());
			}
		}
		
		return b;
	}
	
}
