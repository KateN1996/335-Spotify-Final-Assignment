import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class ColorPicker {
	Image img;
	BufferedImage image;
	
	public ColorPicker(Image albumImage) {
		img = albumImage;
	    image = toBufferedImage();
	}
	
	/**
     * Dominant Color Picker, inspired by the algorithm from jittagornp
     * from https://gist.github.com/jittagornp/6c7fcdab388fe4863c34
     * @param image
     * @return
     */
	public Color getColor() {
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
        int[] rgbVals = getColorArray(colorMap);

		return new Color(rgbVals[0], rgbVals[1], rgbVals[2]);
    }

    /**
     * Gets Color Array from integer map
     */
    private int[] getColorArray(Map<Integer, Integer> map) {
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
     * From a given pixel, separate the color into rgb values
     * 
     * @param pixel (integer for pixel and its color values)
     * @return int array for rgb values of the pixel
     */
    private int[] getRGBArr(int pixel) {
        // use shifting and masking to acquire the actual integer values for 
        // each rgb val from the hex representation
        int redVal = (pixel >> 16) & 0xff;
        int greenVal = (pixel >> 8) & 0xff;
        int blueVal = (pixel) & 0xff;
        
        return new int[] {redVal, greenVal, blueVal};
    }

    /**
     * Checks if a pixel is grayscale
     * 
     * @param rgbArr (array of rgb value)
     * @return boolean
     */
    private boolean isGrayScale(int[] rgbArr) {
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
	private BufferedImage toBufferedImage() {
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
