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
 * This is the UI container containing all the playback controls for the current song. It has a playback bar with working time stamps
 * and a progress bar that updates in real-time with its own thread. There are buttons used for playback controls including shuffle, 
 * restart/play previous, play and pause, play next song, and save song to library. The playback bar also allows the user to select the
 * exact time in the song by clicking anywhere on the progress bar.
 * 
 * @author Kyle Walker
 *
 */
public class VolumeSlider extends JPanel implements Runnable{
	private BrazilBeatsUI gui;
	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	
	private boolean isPaused = false;
	
	private JProgressBar volumeChanger;
	private JLabel volumeStampCurrent;
	
	private int volumeChangerSize = 400;
	private int vol; 
	
	
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
		super(new GridBagLayout());
		this.setPreferredSize(new Dimension(BrazilBeatsUI.IMG_RES_MAX *2, BrazilBeatsUI.IMG_RES_MAX *2));
		this.setFocusable(true);
		
		gui = Main.gui;
		songPlayer = Main.songPlayer;
		
		this.setBackground(gui.appColor);
		
		vol = 50;

		// Gridbag layout of components
		GridBagLayout rowLayout = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		this.setLayout(rowLayout);

		gbc.insets = BrazilBeatsUI.INSET_GAP;
		gbc.anchor = GridBagConstraints.SOUTH;

		// Current song time stamp label
		volumeStampCurrent = new JLabel("0");	
		volumeStampCurrent.setFont(BrazilBeatsUI.captionFont);
		volumeStampCurrent.setForeground(BrazilBeatsUI.detailColor);
		gbc.gridx = 0;
		gbc.gridy = 0;
		this.add(volumeStampCurrent, gbc);

		// Playback bar
		volumeChanger = new JProgressBar(SwingConstants.HORIZONTAL);
		volumeChanger.setPreferredSize(new Dimension(volumeChangerSize, 12));
		volumeChanger.setMaximum(100);								// Max set to max length of song
		volumeChanger.setValue(50);
		volumeChanger.setBackground(BrazilBeatsUI.borderColor);
		volumeChanger.setForeground(BrazilBeatsUI.detailColor);
		volumeChanger.addMouseListener(new volumeChangerListener());
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 1;
		gbc.gridy = 0;
		this.add(volumeChanger, gbc);
		gbc.fill = GridBagConstraints.NONE;

	}
	
	
	private void updatevolumeChanger() {
		volumeChanger.setValue(curVolume);	
		volumeChanger.setMaximum(100); // Max set to max length of song
		String currentTime = (curVolume/ 60 + ":" + (new DecimalFormat("00").format(curVolume % 60)));
		volumeStampCurrent.setText(currentTime); // current time
	}
	
	class volumeChangerListener implements MouseListener{
		
		@Override
		public void mouseClicked(MouseEvent e) {
			double selectionX = e.getPoint().x;
			
			double selectionPercentage = selectionX / volumeChangerSize;
						
			int volumeChangerMax = volumeChanger.getMaximum();
			double selectedTimeSeconds = (volumeChangerMax * selectionPercentage);
			volumeChanger.setValue((int)selectedTimeSeconds);
			
			try {
				// set the volume in songPlayer
				System.out.println("");
			} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
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
	
	
	
