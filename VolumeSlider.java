import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


import javax.swing.JLabel;

import javax.swing.JProgressBar;
import javax.swing.SwingConstants;

/**
 * Container for the volume slider of the song player. Implemented
 * using a progress bar and mouse listeners.  
 * 
 *@author Kevin Nisterenko
 */
public class VolumeSlider extends Container{
	
	private static final long serialVersionUID = 1L;
	
	private JProgressBar volumeChanger;
	private JLabel volumeStampCurrent;
	
	private int volumeChangerSize = 250;
	
	
	private SongPlayer songPlayer;

	/**
	 * Creates the volume bar for the UI. 
	 * 
	 */
	VolumeSlider() {
		// Create main panel 
		this.setLayout(new GridBagLayout());
		this.setFocusable(true);
		
		songPlayer = Main.songPlayer;
		
		// Gridbag layout of components
		GridBagLayout rowLayout = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		this.setLayout(rowLayout);

		gbc.insets = BrazilBeatsUI.INSET_GAP;
		gbc.anchor = GridBagConstraints.SOUTH;

		// Current song volume stamp label
		volumeStampCurrent = new JLabel("<))");	
		volumeStampCurrent.setFont(BrazilBeatsUI.captionFont);
		volumeStampCurrent.setForeground(BrazilBeatsUI.detailColor);
		gbc.gridx = 0;
		gbc.gridy = 0;
		this.add(volumeStampCurrent, gbc);

		// volume bar
		volumeChanger = new JProgressBar(SwingConstants.HORIZONTAL);
		volumeChanger.setPreferredSize(new Dimension(volumeChangerSize, 12));
		volumeChanger.setBackground(BrazilBeatsUI.borderColor);
		volumeChanger.setForeground(BrazilBeatsUI.detailColor);
		volumeChanger.setStringPainted(true);
		volumeChanger.addMouseListener(new volumeChangerListener());
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 1;
		gbc.gridy = 0;
		this.add(volumeChanger, gbc);
		gbc.fill = GridBagConstraints.NONE;		
		
		// set the bounds of the volume changer
		volumeChanger.setMaximum(100);	
		volumeChanger.setMinimum(0);								
		volumeChanger.setValue(100);
	}
	
	/**
	 * Inner class for the volume change listener, which implements
	 * a MouseListener so that it can update the song's volume level.
	 * 
	 * @author Kevin Nisterenko
	 *
	 */
	class volumeChangerListener implements MouseListener{
		
		/**
		 * Gets the selection position from the mouse's position on 
		 * the bar, calculates the new gain/volume based on the
		 * range of the song, and sets the song's gain to that 
		 * level. 
		 * 
		 * @param e, MouseEvent representing a mouse click
		 */
		@Override
		public void mouseClicked(MouseEvent e) {
			float selectionX = e.getPoint().x;
			float selectionPercentage = selectionX / volumeChangerSize;
			float range = songPlayer.getGainMax() - songPlayer.getGainMin();
			float gain = range * selectionPercentage;
			gain += songPlayer.getGainMin();
			
			songPlayer.setGain(gain);
						
			int volumeChangerMax = volumeChanger.getMaximum();
			int selectedVolume =(int) (volumeChangerMax * selectionPercentage);
			volumeChanger.setValue(selectedVolume);	
			volumeChanger.setMaximum(100); 
		}

		// Overrides due to interface implementation
		@Override
		public void mousePressed(MouseEvent e) {}

		@Override
		public void mouseReleased(MouseEvent e) {}

		/**
		 * Sets bar color back to accent color when mouse 
		 * enters volume bar.
		 * 
		 * @param e, MouseEvent representing a mouse click
		 */
		@Override
		public void mouseEntered(MouseEvent e) {
			volumeChanger.setForeground(BrazilBeatsUI.accentColor);
		}
		
		/**
		 * Resets bar color back to original after mouse leaves 
		 * the volume bar.
		 * 
		 * @param e, MouseEvent representing a mouse exit
		 */
		@Override
		public void mouseExited(MouseEvent e) {
			volumeChanger.setForeground(BrazilBeatsUI.detailColor);
		}
		
	}
}
	
	
	
