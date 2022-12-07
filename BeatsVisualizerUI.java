
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * BeatsVisualizer, provides an animated gif that plays together
 * with the song for an added WOW factor. It is a runnable so that
 * it can run separately from other actions (volume changing etc) and so
 * that we can match it with the song player.
 * 
 * @author Kyle Walker
 *
 */
public class BeatsVisualizerUI extends Container implements Runnable {
	private BrazilBeatsUI gui;
	private static final long serialVersionUID = 1L;
	private JLabel beatsDanceView;
	private float lastAmp;
	private float threshold = 0.4f;
	
	// song info from the audio sampler
	private SongPlayer songPlayer;
	private AudioInputStream audioInput;
	private AudioFormat audioFormat;
	private int sampleSize;
	private long frameCount;
	private int sampleRate;
	private int channels;
	private byte[] buffer;

	private Clip currentClip;
	
	/**
	 * Constructor for the container that stores the beat 
	 * visualizer. Sets up the gif and is a runnable, so it
	 * can continuously work on its thread and keep moving/stop depending
	 * on song status.
	 */
	BeatsVisualizerUI() {
		songPlayer = Main.songPlayer;
		
		lastAmp = 0.5f;
		gui = Main.gui;
		GridBagLayout colLayout = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		this.setLayout(colLayout);
		gbc.insets = BrazilBeatsUI.INSET_GAP;

		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.anchor = GridBagConstraints.CENTER;
		
		// Set up Visualizer text label
		JLabel brazilBeatsLabel = new JLabel("Beats Visualizer");
		brazilBeatsLabel.setFont(BrazilBeatsUI.headerFont);
		brazilBeatsLabel.setForeground(BrazilBeatsUI.detailColor);
		gbc.gridy = 0;
		this.add(brazilBeatsLabel, gbc);
		
		// add the dance viewers
		beatsDanceView = new JLabel("");
		gbc.gridy = 1;
		updateBeatsView();
		this.add(beatsDanceView, gbc);

		// Initialize audio components
		songPlayer = Main.songPlayer;

		updateAudioData();

	}
	
	/**
	 * Updates the audio data based on the currently playing
	 * song. Provides access to information about the song 
	 * such as frame count, sample rate and channels.
	 */
	private void updateAudioData() {
		currentClip = songPlayer.getCurrentClip();
		audioInput = songPlayer.getAudioInputStream();
		audioFormat = audioInput.getFormat();
		frameCount = audioInput.getFrameLength();
		sampleRate = (int) audioFormat.getSampleRate();
		sampleSize = audioFormat.getSampleSizeInBits();
		channels = audioFormat.getChannels();
		long dataLength = frameCount * sampleSize * channels;
		buffer = new byte[(int) dataLength];
		// reads the buffer of the audio
		try {
			audioInput.read(buffer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Set Image/GIF
	 */
	public void updateBeatsView() {
		// if the song is currently playing, we set the gif
		if(songPlayer.getCurrentClip().isRunning()) {
			URL url;
			url = getClass().getResource("brazilBeatsDancer.gif");
			Icon icon = new ImageIcon(url);

			beatsDanceView.setIcon(icon);
		}
		// otherwise we idle the gif, since no song is playing
		else {
			URL url;
			url = getClass().getResource("brazilBeatsDancerIdle.gif");
			Icon icon = new ImageIcon(url);

			beatsDanceView.setIcon(icon);
		}

	}

	@Override

	/**
	 * Runnable, always checks the current amplitude of the song, if it falls in
	 * between the range of threshold, it should advance the gif to the next frame.
	 */
	public void run() {
		// TODO Auto-generated method stub

		while (true) {
		}
	}

}
