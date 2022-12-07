

/**
 * Implementation of a song queue to keep track of the 
 * playlist order. A wrapper for an index of a playlist.
 * 
 * @author Kevin Nisterenko 
 */
public class SongQueue {
	
	// current state fields
	private Playlist currentPlaylist;
	private Integer currentPlaylistIndex;
	private Song currSong; 

	
	
	/**
	 * Initializes a songQueue, the current playlist is 
	 * set later.
	 */
	public SongQueue() {
		currentPlaylist = null;
		currentPlaylistIndex = 0;
		currSong = null;
	}
	
	
	
	/**
	 * Dequeue method, returns the song that was "popped" from 
	 * the queue (i.e next song).
	 * 
	 * @return currSong, Song object representing next song
	 */
	public Song dequeue() {
		
		// if we still have not set the playlist, ignore this
		// call
		if (currentPlaylist == null) {
			return null;
		}
		
		// return currSong if we are at the end/new playlist
		if (currentPlaylist.getSize() <= 0) {
			return currSong;
		}
		
		
		// increment index
		this.currentPlaylistIndex += 1;
		
		// reset queue to start/wrap around
		if (currentPlaylistIndex >= currentPlaylist.getSize()) {
			// hit end of playlist
			this.currentPlaylistIndex = 0;
		}
		
		// returns the actual next song based on index
		currSong = this.currentPlaylist.getSongAtIndex(currentPlaylistIndex);
		
		System.out.println(this.currSong.title);
		
		return currSong;
	}
	
	
	
	/**
	 * Sets the current song and playlist, immediately after this, also make sure
	 * to set current playlist (it should have currSong in it), then 
	 * that method by itself should set the queue
	 * 
	 * @param song. Song object representing the current starting song
	 * @param playlist, Playlist that the queue will start playing
	 */
	public void setCurrentPlaylist(Playlist playlist, Song song) {
		this.currentPlaylist = playlist;
		this.currentPlaylistIndex = playlist.getIndexOfSong(song);
	}
	
}


