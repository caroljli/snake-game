
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.awt.Graphics;

import javax.imageio.ImageIO;
import javax.swing.*;

@SuppressWarnings("serial")
public class GameCourt extends JPanel {
	
	private BufferedReader readIn; 
	private BufferedWriter override; 
	private int highscore; 
	private boolean health_init;
	
	private Snake snake; 
	private Apple apple; 
	private Lemon lemon; 
	private Candy candy; 
	private Health health; 
	
	public boolean playing = false; 
	private JLabel status; 

	public static final String GAMEOVER_IMAGE = "files/gameover.png";
	private static BufferedImage gameover;
	
	public static final String INSTRUCTIONS_IMAGE = "files/instructions.png";
	private static BufferedImage instructions;
	
	public static final String STORY_IMAGE = "files/story.png";
	private static BufferedImage story;
	
	public boolean instructionspressed = false;
	public boolean storypressed = false;
	
	public static final int COURT_WIDTH = 800;
	public static final int COURT_HEIGHT = 800;
	public static int VELOCITY_X = 5;
	public static int VELOCITY_Y = 5;

	public static final int INTERVAL = 35;

	public GameCourt(JLabel status) {
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		this.setBackground(Color.GRAY);
		/**
		 * High score IO reader.
		 */
		try {
			if (instructions == null) {
				gameover = ImageIO.read(new File(GAMEOVER_IMAGE));
				instructions = ImageIO.read(new File(INSTRUCTIONS_IMAGE));
				story = ImageIO.read(new File(STORY_IMAGE));
			}
		} catch (IOException e) {
			System.out.println("Internal Error:" + e.getMessage());
		}
		String s;
		try {
			readIn = new BufferedReader(new FileReader("files/highscore.txt"));
			s = readIn.readLine();
			s = s.trim(); 
			highscore = Integer.parseInt(s); 
			
		} catch (IOException e) {}
		
		Timer timer = new Timer(INTERVAL, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tick();
			}
		});
		timer.start(); 
		setFocusable(true);
		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_LEFT) {
					snake.vy = 0;
					snake.vx = -VELOCITY_X;
				} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					snake.vy = 0;
					snake.vx = VELOCITY_X;
				} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
					snake.vx = 0;
					snake.vy = VELOCITY_Y;
				} else if (e.getKeyCode() == KeyEvent.VK_UP) {
					snake.vx = 0;
					snake.vy = -VELOCITY_Y;	
				}
			}
		});
		this.status = status;
	}
	
	/**
	 * Function of the reset button; restores the state of the game to the initial state.
	 */
	public void reset() {
		
		snake = new Snake(COURT_WIDTH, COURT_HEIGHT);
		apple = new Apple(COURT_WIDTH, COURT_HEIGHT);
		candy = new Candy(COURT_WIDTH, COURT_HEIGHT);
		lemon = new Lemon(COURT_WIDTH, COURT_HEIGHT);
		health = null;
		health_init = false; 

		snake.setScore(0);
		snake.setLevel(1);
		
		VELOCITY_X = 4;
		VELOCITY_Y = 4;
		
		instructionspressed = false;
		storypressed = false;
		
		playing = true;
		
		status.setText("High Score: " + Integer.toString(highscore) + "     "
				+ "Level: " + Integer.toString(snake.getLevel()) + 
				"     Score: " + Integer.toString(snake.currentScore()) + "     ");
		
		requestFocusInWindow();
	}

	/**
	 * Activate the instructions button.
	 */
	public void instructions() {
		instructionspressed = true;
		requestFocusInWindow();
		repaint();
	}
	
	/**
	 * Activte the story button.
	 */
	public void story() {
		storypressed = true;
		requestFocusInWindow();
		repaint();
	}
	
	/**
	 * Each "tick" of the game, indicating all checks and functions performed while
	 * the game is running.
	 * 
	 * Checks for intersection of all objects with the snake, and adjusts scores
	 * accordingly.
	 */
	void tick() {
		if (playing) {
			snake.move();
			if ((health!=null) && (Math.random()<0.02)) {
				health.respawn();
			}
			if ((snake.currentScore() > 300) && (Math.random() < 0.01)) {
				if (health_init==false) {
					health = new Health(COURT_WIDTH, COURT_HEIGHT);
					health_init = true;
				} 	
			}
			if ((health!=null) && (health.intersects(snake))) {
				health.respawn();
				snake.shrinkSnake(50);
			}
			if ((apple.intersects(snake))) {
				if (Math.random() < 0.5) {
					candy.add();
				}
				if ((snake.currentScore() > 200) && (Math.random() < 0.3)) {
						lemon.addLemon();
				}
				if ((snake.currentScore()!=0) && (snake.currentScore() % 100 == 0))  {
					snake.increaseLevel();
					VELOCITY_X += 1;
					VELOCITY_Y += 1;
						
				}
				snake.growSnake(5);
				apple.add();
				snake.increaseScore(10);
				status.setText("High Score:" + Integer.toString(highscore) + "     " +
				"Level:" + Integer.toString(snake.getLevel()) + 
				"     Score:" + Integer.toString(snake.currentScore()) + "     ");
			}
			if (lemon.intersects(snake)) {
				if (Math.random() < 0.6) {
					candy.add();
				}
				snake.growSnake(5);
				if ((snake.currentScore() > 350) && (Math.random() < 0.3)) {
					lemon.addLemon();
				}
				if ((snake.currentScore()!=0) && (snake.currentScore() % 100 == 0))  {
					snake.increaseLevel();
					VELOCITY_X += 1;
					VELOCITY_Y += 1;
					
				}
				snake.increaseScore(30);
				status.setText("High Score:" + Integer.toString(highscore) + "     "
						+ "Level:" + Integer.toString(snake.getLevel()) + 
				"     Score:" + Integer.toString(snake.currentScore()) + "     ");		
			} else if ((snake.hasHitWall() || (candy.intersects(snake)
				|| (snake.willHitItself())))) {
				if (snake.currentScore() > highscore) {
					highscore = snake.currentScore();
					try {
						override = new BufferedWriter(new FileWriter("highscore.txt"));
						override.write(Integer.toString(snake.currentScore()));
						override.close();
					} catch (IOException e) {}
				}
				playing = false;
			}
			repaint();
		}
   }

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (instructionspressed) {
			g.drawImage(instructions, 0, 0, 800, 800, null);
		} else if (storypressed) {
			g.drawImage(story, 0, 0, 800, 800, null);
		} else if (!playing) {
	
			//if yes, draw game over sign 
			g.drawImage(gameover, 0, 0, 800, 800, null);
			
		} else {	
			snake.draw(g);
			apple.draw(g);
			candy.draw(g);
			lemon.draw(g);	

			if (health!=null) {
				health.draw(g);
			}
		}
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(COURT_WIDTH, COURT_HEIGHT);
	}
}

