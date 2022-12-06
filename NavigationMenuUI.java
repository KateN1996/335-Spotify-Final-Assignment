import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

/**
 * This is the UI component for the user navigation panel, which allows users to
 * choose a playlist, their library, or the search page.
 * 
 * @author Kyle Walker
 *
 */
public class NavigationMenuUI extends Container {
	private static final long serialVersionUID = 1L;
	// gui reference
	private BrazilBeatsUI gui;
	private PlaylistManager playlistManager;
	
	private JTextField playlistTextField;
	private JList<String> playlistsList;

	/**
	 * Creates the graphics container with navigation buttons and playlist tab.
	 * There are listeners on the buttons to switch the view to new panels.
	 * 
	 */
	NavigationMenuUI() {
		gui = Main.gui;
		playlistManager = Main.playlistManager;

		this.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = BrazilBeatsUI.INSET_GAP;
		// Create components

		// Button to go to your library
		JButton libraryButton = new JButton("Your Library");
		libraryButton.setFont(BrazilBeatsUI.mainFont);
		libraryButton.setForeground(BrazilBeatsUI.detailColor);
		libraryButton.setBackground(BrazilBeatsUI.accentColor);
		// TODO switch to library panel
		libraryButton.addActionListener(e -> gui.switchMainViewPane("Your Library")); // Action listener to switch to
		gbc.gridx = 0;
		gbc.gridy = 0;
		this.add(libraryButton, gbc);

		// Button for search page
		JButton searchButton = new JButton("Search");
		searchButton.setFont(BrazilBeatsUI.mainFont);
		searchButton.setForeground(BrazilBeatsUI.detailColor);
		searchButton.setBackground(BrazilBeatsUI.accentColor);
		// TODO change search panel
		searchButton.addActionListener(e -> gui.switchMainViewPane("Search Page")); // Action listener to switch to
																					// Search
		gbc.gridy = 1;
		this.add(searchButton, gbc);

		// Label above Playlists list
		JLabel playlistsLabel = new JLabel("Playlists");
		playlistsLabel.setFont(BrazilBeatsUI.headerFont);
		playlistsLabel.setForeground(BrazilBeatsUI.detailColor);
		gbc.gridy = 2;
		this.add(playlistsLabel, gbc);
		playlistsLabel.addMouseListener(new MouseListener() { // Mouse listener to switch to Playlist view when pressed
			@Override
			public void mouseClicked(MouseEvent e) {
				gui.switchMainViewPane("Playlists Page");
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}
		});
		
		// Display playlists on interactable list
		updatePlaylistsList();
		playlistsList.setBackground(BrazilBeatsUI.borderColor);
		playlistsList.setForeground(BrazilBeatsUI.detailColor);
		playlistsList.setFont(BrazilBeatsUI.captionFont);
		JScrollPane playlistScroll = new JScrollPane();
		playlistScroll.add(playlistsList, gbc);
		playlistScroll.setViewportView(playlistsList);
		playlistsList.setLayoutOrientation(JList.VERTICAL);
		// JList<Playlist> playlistsList = new JList<Playlist>();
		gbc.gridy = 3;
		this.add(playlistScroll, gbc);
		
		gbc.gridy = 4;
		playlistTextField = new JTextField();
		this.add(playlistTextField,gbc);
		
		gbc.gridy = 5;
		JButton newPlaylistButton = new JButton("Add Playlist");
		newPlaylistButton.setFont(BrazilBeatsUI.captionFont);
		newPlaylistButton.setForeground(BrazilBeatsUI.detailColor);
		newPlaylistButton.setBackground(BrazilBeatsUI.accentColor);
		newPlaylistButton.addActionListener(new newPlaylistListener());
		this.add(newPlaylistButton, gbc);
	}
	
	
	public void updatePlaylistsList() {
		ArrayList<Playlist> allPlaylists = playlistManager.getPlaylists();

		String[] playlistData = new String[allPlaylists.size()];

		// Store all playlist names into list
		for (int i = 0; i < allPlaylists.size(); i++) {
			playlistData[i] = allPlaylists.get(i).title;
		}
		playlistsList = new JList<String>(); // Store an arrayList of Playlist Objects
		// playlistsList.setListData(playlistData);
		playlistsList.setListData(playlistData);
	}
	
	
	class newPlaylistListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			String playlistName = playlistTextField.getText();
			if (playlistName == null) {
				return;
			}
			playlistManager.createPlaylist(playlistName);
			updatePlaylistsList();
		}
		
	}

}
