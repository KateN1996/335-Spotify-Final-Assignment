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
		
		gbc.gridy = 2;
		JButton findButton = new JButton("Search");
		findButton.setFont(BrazilBeatsUI.captionFont);
		findButton.setForeground(BrazilBeatsUI.detailColor);
		findButton.setBackground(BrazilBeatsUI.accentColor);
		findButton.addActionListener(new SearchListener());
		this.add(findButton, gbc);
		
		gbc.gridy = 4;
		JButton addToPlaylistButton = new JButton("Add to Playlist: ");
		addToPlaylistButton.setFont(BrazilBeatsUI.captionFont);
		addToPlaylistButton.setForeground(BrazilBeatsUI.detailColor);
		addToPlaylistButton.setBackground(BrazilBeatsUI.accentColor);
		addToPlaylistButton.addActionListener(new addToPlaylistListener());
		this.add(addToPlaylistButton, gbc);
	
		
		String[] playlistNames = new String[playlistManager.getPlaylists().size()];
		int i = 0;
		for (Playlist p : playlistManager.getPlaylists()) {
			playlistNames[i] = p.title;
			i++;
		}
		playlistSelection = new JComboBox(playlistNames);
		
		gbc.gridy = 5;
		this.add(playlistSelection);
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
		}
		
	}
}
