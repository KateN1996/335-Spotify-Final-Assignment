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

    PlayerPanel(Image albumImage) {

        // Create main panel
		super(new GridBagLayout());
		this.setPreferredSize(defaultRes);
		this.setFocusable(true);


        // get the album image and the dominant color
        albumCover = albumImage;
		ColorPicker bgColorPicker = new ColorPicker(albumCover);
		backgroundColor = bgColorPicker.getColor();

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


        updateSongPreview();

		this.add(songContainer);
    }

    /**
	 * Updates the song preview view to match current song's Album art, title, album
	 * name, and artist name
	 */
	private void updateSongPreview() {
		File albumImageFile = new File("Mmfood.jpg");
		Image albumImage;
		try {
			albumImage = ImageIO.read(albumImageFile);
			albumImage = albumImage.getScaledInstance(IMG_RES_MAX, IMG_RES_MAX, Image.SCALE_DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        albumCover = albumCover.getScaledInstance(IMG_RES_MAX*2, IMG_RES_MAX*2, Image.SCALE_DEFAULT);
        ImageIcon albumIcon = new ImageIcon(albumCover);

        albumCoverPreview.setIcon(albumIcon);
        songTitlePreview.setText("Rapp Snitch Knishes"); // Match song names
        albumTitlePreview.setText("MM...FOOD"); // Match album name
        artistTitlePreview.setText("MF DOOM"); // Match artist name
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