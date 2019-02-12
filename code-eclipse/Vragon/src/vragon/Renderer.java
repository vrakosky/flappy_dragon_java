package vragon;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Renderer extends JPanel {

	private static final long serialVersionUID = 1L;
	protected static BufferedImage image;
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		try{
			image = ImageIO.read(getClass().getResourceAsStream("/eye5.png"));
		} catch(IOException e){
			e.printStackTrace();
		}	
		
		Main.vragon.repaint(g);
	}
}
