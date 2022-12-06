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
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;

/**
 * This is the UI container containing all the playback controls for the current
 * song. It has a playback bar with working time stamps and a progress bar that
 * updates in real-time with its own thread. There are buttons used for playback
 * controls including shuffle, restart/play previous, play and pause, play next
 * song, and save song to library. The playback bar also allows the user to
 * select the exact time in the song by clicking anywhere on the progress bar.
 * There is a volume slider at the bottom as well.
 * 
 * @author Kyle Walker
 *
 */
public class PlaybackOptionsUI extends Container implements Runnable {
	private BrazilBeatsUI gui;
	private static final long serialVersionUID = 1L;

	// Graphics components
	// Playback bar and timestamps
	private JProgressBar playbackBar;
	private int playbackBarSize = 400;
	private JLabel timeStampCurrent;
	private JLabel timeStampEnd;

	// playback options buttons
	private JButton playPauseButton;
	private boolean isPaused = false;
	private JButton shuffleButton;
	private JButton restartButton;
	private JButton skipButton;
	private JButton saveButton;

	// Ref to songplayer
	private SongPlayer songPlayer;

	/**
	 * Creates the playback options menu on the bottom of the screen with the
	 * playback timeBar and all playback buttons. Each button is wired to methods
	 * which complete their respective functions, and the bar updates to match the
	 * song's timing.
	 * 
	 */
	PlaybackOptionsUI() {
		gui = Main.gui;
		songPlayer = Main.songPlayer;

		// Gridbag layout of components
		this.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = BrazilBeatsUI.INSET_GAP;
		gbc.anchor = GridBagConstraints.SOUTH;

		// Current song time stamp label
		timeStampCurrent = new JLabel("0:00");
		timeStampCurrent.setFont(BrazilBeatsUI.captionFont);
		timeStampCurrent.setForeground(BrazilBeatsUI.detailColor);
		gbc.gridx = 0;
		gbc.gridy = 0;
		this.add(timeStampCurrent, gbc);

		// Playback bar (progress bar)
		playbackBar = new JProgressBar(SwingConstants.HORIZONTAL);
		playbackBar.setPreferredSize(new Dimension(playbackBarSize, 12));
		playbackBar.setMaximum(172); // Max set to max length of song
		playbackBar.setValue(48);
		playbackBar.setBackground(BrazilBeatsUI.borderColor);
		playbackBar.setForeground(BrazilBeatsUI.detailColor);
		playbackBar.addMouseListener(new playbackBarListener());
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 1;
		gbc.gridy = 0;
		this.add(playbackBar, gbc);
		gbc.fill = GridBagConstraints.NONE;

		// default timestamp for song duration
		timeStampEnd = new JLabel("0:00");
		timeStampEnd.setFont(BrazilBeatsUI.captionFont);
		timeStampEnd.setForeground(BrazilBeatsUI.detailColor);
		gbc.gridx = 2;
		gbc.gridy = 0;
		this.add(timeStampEnd, gbc);

		// Container for playback options buttons
		Container buttonContainer = new Container();
		Dimension buttonSpacing = new Dimension(10, 0);
		buttonContainer.setLayout(new BoxLayout(buttonContainer, BoxLayout.X_AXIS));

		// Shuffle button
		shuffleButton = new JButton("$");
		shuffleButton.setBackground(BrazilBeatsUI.accentColor);
		shuffleButton.setForeground(BrazilBeatsUI.detailColor);
		shuffleButton.setFont(BrazilBeatsUI.mainFont);
		shuffleButton.setToolTipText("Shuffle");
		shuffleButton.addActionListener(new playbackButtonListener());
		buttonContainer.add(shuffleButton);
		buttonContainer.add(Box.createRigidArea(buttonSpacing));

		// Restart song button
		restartButton = new JButton("|<");
		restartButton.setBackground(BrazilBeatsUI.accentColor);
		restartButton.setForeground(BrazilBeatsUI.detailColor);
		restartButton.setFont(BrazilBeatsUI.mainFont);
		restartButton.setToolTipText("Previous");
		restartButton.addActionListener(new playbackButtonListener());
		buttonContainer.add(restartButton);
		buttonContainer.add(Box.createRigidArea(buttonSpacing));

		// Play / pause button toggle
		playPauseButton = new JButton("");
		playPauseButton.setBackground(BrazilBeatsUI.accentColor);
		playPauseButton.setForeground(BrazilBeatsUI.detailColor);
		playPauseButton.setFont(BrazilBeatsUI.mainFont);
		updatePlayPause(true);
		playPauseButton.addActionListener(new playbackButtonListener());
		buttonContainer.add(playPauseButton);
		buttonContainer.add(Box.createRigidArea(buttonSpacing));

		// Skip song button
		skipButton = new JButton(">|");
		skipButton.setBackground(BrazilBeatsUI.accentColor);
		skipButton.setForeground(BrazilBeatsUI.detailColor);
		skipButton.setFont(BrazilBeatsUI.mainFont);
		skipButton.setToolTipText("Next");
		skipButton.addActionListener(new playbackButtonListener());
		buttonContainer.add(skipButton);
		buttonContainer.add(Box.createRigidArea(buttonSpacing));

		// Save to library button
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

		// Add volume slider below buttons
		gbc.gridx = 1;
		gbc.gridy = 2;
		this.add(new VolumeSlider(), gbc);
	}

	/**
	 * Updates the state of the play/pause button. Uses tooltip name change to
	 * change functionality
	 * 
	 * @param toggle isPaused: is the button paused(true) or playing(false)
	 */
	private void updatePlayPause(boolean toggle) {
		isPaused = toggle;
		if (isPaused) {
			playPauseButton.setText("|>");
			playPauseButton.setToolTipText("Play");
		} else {
			playPauseButton.setText("||");
			playPauseButton.setToolTipText("Pause");
		}
		gui.validateFrame();
	}

	/**
	 * Updates the playback bar to reflect the state of the song. Shows the song's
	 * current time, length, and current position within the progress bar.
	 */
	private void updatePlaybackBar() {
		// Convert song times to seconds
		int songLength = (int) (songPlayer.getCurrentSongLength() / 1000000f);
		int curTime = (int) (songPlayer.getCurrentTimeMicroseconds() / 1000000f);
		playbackBar.setValue(curTime);
		playbackBar.setMaximum(songLength); // Max set to max length of song
		// Change timestamp text to timestamp format (min:secs)
		String currentTime = (curTime / 60 + ":" + (new DecimalFormat("00").format(curTime % 60)));
		String endTime = (songLength / 60 + ":" + (new DecimalFormat("00").format((songLength % 60))));
		timeStampCurrent.setText(currentTime);
		timeStampEnd.setText(endTime);
	}

	/**
	 * Mouse listener attached to playbackBar which allows users to select song time
	 * by clicking along the bar. Finds the percentage of the bar and applies that
	 * to the time of the song at that percentage.
	 * 
	 * @author Kyle Walker
	 *
	 */
	class playbackBarListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			double selectionX = e.getPoint().x;

			double selectionPercentage = selectionX / playbackBarSize;

			double selectedTimeMicroseconds = selectionPercentage * songPlayer.getCurrentSongLength();

			int playbackBarMax = playbackBar.getMaximum();
			double selectedTimeSeconds = (playbackBarMax * selectionPercentage);
			playbackBar.setValue((int) selectedTimeSeconds);

			try {
				songPlayer.setTime((long) selectedTimeMicroseconds);
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

		// Changes the color of the bar when the mouse hovers over it to indicate
		// interactivity.
		@Override
		public void mouseEntered(MouseEvent e) {
			playbackBar.setForeground(BrazilBeatsUI.accentColor);

		}

		@Override
		public void mouseExited(MouseEvent e) {
			playbackBar.setForeground(BrazilBeatsUI.detailColor);

		}

	}

	/**
	 * Listens to interactions with the playback buttons and calls methods on the
	 * song player depending on their current function (stores in tooltip);
	 * 
	 * @author Kyle Walker
	 *
	 */
	class playbackButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton pressedButton = (JButton) (e.getSource());

			// Context of button is stored in tooltip name (allows play and pause toggle by
			// just switching names)
			String context = pressedButton.getToolTipText();

			switch (context) {

			// Shuffle the current queue
			case "Shuffle":
				// TODO: Call backend Shuffle
				System.out.println("Shuffling");
				break;

			// Restarts the current song
			case "Previous":
				try {
					songPlayer.restartCurrentSong();
				} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e2) {
					e2.printStackTrace();
				}
				System.out.println("Restarting /playing previous");
				// Forces the pause button to enter playing mode
				updatePlayPause(false);
				break;

			// Play / resume current song
			case "Play":
				try {
					songPlayer.resume();
				} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
					e1.printStackTrace();
				}
				System.out.println("Playing song");
				// Forces paused button to play mode
				updatePlayPause(false);
				break;

			// Pauses current song
			case "Pause":
				songPlayer.pause();
				System.out.println("Pausing song");
				// Forces play button to paused state
				updatePlayPause(true);
				break;

			// Plays next song in queue
			case "Next":
				System.out.println("Skipping song");
				Song next = Main.songQueue.dequeue();
				try {
					songPlayer.play(next);
				} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				gui.validateFrame();
				// TODO: fast forward to next
				updatePlayPause(false);
				break;
				
			// Saves current song to library playlist
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
			// Updates playback bar in realtime on its own thread to always reflect changes in song player
			updatePlaybackBar();
			if (songPlayer.getCurrentClip().isRunning() && isPaused) {
				updatePlayPause(false);
			}
			else if (!songPlayer.getCurrentClip().isRunning() && !isPaused) {
				updatePlayPause(true);
			}
		}

	}
}
