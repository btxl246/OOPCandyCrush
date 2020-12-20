package code.ui;

import code.model.Model;
import code.model.Grid;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import javax.imageio.ImageIO;
import javax.swing.*;

public class UI implements Runnable {
	private Model model;	// The model.
	private ArrayList<ArrayList<JButton>> playGrid = new ArrayList<ArrayList<JButton>>();	// The play grid.
	private LeaderBoard lBoard = new LeaderBoard();

	private JFrame homeFrame;	// JFrame to show the home screen.
	private JFrame playFrame;	// JFrame to show the play screen.
	private JFrame overFrame;	// JFrame to show the game over screen.
	private JLabel yourScore;		// JLabel to show your score.
	private JButton quitButton;		// JButton to quit current game/not play again.

	private int finalScore;			// The final score.

	/**
	 * The constructor.
	 */

	public UI() {}

	/**
	 * Override the run() method in the Runnable interface.
	 */

	@Override
	public void run() {
		homeScreen();	// Show the home screen first,
		playScreen();	// then the play screen.
	}
	
	public int getFinalScore() {
		return this.finalScore;
	}

	/**
	 * The homeScreen() method displays the home screen.
	 */

	public void homeScreen() {
		this.homeFrame = new JFrame("HOME SCREEN");	// Initialize the frame with a name.

		// Add a background image.
		BufferedImage myImage = null;
		try {
			myImage = ImageIO.read(new File("Images/homeScreen.png"));	// The image file.
		} catch (IOException e) {
			e.printStackTrace();
		}
		homeFrame.setContentPane(new ImagePanel(myImage));	// Add the image.
		homeFrame.getContentPane().setPreferredSize(new Dimension(1280, 720)); 	// Set the frame's size.
		// The start playing button.
		JButton startButton = new JButton("Play!");							// Initialize a new button with a text.
		startButton.setFont(new Font("Candice", Font.PLAIN, 70));		// Set the button's font.
		startButton.setForeground(Color.WHITE);										// Set the color of the text.
		startButton.setBorder(BorderFactory.createEmptyBorder());					// Remove the border around the button.
		startButton.setFocusPainted(false);											// Remove the box around the text.
		startButton.setContentAreaFilled(false);									// Do not show the button.
		startButton.setBounds(479, 460, 315, 94);				// Set where the button is.

		// What the button does when clicked.
		ActionListener startAction = new ActionListener() {
			/**
			 * Override the actionPerformed() method in the ActionListener interface.
			 * @param e The event.
			 */

			@Override
			public void actionPerformed(ActionEvent e) {
				homeFrame.dispose();			// Close the home screen.
				playFrame.setVisible(true);		// Show the play screen.
			}
		};
		startButton.addActionListener(startAction);		// Add the action to the button.

		// The exit game/program button.
		JButton exitButton = new JButton("Exit Game");						// Initialize a new button with a text.
		exitButton.setFont(new Font("Candice", Font.PLAIN, 50)); 	// Set the button's font.
		exitButton.setForeground(Color.WHITE);									// Set the color of the text.
		exitButton.setBorder(BorderFactory.createEmptyBorder());				// Remove the border around the button.
		exitButton.setFocusPainted(false);										// Remove the box around the text.
		exitButton.setContentAreaFilled(false);									// Do not show the button.
		exitButton.setBounds(468, 565, 338, 76);			// Set where the button is.
		exitButton.addActionListener(quitAction(homeFrame));					// Add the action from the quitAction() method to the button.


		// Add the buttons to the home screen.
		homeFrame.add(startButton);
		homeFrame.add(exitButton);

		homeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	// Stop the program if the frame window is closed.
		homeFrame.pack();											// Pack the frame window to size.
		homeFrame.setLocationRelativeTo(null);						// Center the frame window.
		homeFrame.setVisible(true);									// Show the frame window.
	}

	/**
	 * The playScreen() method displays the play screen.
	 */

	public void playScreen() {
		this.model = new Model();
		this.playFrame = new JFrame("CANDY CRUSH");						// Initialize the frame with a name.
		playFrame.getContentPane().setLayout(new GridLayout(1, 2));	// Set the frame's layout.

		// The play panel to contain the play grid.
		JPanel playPanel = new JPanel();
		playPanel.setLayout(new GridLayout(this.model.rows(), this.model.cols())); {	// Set the panel's layout: width * height = tiles.
			// The play grid.
			this.playGrid = new ArrayList<>();	// Initialize the grid.

			// Add buttons to the play grid.
			for (int row = 0; row < model.rows(); row++) {								// For every row in the grid,
				playGrid.add(new ArrayList<>());									// add a new ArrayList of buttons.
				for (int col = 0; col < this.model.cols(); col++) {						// For every col,
					JButton tileButton = new JButton();									// create a new tileButton.
					tileButton.setPreferredSize(new Dimension(50, 50));		// Size of each tileButton, preferably the same as the image files.
					playGrid.get(row).add(tileButton);									// Add the tileButton to the inner ArrayList,
					playPanel.add(tileButton);											// and the panel.
					tileButton.addActionListener(new EventHandler(model, row, col));	// Add the action from the EventHandler class to the tileButton.
				}
			}
		}

		// The result panel to contain the "Score:" and your score labels, and the quit button.
		JPanel resultPanel = new JPanel();
		resultPanel.setLayout(new GridLayout(2, 1));				// Set the panel's layout.
		resultPanel.setPreferredSize(new Dimension(250, 250));	// Set the panel's size.
		resultPanel.setBackground(Color.PINK); 							// Set the panel's background.
		// The "Score: 0" panel
		JPanel scorePanel = new JPanel();
		scorePanel.setLayout(new GridLayout(1, 2));	// Set the panel's layout.
		scorePanel.setBackground(Color.PINK); 					// Set the panel's background.
		// The "Score:" label
		JLabel scoreLabel = new JLabel("Score: ");						// Initialize the label with a text.
		scoreLabel.setFont(new Font("Candice", Font.PLAIN, 30)); 	// Set the label's font.
		scoreLabel.setForeground(Color.RED);								// Set the label's color.

		// Your score's label
		this.yourScore = new JLabel();										// Initialize the label.
		yourScore.setFont(new Font("Candice", Font.PLAIN, 30)); 	// Set the label's font.

		// Add the labels to the panel.
		scorePanel.add(scoreLabel);
		scorePanel.add(yourScore);
		scorePanel.setLayout(new GridBagLayout());	// Center the labels.

		resultPanel.add(scorePanel);			// Add the inner score panel to the result panel.
		resultPanel.add(quitButton(playFrame));	// Add the quit button to the result panel.

		// Add the panels to the frame.
		playFrame.add(playPanel);
		playFrame.add(resultPanel);

		model.addObserver(this);	// Observe the changes in this model.

		playFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);	// Close the frame window.
		playFrame.pack();												// Pack the frame window to size.
		playFrame.setLocationRelativeTo(null);							// Center the frame window.
		// Notice how playFrame is not setVisible here (yet).
	}

	/**
	 * The gameOverScreen() method displays the game over screen.
	 */

	public void gameOverScreen() {
		this.overFrame = new JFrame("GAME OVER");										// Initialize the frame with a name
		overFrame.getContentPane().setPreferredSize(new Dimension(250, 250));	// Set the frame's size.
		overFrame.getContentPane().setLayout(new GridLayout(3, 1));				// Set the frame's layout.

		// The panel to contain the "Game Over" label.
		JPanel overPanel = new JPanel();			// Initialize the panel.
		overPanel.setLayout(new GridBagLayout());	// Center the label.
		overPanel.setBackground(Color.PINK);        // Set the panel's background.
		JLabel gameOverLabel = new JLabel("Game Over");                        // Initialize the label with a text.
		gameOverLabel.setFont(new Font("Candice", Font.PLAIN, 30));    // Set the label's font.
		overPanel.add(gameOverLabel);                                            // Add the label to the panel.

		// The panel to contain the final result.
		JPanel endResultPanel = new JPanel();									// Initialize the panel.
		endResultPanel.setLayout(new GridLayout(1, 2));				// Set the panels' layout.
		endResultPanel.setLayout(new GridBagLayout());							// Center the labels.
		endResultPanel.setPreferredSize(new Dimension(250, 50));	// Set the panel's size.
		endResultPanel.setBackground(Color.PINK); {
			JLabel yourScoreLabel = new JLabel("Your Score: ");					// Create a label with a text.
			yourScoreLabel.setFont(new Font("Candice", Font.PLAIN, 30));	// Set the label's font.
			yourScoreLabel.setForeground(Color.RED);									// Set the label's color.

			JLabel endScore = new JLabel(String.valueOf(this.finalScore));		// Create a label with a text of the final score.
			endScore.setFont(new Font("Candice", Font.PLAIN, 30));	// Set the label's font.

			// Add the labels to the panel.
			endResultPanel.add(yourScoreLabel);
			endResultPanel.add(endScore);
		}

		// Add the panels to the frame.
		overFrame.add(overPanel);
		overFrame.add(endResultPanel);
		overFrame.add(quitButton(overFrame));	// Add a quit button to the frame.
	}

	/**
	 * The update() method updates the grid.
	 */

	public void update() {
		// Update the tiles every time one is clicked.
		for (int row = 0; row < this.model.rows(); row++) {
			for (int col = 0; col < model.cols(); col++) {
				JButton button = this.playGrid.get(row).get(col);
				button.setIcon(new ImageIcon(model.get(new Point(row, col))));
				button.setBackground(Color.WHITE);
			}
		}

		Point p = model.selectedFirst();							// Mark a tile as first selected.
		if (p != null) {											// If a tile is selected,
			playGrid.get(p.x).get(p.y).setBackground(Color.RED);	// paint it with red.
		}

		this.yourScore.setText(String.valueOf(model.score()));		// The score at the moment.

		if (model.exit()) {										// If (true),
			//System.out.println(model.exit());
			for (ArrayList<JButton> al : playGrid)
				for (JButton button : al)
					button.setEnabled(false);	// disable the tiles.
			this.finalScore = model.score();						// Pass the score to the finalScore field.

			this.playFrame.dispose();								// Close the play frame.

			// Start the game over screen.
			gameOverScreen();
			this.overFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	// Stop the program id the frame window is closed.
			overFrame.pack();												// Pack the frame window to size.
			overFrame.setLocationRelativeTo(null);							// Center the frame window.
			overFrame.setVisible(true);										// Show the frame window.
		}

		playFrame.repaint();	// Repaint the play frame.
	}

	/**
	 * The quitButton() method creates a conditional button in a chosen frame.
	 * @param frame The frame.
	 * @return The button.
	 */

	public JButton quitButton(JFrame frame) {
		String str = "";

		if (frame == playFrame)			// If this is the play frame,
			str = "Quit";				// the text is "Quit."
		else if (frame == overFrame)	// Or if the game over frame,
			str = "Play Again";			// it is "Play Again."

		this.quitButton = new JButton();	// Initialize the button.
		quitButton.setText(str);			// Set the button's text.
		quitButton.setFont(new Font("Candice", Font.PLAIN, 30));	// Set the button's font.
		quitButton.setBackground(Color.PINK);								// Set the button's background.
		quitButton.setBorder(BorderFactory.createEmptyBorder());			// Remove the border around the button.
		quitButton.setFocusPainted(false);									// Remove the box around the text.
		quitButton.addActionListener(quitAction(frame));					// Add the action from the quitAction() method to the button.

		return this.quitButton;
	}

	/**
	 * The quitAction() method creates a conditional action for a quit button in a chosen frame.
	 * @param frame The frame to consider.
	 * @return The action.
	 */

	public ActionListener quitAction(JFrame frame) {
		ActionListener quitAction = new ActionListener() {
			/**
			 * Override the actionPerformed in the ActionListener interface.
			 * @param e The event.
			 */

			@Override
			public void actionPerformed(ActionEvent e) {
				playFrame.dispose();						// Close the play frame.
				frame.dispose();							// Close the chosen frame.
				if (frame == homeFrame)						// If this is the home frame,
					System.exit(0);					// stop the program.
				else
					SwingUtilities.invokeLater(new UI());	// Otherwise, restart the process.
			}
		};

		return quitAction;
	}

	/**
	 * The ImagePanel nested class set an image.
	 */

	public class ImagePanel extends JComponent {
		private Image image;	// The image.
		public ImagePanel(Image image) {	// The constructor.
			this.image = image;
		}

		/**
		 * Override the paintComponent() method in the JComponent class.
		 * @param g The graphics.
		 */

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(image, 0, 0, this);
		}
	}
}
