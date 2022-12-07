import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;



/**
 * Single playlist instance based around list of songs.
 * Handled by PlaylistManager.
 * NOTE - this requires SearchInterface to be initialized
 * 
 * @author Kate Nixon
 */
public class Playlist {

	// Playlist attributes
	public final String title;
	private String playlistPath;
	private ArrayList<Song> songs;
	
	/**
	 * Contstructor
	 * NOTE - this requires SearchInterface to be initialized
	 * @param String playlistTitle, title of playlist
	 */
	public Playlist(String playlistTitle) {
		
		// setting playlist fields
		title = playlistTitle;
		playlistPath = "./playlists/" + playlistTitle + ".txt";
		
		// initializing song array
		songs = new ArrayList<Song>();
		
		// checking for playlist file, and creating new if nonexistent
		File file = new File(playlistPath);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		// gettings songs from playlist file via SearchInterface song instances
		try {
			Scanner scanner = new Scanner(file);
			while (scanner.hasNextLine()) {
				String songTitle = scanner.nextLine();
				Song song = SearchInterface.getSong(songTitle);
				if (song != null) {
					songs.add(song);
				}
			}
			scanner.close();
		}
		catch (FileNotFoundException e) {
			// missing file
			e.printStackTrace();
		}
		
		// updating file system
		updateFile();
	}
	
	/**
	 * returns all songs in this playlist
	 */
	public ArrayList<Song> getSongs(){
		return songs;
	}
	
	/**
	 * adds a song to this playlist and updates file system 
	 * @param Song song, song to be added
	 */
	public void addSong(Song song) {
		if (!songs.contains(song)) {
			songs.add(song);
			updateFile();
		}
	}
	
	/**
	 * removes a song from this playlist and updates file system 
	 */
	public void removeSong(Song song) {
		if (songs.contains(song)) {
			songs.remove(song);
			updateFile();
		}
	}
	
	/**
	 * rewrites the playlist system file to match this objects current songs
	 */
	private void updateFile() {
		try {
            		FileWriter writerObj = new FileWriter(playlistPath, false);
            		for (Song song : songs) {
            			writerObj.write(song.title + '\n');
            		}
            		writerObj.close();
        	} catch (IOException e) {
            		e.printStackTrace();
        	}
	}
	
	
	/**
	 * removes this playlist from the file system
	 */
	public void deletePlaylist() {
		File file = new File(playlistPath);
		if (file.exists()) {
			file.delete();
		}
	}
	
	
	
	/**
	 * returns the quantity of songs in this playlist
	 */
	public Integer getSize() {
		return this.songs.size();
	}
	
	
	
	/**
	 * returns the song at the given index in this playlist
	 */
	public Song getSongAtIndex(Integer index) {
		return this.songs.get(index);
	}
	
	
	
	/**
	 * returns the index of the given song in this playlist.
	 */
	public Integer getIndexOfSong(Song song) {
		Integer index = 0;
		for (Song plSong : songs) {
			if (plSong == song) {
				return index;
			}
			index += 1;
		}
		return null;
	}
	
	/**
	 * returns all songs sorted by title
	 */
	public ArrayList<Song> getSongsSortedByTitle(){
		ArrayList<Song> sortedSongs = new ArrayList<Song>(this.getSongs());		
		Collections.sort(sortedSongs, (o1, o2) -> (o1.getTitle().compareTo(o2.getTitle())));
		return sortedSongs;
	}
	
	/**
	 * returns all songs sorted by album
	 */
	public ArrayList<Song> getSongsSortedByAlbum(){
		ArrayList<Song> sortedSongs = new ArrayList<Song>(this.getSongs());		
		Collections.sort(sortedSongs, (o1, o2) -> (o1.getAlbum().compareTo(o2.getAlbum())));
		return sortedSongs;
	}
	
	/**
	 * returns all songs sorted by artist
	 */
	public ArrayList<Song> getSongsSortedByArtist(){
		ArrayList<Song> sortedSongs = new ArrayList<Song>(this.getSongs());		
		Collections.sort(sortedSongs, (o1, o2) -> (o1.getArtist().compareTo(o2.getArtist())));
		return sortedSongs;
	}
	
	/**
	 * sets the song array field of this playlist
	 */
	public void setSongs(ArrayList<Song> songs) {
		this.songs.clear();
		this.songs = songs;
		this.updateFile();
	}
}
