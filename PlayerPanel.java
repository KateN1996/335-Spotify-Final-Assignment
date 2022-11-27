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

    /**
     * Dominant Color Picker, inspired by the algorithm from jittagornp
     * from https://gist.github.com/jittagornp/6c7fcdab388fe4863c34
     * @param image
     * @return
     */
	private static Color getBackgroundColor(BufferedImage image) {
        // setup the colorMap to store the rgb value and the count of each rgb value
        // essentially we are setting a color occurence map
        Map<Integer, Integer> colorMap = new HashMap<Integer, Integer>();

        // iterate over the pixes in the image and get the rgb value from each one
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                int rgb = image.getRGB(i, j);
                // if the pixel is colored (i.e not grayscaled) we want to keep it,
                // so we put it on the colorMap
                if (!isGrayScale(getRGBArr(rgb))) {
                    Integer count = colorMap.get(rgb);
                    if (count == null) {
                        count = 0;
                    }
                    colorMap.put(rgb, ++count);
                }
            }
        }

        // setup the most common color and put them inside the rgb array so we
        // can have red, green and blue value for it
        int[] rgbVals = getMostCommonColor(colorMap);

		return new Color(rgbVals[0], rgbVals[1], rgbVals[2]);
    }

    /**
     * 
     * @param map
     * @return
     */
    private static int[] getMostCommonColor(Map<Integer, Integer> map) {
        // setup a list of each key value pair in the map so we can sort it
        java.util.List<Map.Entry<Integer, Integer>> colorList = new LinkedList<>(map.entrySet());

        // use a lambda to sort the colorList by its entries
        Collections.sort(colorList, (Map.Entry<Integer, Integer> obj1, Map.Entry<Integer, Integer> obj2)
                -> ((Comparable) obj1.getValue()).compareTo(obj2.getValue()));

        // get the last list from the sorted colorList, i.e, the color that happens most
        Map.Entry<Integer, Integer> color = colorList.get(colorList.size() - 1);

        return getRGBArr(color.getKey());
    }

    /**
     * 
     * @param pixel
     * @return
     */
    private static int[] getRGBArr(int pixel) {
        // use shifting and masking to acquire the actual integer values for 
        // each rgb val from the hex representation
        int redVal = (pixel >> 16) & 0xff;
        int greenVal = (pixel >> 8) & 0xff;
        int blueVal = (pixel) & 0xff;
        
        return new int[] {redVal, greenVal, blueVal};
    }

    /**
     * 
     * @param rgbArr
     * @return
     */
    private static boolean isGrayScale(int[] rgbArr) {
        // get the difference between red and green and red and blue, will 
        // use it to figure out which pixels are grayscaled
        int rgDiff = rgbArr[0] - rgbArr[1];
        int rbDiff = rgbArr[0] - rgbArr[2];
        // filtering blacks, whites and grays, can set the tolerance
        int tolerance = 10;
        // if the pixel falls between the accepted tolerance from the spectrum 
        // of grayscale, we can say it is not gray
        if (rgDiff > tolerance || rgDiff < -tolerance) {
            if (rbDiff > tolerance || rbDiff < -tolerance) {
                return false;
            }
        }
        return true;
    }

	/**
	 * Converts a given Image into a BufferedImage
	 * from https://stackoverflow.com/questions/13605248/java-converting-image-to-bufferedimage
	 *
	 * @param img, image we want to convert to the BufferedImage type
	 * @return BufferedImage representing the given image
	 */
	private static BufferedImage toBufferedImage(Image img) {
		// Create a buffered image so we can set it up according to our image
        // we also set it with transparency
		BufferedImage buffImage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

		// Use the Graohics2D type so we can draw the image on our newly created BufferedImage
		Graphics2D bufferGraphic = buffImage.createGraphics();
		bufferGraphic.drawImage(img, 0, 0, null);
		bufferGraphic.dispose();

		// Return the buffered image
		return buffImage;
	}
}