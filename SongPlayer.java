import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;



/**
 * 
 * @author Kate Nixon
 *
 * SongPlayer class uses Java's swing to play
 * wav files. Wav files are played using a 
 * conjuction of currentFile to get currentClip
 * which represents the song itself
 * This class also keeps track of the current
 * frame, the statuss of the song (playing or paused)
 * and the volume
 */
public class SongPlayer {
	// current play data
	private File currentFile;
	private Song currentSong;
	private Clip currentClip;
    	private Long currentFrame;
    	private AudioInputStream currentAudioInputStream;
    	private boolean paused;
    	private FloatControl currentGainControl;
    	private Float currentGain;
	
	/**
	 * constructor
	 * starts off with no song
	 */
	public SongPlayer() {
		currentFile = null;
		currentSong = null;
		currentClip = null;
		currentFrame = 0L;
		currentAudioInputStream = null;
		paused = false;
		currentGain = null;
	}
	
	
	
	/**
	 * plays the given Song
	 * if the song is null, then return without playing anyting
	 * if song is the same as current song, then it restarts
	 * otherwise play the song passed in
	 */
	public void play(Song song) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		if (song == null) {
			return;
		}
		
		if (song == currentSong) {
			restartCurrentSong();
			return;
		}
		
		this.currentSong = song;
		
		closeCurrentSong();

		// building new song clip
		currentFile = new File(song.audioPath).getAbsoluteFile();
		AudioInputStream newInputStream = AudioSystem.getAudioInputStream(currentFile);
		Clip newClip = AudioSystem.getClip();
		newClip.open(newInputStream);
		
		// setting loop quantity
		newClip.loop(0);
		
		// closing old song and playing new song
		
		FloatControl newGainControl = (FloatControl) newClip.getControl(FloatControl.Type.MASTER_GAIN);
		if (currentGain == null) {
			currentGain = newGainControl.getMaximum();
		}
		newGainControl.setValue(currentGain);
		
		newClip.start();
		
		// updating global references
		
		this.currentAudioInputStream = newInputStream;
		this.currentClip = newClip;
		this.currentGainControl = newGainControl;
				
	}
	
	
	
	/**
	 * This pauses the currently playing song
	 * makes sure that the current song exists
	 * and isn't already paused.
	 */
	public void pause() {
				
		if (currentSong == null) {
			return;
		}
		if (paused) {
			return;
		}
		this.currentFrame = this.currentClip.getMicrosecondPosition();
		currentClip.stop();
		paused = true;
	}
	
	/**
	 * if the current song is paused, it resumes playing it
	 */
	public void resume() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		if (currentSong == null) {
			return;
		}
		if (!paused) {
			return;
		}
		currentClip.close();
        	resetAudioStream();
        	currentClip.setMicrosecondPosition(currentFrame);
        	this.currentClip.start();
		paused = false;
	}
	
	/**
	 * sets the current time of the song in microseconds
	 * @param Long time r4epresents the time that the song needs to be set to
	 */
	public void setTime(Long time) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		if (currentSong == null) {
			return;
		}
		if (time > 0 && time < currentClip.getMicrosecondLength()) {
			closeCurrentSong();
            	resetAudioStream();
            	currentClip.stop();
            	currentFrame = time;
            	currentClip.setMicrosecondPosition(time);
            	currentClip.start();
        	}
	}
	
	/**
	 * restarts the current song, if it is not null
	 */
	public void restartCurrentSong() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		if (currentSong == null) {
			return;
		}	
		closeCurrentSong();
		currentFrame = 0L;
		resetAudioStream();
		currentClip.setMicrosecondPosition(0);
		currentClip.start();
	}
	
	/**
	 * stops the current song if it is not null and closes it
	 */
	private void closeCurrentSong() {
		if (currentSong == null) {
			return;
		}
		if (currentClip == null) {
			return;
		}
		paused = false;
		currentFrame = 0L;
        	currentClip.stop();
        	currentClip.close();
	}
	
    /**
     *  Method to reset audio stream
     */
    private void resetAudioStream() throws UnsupportedAudioFileException, IOException,  LineUnavailableException {
        currentAudioInputStream = AudioSystem.getAudioInputStream(new File(currentSong.audioPath).getAbsoluteFile());
        currentClip.open(currentAudioInputStream);
        currentClip.loop(0);
        currentGainControl = (FloatControl) currentClip.getControl(FloatControl.Type.MASTER_GAIN);
        currentGainControl.setValue(currentGain);
    }
    
    /**
     * return gets the current time of the current song in microseconds 
     */
    public long getCurrentTimeMicroseconds() {
    	if (currentSong == null) {
    		return 0;
    	}
    	return currentClip.getMicrosecondPosition();
    }
    
    /**
     * gets the length of the current song in microseconds
     */
    public long getCurrentSongLength() {
    	if (currentSong == null) {
    		return 0;
    	}
    	return currentClip.getMicrosecondLength();
    }
    
    /**
     * returns the float amplitude in range 0,1
     */
    public float getCurrentAmplitude() {
    	return currentClip.getLevel();
    }
    
    /**
     * returns the current song
     */
    public Song getCurrentSong() {
    	return this.currentSong;
    }

    /**
     * increments the volume based on amount passed in
     */
    public void incrementGain(float increment) {
    	this.currentGain += increment;
    	this.currentGainControl.setValue(this.currentGain);
    }
    
    
    /**
     * sets the volume to the gain
     */
    public void setGain(float gain) {
    	this.currentGain = gain;
    	this.currentGainControl.setValue(this.currentGain);
    }
  
    /**
     * returns the max volume
     */
    public float getGainMax() {
    	return currentGainControl.getMaximum();
    }
    
    /**
     * returns sthe min volume
     */
    public float getGainMin() {
    	return currentGainControl.getMinimum();
    }
    
    /**
     * gets the audio input stream
     */
    public AudioInputStream getAudioInputStream(){
    	return currentAudioInputStream;
    }
  
    /**
    *returns the currentClip, the media of the song
    */
    public Clip getCurrentClip(){
    	return currentClip;
    }
}
