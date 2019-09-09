
import java.awt.Graphics;
import java.awt.Point;
import java.util.LinkedList;

/**
 *  Superclass for all objects in the game.
 *  Game objects exist in the game court. They have a position, 
 *  velocity, size and bounds.
 *  Position is always written within the bounds.
 */
public class GameObj {

	public int posx; 
	public int posy;

	public int width;
	public int height;
	
	public int vx;
	public int vy;

	public int maxX;
	public int maxY;
	
	// stores all objects in the game
	public LinkedList<Point> gameObjects;
	
	/**
	 * Constructor for game object, specifying all state-defining variables.
	 */
	public GameObj(int vx, int vy, int posx, int posy, 
		int width, int height, int courtWidth, int courtHeight){
		this.vx = vx;
		this.vy = vy;
		this.posx = posx;
		this.posy = posy;
		this.width = width;
		this.height = height;
		this.maxX = courtWidth - width;
		this.maxY = courtHeight - height; 
		gameObjects = new LinkedList<Point>();
		gameObjects.addFirst(new Point(posx, posy));
	}

	public void move(){
		posx += vx;
		posy += vy;

		clip();
	}

	/**
	 * Prevents the object from going outside of the bounds of the area 
	 * designated for the object. (i.e. Object cannot go outside of the 
	 * active area the user defines for it).
	 */ 
	public void clip(){
		if (posx < 0) posx = 0;
		else if (posx > maxX) posx = maxX;

		if (posy < 0) posy = 0;
		else if (posy > maxY) posy = maxY;
	}

	/**
	 * Checks to see if any objects in list intersects. Determined by comparing bound boxes.
	 * 
	 * @param other object to compare to
	 * @return boolean indicating intersection
	 */
	public boolean intersects(GameObj obj) {
		for (int i = 0; i < gameObjects.size(); i++) {
			Point p = gameObjects.get(i);
			if (p.x + width >= obj.posx
					&& p.y + height >= obj.posy
					&& obj.posx + obj.width >= p.x
					&& obj.posy + obj.height >= p.y) return true;
		}
		return false;
	}

	/**
	 * Adds game object to given position on the board. 
	 */
	public void add() {
		int randomX = (int)(Math.random() * maxX);
		int randomY = (int)(Math.random() * maxY);
		gameObjects.add(new Point(randomX, randomY));
		
	}
	
	/**
	 * Remove at given position i.
	 */
	public void remove(int i) {
		gameObjects.remove(i);
	}

	/**
	 * Checks to see if any objects in list intersects. Determined by comparing bound boxes.
	 * 
	 * @param object to compare intersection with
	 * @return boolean indicating whether an intersection will occur or not
	 */
	public boolean willIntersect(GameObj obj){
		int nextx = posx + vx;
		int nexty = posy + vy;
		int nextObjectX = obj.posx + obj.vx;
		int nextObjectY = obj.posy + obj.vy;
		return (nextx + width >= nextObjectX
				&& nexty + height >= nextObjectY
				&& nextObjectX + obj.width >= nextx 
				&& nextObjectY + obj.height >= nexty);
	}

	
	/** 
	 * Updates velocity post collision.
	 * @param direction of movement.
	 */
	public void bounce(Direction d) {
		if (d == null) return;
		switch (d) {
		case UP:    vy = Math.abs(vy); break;  
		case DOWN:  vy = -Math.abs(vy); break;
		case LEFT:  vx = Math.abs(vx); break;
		case RIGHT: vx = -Math.abs(vx); break;
		}
	}
	
	/** 
	 * Determines whether game object will hit a wall.
	 * @return direction of the wall, and null if clear.
	 */
	public Direction hitWall() {
		if (posx + vx < 0) {
			return Direction.LEFT;
		} else if (posx + vx > maxX) {
			return Direction.RIGHT;
		}
		if (posy + vy < 0) {
			return Direction.UP;
		} else if (posy + vy > maxY) {
			return Direction.DOWN;
		} else {
			return null;
		}
	}
	
	public boolean hasHitWall() {
		if ((posx + vx < 0) || (posx + vx > maxX) 
				|| (posy + vy < 0)|| (posy + vy > maxY)) {
			return true;
		} else {
			return false;
		}
	}
	
	/** 
	 * Determines whether the game object will hit another object in
	 * next time step; returns direction if true.
	 *
	 * @return direction of impending object, and null if clear
	 */
	public Direction hitObj(GameObj other) {

		if (this.willIntersect(other)) {
			double dx = other.posx + other.width /2 - (posx + width /2);
			double dy = other.posy + other.height/2 - (posy + height/2);

			double theta = Math.atan2(dy, dx);
			double diagTheta = Math.atan2(height, width);

			if ( -diagTheta <= theta && theta <= diagTheta ) {
				return Direction.RIGHT;
			} else if ( diagTheta <= theta 
					&& theta <= Math.PI - diagTheta ) {
				return Direction.DOWN;
			} else if ( Math.PI - diagTheta <= theta 
					|| theta <= diagTheta - Math.PI ) {
				return Direction.LEFT;
			} else {
				return Direction.UP;
			}

		} else {
			return null;
		}
	}

	public void draw(Graphics g) {
	}
	
}