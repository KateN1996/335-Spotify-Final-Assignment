import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class SearchPanelUI extends Container {
	private static final long serialVersionUID = 1L;
	//private SearchInterface searchInterface;
	
	GridBagConstraints gbc;
	
	private JTextField searchField;
	private Playlist searchResults;
	private PlaylistManager playlistManager;
	private JComboBox playlistSelection;
	private JButton findButton;
	
	Container playlistSelectionContainer;
	private Container searchContainer;
	
	private SongPlayer songPlayer;
	
	private ListDisplayUI currentSearchResultsTable;
	private BrazilBeatsUI gui;
	
	
	SearchPanelUI(){
		//searchInterface = Main.searchInterface;
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
		
		
		
		JLabel searchTitle = new JLabel("Search");
		searchTitle.setFont(BrazilBeatsUI.headerFont);
		searchTitle.setForeground(BrazilBeatsUI.detailColor);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx =1;
		gbc.weighty = 1;
		searchContainer.add(searchTitle, gbc);
		
		searchField = new JTextField();
		searchField.getDocument().addDocumentListener(new searchTextListener());
		gbc.gridy = 1;
		searchContainer.add(searchField, gbc);
		
		findButton = new JButton("Search");
		findButton.setFont(BrazilBeatsUI.captionFont);
		findButton.setForeground(BrazilBeatsUI.detailColor);
		findButton.setBackground(BrazilBeatsUI.accentColor);
		findButton.setEnabled(false);
		findButton.addActionListener(new SearchListener());
		gbc.gridy = 2;
		searchContainer.add(findButton, gbc);
		
		playlistSelectionContainer = new Container();
		playlistSelectionContainer.setLayout(new GridBagLayout());
		
		JLabel playlistLabel = new JLabel("Playlist:");
		playlistLabel.setFont(BrazilBeatsUI.mainFont);
		playlistLabel.setForeground(BrazilBeatsUI.detailColor);
		gbc.gridy = 0;
		gbc.gridx = 0;
		playlistSelectionContainer.add(playlistLabel, gbc);
		updateFields();
		
		gbc.gridx = 0;
		gbc.gridy = 3;
		searchContainer.add(playlistSelectionContainer, gbc);
		
		
		
		

		
		
		JButton addToPlaylistButton = new JButton("Add to Playlist");
		addToPlaylistButton.setFont(BrazilBeatsUI.captionFont);
		addToPlaylistButton.setForeground(BrazilBeatsUI.detailColor);
		addToPlaylistButton.setBackground(BrazilBeatsUI.accentColor);
		addToPlaylistButton.addActionListener(new addToPlaylistListener());
		gbc.gridy = 4;
		searchContainer.add(addToPlaylistButton, gbc);
		
		JButton removeFromPlaylistButton = new JButton("Remove From Playlist");
		removeFromPlaylistButton.setFont(BrazilBeatsUI.captionFont);
		removeFromPlaylistButton.setForeground(BrazilBeatsUI.detailColor);
		removeFromPlaylistButton.setBackground(BrazilBeatsUI.accentColor);
		removeFromPlaylistButton.addActionListener(new removeFromPlaylistListener());
		gbc.gridy = 5;
		searchContainer.add(removeFromPlaylistButton, gbc);
		
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
	
	
	public void updateResults() {
		gbc.gridx = 1;
		gbc.gridy = 0;
		if (currentSearchResultsTable != null) {
			this.remove(currentSearchResultsTable);
		}
		currentSearchResultsTable = new ListDisplayUI(searchResults);
		
		this.add(currentSearchResultsTable, gbc);
		gui.validateFrame();
	}
	
	
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
		playlistSelection = new JComboBox(playlistNames);
		playlistSelection.setFont(BrazilBeatsUI.captionFont);
		playlistSelection.setBackground(BrazilBeatsUI.accentColor);
		playlistSelection.setForeground(BrazilBeatsUI.detailColor);
	
		gbc.gridy = 0;
		gbc.gridx = 1;
		playlistSelectionContainer.add(playlistSelection, gbc);
		
		gui.validateFrame();
	}
	
	/**
	 * Listens to button press on Search button to search using the string from the search text field.
	 * @author Kyle Walker
	 *
	 */
	class SearchListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			String subject = searchField.getText();
			System.out.println(subject);
			if (subject == null) {
				return;
			}
			SearchInterface.searchByAll(subject);
			searchResults = playlistManager.getSearchResults();
			updateResults();
		}
	}
	
	class addToPlaylistListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			String playlistName = (String) playlistSelection.getSelectedItem();
			if (playlistName == null) {
				return;
			}
			Playlist curPlaylist = playlistManager.getPlaylist(playlistName);
			curPlaylist.addSong(songPlayer.getCurrentSong());
			System.out.println(curPlaylist.getSongs());
			gui.validateFrame();
		}
		
	}
	
	class removeFromPlaylistListener implements ActionListener{

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
	
	class deletePlaylistListener implements ActionListener{

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
	
	class searchTextListener implements DocumentListener{

		@Override
		public void insertUpdate(DocumentEvent e) {
			findButton.setEnabled(true);
			
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
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
