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

public class SearchPanelUI extends Container {
	private static final long serialVersionUID = 1L;
	//private SearchInterface searchInterface;
	
	GridBagConstraints gbc;
	
	private JTextField searchField;
	private Playlist searchResults;
	private PlaylistManager playlistManager;
	private JComboBox playlistSelection;
	
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

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = BrazilBeatsUI.INSET_GAP;
		
		searchField = new JTextField();
		
		JLabel searchTitle = new JLabel("Search");
		searchTitle.setFont(BrazilBeatsUI.headerFont);
		searchTitle.setForeground(BrazilBeatsUI.detailColor);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx =1;
		gbc.weighty = 1;
		this.add(searchTitle, gbc);
		
		gbc.gridy = 1;
		this.add(searchField, gbc);
		
		
		JButton findButton = new JButton("Search");
		findButton.setFont(BrazilBeatsUI.captionFont);
		findButton.setForeground(BrazilBeatsUI.detailColor);
		findButton.setBackground(BrazilBeatsUI.accentColor);
		findButton.addActionListener(new SearchListener());
		gbc.gridy = 2;
		this.add(findButton, gbc);
		
		
		JButton addToPlaylistButton = new JButton("Add to Playlist: ");
		addToPlaylistButton.setFont(BrazilBeatsUI.captionFont);
		addToPlaylistButton.setForeground(BrazilBeatsUI.detailColor);
		addToPlaylistButton.setBackground(BrazilBeatsUI.accentColor);
		addToPlaylistButton.addActionListener(new addToPlaylistListener());
		gbc.gridy = 4;
		this.add(addToPlaylistButton, gbc);
		
		JButton removeFromPlaylistButton = new JButton("Remove From Playlist: ");
		removeFromPlaylistButton.setFont(BrazilBeatsUI.captionFont);
		removeFromPlaylistButton.setForeground(BrazilBeatsUI.detailColor);
		removeFromPlaylistButton.setBackground(BrazilBeatsUI.accentColor);
		removeFromPlaylistButton.addActionListener(new removeFromPlaylistListener());
		gbc.gridy = 5;
		this.add(removeFromPlaylistButton, gbc);
		
		JButton deletePlaylistButton = new JButton("Delete Playlist: ");
		deletePlaylistButton.setFont(BrazilBeatsUI.captionFont);
		deletePlaylistButton.setForeground(BrazilBeatsUI.detailColor);
		deletePlaylistButton.setBackground(BrazilBeatsUI.accentColor);
		deletePlaylistButton.addActionListener(new deletePlaylistListener());
		gbc.gridy = 6;
		this.add(deletePlaylistButton, gbc);
	
		updateFields();
	
	}
	
	
	public void updateResults() {
		gbc.gridy = 3;
		if (currentSearchResultsTable != null) {
			this.remove(currentSearchResultsTable);
		}
		
		currentSearchResultsTable = new ListDisplayUI(searchResults);
		
		this.add(currentSearchResultsTable);
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
			this.remove(playlistSelection);
		}
		playlistSelection = new JComboBox(playlistNames);
		gbc.gridy = 6;
		this.add(playlistSelection);
	}
	
	
	class SearchListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			String subject = searchField.getText();
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
}
