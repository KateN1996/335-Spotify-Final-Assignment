import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class SearchPanelUI extends Container {
	private static final long serialVersionUID = 1L;
	//private SearchInterface searchInterface;
	
	GridBagConstraints gbc;
	
	private JTextField searchField;
	private Playlist searchResults;
	private PlaylistManager playlistManager;
	
	private ListDisplayUI currentSearchResultsTable;
	private BrazilBeatsUI gui;
	
	
	SearchPanelUI(){
		//searchInterface = Main.searchInterface;
		playlistManager = Main.playlistManager;
		gui = Main.gui;
		this.setLayout(new GridBagLayout());
		gbc = new GridBagConstraints();

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = BrazilBeatsUI.INSET_GAP;
		
		searchField = new JTextField();
		
		JLabel searchTitle = new JLabel("Search for Songs");
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
}
