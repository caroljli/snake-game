
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO ;

/**
 * The Health object appears for a brief period of time, and stays for a brief period of time.
 * When the Snake intersects with it, it reduces the length of the snake.
 *
 */

public class Health extends GameObj {
	public static final String IMG = "files/health.png";
	public static final int INIT_X = (int)(Math.random() * 720); 
	public static final int INIT_Y = (int)(Math.random() * 520); 
	public static final int INIT_VEL_X = 0;
	public static final int INIT_VEL_Y = 0;
	public static final int SIZE = 30; 
	public static final int MAX_NUM_HEALTH = 2;

	private static BufferedImage img;
	
	/**
	 * Constructor for Health object, which reads the file and generates a random
	 * Health object in the court.
	 * @param width and height of the game board to generate the health object within.
	 */
	public Health(int courtWidth, int courtHeight) { 
		super(INIT_VEL_X, INIT_VEL_Y, INIT_X, INIT_Y, SIZE, SIZE, courtWidth,
				courtHeight);
		try {
			if (img == null) {
				img = ImageIO.read(new File(IMG));
			}
		} catch (IOException e) {
			System.out.println("Internal Error:" + e.getMessage());
		}
		
	}

	public void draw(Graphics g) {
		g.drawImage(img, posx, posy, width, height, null);
	}
	
	/**
	 * Respawn method for Health object at a different location.
	 */
	public void respawn() {
		posx = (int)(Math.random() * 800);
		posy = (int)(Math.random() * 600);
	}
}