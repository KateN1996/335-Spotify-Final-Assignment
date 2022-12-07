import java.io.File;
import java.util.ArrayList;



/**
 * Global playlist manager for a given spotify instance
 * 
 * @author Ryan Pecha
 */
public class PlaylistManager {
	
	// Manager fields
	private ArrayList<Playlist> playlists;
	private SearchResults searchResults;
	
	
	
	/*
	 * Constructor
	 */
	public PlaylistManager() {
		// initializing playlist array
		playlists = new ArrayList<Playlist>();
		searchResults = new SearchResults();
		
		// getting existing playlist files
		String playlistsPath = "./playlists";
		File playListFolder = new File(playlistsPath);
		File[] playlistFiles = playListFolder.listFiles();
		
		// populating available song list
		for (File file : playlistFiles){
			String playlistFileName = file.getName().replace(".txt", "");
			// ignoring searchResults playlist
			if (playlistFileName.equals(searchResults.title)) {
				continue;
			}
			// creating a playlist in memory for each one in the file system
			createPlaylist(playlistFileName);
		}
	}
	
	
	
	/*
	 * returns the special searchResults PlayList
	 */
	public SearchResults getSearchResults() {
		return this.searchResults;
	}
	
	
	
	/*
	 * returns all playlists excluding searchResults playlist
	 */
	public ArrayList<Playlist> getPlaylists() {
		return this.playlists;
	}
	
	
	
	/*
	 * gets a playlist by name.
	 * null if nonexistent
	 */
	public Playlist getPlaylist(String title) {
		for (Playlist playlist : playlists) {
			if (playlist.title.equals(title)) {
				return playlist;
			}
		}
		return null;
	}
	
	
	
	/*
	 * creates a playlist by name.
	 */
	public void createPlaylist(String title) {
		if (playlistExists(title)) {
			return;
		}
		playlists.add(new Playlist(title));
	}
	
	
	
	/*
	 * deletes a playlist from memory and filespace
	 */
	public void deletePlaylist(Playlist playlist) {
		playlist.deletePlaylist();
		playlists.remove(playlist);
	}
	
	
	
	/*
	 * returns true if playlist is existent in memory
	 * or is the searchResults playlist.
	 * else false
	 */
	private boolean playlistExists(String title) {
		if (title.equals(searchResults.title)) {
			return true;
		}
		for (Playlist playlist : playlists) {
			if (playlist.title.equals(title)) {
				return true;
			}
		}
		return false;
	}
	
}


