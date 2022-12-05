import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;



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
			
			// random index in current playlist
			if (this.shuffle) {
				currentPlaylistIndex = new Random().nextInt(this.currentPlaylist.getSize());
			}
			
			// next index in playlist
			else if (currentPlaylistIndex >= this.currentPlaylist.getSize() - 1) {
				currentPlaylistIndex = 0;
			}
			else {
				currentPlaylistIndex += 1;
			}
			
			// returning song at new index
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
