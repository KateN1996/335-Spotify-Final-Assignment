import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MouseInputListener;

public class BrazilBeatsUI{
	private JFrame frame;
	private JPanel panel;

	// TODO: Create method for automatic colors which uses average color. Have a min val for average color and have update color methods for all ui.
	
	protected static Color appColor = new Color(50, 55, 50);			// Light Brazil Green
	protected static Color borderColor = new Color(30, 33, 30);			// Dark Brazil Green
	protected static Color barColor = new Color(40, 44, 40);			// Medium Brazil Green
	protected static Color accentColor = new Color(30, 80, 30);			// Saturated Brazil Green
	protected static Color detailColor = new Color(230, 220, 100);		// Light Brazil Yellow

	protected static Font mainFont = new Font("Arial Bold", Font.PLAIN, 16);
	protected static Font captionFont = new Font("Arial Bold", Font.PLAIN, 12);
	protected static Font headerFont = new Font("Arial Bold", Font.BOLD, 28);
	protected static Dimension defaultRes = new Dimension(1400, 800);
	
	static final int IMG_RES_MAX = 256;
	
	static final int SPACINGX = 5;
	static final int SPACINGY = 10;
	static final Insets INSET_GAP = new Insets(SPACINGX, SPACINGY, SPACINGX, SPACINGY);

	BrazilBeatsUI() {

	}
	
	public void initializeComponents() {
		// Create Frame
		frame = new JFrame("Brazil Beats Premium");

		// setting frame with card layout so we can swap between panes for 
		// different actions (i.e. see large album image and player)
		frame.getContentPane().setLayout(new CardLayout()); 

		// Create main panel
		panel = new JPanel(new GridBagLayout());
		panel.setPreferredSize(defaultRes);
		panel.setBackground(appColor);
		panel.setFocusable(true);


		// Parent containers to panel
		GridBagConstraints gbc = new GridBagConstraints();

		// Playback Bar
		Container playbackBar = new PlaybackOptionsUI();
		
		JPanel navPanel = new JPanel(new GridBagLayout());
		
		navPanel.setBackground(borderColor);
		gbc.fill = GridBagConstraints.VERTICAL;
		gbc.anchor = GridBagConstraints.SOUTHWEST;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.gridx = 0;
		gbc.gridy = 1;
		panel.add(navPanel, gbc);

		// Current Song Preview and info
		Container songPreview = new SongPreviewUI();
		gbc.insets = INSET_GAP;
		gbc.fill = GridBagConstraints.NONE;
		navPanel.add(songPreview, gbc);

		// Navigation Menu
		Container navigationMenu = new NavigationMenuUI();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.NORTHWEST;

		navPanel.add(navigationMenu, gbc);

		JPanel beatsPanel = new JPanel(new GridBagLayout());
		beatsPanel.setBackground(borderColor);
		gbc.insets = new Insets(0,0,0,0);
		gbc.fill = GridBagConstraints.VERTICAL;
		gbc.anchor = GridBagConstraints.SOUTHEAST;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.gridx = 2;
		gbc.gridy = 1;
		panel.add(beatsPanel, gbc);
		
		// BRAZIL BEATS DANCING CHARACTER CORNER!!!!! LETS GOOOOOOOO!!!!!
		Container brazilBeatsView = new BeatsVisualizerUI();
		gbc.insets = INSET_GAP;
		gbc.anchor = GridBagConstraints.NORTHEAST;
		gbc.fill = GridBagConstraints.NONE;
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.gridwidth = IMG_RES_MAX + (INSET_GAP.left * 2);
		beatsPanel.add(brazilBeatsView, gbc);
		
		// gbc.anchor = GridBagConstraints.NORTH;
		gbc.gridx = 1;
		gbc.gridy = 0;
		// panel.add(libraryView, gbc);

		JPanel playbackPanel = new JPanel();
		playbackPanel.setBackground(barColor);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(0, 0, 0, 0);
		playbackPanel.add(playbackBar, gbc);

		gbc.anchor = GridBagConstraints.SOUTH;
		gbc.gridx = 0;
		gbc.gridy = 1;
		panel.add(playbackPanel, gbc);

		// Set and open frame
		frame.setSize(defaultRes);
		frame.add(panel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack(); // Packs all components to fit on screen
		frame.setLocationRelativeTo(null); // Centers frame on screen
		frame.setVisible(true); // Opens frame
	}
	
	public void switchMainViewPane(String pane) {
		System.out.println("Switching to " + pane);
		//TODO: Use string to create new UI panel for library tab
	}
}