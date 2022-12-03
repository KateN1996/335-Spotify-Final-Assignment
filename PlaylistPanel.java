import java.awt.*;

import java.io.File;


import java.awt.image.BufferedImage;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.*;


public class PlaylistPanel extends JPanel {   
	private Font mainFont = new Font("Arial Bold", Font.PLAIN, 16);
	private Font captionFont = new Font("Arial Bold", Font.PLAIN, 12);
	private Dimension defaultRes = new Dimension(1400, 800); 

	private Color appColor = new Color(50, 55, 50);			// Light Brazil Green
	private Color borderColor = new Color(30, 33, 30);		// Dark Brazil Green
	private Color detailColor = new Color(230, 220, 100);	// Light Brazil Yellow

    static final int IMG_RES_MAX = 256;

    static final int SPACINGX = 5;
	static final int SPACINGY = 10;
    static final Insets INSET_GAP = new Insets(SPACINGX, SPACINGY, SPACINGX, SPACINGY);

	// once we have it setup make this be a list of songs, so we can access the song
	// and stuff from here? i.e. make this the playlist itself
    PlaylistPanel(String playlistName, String[] playlist) {

        // Create main panel
		super(new GridBagLayout());
		this.setPreferredSize(defaultRes);
		this.setFocusable(true);
		this.setBackground(appColor);

        Container playlistContainer = new Container();
		GridBagLayout colLayout = new GridBagLayout();

		playlistContainer.setLayout(colLayout);
		playlistContainer.setMinimumSize(defaultRes);
		playlistContainer.setSize(defaultRes);

        GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = INSET_GAP;
		gbc.anchor = GridBagConstraints.WEST;
		
		gbc.fill = GridBagConstraints.HORIZONTAL;

		JLabel name = new JLabel(playlistName);
		gbc.gridy = 0;
		gbc.gridx = 0;
	
		name.setText(playlistName);
		name.setForeground(detailColor);
		name.setFont(captionFont);
		playlistContainer.add(name, gbc);

		JList<String> lst = new JList<String>(playlist); 

		//lst.setVisibleRowCount(5); why this is not working, I'll never know
		lst.setBackground(borderColor); 
		lst.setForeground(detailColor);
		lst.setFont(captionFont);
		JScrollPane playlistScroll = new JScrollPane();
		playlistScroll.setViewportView(lst);
		lst.setLayoutOrientation(JList.VERTICAL);
		
		gbc.gridy = 1;
		
		playlistContainer.add(lst, gbc);

		this.add(playlistContainer);
    }
}