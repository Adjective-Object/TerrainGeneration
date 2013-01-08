package terraingeneration;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;


public class WorldViewer {
	
	static final Color[] renderColors= new Color[]{
		new Color(200,255,255),
		new Color(165,100,50),
		new Color(50,190,60),
		new Color(100,100,100),
		new Color(150,65,15),
		new Color(255,0,0),
		new Color(30,30,255)
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
