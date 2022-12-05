import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
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
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;

public class PlaybackOptionsUI extends Container implements Runnable{
	private BrazilBeatsUI gui;
	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	
	private boolean isPaused = false;
	
	private JProgressBar playbackBar;
	private JLabel timeStampCurrent;
	private JLabel timeStampEnd;
	
	private JButton playPauseButton;
	private JButton shuffleButton;
	private JButton restartButton;
	private JButton skipButton;
	private JButton saveButton;
	
	private int playbackBarSize = 400;
	
	
	private SongPlayer songPlayer;

	/**
	 * Creates the playback options menu on the bottom of the screen with the
	 * playback timeBar and all playback buttons. Each button will later be wired to
	 * methods which complete their respective functions, and the bar will later
	 * update to match the song's timing.
	 * 
	 * @return playbackContainer
	 */
	PlaybackOptionsUI() {
		gui = Main.gui;
		songPlayer = Main.songPlayer;
		GridBagLayout rowLayout = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		this.setLayout(rowLayout);

		gbc.insets = BrazilBeatsUI.INSET_GAP;
		gbc.anchor = GridBagConstraints.SOUTH;

		timeStampCurrent = new JLabel("0:48");	
		timeStampCurrent.setFont(BrazilBeatsUI.captionFont);
		timeStampCurrent.setForeground(BrazilBeatsUI.detailColor);
		gbc.gridx = 0;
		gbc.gridy = 0;
		this.add(timeStampCurrent, gbc);

		playbackBar = new JProgressBar(SwingConstants.HORIZONTAL);
		playbackBar.setPreferredSize(new Dimension(playbackBarSize, 12));
		playbackBar.setMaximum(172);								// Max set to max length of song
		playbackBar.setValue(48);
		playbackBar.setBackground(BrazilBeatsUI.borderColor);
		playbackBar.setForeground(BrazilBeatsUI.detailColor);
		playbackBar.addMouseListener(new playbackBarListener());
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 1;
		gbc.gridy = 0;
		this.add(playbackBar, gbc);
		gbc.fill = GridBagConstraints.NONE;

		timeStampEnd = new JLabel("2:52");
		timeStampEnd.setFont(BrazilBeatsUI.captionFont);
		timeStampEnd.setForeground(BrazilBeatsUI.detailColor);
		gbc.gridx = 2;
		gbc.gridy = 0;
		this.add(timeStampEnd, gbc);

		Container buttonContainer = new Container();
		Dimension buttonSpacing = new Dimension(10, 0);
		buttonContainer.setLayout(new BoxLayout(buttonContainer, BoxLayout.X_AXIS));

		shuffleButton = new JButton("$");
		shuffleButton.setBackground(BrazilBeatsUI.accentColor);
		shuffleButton.setForeground(BrazilBeatsUI.detailColor);
		shuffleButton.setFont(BrazilBeatsUI.mainFont);
		shuffleButton.setToolTipText("Shuffle");
		shuffleButton.addActionListener(new playbackButtonListener());
		buttonContainer.add(shuffleButton);
		buttonContainer.add(Box.createRigidArea(buttonSpacing));

		restartButton = new JButton("|<");
		restartButton.setBackground(BrazilBeatsUI.accentColor);
		restartButton.setForeground(BrazilBeatsUI.detailColor);
		restartButton.setFont(BrazilBeatsUI.mainFont);
		restartButton.setToolTipText("Previous");
		restartButton.addActionListener(new playbackButtonListener());
		buttonContainer.add(restartButton);
		buttonContainer.add(Box.createRigidArea(buttonSpacing));

		playPauseButton = new JButton("");
		playPauseButton.setBackground(BrazilBeatsUI.accentColor);
		playPauseButton.setForeground(BrazilBeatsUI.detailColor);
		playPauseButton.setFont(BrazilBeatsUI.mainFont);
		updatePlayPause(true);
		playPauseButton.addActionListener(new playbackButtonListener());
		buttonContainer.add(playPauseButton);
		buttonContainer.add(Box.createRigidArea(buttonSpacing));

		skipButton = new JButton(">|");
		skipButton.setBackground(BrazilBeatsUI.accentColor);
		skipButton.setForeground(BrazilBeatsUI.detailColor);
		skipButton.setFont(BrazilBeatsUI.mainFont);
		skipButton.setToolTipText("Next");
		skipButton.addActionListener(new playbackButtonListener());
		buttonContainer.add(skipButton);
		buttonContainer.add(Box.createRigidArea(buttonSpacing));

		saveButton = new JButton(":)");
		saveButton.setBackground(BrazilBeatsUI.accentColor);
		saveButton.setForeground(BrazilBeatsUI.detailColor);
		saveButton.setFont(BrazilBeatsUI.mainFont);
		saveButton.setToolTipText("Save to Library");
		saveButton.addActionListener(new playbackButtonListener());
		buttonContainer.add(saveButton);

		gbc.gridx = 1;
		gbc.gridy = 1;
		this.add(buttonContainer, gbc);
	}
	private void updatePlayPause(boolean toggle) {
		isPaused = toggle;
		if (isPaused) {
			playPauseButton.setText("|>");
			playPauseButton.setToolTipText("Play");
		} else {
			playPauseButton.setText("||");
			playPauseButton.setToolTipText("Pause");
		}
	}
	
	private void updatePlaybackBar() {
		int songLength = (int)(songPlayer.getCurrentSongLength() / 1000000f);
		int curTime = (int) (songPlayer.getCurrentTimeMicroseconds() / 1000000f);
		playbackBar.setValue(curTime);	
		playbackBar.setMaximum(songLength); // Max set to max length of song
		String currentTime = (curTime/ 60 + ":" + (new DecimalFormat("00").format(curTime % 60)));
		String endTime = (songLength / 60 + ":" + (new DecimalFormat("00").format((songLength % 60))));
		timeStampCurrent.setText(currentTime); // current time
		timeStampEnd.setText(endTime); // current time
	}
	
	class playbackBarListener implements MouseListener{
		
		@Override
		public void mouseClicked(MouseEvent e) {
			double selectionX = e.getPoint().x;
			
			double selectionPercentage = selectionX / playbackBarSize;
			
			double selectedTimeMicroseconds = selectionPercentage * songPlayer.getCurrentSongLength();
			
			int playbackBarMax = playbackBar.getMaximum();
			double selectedTimeSeconds = (playbackBarMax * selectionPercentage);
			playbackBar.setValue((int)selectedTimeSeconds);
			
			try {
				songPlayer.setTime((long)selectedTimeMicroseconds);
				updatePlayPause(false);
			} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			updatePlaybackBar();
			
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
			playbackBar.setForeground(BrazilBeatsUI.accentColor);
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			playbackBar.setForeground(BrazilBeatsUI.detailColor);
			
		}
		
	}
	
	class playbackButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton pressedButton = (JButton) (e.getSource());
			String context = pressedButton.getToolTipText();
			switch (context) {
			case "Shuffle":
				// TODO: Call backend Shuffle
				System.out.println("Shuffling");
				break;
			case "Previous":
				try {
					songPlayer.restartCurrentSong();
				} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e2) {
					e2.printStackTrace();
				}
		
				System.out.println("Restarting /playing previous");
				updatePlayPause(false);
				break;
		
			case "Play":
				try {
					songPlayer.resume();
				} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
					e1.printStackTrace();
				}
				System.out.println("Playing song");
				updatePlayPause(false);
				break;
			
			case "Pause":
				songPlayer.pause();
				System.out.println("Pausing song");
				updatePlayPause(true);
				break;
			
			case "Next":
				System.out.println("Skipping song");
				// TODO: fast forward to next
				updatePlayPause(false);
				break;
			case "Save to Library":
				// TODO: Call backend Save song
				System.out.println("Saved Song to library");
				break;
			}
		}
	}

	@Override
	public void run() {
		while (true) {
			updatePlaybackBar();
		}
		
	}
}
	
	
	