
import java.awt.*;

/**
 * The Snake object is represented by a LinkedList whose nodes represent the
 * current state of the snake's body.
 */
public class Snake extends GameObj {
	public static final int SIZE = 12;
	public static final int INIT_VEL_X = 0;
	public static final int INIT_VEL_Y = 0;
	public static final int INIT_POS_X = 20;
	public static final int INIT_POS_Y = 20;

	private static int score; //keeps track of score associated with the snake
	private static int level; //keeps track of current level
	

	public Snake(int courtWidth, int courtHeight) {
		super(INIT_VEL_X, INIT_VEL_Y, INIT_POS_X, INIT_POS_Y, SIZE, SIZE,
				courtWidth, courtHeight);
		score = 0;
		level = 1;
	}
	
	/** 
	 * Updates position of the Snake's "head".
	 */
	@Override 
	public void move() {
		 for(int i = gameObjects.size()-1; i >= 1; i--) {
	            gameObjects.get(i).setLocation(gameObjects.get(i-1));
	        }
	     gameObjects.set(0, new Point((posx += vx),(posy += vy)));
	     clip();
	}

	/**
	 * Determines whether the snake will intersect itself.
	 * @return boolean on whether the snake intersects itself.
	 */
	public boolean willHitItself() {
		
		//loop through to see if coordinates of 
		//the snake's head and any of his body match
		//if so, the snake has hit itself 
		if (gameObjects.size() > 1) {
			
		for (int i=1; i<gameObjects.size(); i++) {
			Point p = gameObjects.getFirst();
			Point p1 = gameObjects.get(i);
			if ((p.x + vx == p1.x) && (p.y + vy == p1.y)) 
				return true;
			}
		}
		return false;
	}
	
	@Override 
	public boolean intersects(GameObj obj) {
		//get coordinates of snake 'head'
		Point p = gameObjects.getFirst();
		return (p.x + width >= obj.posx
				&& p.y + height >= obj.posy
				&& obj.posx + obj.width >= p.x
				&& obj.posy + obj.height >= p.y);
	}
	
	@Override
	public boolean hasHitWall() {
		
		//get coordinates of snake 'head'
		Point p = gameObjects.getFirst();
		
		if ((p.x + vx < 0) || (p.x + vx >= maxX)
				|| (p.y + vy < 0) || (p.y + vy >= maxY)) {
			System.out.println(Integer.toString(p.x));
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Grow snake by given number of nodes, and adds new nodes to tail.
	 * @param number of nodes that the snake grows by.
	 */
	public void growSnake(int num) {
		while (num > 0) {
			gameObjects.add(new Point (gameObjects.getLast()));
			num--;
			
		}
	}

	/**
	 * Returns current score.
	 * @return current score
	 */
	public int currentScore() {
		return score;
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.cyan);
		for(int i = 0; i < gameObjects.size(); i++) {
            Point p = gameObjects.get(i);
            g.fillRect(p.x, p.y, 15, 15);
        }
	}

	// helper methods (!!!)
	
	/**
	 * Increases level.
	 */
	public void increaseLevel() {
		level++;
	}

	public int getLevel() {
		return level;
	}
	
	/**
	 * Sets snake level to specified.
	 * @param num
	 */
	public void setLevel(int num) {
		level = num;	
	}

	/**
	 * Sets snake score as specified num.
	 * @param snake's score to set as
	 */
	public void setScore(int num) {
		score = num;
	}
	
	/**
	 * Increments score by amount num.
	 * @param number of points to increment score by
	 */
	public void increaseScore(int num) {
		score += num;
	}
	
	/**
	 * Shrinks snake by given amount.
	 * @param amount to shrink snake by.
	 */
	public void shrinkSnake(int num) {
		
		//check to make sure snake has enough nodes
		if (gameObjects.size() > num) {
			for (int j = 0; j < num; j++) {
				gameObjects.removeLast();
			} 
			} else {
				//remove all nodes but the first node
				for (int j=1;j<gameObjects.size();j++) {
					gameObjects.removeLast();
				}
			}
		}
}