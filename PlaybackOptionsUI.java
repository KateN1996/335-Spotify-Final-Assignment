import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;

public class PlaybackOptionsUI extends Container{
	private BrazilBeatsUI gui;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton playPauseButton;
	private boolean isPaused = false;
	
	private JProgressBar playbackBar;
	private JLabel timeStampCurrent;
	private JLabel timeStampEnd;

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
		GridBagLayout rowLayout = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		this.setLayout(rowLayout);

		gbc.insets = BrazilBeatsUI.INSET_GAP;
		gbc.anchor = GridBagConstraints.SOUTH;

		JLabel timeStampCur = new JLabel("0:48");	
		timeStampCur.setFont(BrazilBeatsUI.captionFont);
		timeStampCur.setForeground(BrazilBeatsUI.detailColor);
		gbc.gridx = 0;
		gbc.gridy = 0;
		this.add(timeStampCur, gbc);

		JProgressBar playbackBar = new JProgressBar(SwingConstants.HORIZONTAL);
		playbackBar.setPreferredSize(new Dimension(400, 12));
		playbackBar.setMaximum(172);								// Max set to max length of song
		playbackBar.setValue(48);
		playbackBar.setBackground(BrazilBeatsUI.borderColor);
		playbackBar.setForeground(BrazilBeatsUI.detailColor);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 1;
		gbc.gridy = 0;
		this.add(playbackBar, gbc);
		gbc.fill = GridBagConstraints.NONE;

		JLabel timeStampEnd = new JLabel("2:52");
		timeStampEnd.setFont(BrazilBeatsUI.captionFont);
		timeStampEnd.setForeground(BrazilBeatsUI.detailColor);
		gbc.gridx = 2;
		gbc.gridy = 0;
		this.add(timeStampEnd, gbc);

		Container buttonContainer = new Container();
		Dimension buttonSpacing = new Dimension(10, 0);
		buttonContainer.setLayout(new BoxLayout(buttonContainer, BoxLayout.X_AXIS));

		JButton shuffleButton = new JButton("$");
		shuffleButton.setBackground(BrazilBeatsUI.accentColor);
		shuffleButton.setForeground(BrazilBeatsUI.detailColor);
		shuffleButton.setFont(BrazilBeatsUI.mainFont);
		shuffleButton.setToolTipText("Shuffle");
		shuffleButton.addActionListener(new playbackButtonListener());
		buttonContainer.add(shuffleButton);
		buttonContainer.add(Box.createRigidArea(buttonSpacing));

		JButton restartButton = new JButton("|<");
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

		JButton skipButton = new JButton(">|");
		skipButton.setBackground(BrazilBeatsUI.accentColor);
		skipButton.setForeground(BrazilBeatsUI.detailColor);
		skipButton.setFont(BrazilBeatsUI.mainFont);
		skipButton.setToolTipText("Next");
		skipButton.addActionListener(new playbackButtonListener());
		buttonContainer.add(skipButton);
		buttonContainer.add(Box.createRigidArea(buttonSpacing));

		JButton saveButton = new JButton(":)");
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
		//TODO: Get current song info and update
		
		//int songLength = currentSong.length;
		int songLength = 192;
		//int curTime = currentSong.currentTime;
		int curTime = 47;
		playbackBar.setValue(curTime);	
		playbackBar.setMaximum(songLength); // Max set to max length of song
		String currentTime = (curTime/ 60 + ":" + curTime % 60);
		String endTime = (songLength / 60 + ":" + songLength % 60);
		timeStampCurrent.setText(currentTime); // current time
		timeStampEnd.setText(endTime); // current time
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
	
	
	
