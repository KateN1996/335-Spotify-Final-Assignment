import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;

/**
 * 
 * @author Kyle Walker
 *
 */
public class NavigationMenuUI extends Container{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BrazilBeatsUI gui;

	/**
	 * Creates the graphics container with navigation buttons and playlist tab.
	 * 
	 * @return
	 */
	NavigationMenuUI() {
		gui = Main.gui;
		// Colored panel for whole section
		GridBagLayout colLayout = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		
		this.setLayout(colLayout);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = BrazilBeatsUI.INSET_GAP;
		// Create components
		JButton libraryButton = new JButton("Your Library");
		libraryButton.setFont(BrazilBeatsUI.mainFont);
		libraryButton.setForeground(BrazilBeatsUI.detailColor);
		libraryButton.setBackground(BrazilBeatsUI.accentColor);
		// TODO switch to library panel
		libraryButton.addActionListener(e -> gui.switchMainViewPane("Your Library")); // Action listener to switch to
																					// library view when pressed
		gbc.gridx = 0;
		gbc.gridy = 0;
		this.add(libraryButton, gbc);

		JButton searchButton = new JButton("Search");
		searchButton.setFont(BrazilBeatsUI.mainFont);
		searchButton.setForeground(BrazilBeatsUI.detailColor);
		searchButton.setBackground(BrazilBeatsUI.accentColor);
		// TODO change search panel
		searchButton.addActionListener(e -> gui.switchMainViewPane("Search Page")); // Action listener to switch to Search
																				// view when pressed
		gbc.gridy = 1;
		this.add(searchButton, gbc);

		JLabel playlistsLabel = new JLabel("Playlists");
		playlistsLabel.setFont(BrazilBeatsUI.headerFont);
		playlistsLabel.setForeground(BrazilBeatsUI.detailColor);
		gbc.gridy = 2;
		this.add(playlistsLabel, gbc);
		playlistsLabel.addMouseListener(new MouseListener() { // Mouse listener to switch to Playlist view when pressed
			@Override
			public void mouseClicked(MouseEvent e) {
				gui.switchMainViewPane("Playlists Page");}
			@Override
			public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseReleased(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
		});

		// TODO: Change this to actual playlist object array
		String[] examplePlaylists = new String[] { "Drake playlist", "Logic isn't that bad", "6ix9ine hidden gems",
				"YEAT's greatest hits", "Wolfgang Amadeus Mozart", "Dababy sleep playlist 2" };
		JList<String> playlistsList = new JList<String>(examplePlaylists); // Store an arrayList of Playlist Objects
																			// here
		playlistsList.setBackground(BrazilBeatsUI.borderColor);
		playlistsList.setForeground(BrazilBeatsUI.detailColor);
		playlistsList.setFont(BrazilBeatsUI.captionFont);
		JScrollPane playlistScroll = new JScrollPane();
		playlistScroll.setViewportView(playlistsList);
		playlistsList.setLayoutOrientation(JList.VERTICAL);
		// JList<Playlist> playlistsList = new JList<Playlist>();
		gbc.gridy = 3;
		this.add(playlistsList, gbc);
	}

}
