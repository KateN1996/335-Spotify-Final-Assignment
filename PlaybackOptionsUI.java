
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DecimalFormat;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;

import javafx.scene.media.MediaPlayer;

public class PlaybackOptionsUI extends Container{
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
	
	private int curSongTime;
	private int curSongLength;
	private Song curSong;

	/**
	 * Creates the playback options menu on the bottom of the screen with the
	 * playback timeBar and all playback buttons. Each button will later be wired to
	 * methods which complete their respective functions, and the bar will later
	 * update to match the song's timing.
	 * 
	 * @return playbackContainer
	 */
	PlaybackOptionsUI() {
		
	}
	
	public void setSong(Song song) {
		curSong = song;
	}
	
	
	public void setUp() {
		gui = MusicPlayer.gui;
		GridBagLayout rowLayout = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		this.setLayout(rowLayout);

		gbc.insets = BrazilBeatsUI.INSET_GAP;
		gbc.anchor = GridBagConstraints.SOUTH;

		timeStampCurrent = new JLabel("0:00");	
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

		timeStampEnd = new JLabel("0:00");
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
	
	private void updatePlaybackBar(MediaPlayer m) {
		//TODO: Get current song info and update
		
		//int songLength = currentSong.length;
		int songLength = 192;
		//int curTime = currentSong.currentTime;
		int curTime = curSongTime;
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
			float selectionX = e.getPoint().x;
			float selectionPercentage = selectionX / (float)playbackBarSize;
			
			int playbackBarMax = playbackBar.getMaximum();
			//float selectedTimeSeconds = (float) (Math.round((playbackBarMax * selectionPercentage) * 100.0) / 100.0);
			float selectedTimeSeconds = (playbackBarMax * selectionPercentage);
			curSongTime = (int) selectedTimeSeconds;
			
			playbackBar.setValue((int)selectedTimeSeconds);
			updatePlaybackBar(curSong.getPlayer());

			
			// TODO: Play selected time
			
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
				// TODO: Call backend Rewind, to restart/play previous
				System.out.println("Restarting /playing previous");
				updatePlayPause(false);
				break;
			case "Play":
				// TODO: Call backend Play
				System.out.println("Playing song");
				updatePlayPause(false);
				break;
			case "Pause":
				// TODO: Call backend Pause
				System.out.println("Pausing song");
				updatePlayPause(true);
				break;
			case "Next":
				// TODO: Call backend Skip, to play next song
				System.out.println("Skipping song");
				updatePlayPause(false);
				break;
			case "Save to Library":
				// TODO: Call backend Save song
				System.out.println("Saved Song to library");
				break;
			}
		}
	}
}
