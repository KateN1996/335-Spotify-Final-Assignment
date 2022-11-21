
import java.awt.*;
import javax.swing.*;

public class BrazilBeatsUI {
	private JFrame frame;
	private JPanel panel;
	private Color appColor = new Color(130, 150, 180);
	private Dimension defaultRes = new Dimension(1200, 800);
	
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
	
		gbc.anchor = GridBagConstraints.NORTHWEST;

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 0.8;
		gbc.weighty = 0.8;
		panel.add(navigationMenu, gbc);
		//gbc.fill = GridBagConstraints.VERTICAL;
		gbc.gridx = 0;
		gbc.gridy = 1;
		panel.add(songPreview, gbc);
		gbc.gridx = 1;
		gbc.gridy = 0;
		panel.add(libraryView, gbc);
		gbc.gridx = 1;
		gbc.gridy = 1;
		panel.add(playbackBar, gbc);
		gbc.gridx = 2;
		gbc.gridy = 0;
		panel.add(brazilBeatsView, gbc);
		
		// Set and open frame
		frame.setSize(defaultRes);
		frame.add(panel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);			
		frame.pack();											// Packs all components to fit on screen
		frame.setLocationRelativeTo(null);						// Centers frame on screen
		frame.setVisible(true);									// Opens frame
		
	}
	
	private Container createNavigationContainer() {
		Container navContainer = new Container();
		BoxLayout colLayout = new BoxLayout(navContainer, BoxLayout.Y_AXIS);
		navContainer.setLayout(colLayout);
	
		
		// Create components
		JButton libraryButton = new JButton("Your Library");
		JButton searchButton = new JButton("Search");
		
		String[] examplePlaylists = new String[] {"Drake playlist", "Logic isn't that bad", "6ix9ine hidden gems", "YEAT's greatest hits", "Wolfgang Amadeus Mozart"};
		JList<String> playlistsList = new JList<String>(examplePlaylists);					// Store an arrayList of Playlist Objects here
		playlistsList.setBackground(appColor);
		//JList<Playlist> playlistsList = new JList<Playlist>();		
		
		
		// Add all components to container
		navContainer.add(libraryButton);
		navContainer.add(searchButton);
		navContainer.add(playlistsList);
		
		return navContainer;
	}
	
	private Container createSongPreviewContainer() {
		Container songContainer = new Container();
		BoxLayout colLayout = new BoxLayout(songContainer, BoxLayout.Y_AXIS);
		songContainer.setLayout(colLayout);
		
		// Create components
		
		
		
		// Add all components to container
		
		
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

}
