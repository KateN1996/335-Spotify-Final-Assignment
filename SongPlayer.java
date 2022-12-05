import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
  
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;



public class SongPlayer {

	
	
	// current play data
	private Song currentSong;
    private Clip currentClip;
    private Long currentFrame;
    private AudioInputStream currentAudioInputStream;
    private boolean paused;
	private ArrayList<Song> songQueue;

    
	
	/*
	 * cosntructor
	 */
	public SongPlayer() {
		currentSong = null;
		currentClip = null;
		currentFrame = 0L;
		currentAudioInputStream = null;
		paused = false;		
		songQueue = new ArrayList<Song>();
	}
	
	
	
	/*
	 * plays the given Song
	 */
	public void play(Song song) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		if (song == null) {
			return;
		}
		
		if (song == currentSong) {
			restartCurrentSong();
			return;
		}
		
		// building new song clip
		AudioInputStream newInputStream = AudioSystem.getAudioInputStream(new File(song.audioPath).getAbsoluteFile());
		Clip newClip = AudioSystem.getClip();
		newClip.open(newInputStream);
		
		// setting loop quantity
		newClip.loop(1);
		//newClip.loop(Clip.LOOP_CONTINUOUSLY);
		
		// closing old song and playing new song
		closeCurrentSong();
		newClip.start();
		
		// updating global references
		this.currentSong = song;
		this.currentAudioInputStream = newInputStream;
		this.currentClip = newClip;
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
		this.currentFrame = this.currentClip.getMicrosecondPosition();
		currentClip.stop();
		paused = true;
	}
	
	
	
	/*
	 * resumes the currently paused song
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
	
	
	
	/*
	 * sets the current time of the song in microseconds
	 */
	public void setTime(Long time) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		if (currentSong == null) {
			return;
		}
		if (time > 0 && time < currentClip.getMicrosecondLength()) 
        {
			closeCurrentSong();
            resetAudioStream();
            currentFrame = time;
            currentClip.setMicrosecondPosition(time);
            currentClip.start();
        }
	}
	
	
	
	/*
	 * 
	 */
	public void restartCurrentSong() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		if (currentSong == null) {
			return;
		}	
		closeCurrentSong();
		resetAudioStream();
		currentClip.setMicrosecondPosition(0);
		currentClip.start();
	}
	
	
	
	/*
	 * 
	 */
	private void closeCurrentSong() {
		if (currentSong == null) {
			return;
		}
		paused = false;
		currentFrame = 0L;
        currentClip.stop();
        currentClip.close();
	}
	
	
	
    /*
     *  Method to reset audio stream
     */
    private void resetAudioStream() throws UnsupportedAudioFileException, IOException,  LineUnavailableException {
        currentAudioInputStream = AudioSystem.getAudioInputStream(new File(currentSong.audioPath).getAbsoluteFile());
        currentClip.open(currentAudioInputStream);
        currentClip.loop(1);
        currentFrame = 0L;
    }
    
    
    
    /*
     * gets the current time of the current song in microseconds 
     */
    public long getCurrentTimeMicroseconds() {
    	if (currentSong == null) {
    		return 0;
    	}
    	return currentClip.getMicrosecondPosition();
    }
    
    
    
    /*
     * gets the length of the current song in microseconds
     */
    public long getCurrentSongLength() {
    	if (currentSong == null) {
    		return 0;
    	}
    	return currentClip.getMicrosecondLength();
    }
	
}


