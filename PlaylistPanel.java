import java.awt.*;

import java.io.File;


import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.*;


public class PlaylistPanel extends Container {   

	// once we have it setup make this be a list of songs, so we can access the song
	// and stuff from here? i.e. make this the playlist itself
    PlaylistPanel(Playlist playlist) {

        // Create main panel
		this.setLayout(new GridBagLayout());
		this.setPreferredSize(BrazilBeatsUI.defaultRes);
		this.setMinimumSize(BrazilBeatsUI.defaultRes);
		this.setFocusable(true);
		this.setBackground(BrazilBeatsUI.appColor);

        Container playlistContainer = new Container();
		GridBagLayout colLayout = new GridBagLayout();

		playlistContainer.setLayout(colLayout);

        GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = BrazilBeatsUI.INSET_GAP;
		gbc.anchor = GridBagConstraints.CENTER;
		
		gbc.fill = GridBagConstraints.HORIZONTAL;

		JLabel name = new JLabel(playlist.title);
		gbc.gridy = 0;
		gbc.gridx = 0;
	
		name.setForeground(BrazilBeatsUI.detailColor);
		name.setFont(BrazilBeatsUI.headerFont);
		playlistContainer.add(name, gbc);

		/*
		JList<Song> lst = new JList<Song>();
		
		ArrayList<Song> playlistSongs = (playlist.getSongs()); 
		Song[] playlistData = new Song[playlistSongs.size()];
		int i = 0;
		for (Song s : playlistSongs) {
			playlistData[i] = s;
			i ++;
		}
		
		lst.setListData(playlistData);
		*/
		
		String[] examplePlaylists = new String[] { "Drake playlist", "Logic isn't that bad", "6ix9ine hidden gems",
				"YEAT's greatest hits", "Wolfgang Amadeus Mozart", "Dababy sleep playlist 2" };
		JList<String> lst = new JList<String>(examplePlaylists); // Store an arrayList of Playlist Objects
																			// here

		//lst.setVisibleRowCount(5); why this is not working, I'll never know
		lst.setBackground(BrazilBeatsUI.borderColor); 
		lst.setForeground(BrazilBeatsUI.detailColor);
		lst.setFont(BrazilBeatsUI.mainFont);
		JScrollPane playlistScroll = new JScrollPane();
		playlistScroll.setViewportView(lst);
		lst.setLayoutOrientation(JList.VERTICAL);
		
		gbc.gridy = 1;
		
		playlistContainer.add(lst, gbc);

		this.add(playlistContainer);
    }
}