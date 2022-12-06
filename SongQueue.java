import java.util.ArrayList;
import java.util.Random;



/**
 * 
 * @author Kevin
 */
public class SongQueue {
	
	// current state fields
	private Playlist currentPlaylist;
	private Integer currentPlaylistIndex;
	//private ArrayList<Song> userQueue;
	//private Song currSong; 

	
	
	/*
	 * 
	 */
	public SongQueue() {
		currentPlaylist = null;
		currentPlaylistIndex = 0;
		//userQueue = new ArrayList<Song>();
		//currSong = null;
	}
	
	
	
	/*
	 * 
	 */
	public Song dequeue() {
		
		if (currentPlaylist.getSize() <= 0) {
			return null;
		}
		
		this.currentPlaylistIndex += 1;
		
		if (currentPlaylistIndex >= currentPlaylist.getSize()) {
			// hit end of playlist
			this.currentPlaylistIndex = 0;
		}
		
		return this.currentPlaylist.getSongAtIndex(currentPlaylistIndex);
		
		
		/*
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
			
			// next index in playlist
			if (currentPlaylistIndex >= this.currentPlaylist.getSize() - 1) {
				currentPlaylistIndex = 0;
			}
			else {
				currentPlaylistIndex += 1;
			}
			
			// returning song at new index
			Song song = this.currentPlaylist.getSongAtIndex(currentPlaylistIndex);
			return song;
		}
		
		// no user queue or playlist selected
		return null;
		*/
	}
	
	
	
	/**
	 * Sets the current song and playlist, immediately after this, also make sure
	 * to set current playlist (it should have currSong in it), then 
	 * that method by itself should set the queue
	 * 
	 * @param song
	 */
	public void setCurrentPlaylist(Playlist playlist, Song song) {
		this.currentPlaylist = playlist;
		this.currentPlaylistIndex = playlist.getIndexOfSong(song);
		//this.currSong = song;
		//setQueue();
	}
	
	
	
	/**
	 * Assumes that the playlist is already set
	 * 
	 * @param song
	 */
	public void setQueue() {
		/*
		int idx = this.currentPlaylist.getIndexOfSong(currSong);
		ArrayList<Song> playlist = this.currentPlaylist.getSongs();
		this.userQueue.clear();
		// build the queue starting from the current song
		
		for (int i = idx; i < this.currentPlaylist.getSize(); i++) {
			Song song = playlist.get(i);
			this.userQueue.add(song);
		}
		currentPlaylistIndex = idx;
		*/
	}
	
}


