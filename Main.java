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
    public static void main(String[] args) {  
    	
    	new SearchInterface();
    	songPlayer = new SongPlayer();
    	
    	
    	
    	// AUTOPLAY FOR TESTING
    	try {
			songPlayer.play(new Song("test"));
			songPlayer.pause();
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    	
    	playlistManager = new PlaylistManager();
    	
    	/*
    	SongPlayer sp = new SongPlayer();
    	Song song = SearchInterface.search("MF").get(0);
    	
    	
    	
    	try {	
    		sp.play(song);
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
    	
    	
    	
    	playlistManager.createPlaylist("testPlaylist");
    	playlistManager.getPlaylist("testPlaylist").addSong(song);
    	*/
    	
    	
    	/*
    	for (Song song : SearchInterface.getAllSongs()) {
    		System.out.println(song.title);
        	System.out.println(song.artist);
        	System.out.println(song.album);
    	}
    	
    	for (Song song : SearchInterface.search("MF")) {
    		System.out.println(song.title);
        	System.out.println(song.artist);
        	System.out.println(song.album);
    	}
    	*/
    	
    	
    	
    	gui = new BrazilBeatsUI();
    	gui.initializeComponents();
    }
}


