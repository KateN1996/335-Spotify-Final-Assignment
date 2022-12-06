
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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
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
import javax.swing.ImageIcon;
import javax.swing.JLabel;


/**
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

	private SongPlayer songPlayer;
	private AudioInputStream audioInput;
	private AudioFormat audioFormat;
	private int sampleSize;
	private long frameCount;
	private int sampleRate;
	private int channels;
	private byte[] buffer;
	
	
	private Clip currentClip;


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

		
		// Initialize audio compnents
		songPlayer = Main.songPlayer;

		updateAudioData();
		
		
		

	}
	
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
		try {
			audioInput.read(buffer);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    /**
     * Returns sample (amplitude value). Note that in case of stereo samples
     * go one after another. I.e. 0 - first sample of left channel, 1 - first
     * sample of the right channel, 2 - second sample of the left channel, 3 -
     * second sample of the rigth channel, etc.
     */
    public int getSampleInt(int sampleNumber) {

        if (sampleNumber < 0 || sampleNumber >= buffer.length / sampleSize) {
            throw new IllegalArgumentException(
                    "sample number can't be < 0 or >= data.length/"
                            + sampleSize);
        }

        byte[] sampleBytes = new byte[32]; //4byte = int

        for (int i = 0; i < sampleSize; i++) {
            sampleBytes[i] = buffer[sampleNumber * sampleSize * channels + i];
        }

        int sample = ByteBuffer.wrap(sampleBytes)
                .order(ByteOrder.LITTLE_ENDIAN).getInt();
        return sample;
    }

	/**
	 * Set Image/GIF
	 */
	private void updateBeatsView() {
		File danceImageFile = new File("brazilDance.jpg");
		Image danceImage;
		try {
			danceImage = ImageIO.read(danceImageFile);
			danceImage = danceImage.getScaledInstance(BrazilBeatsUI.IMG_RES_MAX, BrazilBeatsUI.IMG_RES_MAX,
					Image.SCALE_DEFAULT);
			ImageIcon albumIcon = new ImageIcon(danceImage);

			beatsDanceView.setIcon(albumIcon);

		} catch (IOException e) {
			e.printStackTrace();
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
			TargetDataLine line;
			int byteBufferSize = 2048;
			try {
	            line = AudioSystem.getTargetDataLine(audioFormat);
	            line.open(audioFormat, byteBufferSize);
	        } catch(LineUnavailableException e) {
	            System.err.println(e);
	            return;
	        }
			
			byte[] buf = new byte[byteBufferSize];
			float[] samples = new float[byteBufferSize /2];
			float lastPeak = 0f;


	        for(int b; (b = line.read(buf, 0, buf.length)) > -1;) {

	            // convert bytes to samples here
	            for(int i = 0, s = 0; i < b;) {
	                int sample = 0;

	                sample |= buf[i++] & 0xFF; // (reverse these two lines
	                sample |= buf[i++] << 8;   //  if the format is big endian)

	                // normalize to range of +/-1.0f
	                samples[s++] = sample / 32768f;
	            }

	            float rms = 0f;
	            float peak = 0f;
	            for(float sample : samples) {

	                float abs = Math.abs(sample);
	                if(abs > peak) {
	                    peak = abs;
	                }

	                rms += sample * sample;
	            }

	            rms = (float)Math.sqrt(rms / samples.length);

	            if(lastPeak > peak) {
	                peak = lastPeak * 0.875f;
	            }

	            System.out.println("lastPeak: " + lastPeak + " peak: " + peak + " rms: " + rms);
	            
	            lastPeak = peak;

		}
		
        }
    }
			
		// while (true) {

		/*
		 * float currAmp = buffer[0]; //float currAmp =
		 * Main.songPlayer.getCurrentAmplitude();
		 * System.out.println("Checking Amps: Last -> " + lastAmp + " Curr ->" +
		 * currAmp); if (currAmp - threshold <= lastAmp || currAmp + threshold >=
		 * lastAmp) { // Advance frame System.out.println("ADVANCE FRAME!!!!"); }
		 * lastAmp = currAmp;
		 */
	
}
