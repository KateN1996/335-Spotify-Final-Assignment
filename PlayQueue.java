import java.util.ArrayList;

public class PlayQueue {

	private Playlist currentPlaylist;
	private Integer currentPosition;
	
	private ArrayList<Song> history;
	private ArrayList<Song> future;
	
	public PlayQueue() {
		currentPlaylist = null;
		currentPosition = null;
		
		history = new ArrayList<Song>();
		future = new ArrayList<Song>();
	}
	
	public void setPositionInPlaylist(Playlist playlist, Song song) {
		currentPlaylist = playlist;
		currentPosition = playlist.getIndexOfSong(song);
		
	}
	
	public Song getNextSong() {
		
	}
	
	
	
}
