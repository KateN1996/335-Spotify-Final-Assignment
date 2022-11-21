
import java.awt.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

public class BrazilBeatsUI {
	private JFrame frame;
	private JPanel panel;
	
	private JLabel albumImagePreview;
	private JLabel songTitlePreview;
	private JLabel albumTitlePreview;
	private JLabel artistTitlePreview;
	
	
	//private Color appColor = new Color(130, 150, 180);
	//private Color detailColor = new Color(70, 70, 70);
	private Color appColor = new Color(50, 55, 50);
	private Color borderColor = new Color(30, 33, 30);
	private Color barColor = new Color(40, 44, 40);
	private Color accentColor = new Color(30, 80, 30);
	private Color detailColor = new Color(230, 220, 100);
	
	private Font mainFont = new Font("Arial Bold", Font.PLAIN, 16);
	private Font captionFont = new Font("Arial Bold", Font.PLAIN, 12);
	private Font headerFont = new Font("Arial Bold", Font.BOLD, 32);
	private Dimension defaultRes = new Dimension(1400, 800);
	
	BrazilBeatsUI(){
		// Create Frame
		frame = new JFrame("Brazil Beats Premium");
		
		// Create main panel
		panel = new JPanel(new GridBagLayout());
		panel.setPreferredSize(defaultRes);
		panel.setBackground(appColor);
		panel.setFocusable(true);
		


		// Library view
		Container libraryView = createLibraryViewContainer();
		// Playback Bar
		Container playbackBar = createPlaybackOptionsContainer();
		// BRAZIL BEATS DANCING CHARACTER CORNER!!!!! LETS GOOOOOOOO!!!!!
		Container brazilBeatsView = createBrazilBeatsContainer();
		
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
		gbc.insets = new Insets(5,10,5,10);
		gbc.fill = GridBagConstraints.NONE;
		navPanel.add(songPreview, gbc);
		
		// Navigation Menu
		Container navigationMenu = createNavigationContainer();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.NORTHWEST;
	
		navPanel.add(navigationMenu, gbc);
		
		//gbc.anchor = GridBagConstraints.NORTH;
		gbc.gridx = 1;
		gbc.gridy = 0;
		//panel.add(libraryView, gbc);
		
		
		JPanel playbackPanel = new JPanel();
		playbackPanel.setBackground(barColor);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(0,0,0,0);
		playbackPanel.add(playbackBar, gbc);
		
		gbc.anchor = GridBagConstraints.SOUTH;
		gbc.gridx = 0;
		gbc.gridy = 1;
		panel.add(playbackPanel, gbc);
		
		//gbc.anchor = GridBagConstraints.NORTHEAST;
		gbc.gridx = 2;
		gbc.gridy = 0;
		//panel.add(brazilBeatsView, gbc);
		
		
		// Set and open frame
		frame.setSize(defaultRes);
		frame.add(panel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);			
		frame.pack();											// Packs all components to fit on screen
		frame.setLocationRelativeTo(null);						// Centers frame on screen
		frame.setVisible(true);									// Opens frame
		
	}
	
	/**
	 * Creates the graphics container with navigation buttons and playlist tab.
	 * @return
	 */
	private Container createNavigationContainer() {
		// Colored panel for whole section

		Container navContainer = new Container();
		GridBagLayout colLayout = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		
		navContainer.setLayout(colLayout);
		
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(5,10,5,10);
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
		String[] examplePlaylists = new String[] {"Drake playlist", "Logic isn't that bad", "6ix9ine hidden gems", "YEAT's greatest hits", "Wolfgang Amadeus Mozart", "Dababy sleep playlist 2"};
		JList<String> playlistsList = new JList<String>(examplePlaylists);					// Store an arrayList of Playlist Objects here
		playlistsList.setBackground(borderColor);
		playlistsList.setForeground(detailColor);
		playlistsList.setFont(captionFont);
		JScrollPane playlistScroll = new JScrollPane();
		playlistScroll.setViewportView(playlistsList);
		playlistsList.setLayoutOrientation(JList.VERTICAL);
		//JList<Playlist> playlistsList = new JList<Playlist>();		
		gbc.gridy = 3;
		navContainer.add(playlistsList, gbc);
		
		return navContainer;
	}
	
	/**
	 * Creates the container with all components used for displaying Current song info and album art.
	 * Will later have a method used for updating graphics to always match current song playing.
	 * @return
	 */
	private Container createSongPreviewContainer() {
		Container songContainer = new Container();
		GridBagLayout colLayout = new GridBagLayout();
		
		songContainer.setLayout(colLayout);
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5,10,5,10);
		gbc.anchor = GridBagConstraints.WEST;
		// Create components
		// TESTING ALBUM COVER -- HARDCODED   ---- 
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
	
	private Container createLibraryViewContainer() {
		Container libContainer = new Container();
		BoxLayout colLayout = new BoxLayout(libContainer, BoxLayout.Y_AXIS);
		libContainer.setLayout(colLayout);
		
		return libContainer;
	}
	
	private Container createPlaybackOptionsContainer() {
		Container playbackContainer = new Container();
		GridBagLayout rowLayout = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		playbackContainer.setLayout(rowLayout);
		
		gbc.insets = new Insets(5,10,5,10);
		gbc.anchor = GridBagConstraints.SOUTH;
	
		JLabel timeStampCur = new JLabel("0:00");
		timeStampCur.setFont(captionFont);
		timeStampCur.setForeground(detailColor);
		gbc.gridx = 0;
		gbc.gridy = 0;
		playbackContainer.add(timeStampCur, gbc);
		
		JProgressBar playbackBar = new JProgressBar(SwingConstants.HORIZONTAL);
		playbackBar.setPreferredSize(new Dimension(400, 12));
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
		Dimension buttonSpacing = new Dimension(10,0);
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
	
	private Container createBrazilBeatsContainer() {
		Container brazilContainer = new Container();
		BoxLayout colLayout = new BoxLayout(brazilContainer, BoxLayout.Y_AXIS);
		brazilContainer.setLayout(colLayout);
		
		return brazilContainer;
	}
	
	/**
	 * Updates the song preview view to match current song's Album art, title, album name, and artist name
	 */
	private void updateSongPreview() {
		File albumImageFile = new File("Mmfood.jpg");
		Image albumImage;
		try {
			albumImage = ImageIO.read(albumImageFile);
			albumImage = albumImage.getScaledInstance(256, 256, Image.SCALE_DEFAULT);
			ImageIcon albumIcon = new ImageIcon(albumImage);
			
			albumImagePreview.setIcon(albumIcon);
			songTitlePreview.setText("Rapp Snitch Knishes");		// Match song name
			albumTitlePreview.setText("MM...FOOD");			// Match album name
			artistTitlePreview.setText("MF DOOM");				// Match artist name
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
