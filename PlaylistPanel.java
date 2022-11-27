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


    static final int IMG_RES_MAX = 256;

    static final int SPACINGX = 5;
	static final int SPACINGY = 10;
    static final Insets INSET_GAP = new Insets(SPACINGX, SPACINGY, SPACINGX, SPACINGY);


    private JLabel albumCoverPreview;
    private JLabel songTitlePreview;
	private JLabel albumTitlePreview;
	private JLabel artistTitlePreview;

    private Image albumCover;
    private Color backgroundColor;
    private Color textColor = new Color(255, 255, 255);

    PlaylistPanel(Image albumImage) {

        // Create main panel
		super(new GridBagLayout());
		this.setPreferredSize(defaultRes);
		this.setFocusable(true);


        // get the album image and the dominant color
        albumCover = albumImage;
		BufferedImage img = toBufferedImage(albumImage);
		backgroundColor = getBackgroundColor(img);


        // setup the containers for the cover and text
        Container songContainer = new Container();
		GridBagLayout colLayout = new GridBagLayout();

		songContainer.setLayout(colLayout);
        GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = INSET_GAP;
		gbc.anchor = GridBagConstraints.WEST;

		// Create components
		albumCoverPreview = new JLabel("");
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.weightx = 1;
        songContainer.add(albumCoverPreview, gbc);

        // need to center them hmm
        songTitlePreview = new JLabel("");
		songTitlePreview.setFont(mainFont);
		songTitlePreview.setForeground(textColor);
		gbc.gridy = 2;
		songContainer.add(songTitlePreview, gbc);

		albumTitlePreview = new JLabel("");
		albumTitlePreview.setFont(captionFont);
		albumTitlePreview.setForeground(textColor);
		gbc.gridy = 3;
		songContainer.add(albumTitlePreview, gbc);

		artistTitlePreview = new JLabel("");
		artistTitlePreview.setFont(captionFont);
		artistTitlePreview.setForeground(textColor);
		gbc.gridy = 4;
		songContainer.add(artistTitlePreview, gbc);

		this.add(songContainer);
    }
}