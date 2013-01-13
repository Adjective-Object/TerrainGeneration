package terraingeneration;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

public class Server {
	
	public Server(World w, int port){
		
	}
	
	
	public static void main(String[] args){
		World g = new World (1000,1000,13);
		
		JFrame frame = new JFrame("Generated Terrain");
		frame.setPreferredSize(new Dimension(800,600));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JLabel image = new JLabel(new ImageIcon(WorldViewer.drawTerrain(g)));
		JScrollPane jsp = new JScrollPane(image);

		frame.getContentPane().add(jsp, BorderLayout.CENTER);
		
		frame.pack();
		frame.setVisible(true);
		
	}
	
}
