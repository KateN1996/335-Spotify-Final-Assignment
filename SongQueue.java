import java.util.ArrayList;
import java.util.Collections;



/*
 * 
 */
public class SongQueue {
	
	//
	private Playlist currentPlaylist;
	private ArrayList<Song> userQueue;
	
	// shuffle state for the current playList
	private boolean shuffle;

	
	
	/*
	 * 
	 */
	public SongQueue(boolean shuffleState) {
		currentPlaylist = null;
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
		if (this.userQueue.size() > 0) {
			return this.userQueue.remove(0);
		}
		
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
