
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Locale;

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
	private Color bgColor = new Color(40, 44, 40);
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
		
		// Navigation Menu
		Container navigationMenu = createNavigationContainer();
		// Current Song Preview and info
		Container songPreview = createSongPreviewContainer();
		// Library view
		Container libraryView = createLibraryViewContainer();
		// Playback Bar
		Container playbackBar = createPlaybackOptionsContainer();
		// BRAZIL BEATS DANCING CHARACTER CORNER!!!!! LETS GOOOOOOOO!!!!!
		Container brazilBeatsView = createBrazilBeatsContainer();
		
		// Parent containers to panel
		GridBagConstraints gbc = new GridBagConstraints();
		
		JPanel navPanel = new JPanel(new GridBagLayout());
		navPanel.setBackground(bgColor);
		gbc.fill = GridBagConstraints.VERTICAL;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.gridx = 0;
		gbc.gridy = 0;
		panel.add(navPanel, gbc);
		
		navPanel.add(navigationMenu, gbc);
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.SOUTHWEST;
		gbc.insets = new Insets(5,10,5,10);
		navPanel.add(songPreview, gbc);
		
		
		
		
		
		/*
		gbc.anchor = GridBagConstraints.SOUTHWEST;
		gbc.gridx = 0;
		gbc.gridy = 1;
		//panel.add(songPreview, gbc);
		gbc.gridx = 1;
		gbc.gridy = 0;
		panel.add(libraryView, gbc);
		gbc.gridx = 1;
		gbc.gridy = 1;
		panel.add(playbackBar, gbc);
		gbc.gridx = 2;
		gbc.gridy = 0;
		panel.add(brazilBeatsView, gbc);
		*/
		
		
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
		gbc.fill = GridBagConstraints.HORIZONTAL;
		navContainer.setLayout(colLayout);
		
		// Create components
		JButton libraryButton = new JButton("Your Library");
		libraryButton.setFont(mainFont);
		libraryButton.setForeground(detailColor);
		libraryButton.setBackground(accentColor);
		
		JButton searchButton = new JButton("Search");
		searchButton.setFont(mainFont);
		searchButton.setForeground(detailColor);
		searchButton.setBackground(accentColor);
		
		JLabel playlistsLabel = new JLabel("Playlists");
		playlistsLabel.setFont(headerFont);
		playlistsLabel.setForeground(detailColor);
		
		String[] examplePlaylists = new String[] {"Drake playlist", "Logic isn't that bad", "6ix9ine hidden gems", "YEAT's greatest hits", "Wolfgang Amadeus Mozart"};
		JList<String> playlistsList = new JList<String>(examplePlaylists);					// Store an arrayList of Playlist Objects here
		playlistsList.setBackground(bgColor);
		playlistsList.setForeground(detailColor);
		playlistsList.setFont(captionFont);
		JScrollPane playlistScroll = new JScrollPane();
		playlistScroll.setViewportView(playlistsList);
		playlistsList.setLayoutOrientation(JList.VERTICAL);
		//JList<Playlist> playlistsList = new JList<Playlist>();		
		
		
		// Add all components to container
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(5,10,5,10);
		navContainer.add(libraryButton, gbc);
		gbc.gridy = 1;
		navContainer.add(searchButton, gbc);
		gbc.gridy = 2;
		navContainer.add(playlistsLabel, gbc);
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
		GridBagConstraints gbc = new GridBagConstraints();
		songContainer.setLayout(colLayout);
		
		// Create components
		// TESTING ALBUM COVER -- HARDCODED   ---- 
		albumImagePreview = new JLabel("");
		
		songTitlePreview = new JLabel("");
		songTitlePreview.setFont(mainFont);
		songTitlePreview.setForeground(detailColor);
		
		albumTitlePreview = new JLabel("");
		albumTitlePreview.setFont(captionFont);
		albumTitlePreview.setForeground(detailColor);
		
		artistTitlePreview = new JLabel("");
		artistTitlePreview.setFont(captionFont);
		artistTitlePreview.setForeground(detailColor);
		
		updateSongPreview();
		
		gbc.insets = new Insets(5,10,5,10);
		gbc.anchor = GridBagConstraints.WEST;
		// Add all components to container
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.weightx = 1;
		songContainer.add(albumImagePreview, gbc);
		gbc.gridy = 2;
		songContainer.add(songTitlePreview, gbc);
		gbc.gridy = 3;
		songContainer.add(albumTitlePreview, gbc);
		gbc.gridy = 4;
		songContainer.add(artistTitlePreview, gbc);
		
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
		BoxLayout colLayout = new BoxLayout(playbackContainer, BoxLayout.Y_AXIS);
		playbackContainer.setLayout(colLayout);
		
		return playbackContainer;
	}
	
	private Container createBrazilBeatsContainer() {
		Container brazilContainer = new Container();
		BoxLayout colLayout = new BoxLayout(brazilContainer, BoxLayout.Y_AXIS);
		brazilContainer.setLayout(colLayout);
		
		return brazilContainer;
	}
	
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
