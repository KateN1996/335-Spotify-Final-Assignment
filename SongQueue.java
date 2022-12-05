import java.util.ArrayList;
import java.util.Collections;



/*
 * 
 */
public class SongQueue {
	
	//
	private Playlist currentPlaylist;
	private Integer currentPlaylistIndex;
	private ArrayList<Song> userQueue;
	
	// shuffle state for the current playList
	private boolean shuffle;

	
	
	/*
	 * 
	 */
	public SongQueue(boolean shuffleState) {
		currentPlaylist = null;
		currentPlaylistIndex = 0;
		userQueue = new ArrayList<Song>();
		shuffle = shuffleState;
	}
	
	
	
	/*
	 * 
	 */
	public void setShuffleState(boolean shuffleState) {
		this.shuffle = shuffleState;
	}
	
	
	
	/*
	 * 
	 */
	public Song dequeue() {
		
		// user queue
		if (this.userQueue.size() > 0) {
			Song song = this.userQueue.remove(0);
			return song;
		}
		
		// current playlist
		if (this.currentPlaylist != null) {
			if (this.currentPlaylist.getSize() == 0) {
				return null;
			}
			
			if (this.shuffle) {
				currentPlaylistIndex = Random
			}
			
			
			
			else if (currentPlaylistIndex >= this.currentPlaylist.getSize()) {
				currentPlaylistIndex = 0;
			}
			else {
				currentPlaylistIndex += 1;
			}
			
			return this.currentPlaylist.getSongAtIndex(currentPlaylistIndex);
		}
		
		// no user queue or playlist selected
		return null;
	}
	
	
	
	/*
	 * 
	 */
	public void addSong(Song song) {
		this.userQueue.add(song);
	}
	
	
	
	/*
	 * 
	 */
	public void setCurrentPlaylist(Playlist playlist) {
		this.currentPlaylist = playlist;
	}
	
}
