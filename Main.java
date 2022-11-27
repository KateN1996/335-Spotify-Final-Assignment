import javax.swing.*;  
import java.awt.*;
public class Main {  

    static final Dimension defaultRes = new Dimension(1400, 800);
	
	static final int IMG_RES_MAX = 256;
	
	static final int SPACINGX = 5;
	static final int SPACINGY = 10;

    public static void main(String[] args) {  

        JFrame frame = new JFrame("Brazil Beats Premium");

		String[] lst = {"Set on You", "Come and Get your Love", "Country Roads (Take me Home)", 
						"Material Girl", "Exagerado", "Highway to Hell", "Friday", "Merry go Round of life",
					    "Hooked on a feeling", "Minecraft Theme Song"};
        JPanel panel = new PlaylistPanel("Random Music", lst);
        // Set and open frame
		frame.setSize(defaultRes);
		frame.add(panel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack(); // Packs all components to fit on screen
		frame.setLocationRelativeTo(null); // Centers frame on screen
		frame.setVisible(true); // Opens frame
    }  
}  
