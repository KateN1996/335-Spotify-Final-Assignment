import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;


public class SongPlayer {

	private Song currentSong;
	private MediaPlayer currentPlayer;
    private boolean paused;

	/*
	 * constructor
	 */
	public SongPlayer() {
		currentSong = null;
		currentPlayer = null;
		paused = false;
	}
	
	
	/*
	 * plays the given Song
	 */
	public void play(Song song){
		if (song == null) {
			return;
		}
		
		if (song == currentSong) {
			restartCurrentSong();
			return;
		}
		Media m = new Media(Paths.get(song.getPath()).toUri().toString());
		currentPlayer = new MediaPlayer(m);
		currentSong = song;
	}
	
	
	
	/*
	 * pauses the currently playing song
	 */
	public void pause() {
				
		if (currentSong == null) {
			return;
		}
		if (paused) {
			return;
		}
		currentPlayer.pause();		
		paused = true;
	}
	
	
	/*
	 * resumes the currently paused song
	 */
	public void resume() {
		if (currentSong == null) {
			return;
		}
		if (!paused) {
			return;
		}
		currentPlayer.pause();
		paused = false;
	}
	
	/*
	 * sets the current time of the song in microseconds
	 */
	public void setTime(Long time) {
		if (currentSong == null) {
			return;
		}
		if(time > 0 && time < currentPlayer.getTotalDuration().toSeconds()) {
			double percent = (double) time / currentPlayer.getTotalDuration().toSeconds();
			currentPlayer.seek(currentPlayer.getTotalDuration().multiply(percent));
		}
	}
	
	/*
	 * 
	 */
	public void restartCurrentSong() {
		if (currentSong == null) {
			return;
		}	
		currentPlayer.seek(currentPlayer.getTotalDuration().multiply(0));
	}
	
	/*
	 * idk what this is
	 */
	private void closeCurrentSong() {
		if (currentSong == null) {
			return;
		}
	}
	
    /*
     *  Method to reset audio stream
     */
    private void resetAudioStream() {
        
    }
    
    
    /*
     * gets the current time of the current song in microseconds 
     */
    public double getCurrentTimeMicroseconds() {
    	if (currentSong == null) {
    		return 0;
    	}
    	return currentPlayer.getCurrentTime().toSeconds();
    }
    
    
    /*
     * gets the length of the current song in microseconds
     */
    public double getCurrentSongLength() {
    	if (currentSong == null) {
    		return 0;
    	}
    	return currentPlayer.getCycleDuration().toSeconds();
    }
    
    /*
     * returns the float amplitude in range 0,1
     */
    public double getCurrentAmplitude() {
    	return currentPlayer.getVolume();
//    	return currentClip.getLevel();
    }
    
    /*
     * 
     */
    public Song getCurrentSong() {
    	return this.currentSong;
    }
	
    /*
     * volume
     */
    public void incrementGain(double increment) {
    	currentPlayer.setVolume(currentPlayer.getVolume() + increment);
    }
    
       
    /*
     * 
     */
    public void setGain(double gain) {
    	currentPlayer.setVolume(gain);
    	//this.currentGainControl.setValue(gain);
    }
    
    
    
    /*
     * 
     */
    /*
    public int getSampleInt(int sampleNumber) {
        if (sampleNumber < 0 || sampleNumber >= data.length / sampleSize) {
            throw new IllegalArgumentException(
                    "sample number can't be < 0 or >= data.length/"
                            + sampleSize);
        }
        byte[] sampleBytes = new byte[4]; //4byte = int
        for (int i = 0; i < sampleSize; i++) {
            sampleBytes[i] = data[sampleNumber * sampleSize * channelsNum + i];
        }
        int sample = ByteBuffer.wrap(sampleBytes)
                .order(ByteOrder.LITTLE_ENDIAN).getInt();
        return sample;
    }
    */
    
    public AudioInputStream getAudioInputStream(){
    	
    	return null;
    	//return currentAudioInputStream;
    }
    public MediaPlayer getPlayer(){
    	return currentPlayer;
    	//return currentClip;
    }
}
