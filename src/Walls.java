
import java.awt.*;
import java.util.LinkedList;

public class Walls extends GameObj {
	public static final int INIT_X = (int)(Math.random() * 800); //randomized INITIAL x coord
	public static final int INIT_Y = (int)(Math.random() * 600); //randomized INITIAL y coord
	public static final int INIT_VEL_X = 0;
	public static final int INIT_VEL_Y = 0;
	public static final int SIZE = 22; 
	public static final int MAX_NUM_WALLS = 8;

	public LinkedList<Point> wallSize;
	
	public Walls (int courtWidth, int courtHeight) {
		super(INIT_VEL_X, INIT_VEL_Y, INIT_X, INIT_Y, SIZE, SIZE, courtWidth,
				courtHeight);
		wallSize = new LinkedList<Point>();
	
	}
	
	/**
	 * Add walls to points in different positions,
	 */
	@Override
	public void add() {
		int randomX = (int)(Math.random() * maxX);
		int randomY = (int)(Math.random() * maxY);
		gameObjects.add(new Point(randomX, randomY)); 
		int wall_width = (int)(Math.random()*50);
		int wall_height = (int)(Math.random()*50);
		wallSize.add(new Point(wall_width, wall_height));
		
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.RED);

		if (gameObjects.size() >= 1) {
		for(int i = 0; i< gameObjects.size();i++) {
			Point p = gameObjects.get(i);
			Point wall_coords = wallSize.get(i);
			g.fillRect(p.x,p.y,wall_coords.x,wall_coords.y);
		}
		}
	}
}