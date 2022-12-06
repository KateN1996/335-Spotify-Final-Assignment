import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MouseInputListener;

/**
 * 
 * @author Kyle Walker
 *
 */
public class BrazilBeatsUI {
	private JFrame frame;
	private JPanel panel;

	// TODO: Create method for automatic colors which uses average color. Have a min
	// val for average color and have update color methods for all ui.

	protected static Color appColor = new Color(50, 55, 50); // Light Brazil Green
	protected static Color borderColor = new Color(30, 33, 30); // Dark Brazil Green
	protected static Color barColor = new Color(40, 44, 40); // Medium Brazil Green
	protected static Color accentColor = new Color(30, 80, 30); // Saturated Brazil Green
	protected static Color detailColor = new Color(230, 220, 100); // Light Brazil Yellow

	protected static Font mainFont = new Font("Arial Bold", Font.PLAIN, 16);
	protected static Font captionFont = new Font("Arial Bold", Font.PLAIN, 12);
	protected static Font headerFont = new Font("Arial Bold", Font.BOLD, 28);
	protected static Dimension defaultRes = new Dimension(1400, 800);

	static final int IMG_RES_MAX = 256;
	static final int IMG_RES_MIN = 64;

	static final int SPACINGX = 5;
	static final int SPACINGY = 10;
	static final Insets INSET_GAP = new Insets(SPACINGX, SPACINGY, SPACINGX, SPACINGY);

	private JPanel navPanel;
	private JPanel beatsPanel;
	private JPanel playbackPanel;

	private Container songPreview;
	private Container navigationMenu;
	private Container brazilBeatsView;
	private Container viewContainer;

	private PlaylistManager playlistManager;
	private SongPlayer songPlayer;

	private GridBagConstraints gbc;

	BrazilBeatsUI() {
		playlistManager = Main.playlistManager;
		songPlayer = Main.songPlayer;
	}

	public void initializeComponents() {
		
		recalculateColors(new Color(30, 35, 35));
		//recalculateColors(new Color(150, 90, 30));
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
		gbc = new GridBagConstraints();

		viewContainer = new JPanel();
		viewContainer.setBackground(appColor);
		gbc.anchor = GridBagConstraints.NORTH;
		gbc.fill = GridBagConstraints.VERTICAL;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.gridheight = 2;
		gbc.gridwidth = 1;
		gbc.gridx = 1;
		gbc.gridy = 0;
		panel.add(viewContainer, gbc);

		navPanel = new JPanel(new GridBagLayout());

		navPanel.setBackground(borderColor);
		gbc.fill = GridBagConstraints.VERTICAL;
		gbc.anchor = GridBagConstraints.SOUTHWEST;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.gridheight = 2;
		gbc.gridx = 0;
		gbc.gridy = 0;
		panel.add(navPanel, gbc);

		// Current Song Preview and info
		songPreview = new SongPreviewUI();
		gbc.insets = INSET_GAP;
		gbc.fill = GridBagConstraints.NONE;
		navPanel.add(songPreview, gbc);

		// Navigation Menu
		navigationMenu = new NavigationMenuUI();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.NORTHWEST;

		navPanel.add(navigationMenu, gbc);

		beatsPanel = new JPanel(new GridBagLayout());
		beatsPanel.setBackground(borderColor);
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.fill = GridBagConstraints.VERTICAL;
		gbc.anchor = GridBagConstraints.SOUTHEAST;
		gbc.gridheight = 2;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.gridx = 2;
		gbc.gridy = 0;
		panel.add(beatsPanel, gbc);

		// BRAZIL BEATS DANCING CHARACTER CORNER!!!!! LETS GOOOOOOOO!!!!!
		Runnable brazilBeatsView = new BeatsVisualizerUI();
		Thread visualizerThread = new Thread(brazilBeatsView);
		visualizerThread.start();
		gbc.insets = INSET_GAP;
		gbc.anchor = GridBagConstraints.NORTHEAST;
		gbc.fill = GridBagConstraints.NONE;
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.gridwidth = IMG_RES_MAX + (INSET_GAP.left * 2);
		beatsPanel.add((Container) brazilBeatsView, gbc);

		// Playback Bar
		Runnable playbackBar = new PlaybackOptionsUI();
		Thread playbackThread = new Thread(playbackBar);
		playbackThread.start();
		playbackPanel = new JPanel();
		playbackPanel.setBackground(barColor);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(0, 0, 0, 0);
		playbackPanel.add((Container) playbackBar, gbc);

		gbc.anchor = GridBagConstraints.SOUTH;
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridheight = 1;
		gbc.gridwidth = 3;

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
		// TODO: FIx this
		Container newPane;

		switch (pane) {
		case "Song View":
			
			newPane = new PlayerPanel(songPlayer.getCurrentSong());
			
			break;

		case "Your Library":
			/// newPane = new LibraryPaneUI();
			newPane = null;
			break;

		case "Search Page":
			/// newPane = new SearchPaneUI();
			newPane = null;
			break;

		case "Playlists Page":
			// newPane = new PlaylistsPaneUI();
			newPane = new ListDisplayUI(playlistManager.getPlaylists().get(0));
			break;

		default:
			newPane = null;
			return;

		}

		System.out.println("Switching to " + pane);
		viewContainer.removeAll();
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.fill = GridBagConstraints.VERTICAL;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridheight = 2;
		viewContainer.add(newPane, gbc);
		frame.validate();
		// frame.pack();

	}
	
	public void recalculateColors(Color base) {
		int r = base.getRed();
		int g = base.getGreen();
		int b = base.getBlue();
		if (r < 30) {
			r = 30;
		}
		if (r > 105)
			r = 105;
		if (g < 30) {
			g = 30;
		}
		if (g > 105)
			g = 105;
		if (b < 30) {
			b = 30;
		}
		if (b > 105)
			b = 105;
		

		//appColor = new Color(50, 55, 50);		
		appColor = new Color(r, g, b);			
		borderColor = new Color(r - 20, g - 20, b - 20);			
		barColor = new Color(r - 10, g - 10, b - 10);			
		accentColor = new Color(r-20, g + 30, b + 30);			
		detailColor = new Color(255 - r, 255 - g, 255 - b);	
	}
	

}
