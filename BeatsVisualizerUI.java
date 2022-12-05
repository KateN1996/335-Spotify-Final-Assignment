/**
 * 
 * Beat Visualizer class for GIF/motion changing
 * 
 * Author: Kevin Nisterenko
 */

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;


public class BeatsVisualizerUI extends Container implements Runnable{
	private BrazilBeatsUI gui;
	private static final long serialVersionUID = 1L;
	private JLabel beatsDanceView;
	private float lastAmp;
	private float threshold = 0.4f;

	BeatsVisualizerUI() {
		lastAmp = 0.5f;
		gui = Main.gui;
		GridBagLayout colLayout = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		this.setLayout(colLayout);
		gbc.insets = BrazilBeatsUI.INSET_GAP;
		
		JLabel brazilBeatsLabel = new JLabel("Beats Visualizer");
		brazilBeatsLabel.setFont(BrazilBeatsUI.headerFont);
		brazilBeatsLabel.setForeground(BrazilBeatsUI.detailColor);
		gbc.gridy = 0;
		this.add(brazilBeatsLabel, gbc);
		
		beatsDanceView = new JLabel("");
		gbc.gridy = 1;
		updateBeatsView();
		this.add(beatsDanceView, gbc);
		
	}
	
	
	/**
	 * Set Image/GIF
	 */
	private void updateBeatsView() {
		File danceImageFile = new File("brazilDance.jpg");
		Image danceImage;
		try {
			danceImage = ImageIO.read(danceImageFile);
			danceImage = danceImage.getScaledInstance(BrazilBeatsUI.IMG_RES_MAX, BrazilBeatsUI.IMG_RES_MAX, Image.SCALE_DEFAULT);
			ImageIcon albumIcon = new ImageIcon(danceImage);

			beatsDanceView.setIcon(albumIcon);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	
	/**
	 * Runnable, always checks the current amplitude of the song, if it falls 
	 * in between the range of threshold, it should advance the gif to the next 
	 * frame. 
	 */
	public void run() {
		// TODO Auto-generated method stub
		System.out.println(Main.songPlayer);
		while (true) {
			
			
			float currAmp = Main.songPlayer.getCurrentAmplitude();
			System.out.println("Checking Amps: Last -> " + lastAmp + " Curr ->" + currAmp);
			if (currAmp - threshold <= lastAmp || currAmp + threshold >= lastAmp) {
				// Advance frame
				System.out.println("ADVANCE FRAME!!!!");
			}
			lastAmp = currAmp;
		}
		
	}
}
