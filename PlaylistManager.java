import java.io.File;
import java.util.ArrayList;

/*
 * 
 */
public class PlaylistManager {
	
	//
	private ArrayList<Playlist> playlists;
	private Library library;
	
	
	/*
	 * 
	 */
	public PlaylistManager() {
		// initializing playlist array
		playlists = new ArrayList<Playlist>();
		
		// getting existing playlist files
		String playlistsPath = "./playlists";
		File playListFolder = new File(playlistsPath);
		File[] playlistFiles = playListFolder.listFiles();
		
		// populating available song list
		for (File file : playlistFiles){
			String playlistFileName = file.getName().replace(".txt", "");
			playlists.add(new Playlist(playlistFileName));
		}
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
		playlists.add(new Playlist(title));
	}
	
	
	
	/*
	 * 
	 */
	public void deletePlaylist(Playlist playlist) {
		playlist.deletePlaylist();
		playlists.remove(playlist);
	}
	
}


