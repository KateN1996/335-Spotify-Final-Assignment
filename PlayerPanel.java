/**
 * Panel for the album cover/big player view. 
 * 
 * Author: Kevin Nisterenko
 */

import java.awt.*;

import java.io.File;
import java.awt.image.BufferedImage;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.*;


public class PlayerPanel extends JPanel{   
    private JLabel albumCoverPreview;
    private JLabel songTitlePreview;
	private JLabel albumTitlePreview;
	private JLabel artistTitlePreview;
	private Song currentSong;

    private Color textColor = new Color(255, 255, 255);
    private Color backgroundColor;

    PlayerPanel(Song curSong) {
        // Create main panel 
		super(new GridBagLayout());
		this.setPreferredSize(new Dimension(BrazilBeatsUI.IMG_RES_MAX *2, BrazilBeatsUI.IMG_RES_MAX *2));

		currentSong = curSong;

        // setup the containers for the cover and text
        Container songContainer = new Container();
		GridBagLayout colLayout = new GridBagLayout();

		songContainer.setLayout(colLayout);
        GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = BrazilBeatsUI.INSET_GAP;
		gbc.anchor = GridBagConstraints.CENTER;

		// Create components
		albumCoverPreview = new JLabel("");
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1;
        songContainer.add(albumCoverPreview, gbc);

        // need to center them hmm
        songTitlePreview = new JLabel("");
		songTitlePreview.setFont(BrazilBeatsUI.mainFont);
		songTitlePreview.setForeground(textColor);
		gbc.gridy = 1;
		songContainer.add(songTitlePreview, gbc);

		albumTitlePreview = new JLabel("");
		albumTitlePreview.setFont(BrazilBeatsUI.captionFont);
		albumTitlePreview.setForeground(textColor);
		gbc.gridy = 2;
		songContainer.add(albumTitlePreview, gbc);

		artistTitlePreview = new JLabel("");
		artistTitlePreview.setFont(BrazilBeatsUI.captionFont);
		artistTitlePreview.setForeground(textColor);
		gbc.gridy = 3;
		songContainer.add(artistTitlePreview, gbc);


        updateSongPreview();

		this.add(songContainer);
    }

    /**
	 * Updates the song preview view to match current song's Album art, title, album
	 * name, and artist name
	 */
	public void updateSongPreview() {
		File albumImageFile = new File(currentSong.coverPath);
		Image albumImage;
		try {
			albumImage = ImageIO.read(albumImageFile);
			albumImage = albumImage.getScaledInstance((int)(BrazilBeatsUI.IMG_RES_MAX*1.4), (int)(BrazilBeatsUI.IMG_RES_MAX*1.4), Image.SCALE_DEFAULT);
		       // get the album image and the dominant color
			ColorPicker bgColorPicker = new ColorPicker(albumImage);
			backgroundColor = (bgColorPicker.getColor());
			ImageIcon albumIcon = new ImageIcon(albumImage);
		    albumCoverPreview.setIcon(albumIcon);
			
        } catch (Exception e) {
            e.printStackTrace();
        }
       
        songTitlePreview.setText(currentSong.title); // Match song names
        albumTitlePreview.setText(currentSong.album); // Match album name
        artistTitlePreview.setText(currentSong.artist); // Match artist name
    }

	/**
	 * Override the paintComponent in order to have a gradient background for the JPanel
	 */
	@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

		// setup the 2D Graphics and modify the rendering
        Graphics2D Object2D = (Graphics2D) g;
        Object2D.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        int width = getWidth();
		int height = getHeight();

		// set the gradient paint going from the dominat color to black
        GradientPaint gp = new GradientPaint(0, 0, backgroundColor, width, height, Color.BLACK);
		// paint the background
        Object2D.setPaint(gp);
        Object2D.fillRect(0, 0, width, height);
    }
}