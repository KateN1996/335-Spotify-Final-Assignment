import java.io.File;
import java.util.ArrayList;



/**
 * 
 * 
 * @author Ryan Pecha
 */
public class PlaylistManager {
	
	// Manager fields
	private ArrayList<Playlist> playlists;
	private SearchResults searchResults;
	
	
	
	/*
	 * 
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
			if (playlistFileName.equals(searchResults.title)) {
				continue;
			}
			createPlaylist(playlistFileName);
		}
	}
	
	
	
	/*
	 * 
	 */
	public SearchResults getSearchResults() {
		return this.searchResults;
	}
	
	
	
	/*
	 * 
	 */
	public ArrayList<Playlist> getPlaylists() {
		return this.playlists;
	}
	
	
	
	/*
	 * 
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
	 * 
	 */
	public void createPlaylist(String title) {
		if (playlistExists(title)) {
			return;
		}
		playlists.add(new Playlist(title));
	}
	
	
	
	/*
	 * 
	 */
	public void deletePlaylist(Playlist playlist) {
		playlist.deletePlaylist();
		playlists.remove(playlist);
	}
	
	
	
	/*
	 * 
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


