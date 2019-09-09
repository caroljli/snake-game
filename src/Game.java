/**
 * CIS 120 HW10
 * Carol Li
 * (c) University of Pennsylvania
 * @version 1.0, 2018
 */

// imports necessary libraries for Java swing
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
public class Game implements Runnable {
	/**
	 * GUI class of game; represents all components necessary for game to run.
	 */
	public void run() {
		
		final JFrame frame = new JFrame("SNAKE!!");
		frame.setLocation(800, 800);


		/* score panel */
		final JPanel statusPanel = new JPanel();
		frame.add(statusPanel, BorderLayout.SOUTH);
		
		final JLabel status = new JLabel();
		status.setFont((new Font("Monaco",1,15)));
		statusPanel.add(status);
		
		/* main game board */
		final GameCourt court = new GameCourt(status);
		frame.add(court, BorderLayout.CENTER);
		
		
		/* reset button */
		final JButton reset = new JButton("RESET");
		reset.setFont(new Font("Monaco",1,15));
		reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				court.reset();
			}
		});
		
		
		/* instructions activator */
		final JButton instructions = new JButton("INSTRUCTIONS");
		instructions.setFont(new Font("Monaco",1,15));
		instructions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				court.instructions();
			}
		});
		
		/* story activator */
		final JButton story = new JButton("STORY");
		story.setFont(new Font("Monaco",1,15));
		story.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				court.story();
			}
		});
		
		statusPanel.add(reset);
		statusPanel.add(instructions);
		statusPanel.add(story);
		
		/* frame */
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		/* start game! */
		court.reset();
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Game());
	}
}