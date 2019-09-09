
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import javax.imageio.ImageIO;

public class Lemon extends GameObj {
	/**
	 * The Lemon class extends GameObj and is a 30 point addition.
	 */
	public static final String IMG = "files/lemon.png";
	
	public static final int INIT_X = (int)(Math.random() * 740); //randomized INITIAL x coord
	public static final int INIT_Y = (int)(Math.random() * 540); //randomized INITIAL y coord
	public static final int INIT_VEL_X = 0;
	public static final int INIT_VEL_Y = 0;
	public static final int SIZE = 30; // size of the lemon
	public static final int MAX_NUM_LEMONS = 5; // max number of lemons to be on the board at any one time

	private static BufferedImage img;

	private LinkedList<Point> lemon_objs;

	/**
	 * Constructor for lemon object.
	 * @param courtWidth
	 * @param courtHeight
	 */
	public Lemon(int courtWidth, int courtHeight) {
		super(INIT_VEL_X, INIT_VEL_Y, INIT_X, INIT_Y, SIZE, SIZE, courtWidth,
				courtHeight);
		try {
			if (img == null) {
				img = ImageIO.read(new File(IMG));
			}
		} catch (IOException e) {
			System.out.println("Internal Error:" + e.getMessage());
		}
		
	lemon_objs = new LinkedList<Point>();
	lemon_objs.addFirst(new Point(posx, posy));
		
	}
	
	/**
	 * Draw all lemons, check for intersection, random generation of lemon objects one at 
	 * a time at random positions.
	 */
	@Override
	public void draw(Graphics g) {
		for (int i=0; i< lemon_objs.size(); i++) {
		Point p = lemon_objs.get(i);
		g.drawImage(img, p.x, p.y, width, height, null);
		}
	}

	@Override 
	public boolean intersects(GameObj obj) {
		for (int i=0; i<lemon_objs.size();i++) {
			Point p = lemon_objs.get(i);
			if (p.x + width >= obj.posx
					&& p.y + height >= obj.posy
					&& obj.posx + obj.width >= p.x
					&& obj.posy + obj.height >= p.y) {
				removeLemon(i);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Adds lemon.
	 */
	public void addLemon() {
		if (lemon_objs.size() <= MAX_NUM_LEMONS) {
			int rand_x = (int)(Math.random() * maxX);
			int rand_y = (int)(Math.random() * maxY);
			lemon_objs.add(new Point(rand_x, rand_y));
			
		}
	}
	
	/**
	 * Removes lemon at given position in LinkedList.
	 * @param position in LinkedList
	 */
	public void removeLemon(int i) {
		lemon_objs.remove(i);
	}
	
}