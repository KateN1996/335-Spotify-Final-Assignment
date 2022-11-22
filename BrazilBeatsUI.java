
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

public class BrazilBeatsUI {
	private HomeUI home; 
	//private PlayerUI player;
	private MyPanel2 panel2;
	private Dimension defaultRes = new Dimension(1400, 800);

	BrazilBeatsUI() {
		JFrame frame = new JFrame("Brazil Beats Premium");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		home = new HomeUI(frame);
        panel2 = new MyPanel2();

		frame.getContentPane().setLayout(new CardLayout());
		frame.getContentPane().add(home, "1");
		frame.getContentPane().add(panel2, "2");

        // Set and open frame
		frame.setSize(defaultRes);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack(); // Packs all components to fit on screen
		frame.setLocationRelativeTo(null); // Centers frame on screen
		frame.setVisible(true); // Opens frame

		CardLayout cl = (CardLayout) (frame.getContentPane().getLayout());
        cl.show(frame.getContentPane(), "1");
	}
}

class HomeUI extends JPanel{
	private JPanel panel;
	private JFrame window;

	private JLabel albumImagePreview;
	private JLabel songTitlePreview;
	private JLabel albumTitlePreview;
	private JLabel artistTitlePreview;
	
	private JLabel beatsDanceView;

	private Color appColor = new Color(50, 55, 50);			// Light Brazil Green
	private Color borderColor = new Color(30, 33, 30);		// Dark Brazil Green
	private Color barColor = new Color(40, 44, 40);			// Medium Brazil Green
	private Color accentColor = new Color(30, 80, 30);		// Saturated Brazil Green
	private Color detailColor = new Color(230, 220, 100);	// Light Brazil Yellow

	private Font mainFont = new Font("Arial Bold", Font.PLAIN, 16);
	private Font captionFont = new Font("Arial Bold", Font.PLAIN, 12);
	private Font headerFont = new Font("Arial Bold", Font.BOLD, 28);
	private Dimension defaultRes = new Dimension(1400, 800);
	
	static final int IMG_RES_MAX = 256;
	
	static final int SPACINGX = 5;
	static final int SPACINGY = 10;
	static final Insets INSET_GAP = new Insets(SPACINGX, SPACINGY, SPACINGX, SPACINGY);

	HomeUI(JFrame frame) {
		// Create main 
		window = frame;
		panel = new JPanel(new GridBagLayout());
		panel.setPreferredSize(defaultRes);
		panel.setBackground(appColor);
		panel.setFocusable(true);

		// Library view
		Container libraryView = createLibraryViewContainer();
		// Playback Bar
		Container playbackBar = createPlaybackOptionsContainer();

		// Parent containers to panel
		GridBagConstraints gbc = new GridBagConstraints();

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
		Container songPreview = createSongPreviewContainer();
		gbc.insets = INSET_GAP;
		gbc.fill = GridBagConstraints.NONE;
		navPanel.add(songPreview, gbc);

		// add the mouseListener so we can add the pane switching
		songPreview.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				CardLayout cl = (CardLayout) (window.getContentPane().getLayout());
                cl.show(window.getContentPane(), "2");
			}

			@Override
			public void mousePressed(MouseEvent e) {}

			@Override
			public void mouseReleased(MouseEvent e) {}

			@Override
			public void mouseEntered(MouseEvent e) {}

			@Override
			public void mouseExited(MouseEvent e) {}
		});

		// Navigation Menu
		Container navigationMenu = createNavigationContainer();
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
		Container brazilBeatsView = createBrazilBeatsContainer();
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

	}


	/**
	 * Creates the graphics container with navigation buttons and playlist tab.
	 * 
	 * @return
	 */
	private Container createNavigationContainer() {
		// Colored panel for whole section

		Container navContainer = new Container();
		GridBagLayout colLayout = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();

		navContainer.setLayout(colLayout);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = INSET_GAP;
		// Create components
		JButton libraryButton = new JButton("Your Library");
		libraryButton.setFont(mainFont);
		libraryButton.setForeground(detailColor);
		libraryButton.setBackground(accentColor);
		gbc.gridx = 0;
		gbc.gridy = 0;
		navContainer.add(libraryButton, gbc);

		JButton searchButton = new JButton("Search");
		searchButton.setFont(mainFont);
		searchButton.setForeground(detailColor);
		searchButton.setBackground(accentColor);
		gbc.gridy = 1;
		navContainer.add(searchButton, gbc);

		JLabel playlistsLabel = new JLabel("Playlists");
		playlistsLabel.setFont(headerFont);
		playlistsLabel.setForeground(detailColor);
		gbc.gridy = 2;
		navContainer.add(playlistsLabel, gbc);

		// TODO: Change this to actual playlist object array
		String[] examplePlaylists = new String[] { "Drake playlist", "Logic isn't that bad", "6ix9ine hidden gems",
				"YEAT's greatest hits", "Wolfgang Amadeus Mozart", "Dababy sleep playlist 2" };
		JList<String> playlistsList = new JList<String>(examplePlaylists); // Store an arrayList of Playlist Objects
																			// here
		playlistsList.setBackground(borderColor);
		playlistsList.setForeground(detailColor);
		playlistsList.setFont(captionFont);
		JScrollPane playlistScroll = new JScrollPane();
		playlistScroll.setViewportView(playlistsList);
		playlistsList.setLayoutOrientation(JList.VERTICAL);
		// JList<Playlist> playlistsList = new JList<Playlist>();
		gbc.gridy = 3;
		navContainer.add(playlistsList, gbc);

		return navContainer;
	}

	/**
	 * Creates the container with all components used for displaying Current song
	 * info and album art. Will later have a method used for updating graphics to
	 * always match current song playing.
	 * 
	 * @return
	 */
	private Container createSongPreviewContainer() {
		Container songContainer = new Container();
		GridBagLayout colLayout = new GridBagLayout();

		songContainer.setLayout(colLayout);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = INSET_GAP;
		gbc.anchor = GridBagConstraints.WEST;
		// Create components
		// TESTING ALBUM COVER -- HARDCODED ----
		albumImagePreview = new JLabel("");
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.weightx = 1;
		songContainer.add(albumImagePreview, gbc);

		songTitlePreview = new JLabel("");
		songTitlePreview.setFont(mainFont);
		songTitlePreview.setForeground(detailColor);
		gbc.gridy = 2;
		songContainer.add(songTitlePreview, gbc);

		albumTitlePreview = new JLabel("");
		albumTitlePreview.setFont(captionFont);
		albumTitlePreview.setForeground(detailColor);
		gbc.gridy = 3;
		songContainer.add(albumTitlePreview, gbc);

		artistTitlePreview = new JLabel("");
		artistTitlePreview.setFont(captionFont);
		artistTitlePreview.setForeground(detailColor);
		gbc.gridy = 4;
		songContainer.add(artistTitlePreview, gbc);

		updateSongPreview();

		return songContainer;
	}

	/**
	 * Creates UI container with the current library view. It should change for if
	 * you are looking at artists, playlists, songs, or albums.
	 * 
	 * @return libContainer
	 */
	private Container createLibraryViewContainer() {
		Container libContainer = new Container();
		BoxLayout colLayout = new BoxLayout(libContainer, BoxLayout.Y_AXIS);
		libContainer.setLayout(colLayout);

		return libContainer;
	}

	/**
	 * Creates the playback options menu on the bottom of the screen with the
	 * playback timeBar and all playback buttons. Each button will later be wired to
	 * methods which complete their respective functions, and the bar will later
	 * update to match the song's timing.
	 * 
	 * @return playbackContainer
	 */
	private Container createPlaybackOptionsContainer() {
		Container playbackContainer = new Container();
		GridBagLayout rowLayout = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		playbackContainer.setLayout(rowLayout);

		gbc.insets = INSET_GAP;
		gbc.anchor = GridBagConstraints.SOUTH;

		JLabel timeStampCur = new JLabel("0:48");	
		timeStampCur.setFont(captionFont);
		timeStampCur.setForeground(detailColor);
		gbc.gridx = 0;
		gbc.gridy = 0;
		playbackContainer.add(timeStampCur, gbc);

		JProgressBar playbackBar = new JProgressBar(SwingConstants.HORIZONTAL);
		playbackBar.setPreferredSize(new Dimension(400, 12));
		playbackBar.setMaximum(172);								// Max set to max length of song
		playbackBar.setValue(48);
		playbackBar.setBackground(borderColor);
		playbackBar.setForeground(detailColor);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 1;
		gbc.gridy = 0;
		playbackContainer.add(playbackBar, gbc);
		gbc.fill = GridBagConstraints.NONE;

		JLabel timeStampEnd = new JLabel("2:52");
		timeStampEnd.setFont(captionFont);
		timeStampEnd.setForeground(detailColor);
		gbc.gridx = 2;
		gbc.gridy = 0;
		playbackContainer.add(timeStampEnd, gbc);

		Container buttonContainer = new Container();
		Dimension buttonSpacing = new Dimension(10, 0);
		buttonContainer.setLayout(new BoxLayout(buttonContainer, BoxLayout.X_AXIS));

		JButton shuffleButton = new JButton("$");
		shuffleButton.setBackground(accentColor);
		shuffleButton.setForeground(detailColor);
		shuffleButton.setFont(mainFont);
		buttonContainer.add(shuffleButton);
		buttonContainer.add(Box.createRigidArea(buttonSpacing));

		JButton restartButton = new JButton("|<");
		restartButton.setBackground(accentColor);
		restartButton.setForeground(detailColor);
		restartButton.setFont(mainFont);
		buttonContainer.add(restartButton);
		buttonContainer.add(Box.createRigidArea(buttonSpacing));

		JButton playPauseButton = new JButton("||");
		playPauseButton.setBackground(accentColor);
		playPauseButton.setForeground(detailColor);
		playPauseButton.setFont(mainFont);
		buttonContainer.add(playPauseButton);
		buttonContainer.add(Box.createRigidArea(buttonSpacing));

		JButton skipButton = new JButton(">|");
		skipButton.setBackground(accentColor);
		skipButton.setForeground(detailColor);
		skipButton.setFont(mainFont);
		buttonContainer.add(skipButton);
		buttonContainer.add(Box.createRigidArea(buttonSpacing));

		JButton saveButton = new JButton(":)");
		saveButton.setBackground(accentColor);
		saveButton.setForeground(detailColor);
		saveButton.setFont(mainFont);
		buttonContainer.add(saveButton);

		gbc.gridx = 1;
		gbc.gridy = 1;
		playbackContainer.add(buttonContainer, gbc);

		return playbackContainer;
	}

	/**
	 * Creates and returns the UI container for the Dancing Brazil Beats Visualizer
	 * Section. The dancing character will be updated in realtime along with the
	 * music from an outside music player class.
	 * 
	 * @return
	 */
	private Container createBrazilBeatsContainer() {
		Container brazilContainer = new Container();
		GridBagLayout colLayout = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		brazilContainer.setLayout(colLayout);
		gbc.insets = INSET_GAP;
		
		JLabel brazilBeatsLabel = new JLabel("Beats Visualizer");
		brazilBeatsLabel.setFont(headerFont);
		brazilBeatsLabel.setForeground(detailColor);
		gbc.gridy = 0;
		brazilContainer.add(brazilBeatsLabel, gbc);
		
		beatsDanceView = new JLabel("");
		gbc.gridy = 1;
		updateBeatsView();
		brazilContainer.add(beatsDanceView, gbc);
		
		return brazilContainer;
	}

	/**
	 * Updates the song preview view to match current song's Album art, title, album
	 * name, and artist name
	 */
	private void updateSongPreview() {
		File albumImageFile = new File("Mmfood.jpg");
		Image albumImage;
		try {
			albumImage = ImageIO.read(albumImageFile);
			albumImage = albumImage.getScaledInstance(IMG_RES_MAX, IMG_RES_MAX, Image.SCALE_DEFAULT);
			ImageIcon albumIcon = new ImageIcon(albumImage);

			albumImagePreview.setIcon(albumIcon);
			songTitlePreview.setText("Rapp Snitch Knishes"); // Match song name
			albumTitlePreview.setText("MM...FOOD"); // Match album name
			artistTitlePreview.setText("MF DOOM"); // Match artist name

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void updateBeatsView() {
		File danceImageFile = new File("brazilDance.jpg");
		Image danceImage;
		try {
			danceImage = ImageIO.read(danceImageFile);
			danceImage = danceImage.getScaledInstance(IMG_RES_MAX, IMG_RES_MAX, Image.SCALE_DEFAULT);
			ImageIcon albumIcon = new ImageIcon(danceImage);

			beatsDanceView.setIcon(albumIcon);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

class MyPanel2 extends JPanel 
{

    private JButton jcomp1;
    private JPanel contentPane;

    public MyPanel2()  {   
        contentPane = new JPanel();

        setOpaque(true);
        setBackground(Color.GREEN.darker().darker());

        //construct components
        jcomp1 = new JButton ("Back");
	}

    @Override
    public Dimension getPreferredSize() {
        return (new Dimension(500, 500));
    }
}