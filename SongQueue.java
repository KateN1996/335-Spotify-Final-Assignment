import java.util.ArrayList;
import java.util.Random;



/*
 * 
 */
public class SongQueue {
	
	//
	private Playlist currentPlaylist;
	private Integer currentPlaylistIndex;
	private ArrayList<Song> userQueue;
	private ArrayList<Song> historyQueue;
	private Song lastPolledSong;
	private Song currSong; 
	
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
		lastPolledSong = null;
		currSong = null;
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
			if (lastPolledSong != null) {				
				this.historyQueue.add(0, lastPolledSong);
				lastPolledSong = song;
			}
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
			Song song = this.currentPlaylist.getSongAtIndex(currentPlaylistIndex);
			lastPolledSong = song;
			return song;
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
		setQueue();
	}
	
	
	
	/*
	 * 
	 */
	public Song getPreviousSong() {
		if (this.historyQueue.size() == 0) {
			return null;
		}
		if (this.lastPolledSong != null) {			
			this.addSong(lastPolledSong);
			lastPolledSong = this.historyQueue.get(0);
		}
		return this.historyQueue.get(0);
	}
	
	/**
	 * Assumes that the playlist is already set
	 * 
	 * @param song
	 */
	public void setQueue() {
		int idx = this.currentPlaylist.getIndexOfSong(currSong);
		ArrayList<Song> playlist = this.currentPlaylist.getSongs();
		int qIdx = 0;
		this.userQueue.clear();
		// build the queue starting from the current song
		for (int i = idx; i < this.currentPlaylist.getSize(); i++) {
			this.userQueue.set(qIdx, playlist.get(i));
			qIdx++;
		}
		currentPlaylistIndex = idx;
	}
	
	/**
	 * Sets the current song, immediately after this, also make sure
	 * to set current playlist (it should have currSong in it), then 
	 * that method by itself should set the queue
	 * 
	 * @param song
	 */
	public void setCurrSong(Song song) {
		currSong = song;
	}
}


