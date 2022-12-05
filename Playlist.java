import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Playlist {

	public final String title;
	private String playlistPath;
	private ArrayList<Song> songs;
	
	
	
	public Playlist(String playlistTitle) {
		title = playlistTitle;
		songs = new ArrayList<Song>();
		playlistPath = "./playlists" + playlistTitle;
		
		File playListFolder = new File(playlistPath);
		File[] playlistFiles = playListFolder.listFiles();
		
		// populating available song list
		for (File file : playlistFiles){
			String playlistFileName = file.getName().replace(".txt", "");
			
		}
		
		//SearchInterface.getSong(playlistTitle);
	}
	
	
	
	public ArrayList<Song> getSongs(){
		return songs;
	}
	
	
	
	public void addSong(Song song) {
		if (!songs.contains(song)) {
			songs.add(song);
		}
	}
	
	
	
	public void removeSong(Song song) {
		if (songs.contains(song)) {
			songs.remove(song);
		}
	}
	
}
