
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Candy extends GameObj {
	public static final String IMG = "files/candy.png";
	public static final int SIZE = 22; //size of the bad object
	public static final int INIT_X = (int)(Math.random() * 754); 
	public static final int INIT_Y = (int)(Math.random() * 554); 
	public static final int INIT_VEL_X = 0;
	public static final int INIT_VEL_Y = 0;

	private static BufferedImage img;

	/**
	 * Constructor for Candy class, initializing the object that adds points to the snake.
	 * @param courtWidth, courtHeight indicating the size of the court.
	 */
	public Candy (int courtWidth, int courtHeight) {
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
	
	public void add() {
		int numTimes = (int)(Math.random()*2);
		for (int i =0; i<numTimes; i++) {
			int randomX = (int)(Math.random() * maxX);
			int randomY = (int)(Math.random() * maxY);
			gameObjects.add(new Point(randomX, randomY));
		}
	}
		
		@Override
	public void draw(Graphics g) {
		for(int i = 0; i< gameObjects.size();i++) {
			Point p = gameObjects.get(i);
			g.drawImage(img, p.x, p.y, width, height, null);
		}
	}
}