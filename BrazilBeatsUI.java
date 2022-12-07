import java.awt.*;
import javax.swing.*;

/**
 * This class is the main UI which holds the JFrame and all of the different UI
 * components. It has the static variables used across all UI components such as
 * app colors and fonts. It serves as the main display for users to interact
 * with and perform control in the app. It contains panels for navigation,
 * current song display, playback options, main view, and the visualizer panel.
 * 
 * @author Kyle Walker
 *
 */
public class BrazilBeatsUI {
	private JFrame frame;
	private JPanel panel;

	// App colors
	protected static Color appColor = new Color(50, 55, 50); // Light Brazil Green
	protected static Color borderColor = new Color(30, 33, 30); // Dark Brazil Green
	protected static Color barColor = new Color(40, 44, 40); // Medium Brazil Green
	protected static Color accentColor = new Color(30, 80, 30); // Saturated Brazil Green
	protected static Color detailColor = new Color(230, 220, 100); // Light Brazil Yellow

	// App fonts
	protected static Font mainFont = new Font("Arial Bold", Font.PLAIN, 16);
	protected static Font captionFont = new Font("Arial Bold", Font.PLAIN, 12);
	protected static Font headerFont = new Font("Arial Bold", Font.BOLD, 28);
	protected static Dimension defaultRes = new Dimension(1360, 740);

	// App dimensions
	static final int IMG_RES_MAX = 256;
	static final int IMG_RES_MIN = 64;

	// Component spacing
	static final int SPACINGX = 5;
	static final int SPACINGY = 10;
	static final Insets INSET_GAP = new Insets(SPACINGX, SPACINGY, SPACINGX, SPACINGY);

	// Graphics components
	// Panels
	private JPanel navPanel;
	private JPanel beatsPanel;
	private JPanel playbackPanel;

	// UI Container classes
	private Container songPreview;
	private Container navigationMenu;
	private Container brazilBeatsView;
	private Container viewContainer;
	private Container playerViewPanel;
	private Container searchPanel;

	// References to music player
	private PlaylistManager playlistManager;
	private SongPlayer songPlayer;

	// Layout constraints
	private GridBagConstraints gbc;

	/**
	 * Constructs the UI and gets references to song player
	 */
	BrazilBeatsUI() {
		playlistManager = Main.playlistManager;
		songPlayer = Main.songPlayer;
	}

	/**
	 * Creates and adds all UI components to the frame after getting references.
	 */
	public void initializeComponents() {

		// Sets the app's color scheme to match this color
		recalculateColors(new Color(30, 35, 35));

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

		// Main center view panel
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

		// Navigation panel (left side)
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

		// Current Song Preview and info (bottom left)
		songPreview = new SongPreviewUI();
		gbc.insets = INSET_GAP;
		gbc.fill = GridBagConstraints.NONE;
		navPanel.add(songPreview, gbc);

		// Navigation Menu (top left)
		navigationMenu = new NavigationMenuUI();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		navPanel.add(navigationMenu, gbc);

		// Beats panel (top right)
		
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

		// BRAZIL BEATS DANCING CHARACTER CORNER (Visualizer)
		Runnable brazilBeatsVisualizer = new BeatsVisualizerUI();
		Thread visualizerThread = new Thread(brazilBeatsVisualizer);
		brazilBeatsView = (Container)brazilBeatsVisualizer;
		visualizerThread.start();
		gbc.insets = INSET_GAP;
		gbc.anchor = GridBagConstraints.NORTHEAST;
		gbc.fill = GridBagConstraints.NONE;
		gbc.gridx = 2;
		gbc.gridy = 0;
		beatsPanel.add((Container) brazilBeatsView, gbc);
		

		// Playback Bar (Bottom middle)
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

	/**
	 * Switches the main center view to a new UI container as specified in the pane
	 * argument.
	 * 
	 * @param pane The name of the view to switch to
	 */
	public void switchMainViewPane(String pane) {
		Container newPane;

		switch (pane) {
		// Add a song display to the main view
		case "Song View":
			playerViewPanel = new PlayerPanel();
			newPane = playerViewPanel;
			break;

		// Switch to Library playlist
		case "Your Library":
			/// newPane = new LibraryPaneUI();
			newPane = null;
			break;

		// Switch to Search bar
		case "Search Page":
			searchPanel =  new SearchPanelUI();
			newPane = searchPanel;
			break;

		default:
			newPane = null;
			break;
		}
		if (newPane == null) {
			return;
		}

		System.out.println("Switching to " + pane);
		// Removes previous component from view
		viewContainer.removeAll();
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridheight = 2;
		viewContainer.add(newPane, gbc);
		frame.validate();
		frame.pack();
	}
	
	public void switchMainViewPane(Container panel) {
		
		Container newPane;
		newPane = panel;

		// Removes previous component from view
		viewContainer.removeAll();
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridheight = 2;
		viewContainer.add(newPane, gbc);
		frame.validate();
		frame.pack();
	}

	/**
	 * Creates a set of themed colors based on the one base color so the UI will
	 * automatically follow a theme with any chosen color.
	 * 
	 * @param base base color to set as the main theme for the UI which all other
	 *             app colors will be based on
	 */
	public void recalculateColors(Color base) {
		int r = base.getRed();
		int g = base.getGreen();
		int b = base.getBlue();
		// ensures all color values will fit in 0-255 range
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

		// appColor = new Color(50, 55, 50); // Brazil color scheme
		appColor = new Color(r, g, b);
		borderColor = new Color(r - 20, g - 20, b - 20); // Darker version of base
		barColor = new Color(r - 10, g - 10, b - 10); // Slightly darker version of base
		accentColor = new Color(r - 20, g + 30, b + 30); // Brighter and bluer version of base
		detailColor = new Color(255 - r, 255 - g, 255 - b); // Inverted color of base
	}
	
	public void validateFrame() {
		((SongPreviewUI) songPreview).updateSongPreview();
		if (playerViewPanel != null) {
			((PlayerPanel)playerViewPanel).updateSongPreview();
		}
		
		((NavigationMenuUI)navigationMenu).updatePlaylistsList();
			
		if (searchPanel != null) {
			((SearchPanelUI)searchPanel).updateFields();
		}
		
		if (brazilBeatsView != null) {
			((BeatsVisualizerUI)brazilBeatsView).updateBeatsView();
		}
		frame.validate();
	}

}
