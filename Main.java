import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;


/*
 * 
 */
public class Main {  
	public static BrazilBeatsUI gui;
    public static void main(String[] args) {  
    	
    	new SearchInterface();
    	
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
    	
    	PlaylistManager pm = new PlaylistManager();
    	pm.createPlaylist("testPlaylist");
    	pm.getPlaylist("testPlaylist").addSong(song);
    	
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
    	
    	
    	
    	
    	
    	//MusicPlayer spotify = new MusicPlayer();
    	
    	gui = new BrazilBeatsUI();
    	gui.initializeComponents();
    }  
}  
