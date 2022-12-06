import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;


/*
 * 
 */
public class Main {  
	public static BrazilBeatsUI gui;
	public static SongPlayer songPlayer;
	public static PlaylistManager playlistManager;
	public static SearchInterface searchInterface;
	public static SongQueue songQueue;
    public static void main(String[] args) {  
    	
    	searchInterface = new SearchInterface();
    	songPlayer = new SongPlayer();
    	playlistManager = new PlaylistManager();
    	    	
    	// AUTOPLAY FOR TESTING
    	try {
    		Song newSong = new Song("balogna");
    		System.out.println(newSong.audioPath);
			songPlayer.play(newSong);
			songPlayer.pause();
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 
    	
    	
    	songQueue = new SongQueue(false);
    	gui = new BrazilBeatsUI();
    	gui.initializeComponents();
    	
    }
}


