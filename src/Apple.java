
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * The Heart class extends GameObj and is a 10 point addition when eaten by the snake.
 *
 */
public class Apple extends GameObj {
	public static final String IMG = "files/apple.png";
	public static final int INIT_X = (int)(Math.random() * 740);
	public static final int INIT_Y = (int)(Math.random() * 540); 
	public static final int INIT_VEL_X = 0;
	public static final int INIT_VEL_Y = 0;
	public static final int SIZE = 30; // size of apple!
	public static final int MAX_NUM_LEMONS = 5; // the max. number of hearts on the board at any time

	private static BufferedImage img;
	
	public Apple(int courtWidth, int courtHeight) {
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
	/**
	 * Draws all Apples and places it within object list.
	 */
	@Override
	public void draw(Graphics g) {
		for (int i=0; i< gameObjects.size(); i++) {
		Point p = gameObjects.get(i);
		g.drawImage(img, p.x, p.y, width, height, null);
		}
	}

	/**
	 * Checks for intersection.
	 */
	@Override 
	public boolean intersects(GameObj obj) {
		for (int i=0; i<gameObjects.size();i++) {
			Point p = gameObjects.get(i);
			if (p.x + width >= obj.posx
					&& p.y + height >= obj.posy
					&& obj.posx + obj.width >= p.x
					&& obj.posy + obj.height >= p.y) {
				
				//has intersected, remove heart
				remove(i);
				return true;
			}
		}
		return false;
	}

	/**
	 * Generates random number of Apples at any given time, with a max of 3 and a min of 1.
	 */
	@Override
	public void add() {
		int num_times = (int)(Math.random()*3);
		if ((num_times == 0) && (gameObjects.size() == 0)) {
			num_times = 1;
		}
		if (gameObjects.size() <= MAX_NUM_LEMONS) {
			for(int i = 0; i<num_times; i++) {
				int randomX = (int)(Math.random() * maxX);
				int randomY = (int)(Math.random() * maxY);
				gameObjects.add(new Point(randomX, randomY));
			}
		}
	}
}