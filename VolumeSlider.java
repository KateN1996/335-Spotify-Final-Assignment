import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.text.DecimalFormat;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;

/**
 *
 */
public class VolumeSlider extends Container implements Runnable{
	private BrazilBeatsUI gui;
	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	
	private JProgressBar volumeChanger;
	private JLabel volumeStampCurrent;
	
	private int volumeChangerSize = 160;
	private int curVolume; 
	
	
	private SongPlayer songPlayer;

	/**
	 * Creates the playback options menu on the bottom of the screen with the
	 * playback timeBar and all playback buttons. Each button is wired to
	 * methods which complete their respective functions, and the bar updates
	 * to match the song's timing.
	 * 
	 * @return playbackContainer
	 */
	VolumeSlider() {
		// Create main panel 
		this.setLayout(new GridBagLayout());
		this.setFocusable(true);
		
		gui = Main.gui;
		songPlayer = Main.songPlayer;
		
		curVolume = 50;

		// Gridbag layout of components
		GridBagLayout rowLayout = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		this.setLayout(rowLayout);

		gbc.insets = BrazilBeatsUI.INSET_GAP;
		gbc.anchor = GridBagConstraints.SOUTH;

		// Current song time stamp label
		volumeStampCurrent = new JLabel("<))");	
		volumeStampCurrent.setFont(BrazilBeatsUI.captionFont);
		volumeStampCurrent.setForeground(BrazilBeatsUI.detailColor);
		gbc.gridx = 0;
		gbc.gridy = 0;
		this.add(volumeStampCurrent, gbc);

		// Playback bar
		volumeChanger = new JProgressBar(SwingConstants.HORIZONTAL);
		volumeChanger.setPreferredSize(new Dimension(volumeChangerSize, 12));
		volumeChanger.setMaximum(100);								
		volumeChanger.setValue(50);
		volumeChanger.setBackground(BrazilBeatsUI.borderColor);
		volumeChanger.setForeground(BrazilBeatsUI.detailColor);
		volumeChanger.setStringPainted(true);
		volumeChanger.addMouseListener(new volumeChangerListener());
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 1;
		gbc.gridy = 0;
		this.add(volumeChanger, gbc);
		gbc.fill = GridBagConstraints.NONE;

	}
	
	
	private void updatevolumeChanger() {
		volumeChanger.setValue(curVolume);	
		volumeChanger.setMaximum(100); 
	}
	
	class volumeChangerListener implements MouseListener{
		
		@Override
		public void mouseClicked(MouseEvent e) {
			double selectionX = e.getPoint().x;
			
			double selectionPercentage = selectionX / volumeChangerSize;
						
			int volumeChangerMax = volumeChanger.getMaximum();
			double selectedVolume = (volumeChangerMax * selectionPercentage);
			volumeChanger.setValue((int)selectedVolume);
			
			int lastVol = curVolume;
			// this should change curVolume, so then when the update method is called,
			// it always has the up to date volume
			curVolume = (int) selectedVolume;
			
			songPlayer.incrementGain((float)(selectedVolume - lastVol));
			
			
			updatevolumeChanger();
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			volumeChanger.setForeground(BrazilBeatsUI.accentColor);
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			volumeChanger.setForeground(BrazilBeatsUI.detailColor);
			
		}
		
	}
	

	@Override
	public void run() {
		while (true) {
			updatevolumeChanger();
		}
		
	}
}
	
	
	
