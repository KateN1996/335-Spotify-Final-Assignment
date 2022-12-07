import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;


/**
 * Spotify main class
 * 
 * @author Ryan Pecha
 */
public class Main {  
	
	// declaring static global classes
	public static BrazilBeatsUI gui;
	public static SongPlayer songPlayer;
	public static PlaylistManager playlistManager;
	public static SearchInterface searchInterface;
	public static SongQueue songQueue;
	
	// main
    public static void main(String[] args) {  
    	
    	// initializing static global classes
    	searchInterface = new SearchInterface();
    	songPlayer = new SongPlayer();
    	playlistManager = new PlaylistManager();
    	    	
    	// initial song load for UI reference
    	try {
    		Song newSong = new Song("balogna");
			songPlayer.play(newSong);
			songPlayer.pause();
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			e.printStackTrace();
		}
 
    	// trailing queue initialization
    	songQueue = new SongQueue();
    	
    	// ui initialization
    	gui = new BrazilBeatsUI();
    	gui.initializeComponents();
    	
    }
}
