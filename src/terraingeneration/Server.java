package terraingeneration;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Server {
	
	public Server(World w, int port){
		
	}
	
	
	public static void main(String[] args){
		World g = new World (800,600,13);
		
		JFrame frame = new JFrame("Generated Terrain");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JLabel image = new JLabel(new ImageIcon(WorldViewer.drawTerrain(g)));
		frame.getContentPane().add(image, BorderLayout.CENTER);
		
		frame.pack();
		frame.setVisible(true);
		
	}
	
}
