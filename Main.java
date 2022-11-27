import javax.swing.*;  
import java.awt.*;
import java.io.File;
import javax.imageio.ImageIO;
public class Main {  
	static final Dimension defaultRes = new Dimension(1400, 800);
	
	static final int IMG_RES_MAX = 256;
	
	static final int SPACINGX = 5;
	static final int SPACINGY = 10;
	static final Insets INSET_GAP = new Insets(SPACINGX, SPACINGY, SPACINGX, SPACINGY);
    public static void main(String[] args) {  
        JFrame frame = new JFrame("Brazil Beats Premium");

        
        JPanel panel = new PlaylistPanel();
        // Set and open frame
		frame.setSize(defaultRes);
		frame.add(panel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack(); // Packs all components to fit on screen
		frame.setLocationRelativeTo(null); // Centers frame on screen
		frame.setVisible(true); // Opens frame
    }  
}  
