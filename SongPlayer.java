import java.util.ArrayList;

public class SongPlayer {

	private Song currentSong;
	private Integer currentTime;
	private ArrayList<Song> queue;
	
	public SongPlayer() {
		
	}
	
	public void playSong(Song song) {
		song.play();
		
	}
	
	public void setSongTIme(Integer time) {
		
	}
	
}
