import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * Serves as the main Search UI for searching for songs, adding and delteing to
 * playlists, and even deleting selected playlists. Allows user to search for
 * songs stored in the app using the searchInterface search algorithm, and can
 * play when selecting each song.
 * 
 * @author Kyle Walker
 *
 */
public class SearchPanelUI extends Container {
	private static final long serialVersionUID = 1L;

	GridBagConstraints gbc;

	// UI Components
	private JTextField searchField;
	private Playlist searchResults;
	private PlaylistManager playlistManager;
	private JComboBox<String> playlistSelection;
	private Container searchContainer;
	private JButton findButton;
	Container playlistSelectionContainer;

	private ListDisplayUI currentSearchResultsTable;

	// References
	private SongPlayer songPlayer;
	private BrazilBeatsUI gui;

	/**
	 * Constructs a new search panel and prepares search bar.
	 */
	SearchPanelUI() {
		playlistManager = Main.playlistManager;
		gui = Main.gui;
		songPlayer = Main.songPlayer;

		this.setLayout(new GridBagLayout());
		gbc = new GridBagConstraints();

		searchContainer = new Container();
		searchContainer.setLayout(new GridBagLayout());

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = BrazilBeatsUI.INSET_GAP;

		// Title label
		JLabel searchTitle = new JLabel("Search");
		searchTitle.setFont(BrazilBeatsUI.headerFont);
		searchTitle.setForeground(BrazilBeatsUI.detailColor);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1;
		gbc.weighty = 1;
		searchContainer.add(searchTitle, gbc);

		// Search bar
		searchField = new JTextField();
		searchField.getDocument().addDocumentListener(new searchTextListener());
		gbc.gridy = 1;
		searchContainer.add(searchField, gbc);

		// Search button (only active after typing in bar)
		findButton = new JButton("Search");
		findButton.setFont(BrazilBeatsUI.captionFont);
		findButton.setForeground(BrazilBeatsUI.detailColor);
		findButton.setBackground(BrazilBeatsUI.accentColor);
		findButton.setEnabled(false);
		findButton.addActionListener(new SearchListener());
		gbc.gridy = 2;
		searchContainer.add(findButton, gbc);

		// Container for playlist selection components
		playlistSelectionContainer = new Container();
		playlistSelectionContainer.setLayout(new GridBagLayout());

		// Label for playlist dropdown
		JLabel playlistLabel = new JLabel("Playlist:");
		playlistLabel.setFont(BrazilBeatsUI.mainFont);
		playlistLabel.setForeground(BrazilBeatsUI.detailColor);
		gbc.gridy = 0;
		gbc.gridx = 0;
		playlistSelectionContainer.add(playlistLabel, gbc);

		// Creates playlist Dropdown using current playlists
		updateFields();

		gbc.gridx = 0;
		gbc.gridy = 3;
		searchContainer.add(playlistSelectionContainer, gbc);

		// button to add selected song to playlist
		JButton addToPlaylistButton = new JButton("Add to Playlist");
		addToPlaylistButton.setFont(BrazilBeatsUI.captionFont);
		addToPlaylistButton.setForeground(BrazilBeatsUI.detailColor);
		addToPlaylistButton.setBackground(BrazilBeatsUI.accentColor);
		addToPlaylistButton.addActionListener(new addToPlaylistListener());
		gbc.gridy = 4;
		searchContainer.add(addToPlaylistButton, gbc);

		// button to remove selected song from playlist
		JButton removeFromPlaylistButton = new JButton("Remove From Playlist");
		removeFromPlaylistButton.setFont(BrazilBeatsUI.captionFont);
		removeFromPlaylistButton.setForeground(BrazilBeatsUI.detailColor);
		removeFromPlaylistButton.setBackground(BrazilBeatsUI.accentColor);
		removeFromPlaylistButton.addActionListener(new removeFromPlaylistListener());
		gbc.gridy = 5;
		searchContainer.add(removeFromPlaylistButton, gbc);

		// Button for deleting selected playlist
		JButton deletePlaylistButton = new JButton("Delete Playlist");
		deletePlaylistButton.setFont(BrazilBeatsUI.captionFont);
		deletePlaylistButton.setForeground(BrazilBeatsUI.detailColor);
		deletePlaylistButton.setBackground(BrazilBeatsUI.accentColor);
		deletePlaylistButton.addActionListener(new deletePlaylistListener());
		gbc.gridy = 6;
		searchContainer.add(deletePlaylistButton, gbc);

		gbc.gridx = 0;
		gbc.gridy = 0;
		this.add(searchContainer, gbc);

	}

	/**
	 * Updates the search results display to reflect changes to song library.
	 * Creates a new List Display with the current search results playlist, so that
	 * every time a new item is searched the order will be displayed properly.
	 */
	public void updateResults() {
		gbc.gridx = 1;
		gbc.gridy = 0;
		if (currentSearchResultsTable != null) {
			this.remove(currentSearchResultsTable);
		}
		// new List Display for current search results order
		currentSearchResultsTable = new ListDisplayUI(searchResults);

		this.add(currentSearchResultsTable, gbc);
		// Update components
		gui.validateFrame();
	}

	/**
	 * Updates and recreates playlist selection dropdown to reflect current list of
	 * available playlists.
	 */
	public void updateFields() {
		String[] playlistNames = new String[playlistManager.getPlaylists().size()];
		int i = 0;
		for (Playlist p : playlistManager.getPlaylists()) {
			playlistNames[i] = p.title;
			i++;
		}
		if (playlistSelection != null) {
			playlistSelectionContainer.remove(playlistSelection);
		}
		playlistSelection = new JComboBox<String>(playlistNames);
		playlistSelection.setFont(BrazilBeatsUI.captionFont);
		playlistSelection.setBackground(BrazilBeatsUI.accentColor);
		playlistSelection.setForeground(BrazilBeatsUI.detailColor);

		gbc.gridy = 0;
		gbc.gridx = 1;
		playlistSelectionContainer.add(playlistSelection, gbc);
	}

	/**
	 * Listens to button press on Search button to search using the string from the
	 * search text field. uses the Search Interface search algorithm to present the
	 * most relevant songs in order on the List Display.
	 * 
	 * @author Kyle Walker
	 *
	 */
	class SearchListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String subject = searchField.getText();
			if (subject == null) {
				return;
			}
			// uses search to update dislay with relevant songs
			SearchInterface.searchByAll(subject);
			searchResults = playlistManager.getSearchResults();
			updateResults();
		}
	}

	/**
	 * Listener for adding to playlist button. Whenever it is pressed, it will add
	 * the selected song to the selected playlist.
	 * 
	 * @author Kyle Walker
	 *
	 */
	class addToPlaylistListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String playlistName = (String) playlistSelection.getSelectedItem();
			if (playlistName == null) {
				return;
			}
			// add song to playlist
			Playlist curPlaylist = playlistManager.getPlaylist(playlistName);
			curPlaylist.addSong(songPlayer.getCurrentSong());
			gui.validateFrame();
		}

	}

	/**
	 * Listens to remove from playlist button and removes selected song from
	 * selected playlist.
	 * 
	 * @author Kyle Walker
	 *
	 */
	class removeFromPlaylistListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String playlistName = (String) playlistSelection.getSelectedItem();
			if (playlistName == null) {
				return;
			}
			Playlist curPlaylist = playlistManager.getPlaylist(playlistName);
			curPlaylist.removeSong(songPlayer.getCurrentSong());
			gui.validateFrame();
		}

	}

	/**
	 * Listens to delte playlist button and delteds the selcted playlist from the
	 * allPlaylists list.
	 * 
	 * @author Kyle Walker
	 *
	 */
	class deletePlaylistListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String playlistName = (String) playlistSelection.getSelectedItem();
			if (playlistName == null) {
				return;
			}
			Playlist curPlaylist = playlistManager.getPlaylist(playlistName);
			playlistManager.deletePlaylist(curPlaylist);
			gui.validateFrame();
		}

	}

	/**
	 * Listens to changes made in the search bar text field to detect if it is
	 * empty, in which case it will disable the search button and activate it when
	 * something is entered.
	 * 
	 * @author Kyle Walker
	 *
	 */
	class searchTextListener implements DocumentListener {

		@Override
		public void insertUpdate(DocumentEvent e) {
			// Enable button when text is entered
			findButton.setEnabled(true);

		}

		@Override
		public void removeUpdate(DocumentEvent e) {

			// Disable button if field is empty
			if (searchField.getText().length() <= 0) {
				findButton.setEnabled(false);
			}

		}

		@Override
		public void changedUpdate(DocumentEvent e) {
			// TODO Auto-generated method stub

		}

	}
}
